package com.example.eximap_nisarg.navigation

sealed class RouteClass(var route: String){
    object Feed: RouteClass(route = "feed_screen")
    object CommentScreen: RouteClass(route = "comments_screen")
}
