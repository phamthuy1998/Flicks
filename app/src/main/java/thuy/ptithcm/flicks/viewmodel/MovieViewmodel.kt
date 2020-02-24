package thuy.ptithcm.flicks.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.youtube.player.YouTubePlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.flicks.api.MovieRespositeries
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube

class MovieViewmodel : ViewModel() {

    var listMovieLiveData = MutableLiveData<ArrayList<Movie>>().apply { value = arrayListOf() }
    var listSearchLiveData = MutableLiveData<ArrayList<Movie>>().apply { value = arrayListOf() }
    private val apiManager: MovieRespositeries by lazy { MovieRespositeries() }

    // CompositeDisposable dùng để quản lý Disposable, được sinh ra để chứa tất cả các Disposable
    // Disposable: là một Subscription mới
    // Subscription: mỗi khi có thực hiện 1 Observable sau đó chuyển qua 1 subcriber xử lý sẽ trả về 1 Subscription
    private val composite by lazy { CompositeDisposable() }
    var listTempt = ArrayList<Movie>()

    init {
        Log.d("ptumang", "co khoi tao")
        if (listMovieLiveData.value.isNullOrEmpty())
            getMovie()
    }

    fun getMovie(page: Int = 1) {
        listTempt = arrayListOf()
        composite.add(
            apiManager.getListMovie(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.results.isNullOrEmpty()) {
                        it.results.forEach { listTempt.add(it) }
                        listMovieLiveData.postValue(listTempt)
                    }
                }, {
                })
        )
    }

    fun getMovieSearch(strSearch: String = "") {
        apiManager.getMovieSearch(strSearch = strSearch)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                listSearchLiveData.value = it.results
            }, {
            })?.let {
                composite.add(
                    it
                )
            }
    }

    fun reFresh() {
        getMovie()
    }
}