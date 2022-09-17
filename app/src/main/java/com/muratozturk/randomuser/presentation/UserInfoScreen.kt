package com.muratozturk.randomuser.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skgmn.composetooltip.AnchorEdge
import com.github.skgmn.composetooltip.Tooltip
import com.muratozturk.randomuser.R
import com.muratozturk.randomuser.common.*
import com.muratozturk.randomuser.common.components.AnimatedButton
import com.muratozturk.randomuser.common.components.ErrorPage
import com.muratozturk.randomuser.ui.theme.RandomUserTheme
import com.mxalbert.sharedelements.*
import com.skydoves.landscapist.glide.GlideImage
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val selectedUser = viewModel.selectedUser.observeAsState("null")
            RandomUserTheme {
                SharedElementsRoot {


                    BackHandler(enabled = selectedUser.value != "null") {
                        changeUser("null")
                    }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.secondary
                    ) {

                        Crossfade(
                            targetState = selectedUser.value,
                            animationSpec = tween(durationMillis = TransitionDurationMillis)
                        ) { user ->

                            when (user) {
                                "null" -> UserCard()
                                else -> UserImage(selectedUser.value)
                            }
                        }

                    }


                }
            }


        }

    }

}

private fun SharedElementsRootScope.changeUser(url: String) {

    prepareTransition(url)
    viewModel.showUserImage(url)
}


val viewModel = UserInfoViewModel()

@ExperimentalComposeUiApi
@Composable
fun UserCard() {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val isClicked = remember {
        mutableStateOf(false)
    }
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    val userInfo = viewModel.userData.observeAsState()
    val nameSurname =
        userInfo.value?.name?.format()
    val isLoading = viewModel.isLoading.observeAsState(true)
    val isErrorOccurred = viewModel.isErrorOccurred.observeAsState(false)
    Column {
        Box {

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(60.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    elevation = 5.dp,
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(20.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))
                        if (isErrorOccurred.value) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.height(308.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.error_occurred),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.primaryVariant,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .conditional(isLoading.value) {
                                        shimmer(shimmerInstance)
                                    }
                                    .padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                if (isLoading.value.not()) {
                                    if (nameSurname != null) {
                                        Text(
                                            text = nameSurname,
                                            style = MaterialTheme.typography.h4,
                                            color = MaterialTheme.colors.primaryVariant,
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .clickable {
                                                    isClicked.value = !isClicked.value
                                                }
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(25.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color.Gray)


                                    )
                                }

                                if (isClicked.value) {
                                    Tooltip(
                                        anchorEdge = AnchorEdge.Top,
                                        modifier = Modifier.clickable {
                                            clipboardManager.setText(AnnotatedString((nameSurname.toString())))
                                            isClicked.value = false
                                        }
                                    ) {
                                        Text(
                                            stringResource(R.string.copy),
                                            color = MaterialTheme.colors.primaryVariant,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Divider(
                                color = MaterialTheme.colors.secondary,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
                            )

                            InfoRow(
                                info = userInfo.value?.email.toString(),
                                image = R.drawable.ic_baseline_email_24, isLoading
                            )

                            InfoRow(
                                info = userInfo.value?.dob?.date.format(),
                                image = R.drawable.ic_baseline_date_range_24, isLoading
                            )

                            InfoRow(
                                info = userInfo.value?.location.format(),
                                image = R.drawable.ic_baseline_home_24, isLoading
                            )

                            InfoRow(
                                info = userInfo.value?.phone.toString(),
                                image = R.drawable.ic_baseline_phone_24, isLoading
                            )
                            InfoRow(
                                info = userInfo.value?.login?.password.toString(),
                                image = R.drawable.ic_baseline_lock_24, isLoading
                            )
                        }


                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp), horizontalArrangement = Arrangement.Center

            ) {
                Card(
                    elevation = 0.dp,
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(500.dp)
                ) {
                    if (isErrorOccurred.value) {
                        Row(
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        ) {
                            ErrorPage()
                        }
                    } else {
                        if (isLoading.value.not()) {
                            val scope = LocalSharedElementsRootScope.current!!
                            SharedMaterialContainer(
                                key = "userImage",
                                screenKey = "userScreen",
                                shape = CircleShape,
                                color = Color.Transparent,
                                transitionSpec = FadeOutTransitionSpec
                            ) {

                                GlideImage(
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(10.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            Log.e("viewModelGlobal", "")
                                            userInfo.value?.picture?.large?.let {
                                                scope.changeUser(
                                                    it
                                                )
                                            }

                                        },
                                    imageModel = userInfo.value?.picture?.large,
                                    contentDescription = stringResource(R.string.user_image),
                                    contentScale = ContentScale.Crop
                                )

                            }


                        } else {
                            Row(
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(5.dp)
                                    .conditional(isLoading.value) {
                                        shimmer(shimmerInstance)
                                    }
                                    .clip(CircleShape)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(150.dp)
                                        .background(Color.Gray)
                                        .clip(CircleShape)


                                )
                            }
                        }
                    }


                }

            }

        }

        AnimatedButton(onClick = {
            viewModel.getRandomUser()
        }, image = R.drawable.ic_dice, text = stringResource(R.string.generate_random_user))

    }
}


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RandomUserTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            UserCard()
        }
    }
}