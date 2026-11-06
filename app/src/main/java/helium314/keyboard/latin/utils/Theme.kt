// SPDX-License-Identifier: GPL-3.0-only
package helium314.keyboard.latin.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// "Firouzeh" (Persian turquoise) palette, matches the default keyboard theme
private val firouzehLight = lightColorScheme(
    primary = Color(0xFF0E9F94),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC4EEE9),
    onPrimaryContainer = Color(0xFF00201D),
    secondary = Color(0xFF4A6360),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCCE8E3),
    onSecondaryContainer = Color(0xFF05201D),
    tertiary = Color(0xFF3F6375),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFC3E8FD),
    onTertiaryContainer = Color(0xFF001E2B),
    background = Color(0xFFF3FAF9),
    onBackground = Color(0xFF141D1C),
    surface = Color(0xFFF3FAF9),
    onSurface = Color(0xFF141D1C),
    surfaceVariant = Color(0xFFD9E5E2),
    onSurfaceVariant = Color(0xFF3F4947),
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFEDF5F3),
    surfaceContainer = Color(0xFFE7F0EE),
    surfaceContainerHigh = Color(0xFFE1EAE8),
    surfaceContainerHighest = Color(0xFFDBE4E2),
    outline = Color(0xFF6F7977),
    outlineVariant = Color(0xFFBEC9C6),
)

private val firouzehDark = darkColorScheme(
    primary = Color(0xFF2DD4BF),
    onPrimary = Color(0xFF00382F),
    primaryContainer = Color(0xFF005046),
    onPrimaryContainer = Color(0xFF9CF2E3),
    secondary = Color(0xFFB1CCC7),
    onSecondary = Color(0xFF1C3532),
    secondaryContainer = Color(0xFF334B48),
    onSecondaryContainer = Color(0xFFCCE8E3),
    tertiary = Color(0xFFA7CBE0),
    onTertiary = Color(0xFF0C3547),
    tertiaryContainer = Color(0xFF264C5E),
    onTertiaryContainer = Color(0xFFC3E8FD),
    background = Color(0xFF0E1514),
    onBackground = Color(0xFFDDE5E3),
    surface = Color(0xFF0E1514),
    onSurface = Color(0xFFDDE5E3),
    surfaceVariant = Color(0xFF3F4947),
    onSurfaceVariant = Color(0xFFBEC9C6),
    surfaceContainerLowest = Color(0xFF090F0F),
    surfaceContainerLow = Color(0xFF161D1C),
    surfaceContainer = Color(0xFF1A2120),
    surfaceContainerHigh = Color(0xFF242B2A),
    surfaceContainerHighest = Color(0xFF2F3635),
    outline = Color(0xFF899390),
    outlineVariant = Color(0xFF3F4947),
)

@Composable
fun Theme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val material3 = Typography()
    MaterialTheme(
        colorScheme = if (dark) firouzehDark else firouzehLight,
        typography = Typography(
            headlineSmall = material3.headlineSmall.copy(fontWeight = FontWeight.Bold),
            titleLarge = material3.titleLarge.copy(fontWeight = FontWeight.Bold),
            titleMedium = material3.titleMedium.copy(fontWeight = FontWeight.Bold),
            titleSmall = material3.titleSmall.copy(fontWeight = FontWeight.Bold)
        ),
        shapes = Shapes(
            extraSmall = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(12.dp),
            medium = RoundedCornerShape(18.dp),
            large = RoundedCornerShape(26.dp),
            extraLarge = RoundedCornerShape(34.dp)
        ),
        content = content
    )
}

const val previewDark = true
