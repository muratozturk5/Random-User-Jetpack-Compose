package com.muratozturk.randomuser.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.muratozturk.randomuser.data.models.Result
import com.muratozturk.randomuser.data.retrofit.ApiUtils
import com.muratozturk.randomuser.data.retrofit.UserDaoInterface

class Repository {

    val isLoading = MutableLiveData<Boolean>()
    val isErrorOccurred = MutableLiveData<Boolean>()
    val userInfoData = MutableLiveData<Result>()
    private var dif: UserDaoInterface = ApiUtils.getInterfaceDao()

    suspend fun getRandomUser() {
        try {
            isLoading.value = true
            isErrorOccurred.value = false
            val response = dif.getRandomUser()
            if (response.isSuccessful) {
                userInfoData.value = response.body()!!.results[0]
                isLoading.value = false
                isErrorOccurred.value = false
            }
        } catch (t: Throwable) {
            t.localizedMessage?.toString()?.let { Log.e("getRandomUser", it) }
            isLoading.value = false
            isErrorOccurred.value = true
        }
    }
}