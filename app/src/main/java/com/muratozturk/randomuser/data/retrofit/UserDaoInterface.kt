package com.muratozturk.randomuser.data.retrofit

import com.muratozturk.randomuser.data.models.RandomUser
import retrofit2.Response
import retrofit2.http.GET

interface UserDaoInterface {

    @GET("api")
    suspend fun getRandomUser(): Response<RandomUser>
    
}