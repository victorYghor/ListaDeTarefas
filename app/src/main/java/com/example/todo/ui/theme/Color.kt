package com.example.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkPrimary = Color(0xFF313131)
val DarkSecondary = Color(0xFF3a0ca3)
//light colors
val LightPrimary = Color(0xFFbde0fe)
val LightSecondary = Color(0xFFA0B4E8)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val PurplePastel = Color(0xFFcdb4db)
val PurplePastelContrast = Color(0xFF8e54b0)
val Purple200 = Color(0xFFb794f6)


val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9c9c)
val ElevationGray = Color(0xFF555555)

val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val HighPriorityColor = Color(0xFFFF4646)
val MediumPriorityColor = Color(0xFFFFC114)
val NonePriorityColor = Color(0xFFFFFFFF)

val splashScreenBackground: Color
    @Composable
    get() = if (isInDarkMode) DarkGray else LightGray
val isInDarkMode
    @Composable
    get() = isSystemInDarkTheme()
val dialogTextColor: Color
    @Composable
    get() = if (isInDarkMode) ElevationGray else MediumGray
val iconColors: Color
    @Composable
    get() = if (isInDarkMode) LightGray else DarkGray
val taskItemBackgroundColor: Color
    @Composable
    get() = if (isInDarkMode) ElevationGray else Color.White
val textColor: Color
    @Composable
    get() = if (isInDarkMode) Color.White else Color.Black
val fabBackgroundColor: Color
    @Composable
    get() = if (isInDarkMode) DarkSecondary else LightSecondary
val topAppBarColor: Color
    @Composable
    get() = if (isInDarkMode) DarkSecondary else LightSecondary
val secondaryColor: Color
    @Composable
    get() = if (isInDarkMode) ElevationGray else LightPrimary

