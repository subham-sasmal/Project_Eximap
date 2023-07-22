package com.example.eximap_nisarg.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projecteximap.composables.CommentScreen
import com.example.projecteximap.composables.TabScreen
import com.example.projecteximap.viewmodel.FeedsDetailsViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    feedScreenViewModel: FeedsDetailsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = RouteClass.Feed.route
    ) {
        composable(
            route = RouteClass.Feed.route,
//            enterTransition = {slideInHorizontally(animationSpec = tween(500)) },
//            exitTransition = {slideOutHorizontally(animationSpec = tween(500)) },
//            popEnterTransition = { fadeIn(animationSpec = tween(400)) }

        ) {
            TabScreen(feedScreenViewModel, navController)
        }
        composable(
            route = RouteClass.CommentScreen.route
        ) {
            CommentScreen(feedScreenViewModel, navController)
        }
    }
}