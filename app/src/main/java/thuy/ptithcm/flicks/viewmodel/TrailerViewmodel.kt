package thuy.ptithcm.flicks.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.notifyAll
import thuy.ptithcm.flicks.api.MovieRespositeries
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube

class TrailerViewmodel() : ViewModel() {

    val listTrailerLiveData = MutableLiveData<List<Youtube>>().apply { value = mutableListOf() }
    private val apiManager: MovieRespositeries by lazy { MovieRespositeries() }

    private val composite by lazy { CompositeDisposable() }

//    init {
//        Log.d("ptumang", "co khoi tao")
//        if (listTrailerLiveData.value.isNullOrEmpty())
//            movie?.id?.let { getTrailer(it) }
//    }

    fun getTrailer( id:Int) {
        composite.add(
            apiManager.getMovieInfor(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listTrailerLiveData.value = it.youtube
                }, {
                })
        )
    }

//    fun reFresh() {
//        movie?.id?.let { getTrailer(it) }
//    }
}