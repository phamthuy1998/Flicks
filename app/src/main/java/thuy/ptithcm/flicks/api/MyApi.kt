package thuy.ptithcm.flicks.api

import retrofit2.Call
import retrofit2.http.*
import thuy.ptithcm.flicks.model.MovieList
import thuy.ptithcm.flicks.model.Trailer
import thuy.ptithcm.flicks.utils.TOKEN_API

interface MyApi {

    @GET("movie/now_playing")
    fun getListMovie(
        @Query("api_key") token: String = TOKEN_API,
        @Query("page") page: Int = 1
    ): Call<MovieList>

    @GET("movie/{id}/trailers")
    fun getMovieInforVideo(
        @Path("id") id: Int,
        @Query("api_key") token: String = TOKEN_API
    ): Call<Trailer>

    @GET("search/movie")
    fun getMovieSearch(
        @Query("api_key") token: String = TOKEN_API,
        @Query("query") strSearch: String = ""    ): Call<MovieList>

}