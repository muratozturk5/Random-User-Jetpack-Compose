package com.muratozturk.randomuser.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import com.muratozturk.randomuser.data.models.Location
import com.muratozturk.randomuser.data.models.Name
import com.mxalbert.sharedelements.*
import java.text.SimpleDateFormat
import java.util.*

const val TransitionDurationMillis = 1000

 val FadeOutTransitionSpec = MaterialContainerTransformSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)
 val CrossFadeTransitionSpec = SharedElementsTransitionSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Cross,
    fadeProgressThresholds = ProgressThresholds(0.10f, 0.40f)
)
 val MaterialFadeInTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.In
)
 val MaterialFadeOutTransitionSpec by lazy {
    MaterialContainerTransformSpec(
        pathMotionFactory = MaterialArcMotionFactory,
        durationMillis = TransitionDurationMillis,
        fadeMode = FadeMode.Out
    )
}




fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(this))
    } else {
        this
    }
}

fun Date?.format(): String {
    val pattern = "dd-MM-yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern)

    return if (this == null) {
        this.toString()
    } else {
        simpleDateFormat.format(this)
    }

}

fun Name.format(): String {
    return this.first + " " + this.last
}

fun Location?.format(): String {
    return this?.street?.number.toString() + " " + (this?.street?.name ?: "") + " " + (this?.state
        ?: "") + " " + (this?.city ?: "") + " / " + (this?.country ?: "")
}

