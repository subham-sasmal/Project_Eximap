package com.example.projecteximap.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.projecteximap.viewmodel.FeedsDetailsViewModel


@Composable
fun CharchaScreen(navHostController: NavHostController, viewModel: FeedsDetailsViewModel) {

    val pagingData = viewModel.getData.collectAsLazyPagingItems()

    LazyColumn() {
        items(
            count = pagingData.itemCount,
            key = pagingData.itemKey { it.postId },
            contentType = pagingData.itemContentType { "contentType" }
        ) { index ->
            val item = pagingData[index]
            if (item != null) {
                Column {
                    ProfileDetails(
                        item,
                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                    )

                    CaptionWithPostDetails(
                        item
                    )

                    LikeCommentShare(
                        item,
                        navHostController,
                        modifier = Modifier
                            .padding(10.dp, 10.dp, 10.dp, 5.dp)
                    )

                    Spacer(
                        modifier = Modifier.padding(0.dp, 2.dp)
                    )

                    DividerLine()
                }
            }
        }
    }
}
