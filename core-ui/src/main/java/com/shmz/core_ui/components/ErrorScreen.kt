package com.shmz.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shmz.core_ui.R
import com.shmz.core_utils.asString

@Composable
fun ErrorScreen(
    errorMessage: String = "",
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
            contentScale = ContentScale.None,
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(height = 40.dp))
        Text(
            text = errorMessage,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.height(height = 20.dp))
        if (onRetry != null) {
            Button(
                modifier = Modifier.padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = onRetry
            ) {
                Text(
                    text = R.string.try_again.asString(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}