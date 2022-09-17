package com.muratozturk.randomuser.common.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.muratozturk.randomuser.ui.theme.Teal200

@ExperimentalComposeUiApi
@Composable
fun AnimatedButton(onClick: () -> Unit = {}, image: Int, text: String) {
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
                        onClick()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        selected.value = false
                    }

                }
                true
            },
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(20.dp),

        ) {

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
                    .size(50.dp)
                    .padding(10.dp)
            )

            Text(
                text = text,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.body1
            )


        }

    }
}