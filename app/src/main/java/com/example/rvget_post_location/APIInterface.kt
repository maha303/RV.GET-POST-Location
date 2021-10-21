package com.example.rvget_post_location

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("/test/")
    fun getUser():Call<List<UserItem>>

    @POST("/test/")
    fun addUser(@Body userItem: UserItem): Call<List<UserItem>>
}