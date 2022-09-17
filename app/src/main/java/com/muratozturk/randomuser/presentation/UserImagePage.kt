package com.muratozturk.randomuser.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muratozturk.randomuser.R
import com.muratozturk.randomuser.common.FadeOutTransitionSpec
import com.mxalbert.sharedelements.SharedMaterialContainer

import com.skydoves.landscapist.glide.GlideImage


@Composable
fun UserImage(url: String?) {


    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedMaterialContainer(
            key = "userImage",
            screenKey = "userImageScreen",
            shape = MaterialTheme.shapes.medium,
            color = Color.Transparent,
            elevation = 10.dp,
            transitionSpec = FadeOutTransitionSpec
        ) {

            GlideImage(
                modifier = Modifier
                    .size(250.dp),
                imageModel = url,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.user_image)
            )


        }
    }


}

