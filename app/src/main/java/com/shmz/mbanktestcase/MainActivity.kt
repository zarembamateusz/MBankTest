package com.shmz.mbanktestcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.shmz.feature_currency_list.CurrencyListScreen
import com.shmz.core_ui.theme.MBankTestCaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MBankTestCaseTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
