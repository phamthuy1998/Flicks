package thuy.ptithcm.flicks.api

import android.util.Log
import io.reactivex.Single
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thuy.ptithcm.flicks.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import thuy.ptithcm.flicks.interface1.MyApi
import thuy.ptithcm.flicks.model.MovieList
import thuy.ptithcm.flicks.model.Trailer

class MovieRespositeries {

    private val _myApi: MyApi by lazy {
        getHelperRestFull()!!.create(MyApi::class.java)
    }

    companion object {

        private var retrofit: Retrofit? = null

        val logging = HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("API",message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BASIC)
        var client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        fun getHelperRestFull(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit
                    .Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    // chuyen tu gson factory qua json de gui len server va nguoc lai
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

    private fun <T : Any> buildRequest(call: Call<T>): Single<T> {
        return Single.create {
            call.enqueue(object : Callback<T> {

                // tra ve kieu thanh cong,
                // co the request gui thanh cong nhung server kkhong chap nhan
                // 1: thanh cong tat ca
                // 2: thanh cong 1 phan
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        it.onSuccess(response.body()!!) // !!server bat buoc tra ve co hoac rong, khong the tra ve null
                    } catch (ex: Exception) {
                        it.onError(ex)
                    }
                }

                override fun onFailure(p0: Call<T>, response: Throwable) {
                    // server tra ve dinh dang loi nhu the nao cho  nguoi dung
                    it.onError(response)
                }
            })
        }
    }

    fun getListMovie(page: Int= 1): Single<MovieList> {
        return buildRequest(_myApi.getListMovie(page = page))
    }

    fun getMovieInfor(id: Int): Single<Trailer> {
        return buildRequest(_myApi.getMovieInforVideo(id))
    }

    fun getMovieSearch(strSearch: String): Single<MovieList>? {
        return buildRequest(_myApi.getMovieSearch(strSearch=strSearch))
    }
}