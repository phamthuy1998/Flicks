package thuy.ptithcm.flicks.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.flicks.api.ApiManager
import thuy.ptithcm.flicks.model.Movie

class MovieViewmodel : ViewModel() {

    val listMovieLiveData = MutableLiveData<List<Movie>>().apply { value = mutableListOf() }
    private val apiManager: ApiManager by lazy { ApiManager() }

    // CompositeDisposable dùng để quản lý Disposable, được sinh ra để chứa tất cả các Disposable
    // Disposable: là một Subscription mới
    // Subscription: mỗi khi có thực hiện 1 Observable sau đó chuyển qua 1 subcriber xử lý sẽ trả về 1 Subscription
    private val composite by lazy { CompositeDisposable() }

    init {
        Log.d("ptumang", "co khoi tao")
        //if (listMovieLiveData.value.isNullOrEmpty())
            getMovie()
    }

    fun getMovie() {
        composite.add(
            apiManager.getListMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listMovieLiveData.value = it
                    Log.d("ptumang", it.size.toString()+"getmovie")
                }, {

    })
        )
    }

    fun reFresh() {
        getMovie()
    }

}