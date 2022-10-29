package com.chirikualii.materiapi.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing?api_key=3b980052023e8e6898f4578a6972740b&language=en-US")
    suspend fun getMovies(@Query("page") page:String) : Response<NowPlayingResponse>


}