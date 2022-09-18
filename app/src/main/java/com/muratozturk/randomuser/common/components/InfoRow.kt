package com.muratozturk.randomuser.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.skgmn.composetooltip.AnchorEdge
import com.github.skgmn.composetooltip.Tooltip
import com.muratozturk.randomuser.R
import com.muratozturk.randomuser.presentation.theme.Teal200
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun InfoRow(info: String, image: Int, isLoading: State<Boolean>) {
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
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
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
                    anchorEdge = AnchorEdge.Start,
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString((info)))
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

    }
}