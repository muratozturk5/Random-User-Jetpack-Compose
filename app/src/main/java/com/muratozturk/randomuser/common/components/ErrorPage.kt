package com.muratozturk.randomuser.common.components



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.muratozturk.randomuser.R


@Composable
fun ErrorPage() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_occurred))

    LottieAnimation(
        composition
    )

}

