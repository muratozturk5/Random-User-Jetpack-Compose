package com.muratozturk.randomuser.presentation.userimage

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muratozturk.randomuser.R
import com.muratozturk.randomuser.common.MaterialFadeOutTransitionSpec
import com.muratozturk.randomuser.common.components.AnimatedButton
import com.muratozturk.randomuser.presentation.userinfo.changeUser
import com.mxalbert.sharedelements.LocalSharedElementsRootScope
import com.mxalbert.sharedelements.SharedMaterialContainer
import com.skydoves.landscapist.glide.GlideImage


@ExperimentalComposeUiApi
@Composable
fun UserImage(url: String?) {


    val context = LocalContext.current
    val viewModel: UserImagePageViewModel = viewModel()

    val scope = LocalSharedElementsRootScope.current!!
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
            transitionSpec = MaterialFadeOutTransitionSpec
        ) {

            GlideImage(
                modifier = Modifier
                    .size(250.dp),
                imageModel = url,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.user_image)
            )

        }

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AnimatedButton(
                    onClick = {
                        if (url != null) {
                            viewModel.askPermissions(url, context)
                        }
                    },
                    image = R.drawable.ic_baseline_cloud_download_24,
                    text = stringResource(R.string.download_image),

                    )
            }
            Column(modifier = Modifier.weight(1f)) {

                AnimatedButton(
                    onClick = {
                        scope.changeUser(
                            "null"
                        )
                    },
                    image = R.drawable.ic_baseline_close_fullscreen_24,
                    text = stringResource(R.string.close)
                )
            }


        }
    }


}
