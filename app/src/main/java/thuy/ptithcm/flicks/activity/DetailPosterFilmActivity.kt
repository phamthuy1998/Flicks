package thuy.ptithcm.flicks.activity

import android.content.Intent
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
//            if (!it.isNullOrEmpty()){
//                listTrailer = it
//                showVideo(it)
//            }
            if (!it.isNullOrEmpty()){
                val intent = Intent(this, QuickPlayActivity::class.java)
                intent.putExtra("ID_VIDEO", it.firstOrNull()?.source)
                intent.putExtra("MOVIE",movie)
                startActivity(intent)
            }
            else{
                return@Observer
            }

        })



    }

}
