package thuy.ptithcm.flicks.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import thuy.ptithcm.flicks.model.MovieList
import thuy.ptithcm.flicks.model.Trailer
import thuy.ptithcm.flicks.utils.TOKEN_API

interface MyApi {

    @GET("now_playing")
    fun getListMovie(
        @Query("api_key") token: String = TOKEN_API,
        @Query("page") page: Int = 1
    ): Call<MovieList>

    @GET("{id}/trailers")
    fun getMovieInforVideo(
        @Path("id") id: Int,
        @Query("api_key") token: String = TOKEN_API
    ): Call<Trailer>

}