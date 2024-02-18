package dev.kuromiichi.retrofitapi.services

import dev.kuromiichi.retrofitapi.models.Dogs
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DogService {
    @GET("images/search")
    suspend fun getDogs(@QueryMap params: Map<String, String>): Dogs
}