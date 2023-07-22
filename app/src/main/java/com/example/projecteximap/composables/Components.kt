package com.example.projecteximap.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.eximap_nisarg.navigation.RouteClass
import com.example.projecteximap.R
import com.example.projecteximap.model.PostDetails
import com.example.projecteximap.ui.theme.darkBlue
import com.example.projecteximap.ui.theme.dividerColor
import com.example.projecteximap.ui.theme.lightBlue
import com.example.projecteximap.ui.theme.lightGray
import com.example.projecteximap.viewmodel.FeedsDetailsViewModel
import java.util.Locale

lateinit var viewModelGlobalScope: FeedsDetailsViewModel

val poppinsFamily = FontFamily(
    Font(R.font.poppins_regular)
)

@Composable
fun TabScreen(feedsDetailsViewModel: FeedsDetailsViewModel, navHostController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }

    viewModelGlobalScope = feedsDetailsViewModel

    val tabs = listOf("charcha", "bazaar", "profile")

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TabRow(
            contentColor = Color.Blue,
            containerColor = Color.White,
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color.Blue,
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(title)
                    },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                    }
                )
            }
        }

        when (tabIndex) {
            0 -> CharchaScreen(
                navHostController = navHostController,
                viewModel = feedsDetailsViewModel
            )

            1 -> BazaarScreen()
            2 -> ProfileScreen()
        }
    }
}


@Composable
fun ProfileDetails(
    user: PostDetails,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
        ) {
            ProfilePhoto(user = user)

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 8.dp, 0.dp)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = user.postAuthorName,
                    style = TextStyle(
                        Color.Black,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFamily
                    ),
                )
                Text(
                    text = "2 hours ago",
                    style = TextStyle(
                        lightGray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFamily
                    )
                )
            }

            if (user.tags.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(lightBlue)

                ) {
                    Text(
                        text = user.tags.toUpperCase(Locale.ROOT),
                        style = TextStyle(
                            darkBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFamily
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.more_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(20.dp)
        )
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfilePhoto(user: PostDetails) {

    if (user.postAuthorImage.isEmpty()) {
        Image(
            painter = painterResource(id = R.drawable.profile_pic_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .aspectRatio(
                    1f, matchHeightConstraintsFirst = true
                )
                .clip(CircleShape),
            alignment = Alignment.Center
        )
    } else {
        GlideImage(
            model = user.postAuthorImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .aspectRatio(
                    1f, matchHeightConstraintsFirst = true
                )
                .clip(CircleShape),
            alignment = Alignment.Center
        )
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CaptionWithPostDetails(
    user: PostDetails
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val size = user.mediaList.size

    if (user.mediaList.isEmpty()) {
        Text(
            text = user.postTitle,
            style = TextStyle(
                Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                fontFamily = poppinsFamily
            ),
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
    } else {
        if (user.mediaList[0].mimeType == "video/mp4") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(10.dp, 5.dp)
            ) {
                Text(
                    text = user.postTitle,
                    style = TextStyle(
                        Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        fontFamily = poppinsFamily
                    ),
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.padding(5.dp, 5.dp))

                Row(
                    modifier = Modifier
                ) {
                    val exoPlayer = ExoPlayer.Builder(context).build()

                    val mediaItem = MediaItem.fromUri((Uri.parse(user.mediaList[0].url)))

                    exoPlayer.setMediaItem(mediaItem)

                    val playerView = PlayerView(context)
                    playerView.player = exoPlayer

                    DisposableEffect(
                        AndroidView(
                            factory = {
                                playerView
                            }
                        )
                    ) {
                        exoPlayer.prepare()
                        exoPlayer.playWhenReady = true

                        onDispose {
                            exoPlayer.release()
                        }
                    }
                }
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(10.dp, 5.dp)
            ) {
                if (size > 1) {
                    Text(
                        text = user.postTitle,
                        style = TextStyle(
                            Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            fontFamily = poppinsFamily
                        ),
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.padding(5.dp, 5.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                    ) {
                        for (i in 0 until size) {
                            GlideImage(
                                model = user.mediaList[i].url,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(0.dp, 0.dp, 5.dp, 0.dp)
                                    .clip(shape = RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                } else {
                    Text(
                        text = user.postTitle,
                        style = TextStyle(
                            Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            fontFamily = poppinsFamily
                        ),
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.padding(5.dp, 5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        GlideImage(
                            model = user.mediaList[0].url,
                            contentDescription = null,
                            modifier = Modifier
                                .wrapContentHeight()
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LikeCommentShare(
    user: PostDetails,
    navHostController: NavHostController,
    modifier: Modifier
) {
    var likeCheck by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Image(
                modifier = Modifier
                    .clickable {
                        likeCheck = !likeCheck
                    }
                    .width(18.dp)
                    .height(18.dp)
                    .aspectRatio(1f),
                painter = if (likeCheck) {
                    painterResource(id = R.drawable.blue_heart_icon)
                } else {
                    painterResource(id = R.drawable.like_icon)
                },
                contentDescription = null,
            )

            Text(
                text = user.numLikes.toString(),
                style = TextStyle(fontSize = 12.sp, fontFamily = poppinsFamily),
                modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
            )

            Text(
                text = "likes",
                style = TextStyle(fontSize = 12.sp, fontFamily = poppinsFamily),
                modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clickable {
                    viewModelGlobalScope.commentsItem.value = user
                    navHostController.navigate(RouteClass.CommentScreen.route)
                },
            horizontalArrangement = Arrangement.Center,

            ) {
            Image(
                painter = painterResource(id = R.drawable.comment_icon),
                contentDescription = null,
                modifier = Modifier
            )

            Text(
                text = user.numReplies.toString(),
                style = TextStyle(fontSize = 12.sp, fontFamily = poppinsFamily),
                modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
            )

            Text(
                text = "comments",
                style = TextStyle(fontSize = 12.sp, fontFamily = poppinsFamily),
                modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.share_icon),
                contentDescription = null,
                modifier = Modifier
            )

            Text(
                text = "Share",
                style = TextStyle(fontSize = 12.sp, fontFamily = poppinsFamily),
                modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
fun DividerLine() {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 3.dp,
        color = dividerColor
    )
}


@Composable
fun NavigateBack(
    backNavigationIcon: Int,
    navHostController: NavHostController
) {
    Row(
        modifier = Modifier
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = backNavigationIcon),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    navHostController.popBackStack()
                }
        )

        Text(
            text = "Comments",
            style = TextStyle(color = Color.Black, fontSize = 18.sp, fontFamily = poppinsFamily),
            modifier = Modifier
                .padding(10.dp, 0.dp)
        )
    }
}


@Composable
fun CommentInfo(
    commentNumber: PostDetails?,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
    ) {
        Row {
            if (commentNumber != null) {
                Text(
                    text = commentNumber.numReplies.toString(),
                    style = TextStyle(color = Color.Black, fontSize = 15.sp, fontFamily = poppinsFamily)
                )
            }

            Text(
                text = "Comments",
                style = TextStyle(fontSize = 15.sp, fontFamily = poppinsFamily),
                modifier = Modifier
                    .padding(3.dp, 0.dp, 0.dp, 0.dp)
            )
        }

        Row {
            Icon(
                painter = painterResource(id = R.drawable.recent_icon),
                contentDescription = null,
            )

            Text(
                text = "Recent",
                style = TextStyle(Color.Blue, fontSize = 13.sp, fontFamily = poppinsFamily),
                modifier = Modifier
                    .padding(5.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}


@Composable
fun CommentUserDetails(
    userCUD: PostDetails?,
    user: PostDetails,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(5.dp, 5.dp, 0.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
        ) {
            ProfilePhoto(user = user)

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 8.dp, 0.dp)
                    .fillMaxHeight(),
            ) {
                if (userCUD != null) {
                    Text(
                        text = userCUD.postAuthorName,
                        style = TextStyle(
                            Color.Black,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFamily
                        )
                    )

                    Text(
                        text = "Public",
                        style = TextStyle(
                            lightGray,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsFamily
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.more_down),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
            )
        }
    }
}


@Composable
fun UserComment(modifier: Modifier) {
    Text(
        text = "This is comment place holder",
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp, 0.dp, 0.dp)
    )
}


@Composable
fun OptionLike(modifier: Modifier) {
    var likeCheck by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp, 5.dp, 0.dp, 5.dp)
    ) {
        Image(
            modifier = Modifier
                .clickable {
                    likeCheck = !likeCheck
                }
                .width(17.dp)
                .height(17.dp),
            painter = if (likeCheck) {
                painterResource(id = R.drawable.blue_heart_icon)
            } else {
                painterResource(id = R.drawable.like_icon)
            },
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .padding(7.dp, 0.dp, 0.dp, 1.dp),
            text = "Like",
            style = if (likeCheck) {
                TextStyle(Color.Blue, fontSize = 12.sp, fontFamily = poppinsFamily)
            } else {
                TextStyle(Color.Gray, fontSize = 12.sp, fontFamily = poppinsFamily)
            }
        )
    }
}