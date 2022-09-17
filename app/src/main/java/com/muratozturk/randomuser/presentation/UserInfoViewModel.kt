package com.muratozturk.randomuser.presentation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.randomuser.data.models.Result
import com.muratozturk.randomuser.data.repository.Repository
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {

    private val repository = Repository()
    val userData: MutableLiveData<Result> = repository.userInfoData
    val isLoading: MutableLiveData<Boolean> = repository.isLoading
    val selectedUser = MutableLiveData<String>()

    val isErrorOccurred: MutableLiveData<Boolean> = repository.isErrorOccurred

    init {

        getRandomUser()
    }

    fun getRandomUser() {
        viewModelScope.launch {
            repository.getRandomUser()
        }
    }

    fun showUserImage(url: String?) {

        selectedUser.value = url

    }
}