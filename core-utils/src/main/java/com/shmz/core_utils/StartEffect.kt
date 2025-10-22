package com.shmz.core_utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun StartEffect(action: () -> Unit) = LaunchedEffect(true) { action() }