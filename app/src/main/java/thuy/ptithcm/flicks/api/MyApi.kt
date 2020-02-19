package thuy.ptithcm.flicks.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import thuy.ptithcm.flicks.model.Movie

interface MyApi {

    @GET("now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
    fun getListMovie(): Call<List<Movie>>


}