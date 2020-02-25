package thuy.ptithcm.flicks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.youtube.player.YouTubePlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.flicks.api.MovieRespositeries
import thuy.ptithcm.flicks.model.Youtube

class TrailerViewmodel() : ViewModel() {

    val listTrailerLiveData = MutableLiveData<ArrayList<Youtube>>().apply { value = arrayListOf() }
    private val apiManager: MovieRespositeries by lazy { MovieRespositeries() }
    lateinit var youTubePlayerG: YouTubePlayer
    private val composite by lazy { CompositeDisposable() }

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

    fun setYoutubePlayer(youTubePlayer: YouTubePlayer){
        youTubePlayerG = youTubePlayer
    }
}