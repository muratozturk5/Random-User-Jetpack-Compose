@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.muratozturk.randomuser

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skgmn.composetooltip.AnchorEdge
import com.github.skgmn.composetooltip.Tooltip
import com.muratozturk.randomuser.ui.theme.Light
import com.muratozturk.randomuser.ui.theme.Primary
import com.muratozturk.randomuser.ui.theme.RandomUserTheme
import com.muratozturk.randomuser.ui.theme.Teal200
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Light
                ) {
                    UserCard()
                }
            }
        }
    }
}


@Composable
fun UserCard() {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val isClicked = remember {
        mutableStateOf(false)
    }
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    val isLoading = remember {
        mutableStateOf(false)
    }
    val nameSurname = "Name Surname"
    Column {
        Box {

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(60.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    elevation = 5.dp,
                    backgroundColor = Primary,
                    shape = RoundedCornerShape(20.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))

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
                                Text(
                                    text = nameSurname,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .clickable {
                                            isClicked.value = !isClicked.value
                                        }
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(27.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.Gray)


                                )
                            }


                            if (isClicked.value) {
                                Tooltip(
                                    anchorEdge = AnchorEdge.Top,
                                    modifier = Modifier.clickable {
                                        clipboardManager.setText(AnnotatedString((nameSurname)))
                                        isClicked.value = false
                                    }
                                ) {
                                    Text("Copy", fontWeight = FontWeight.Bold)
                                }
                            }

                        }

                        Divider(
                            color = Light,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
                        )

                        InfoRow(
                            info = "user@example.com",
                            image = R.drawable.ic_baseline_email_24, isLoading
                        )
                        InfoRow(
                            info = "11/2/1998",
                            image = R.drawable.ic_baseline_date_range_24, isLoading
                        )
                        InfoRow(
                            info = "1908 Robinson Rd",
                            image = R.drawable.ic_baseline_home_24, isLoading
                        )
                        InfoRow(
                            info = "(953) 619-6488",
                            image = R.drawable.ic_baseline_phone_24, isLoading
                        )
                        InfoRow(
                            info = "trader",
                            image = R.drawable.ic_baseline_lock_24, isLoading
                        )

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
                    backgroundColor = Primary,
                    shape = RoundedCornerShape(500.dp)
                ) {

                    if (isLoading.value.not()) {
                        Image(
                            painter = painterResource(R.drawable.user),
                            contentDescription = "avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .padding(10.dp)
                                .clip(CircleShape)
                        )
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

        AnimatedButton(isLoading)

    }
}

@Composable
fun AnimatedButton(isLoading: MutableState<Boolean>) {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) 0.9f else 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .scale(scale.value)
            .padding(15.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        selected.value = true

                    }
                    MotionEvent.ACTION_UP -> {
                        selected.value = false
                        isLoading.value = !isLoading.value
                        Log.e("isClickedGlobal", "Clicked")
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        selected.value = false
                    }
                }
                true
            },
        elevation = 5.dp,
        backgroundColor = Primary,
        shape = RoundedCornerShape(20.dp),

        ) {

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_dice),
                contentDescription = "image",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Teal200),
                modifier = Modifier
                    .size(50.dp)
                    .padding(10.dp)
            )

            Text(
                text = "Generate Random User",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,

                )


        }

    }
}


@Composable
fun InfoRow(info: String, image: Int, isLoading: MutableState<Boolean>) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val isClicked = remember {
        mutableStateOf(false)
    }
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(image),
            contentDescription = "image",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Teal200),
            modifier = Modifier
                .conditional(isLoading.value) {
                    shimmer(shimmerInstance)
                }
                .size(50.dp)
                .padding(10.dp)

        )
        Column(modifier = Modifier.conditional(isLoading.value) {
            shimmer(shimmerInstance)
        }) {

            if (isLoading.value.not()) {
                Text(
                    text = info,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        isClicked.value = !isClicked.value
                    }
                )
            } else {
                Row(modifier = Modifier.padding(end = 10.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(22.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Gray)


                    )
                }


            }




            if (isClicked.value) {
                Tooltip(
                    anchorEdge = AnchorEdge.Top,
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString((info)))
                        isClicked.value = false
                    }
                ) {
                    Text("Copy", fontWeight = FontWeight.Bold)
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RandomUserTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Light
        ) {
            UserCard()
        }
    }
}