// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.inputlogic

import helium314.keyboard.latin.RichInputConnection
import helium314.keyboard.latin.utils.PersianVisual

/**
 * Keeps track of the text typed while "visual Persian" mode is on.
 *
 * The typed (logical) text is kept here. After every key the whole visual version of it
 * (shaped and reversed, see [PersianVisual]) replaces the previously committed visual version in the
 * editor. This is needed because typing one more letter changes the shape of the previous letter and
 * puts the new letter at the left end of the text.
 *
 * Text that is not part of this run (anything typed before the first Persian letter, or after the
 * cursor was moved elsewhere) is never touched.
 */
class PersianVisualInput {
    private val logical = StringBuilder()
    private var lastVisual = ""

    fun reset() {
        logical.setLength(0)
        lastVisual = ""
    }

    /**
     * Handle a typed code point.
     * [prepare] is run right before the editor is changed, so the caller can end a composing word.
     * @return false if the code point should be handled the normal way
     */
    fun onCodePoint(codePoint: Int, connection: RichInputConnection, prepare: Runnable): Boolean {
        if (connection.hasSelection()) {
            reset()
            return false
        }
        syncWithEditor(connection)
        if (logical.length >= MAX_LOGICAL_LENGTH) reset() // keep per-key work small, older text just stays as it is
        if (logical.isEmpty() && !PersianVisual.isConvertibleLetter(codePoint)) return false
        prepare.run()
        logical.appendCodePoint(codePoint)
        render(connection)
        return true
    }

    /** @return false if backspace should be handled the normal way */
    fun onBackspace(connection: RichInputConnection, prepare: Runnable): Boolean {
        if (connection.hasSelection()) {
            reset()
            return false
        }
        syncWithEditor(connection)
        if (logical.isEmpty()) return false
        prepare.run()
        logical.setLength(logical.length - Character.charCount(logical.codePointBefore(logical.length)))
        render(connection)
        return true
    }

    private fun render(connection: RichInputConnection) {
        val visual = if (logical.isEmpty()) "" else PersianVisual.toVisual(logical)
        connection.beginBatchEdit()
        if (lastVisual.isNotEmpty()) connection.deleteTextBeforeCursor(lastVisual.length)
        if (visual.isNotEmpty()) connection.commitText(visual, 1)
        connection.endBatchEdit()
        lastVisual = visual
    }

    /** forget everything if the text before the cursor is not what we committed last (cursor moved, field changed, ...) */
    private fun syncWithEditor(connection: RichInputConnection) {
        if (lastVisual.isEmpty()) {
            logical.setLength(0)
            return
        }
        val before = connection.getTextBeforeCursor(lastVisual.length, 0) ?: return // unknown: trust our state
        if (before.toString() != lastVisual) reset()
    }

    companion object {
        private const val MAX_LOGICAL_LENGTH = 200
    }
}
