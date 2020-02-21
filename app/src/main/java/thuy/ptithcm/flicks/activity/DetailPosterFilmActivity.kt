package thuy.ptithcm.flicks.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_detail_film.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.databinding.ActivityDetailFilmBinding
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.viewmodel.MovieViewmodel
import thuy.ptithcm.flicks.viewmodel.TrailerViewmodel

class DetailPosterFilmActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    companion object {
        private var instance: DetailPosterFilmActivity? = null
        fun getInstance(): DetailPosterFilmActivity {
            if (instance == null) instance = DetailPosterFilmActivity()
            return instance!!
        }
    }

    val trailerViewModel: TrailerViewmodel by lazy {
        ViewModelProviders
            .of(Fragment())
            .get(TrailerViewmodel::class.java)
    }

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val binding: ActivityDetailFilmBinding =DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.trailerViewModel = trailerViewModel
        // Specify the current activity as the lifecycle owner.
//        binding.lifecycleOwner = this

        movie = intent.getParcelableExtra("manga")
//        ViewModelProviders.of(
//            ,
//            TrailerViewmodel(movie)
//        ).get(TrailerViewmodel::class.java)

    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        youTubePlayer: YouTubePlayer, b: Boolean
    ) {
        // do any work here to cue video, play video, etc.
//        youTubePlayer.cueVideo(list?.get(0)?.source)
//
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        youTubeInitializationResult: YouTubeInitializationResult
    ) {

    }
}
