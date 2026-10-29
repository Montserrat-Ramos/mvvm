package com.example.mvvm.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvm.ui.theme.AliveGreen
import com.example.mvvm.ui.theme.DeadRed
import com.example.mvvm.ui.theme.UnknownGray

@Composable
fun CharacterStatusBadge(
    status: String
){
    val statusColor = getStatusColor(status)

    Surface(shape = RoundedCornerShape(50),
        color = statusColor.copy(alpha = 0.15f)
    ) {
        Row(modifier = Modifier.padding(
            horizontal = 10.dp,
            vertical = 6.dp
        ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "●",
                fontSize = 10.sp,
                color = statusColor
            )
            Text(
                text = status,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = statusColor
            )
        }
    }
}

private fun getStatusColor(
    status: String
): Color {

    return when (status.lowercase()) {

        "alive" -> AliveGreen

        "dead" -> DeadRed

        else -> UnknownGray
    }
}