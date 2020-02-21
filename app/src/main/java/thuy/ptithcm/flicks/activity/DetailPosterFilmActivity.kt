package thuy.ptithcm.flicks.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import kotlinx.android.synthetic.main.activity_detail_film.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.viewmodel.TrailerViewmodel


class DetailPosterFilmActivity : AppCompatActivity() {
    lateinit var youTubePlayerG: YouTubePlayer

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
    private var playVideo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)
        movie = intent?.getParcelableExtra("movie")
        playVideo = intent?.getBooleanExtra("play", false) ?: false

        val youtubeFragment =
            fragmentManager.findFragmentById(R.id.movie_player_detail) as YouTubePlayerFragment

        bindings()

        setInforMovie(movie)

        youtubeFragment.initialize("YOUR API KEY",
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) { // do any work here to
                    youTubePlayerG = youTubePlayer

                    movie?.id?.let { trailerViewModel.getTrailer(it) }
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })
    }

    private fun setInforMovie(movie: Movie?) {
        tv_title_detail.text = movie?.original_title
        rating_film.numStars = movie?.vote_average?.toInt() ?: 0
        val date_release = getString(R.string.release_date) + " " + movie?.release_date
        tv_release_date.text = date_release
    }

    private fun bindings() {
        trailerViewModel.listTrailerLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                youTubePlayerG.cueVideo(it[0].source)
            }
        })
    }
}
