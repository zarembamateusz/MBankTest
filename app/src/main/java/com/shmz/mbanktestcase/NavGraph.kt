package com.shmz.mbanktestcase

import com.shmz.feature_currency_list.CurrencyListScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.CURRENCY_LIST.route
    ) {
        composable(Destination.CURRENCY_LIST.route) {
            CurrencyListScreen(
//                onNavigateToDetails = { currencyId ->
//                    navController.navigate(
//                        Destination.CURRENCY_DETAILS.routeWithArguments(
//                            Destination.Argument.CURRENCY_ID.value,
//                            currencyId.toString()
//                        )
//                    )
//                },
            )
        }
//        composable(Destination.CURRENCY_DETAILS.route) {
//            val currencyId =
//                it.arguments?.getString(Destination.Argument.CURRENCY_ID.value)
//                    ?.removePrefix("{")
//                    ?.removeSuffix("}")?.toInt()
//            if (currencyId == null) {
//                navController.navigateUp()
//            } else {
//                CurrenecyDetailsScreen(currencyId, { navController.navigateUp() })
//            }
//        }
    }
}

enum class Destination(val route: String) {
    CURRENCY_LIST("currency_list"),
    CURRENCY_DETAILS("currency_details/{${Argument.CURRENCY_ID}}");

    override fun toString(): String = route

    fun routeWithArguments(argumentName: String, value: String): String {
        return this.route.replace(argumentName, value)

    }

    enum class Argument(internal val value: String) {
        CURRENCY_ID("currency_id");

        val routeValue: String = value.asRouteValue()

        override fun toString(): String = value

        @Suppress("MemberVisibilityCanBePrivate")
        fun String.asRouteValue(): String = "{$this}"
    }
}