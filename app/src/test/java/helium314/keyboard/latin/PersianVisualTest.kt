// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin

import helium314.keyboard.latin.utils.PersianVisual
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// all strings are written as unicode escapes, so the test does not depend on the editor or source encoding
class PersianVisualTest {
    @Test fun sentenceFromConverterSite() {
        // "سلام چطورید" -> the output of https://www.timecode.ir/font-converter/
        assertEquals(
            "\uFEAA\uFBFE\uFEAD\uFEEE\uFEC4\uFB7C \uFEE1\uFE8E\uFEE0\uFEB3",
            PersianVisual.toVisual("\u0633\u0644\u0627\u0645 \u0686\u0637\u0648\u0631\u06CC\u062F")
        )
    }

    @Test fun singleWord() {
        assertEquals("\uFEE1\uFE8E\uFEE0\uFEB3", PersianVisual.toVisual("\u0633\u0644\u0627\u0645")) // سلام
        assertEquals("\uFE8F\uFE8E\uFE98\uFB90", PersianVisual.toVisual("\u06A9\u062A\u0627\u0628")) // کتاب
    }

    @Test fun singleLetterIsIsolated() {
        assertEquals("\uFEB1", PersianVisual.toVisual("\u0633"))
    }

    @Test fun digitsKeepTheirOrderAndBracketsAreMirrored() {
        // "من 123 تا (دارم)"
        assertEquals(
            "(\uFEE1\uFEAD\uFE8D\uFEA9) \uFE8E\uFE97 123 \uFEE6\uFEE3",
            PersianVisual.toVisual("\u0645\u0646 123 \u062A\u0627 (\u062F\u0627\u0631\u0645)")
        )
    }

    @Test fun zwnjBreaksJoiningAndIsDropped() {
        // "می‌خواهم"
        assertEquals(
            "\uFEE2\uFEEB\uFE8D\uFEEE\uFEA7\uFBFD\uFEE3",
            PersianVisual.toVisual("\u0645\u06CC\u200C\u062E\u0648\u0627\u0647\u0645")
        )
    }

    @Test fun latinWordKeepsLetterOrder() {
        assertEquals("\uFEE1\uFE8E\uFEE0\uFEB3 hello", PersianVisual.toVisual("hello \u0633\u0644\u0627\u0645"))
    }

    @Test fun plainTextIsOnlyReordered() {
        assertEquals("", PersianVisual.toVisual(""))
        assertEquals("hello", PersianVisual.toVisual("hello"))
    }

    @Test fun convertibleLetters() {
        assertTrue(PersianVisual.isConvertibleLetter('\u067E'.code)) // پ
        assertTrue(PersianVisual.isConvertibleLetter('\u06CC'.code)) // ی
        assertFalse(PersianVisual.isConvertibleLetter('a'.code))
        assertFalse(PersianVisual.isConvertibleLetter('1'.code))
        assertFalse(PersianVisual.isConvertibleLetter(' '.code))
    }
}
