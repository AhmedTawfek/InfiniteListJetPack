package com.tawfek.infinitelistjetpack.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconWithText(icon : Int, text : String, textSize : TextUnit = 10.sp, iconColor: Color = Color.Gray) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = icon),contentDescription = null, modifier = Modifier.size(18.dp), tint = iconColor)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = textSize, modifier = Modifier.align(Alignment.CenterVertically))
    }
}