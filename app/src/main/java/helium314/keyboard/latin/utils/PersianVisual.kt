// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.utils

/**
 * Converts logical (typed) Persian / Arabic text into "visual" text: letters are replaced with their
 * contextual presentation forms (isolated / final / initial / medial) and the whole string is reversed.
 *
 * This is what some games and engines need, because they draw every string left-to-right and do no
 * shaping at all. Typing "سلام چطورید" results in "ﺪﯾﺭﻮﻄﭼ ﻡﺎﻠﺳ" which looks right when drawn left-to-right.
 *
 * - Lam-alef ligatures are intentionally not produced (lam and alef stay separate glyphs),
 *   so every typed character still maps to exactly one output cluster.
 * - Digits and Latin words keep their internal order, brackets are mirrored, ZWNJ is dropped
 *   (it only affects joining), diacritics stay attached to their letter.
 */
object PersianVisual {
    private const val ISO = 1
    private const val FIN = 2
    private const val INI = 3
    private const val MED = 4
    private const val ZWNJ = 0x200C
    private const val ZWJ = 0x200D

    /** rows: logical code point, isolated, final, initial, medial (0 = form does not exist) */
    private val ROWS = arrayOf(
        intArrayOf(0x0621, 0xFE80, 0, 0, 0),
        intArrayOf(0x0622, 0xFE81, 0xFE82, 0, 0),
        intArrayOf(0x0623, 0xFE83, 0xFE84, 0, 0),
        intArrayOf(0x0624, 0xFE85, 0xFE86, 0, 0),
        intArrayOf(0x0625, 0xFE87, 0xFE88, 0, 0),
        intArrayOf(0x0626, 0xFE89, 0xFE8A, 0xFE8B, 0xFE8C),
        intArrayOf(0x0627, 0xFE8D, 0xFE8E, 0, 0),
        intArrayOf(0x0628, 0xFE8F, 0xFE90, 0xFE91, 0xFE92),
        intArrayOf(0x0629, 0xFE93, 0xFE94, 0, 0),
        intArrayOf(0x062A, 0xFE95, 0xFE96, 0xFE97, 0xFE98),
        intArrayOf(0x062B, 0xFE99, 0xFE9A, 0xFE9B, 0xFE9C),
        intArrayOf(0x062C, 0xFE9D, 0xFE9E, 0xFE9F, 0xFEA0),
        intArrayOf(0x062D, 0xFEA1, 0xFEA2, 0xFEA3, 0xFEA4),
        intArrayOf(0x062E, 0xFEA5, 0xFEA6, 0xFEA7, 0xFEA8),
        intArrayOf(0x062F, 0xFEA9, 0xFEAA, 0, 0),
        intArrayOf(0x0630, 0xFEAB, 0xFEAC, 0, 0),
        intArrayOf(0x0631, 0xFEAD, 0xFEAE, 0, 0),
        intArrayOf(0x0632, 0xFEAF, 0xFEB0, 0, 0),
        intArrayOf(0x0633, 0xFEB1, 0xFEB2, 0xFEB3, 0xFEB4),
        intArrayOf(0x0634, 0xFEB5, 0xFEB6, 0xFEB7, 0xFEB8),
        intArrayOf(0x0635, 0xFEB9, 0xFEBA, 0xFEBB, 0xFEBC),
        intArrayOf(0x0636, 0xFEBD, 0xFEBE, 0xFEBF, 0xFEC0),
        intArrayOf(0x0637, 0xFEC1, 0xFEC2, 0xFEC3, 0xFEC4),
        intArrayOf(0x0638, 0xFEC5, 0xFEC6, 0xFEC7, 0xFEC8),
        intArrayOf(0x0639, 0xFEC9, 0xFECA, 0xFECB, 0xFECC),
        intArrayOf(0x063A, 0xFECD, 0xFECE, 0xFECF, 0xFED0),
        intArrayOf(0x0640, 0x0640, 0x0640, 0x0640, 0x0640), // tatweel
        intArrayOf(0x0641, 0xFED1, 0xFED2, 0xFED3, 0xFED4),
        intArrayOf(0x0642, 0xFED5, 0xFED6, 0xFED7, 0xFED8),
        intArrayOf(0x0643, 0xFED9, 0xFEDA, 0xFEDB, 0xFEDC),
        intArrayOf(0x0644, 0xFEDD, 0xFEDE, 0xFEDF, 0xFEE0),
        intArrayOf(0x0645, 0xFEE1, 0xFEE2, 0xFEE3, 0xFEE4),
        intArrayOf(0x0646, 0xFEE5, 0xFEE6, 0xFEE7, 0xFEE8),
        intArrayOf(0x0647, 0xFEE9, 0xFEEA, 0xFEEB, 0xFEEC),
        intArrayOf(0x0648, 0xFEED, 0xFEEE, 0, 0),
        intArrayOf(0x0649, 0xFEEF, 0xFEF0, 0xFEF3, 0xFEF4), // alef maksura, uses yeh forms when joining forward
        intArrayOf(0x064A, 0xFEF1, 0xFEF2, 0xFEF3, 0xFEF4),
        // Persian / extended letters (Arabic Presentation Forms-A)
        intArrayOf(0x067E, 0xFB56, 0xFB57, 0xFB58, 0xFB59), // پ
        intArrayOf(0x0686, 0xFB7A, 0xFB7B, 0xFB7C, 0xFB7D), // چ
        intArrayOf(0x0698, 0xFB8A, 0xFB8B, 0, 0),           // ژ
        intArrayOf(0x06A4, 0xFB6A, 0xFB6B, 0xFB6C, 0xFB6D), // ڤ
        intArrayOf(0x06A9, 0xFB8E, 0xFB8F, 0xFB90, 0xFB91), // ک
        intArrayOf(0x06AF, 0xFB92, 0xFB93, 0xFB94, 0xFB95), // گ
        intArrayOf(0x06BE, 0xFBAA, 0xFBAB, 0xFBAC, 0xFBAD), // ھ
        intArrayOf(0x06CC, 0xFBFC, 0xFBFD, 0xFBFE, 0xFBFF), // ی
    )

    private val FORMS: Map<Int, IntArray> = HashMap<Int, IntArray>().also { map -> ROWS.forEach { map[it[0]] = it } }

    private val MIRRORED: Map<String, String> = mapOf(
        "(" to ")", ")" to "(", "[" to "]", "]" to "[", "{" to "}", "}" to "{",
        "<" to ">", ">" to "<", "\u00AB" to "\u00BB", "\u00BB" to "\u00AB",
    )

    /** true for letters that start / belong to a text that needs conversion (Persian and Arabic letters) */
    @JvmStatic
    fun isConvertibleLetter(codePoint: Int) = FORMS.containsKey(codePoint)

    @JvmStatic
    fun toVisual(logical: CharSequence): String {
        val text = logical.toString()
        val cps = ArrayList<Int>(text.length)
        var idx = 0
        while (idx < text.length) {
            val cp = text.codePointAt(idx)
            cps.add(cp)
            idx += Character.charCount(cp)
        }
        val clusters = shape(cps)

        // find runs that must keep their own left-to-right order (digits, latin words, 3.5 / 1,000)
        val ltr = BooleanArray(clusters.size)
        for (i in clusters.indices) {
            val c = clusters[i]
            ltr[i] = isLtrCluster(c) || (
                isNumberSeparator(c) && i > 0 && i < clusters.size - 1
                    && isDigitCluster(clusters[i - 1]) && isDigitCluster(clusters[i + 1])
            )
        }
        val units = ArrayList<String>()
        var i = 0
        while (i < clusters.size) {
            if (ltr[i]) {
                val run = StringBuilder()
                while (i < clusters.size && ltr[i]) {
                    run.append(clusters[i])
                    i++
                }
                units.add(run.toString())
            } else {
                units.add(MIRRORED[clusters[i]] ?: clusters[i])
                i++
            }
        }
        val result = StringBuilder()
        for (u in units.indices.reversed()) result.append(units[u])
        return result.toString()
    }

    /** one cluster per typed character; diacritics are appended to the cluster of their letter */
    private fun shape(cps: List<Int>): List<String> {
        val clusters = ArrayList<String>(cps.size)
        for (i in cps.indices) {
            val cp = cps[i]
            if (isMark(cp)) {
                if (clusters.isEmpty()) clusters.add(String(Character.toChars(cp)))
                else clusters[clusters.size - 1] = clusters[clusters.size - 1] + String(Character.toChars(cp))
                continue
            }
            if (cp == ZWNJ || cp == ZWJ) {
                clusters.add("")
                continue
            }
            val row = FORMS[cp]
            if (row == null) {
                clusters.add(String(Character.toChars(cp)))
                continue
            }
            val prev = previousBase(cps, i)
            val next = nextBase(cps, i)
            val left = prev != -1 && joinsNext(prev) && row[FIN] != 0
            val right = next != -1 && row[INI] != 0 && joinsPrevious(next)
            val form = when {
                left && right -> row[MED]
                left -> row[FIN]
                right -> row[INI]
                else -> row[ISO]
            }
            clusters.add(String(Character.toChars(if (form != 0) form else row[ISO])))
        }
        return clusters
    }

    private fun previousBase(cps: List<Int>, index: Int): Int {
        var j = index - 1
        while (j >= 0 && isMark(cps[j])) j--
        return if (j >= 0) cps[j] else -1
    }

    private fun nextBase(cps: List<Int>, index: Int): Int {
        var j = index + 1
        while (j < cps.size && isMark(cps[j])) j++
        return if (j < cps.size) cps[j] else -1
    }

    /** can connect to the following letter */
    private fun joinsNext(cp: Int): Boolean = cp == ZWJ || (FORMS[cp]?.get(INI) ?: 0) != 0

    /** can connect to the preceding letter */
    private fun joinsPrevious(cp: Int): Boolean = cp == ZWJ || (FORMS[cp]?.get(FIN) ?: 0) != 0

    private fun isMark(cp: Int) = cp in 0x064B..0x065F || cp == 0x0670 || cp in 0x06D6..0x06DC
        || cp in 0x06DF..0x06E4 || cp in 0x06E7..0x06E8 || cp in 0x06EA..0x06ED

    private fun isArabicScript(cp: Int) = cp in 0x0600..0x06FF || cp in 0x0750..0x077F
        || cp in 0xFB50..0xFDFF || cp in 0xFE70..0xFEFF

    private fun isDigitCluster(c: String): Boolean {
        if (c.isEmpty()) return false
        val cp = c.codePointAt(0)
        return cp in 0x30..0x39 || cp in 0x0660..0x0669 || cp in 0x06F0..0x06F9
    }

    private fun isLtrCluster(c: String): Boolean {
        if (c.isEmpty()) return false
        if (isDigitCluster(c)) return true
        val cp = c.codePointAt(0)
        return Character.isLetter(cp) && !isArabicScript(cp)
    }

    private fun isNumberSeparator(c: String) = c == "." || c == "," || c == ":" || c == "/" || c == "\u066B" || c == "\u066C"
}
