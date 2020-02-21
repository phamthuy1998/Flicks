package thuy.ptithcm.flicks.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.viewmodel.TrailerViewmodel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerView
import thuy.ptithcm.flicks.R
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
    private lateinit var youTubePlayers: YouTubePlayer

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

        binding()

        val youTubePlayerView =
            findViewById(thuy.ptithcm.flicks.R.id.movie_player_detail) as YouTubePlayerView
        youTubePlayerView.initialize(
            YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    youTubePlayers = youTubePlayer
                    movie?.id?.let { trailerViewModel.getTrailer(it) }
                    //  youTubePlayer.cueVideo(listTrailer?.get(0)?.source)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.err_load_video),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

        movie?.id?.let { trailerViewModel.getTrailer(it) }

    }

    fun binding(){
        trailerViewModel.listTrailerLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                listTrailer = it
                youTubePlayers.cueVideo(it.get(0).source)
            }
        })
    }
}
