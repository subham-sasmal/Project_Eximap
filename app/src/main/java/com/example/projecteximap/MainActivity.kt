package com.example.projecteximap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.eximap_nisarg.navigation.SetupNavGraph
import com.example.projecteximap.api.GRPCResponse
import com.example.projecteximap.model.GRPCDatabase
import com.example.projecteximap.repository.Repository
import com.example.projecteximap.ui.theme.ProjectEximapTheme
import com.example.projecteximap.viewmodel.FeedsDetailsViewModel
import com.example.projecteximap.viewmodel.FeedsScreenViewModelFactory
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    private lateinit var feedsScreenViewModelInstance: FeedsDetailsViewModel

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val databaseObject = GRPCDatabase.getDatabase(this)

        feedsScreenViewModelInstance = ViewModelProvider(
            this,
            FeedsScreenViewModelFactory(Repository(GRPCResponse(), databaseObject))
        )[FeedsDetailsViewModel::class.java]

        super.onCreate(savedInstanceState)
        setContent {
            ProjectEximapTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        feedScreenViewModel = feedsScreenViewModelInstance
                    )
                }
            }
        }
    }
}