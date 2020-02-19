package thuy.ptithcm.flicks.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.utils.BASE_URL

class ApiManager {

    private val _myApi: MyApi by lazy {
        getHelperRestFull()!!.create(MyApi::class.java)
    }

    companion object {

        private var retrofit: Retrofit? = null

        fun getHelperRestFull(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
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

    fun getListMovie(): Single<List<Movie>> {
        return buildRequest(_myApi.getListMovie())
    }
}