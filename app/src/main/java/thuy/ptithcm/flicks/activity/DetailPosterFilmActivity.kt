package thuy.ptithcm.flicks.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.viewmodel.TrailerViewmodel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerView
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.utils.YOUTUBE_API


class DetailPosterFilmActivity : AppCompatActivity() {

    companion object {
        private var instance: DetailPosterFilmActivity? = null
        fun getInstance(): DetailPosterFilmActivity {
            if (instance == null) instance = DetailPosterFilmActivity()
            return instance!!
        }
    }


    private var listTrailer: List<Youtube>? = null

    val trailerViewModel: TrailerViewmodel by lazy {
        ViewModelProviders
            .of(this)
            .get(TrailerViewmodel::class.java)
    }

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        movie = intent?.getParcelableExtra("movie")

        Log.d("aaaa",movie.toString())
        movie?.id?.let { trailerViewModel.getTrailer(it) }
        Log.d("aaaa","bbbb"+trailerViewModel.listTrailerLiveData.toString())
        trailerViewModel.listTrailerLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()){
                listTrailer = it
                showVideo(it)
            }
        })

        // do any work here to cue video, play video, etc.
        Log.d("aaaa",listTrailer?.get(0)?.source.toString())


//
//    override fun onInitializationSuccess(
//        provider: YouTubePlayer.Provider,
//        youTubePlayer: YouTubePlayer, b: Boolean
//    ) {
//        // do any work here to cue video, play video, etc.
//        youTubePlayer.cueVideo("5xVh-7ywKpE")
////
//    }
//
//    override fun onInitializationFailure(
//        provider: YouTubePlayer.Provider,
//        youTubeInitializationResult: YouTubeInitializationResult
//    ) {
//
    }

    fun showVideo(listTrailer : List<Youtube>){
        val youTubePlayerView =
            findViewById(thuy.ptithcm.flicks.R.id.movie_player_detail) as YouTubePlayerView

        youTubePlayerView.initialize(
            YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {

                    youTubePlayer.cueVideo(listTrailer?.get(0)?.source)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Toast.makeText(applicationContext,getString(R.string.err_load_video),Toast.LENGTH_LONG).show()
                }
            })
    }
}
