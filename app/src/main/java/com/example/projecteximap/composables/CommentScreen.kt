package com.example.projecteximap.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.projecteximap.R
import com.example.projecteximap.viewmodel.FeedsDetailsViewModel

@Composable
fun CommentScreen(viewModel: FeedsDetailsViewModel, navHostController: NavHostController) {
    val userPost = viewModel.commentsItem.collectAsState().value

    Column {
        NavigateBack(
            backNavigationIcon = R.drawable.navigate_back_icon,
            navHostController
        )

        if (userPost != null) {
            ProfileDetails(
                user = userPost,
                modifier = Modifier
                    .padding(10.dp, 10.dp)
            )
        }

        if (userPost != null) {
            CaptionWithPostDetails(
                userPost
            )
        }

        CommentInfo(
            commentNumber = userPost,
            modifier = Modifier
                .padding(10.dp, 10.dp)
        )

        LazyColumn() {
            items(10) {
                Column {
                    if (userPost != null) {
                        CommentUserDetails(
                            user = userPost,
                            userCUD = userPost,
                            modifier = Modifier
                        )
                    }
                    UserComment(
                        modifier = Modifier
                    )
                    OptionLike(
                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                    )
                    DividerLine()
                }
            }
        }
    }
}