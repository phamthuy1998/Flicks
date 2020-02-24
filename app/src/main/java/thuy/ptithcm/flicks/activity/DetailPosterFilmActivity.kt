package thuy.ptithcm.flicks.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import kotlinx.android.synthetic.main.activity_detail_film.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.adapter.TrailerAdapter
import thuy.ptithcm.flicks.adapter.TrailerAdapterEvent
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.model.Youtube
import thuy.ptithcm.flicks.utils.YOUTUBE_API
import thuy.ptithcm.flicks.viewmodel.TrailerViewmodel


class DetailPosterFilmActivity : AppCompatActivity() , TrailerAdapterEvent {
    lateinit var youTubePlayerG: YouTubePlayer
    private var youTubeInit = false

    companion object {
        private var instance: DetailPosterFilmActivity? = null
        fun getInstance(): DetailPosterFilmActivity {
            if (instance == null) instance = DetailPosterFilmActivity()
            return instance!!
        }
    }

    val trailerViewModel: TrailerViewmodel by lazy {
        ViewModelProviders
            .of(this)
            .get(TrailerViewmodel::class.java)
    }

    private var listTrailerVideo = arrayListOf<Youtube>()
    private val trailerAdapter: TrailerAdapter by lazy {
        TrailerAdapter(listTrailerVideo, this, this)
    }


    private var movie: Movie? = null
    private var playVideo = false
    private var showOverview = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)
        movie = intent?.getParcelableExtra("movie")
        playVideo = intent?.getBooleanExtra("play", false) ?: false

        inItView()
        bindings()
        initAdapter()
        setInforMovie(movie)
        addEvents()
    }

    private fun initAdapter() {
        rv_another_trailer?.run {
            this.adapter = trailerAdapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun inItView() {
        val youtubeFragment =
            fragmentManager.findFragmentById(R.id.movie_player_detail) as YouTubePlayerFragment

        youtubeFragment.initialize(
            YOUTUBE_API,
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
        val orientation = getResources().getConfiguration().orientation
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            rv_another_trailer.layoutManager = LinearLayoutManager(
//                this,
//                LinearLayoutManager.HORIZONTAL,
//                false
//            )
//            rv_another_trailer.adapter = trailerAdapter
//        }


    }


    private fun addEvents() {
        btn_trailer_back.setOnClickListener {
            finish()
        }
        val orientation = getResources().getConfiguration().orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            tv_title_overView_detail.setOnClickListener {
                showOverview = !showOverview
                if (showOverview) {
                    tv_overView_detail.visibility = View.VISIBLE
                } else
                    tv_overView_detail.visibility = View.GONE
            }
        }

    }

    private fun setInforMovie(movie: Movie?) {
        val orientation = getResources().getConfiguration().orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            tv_title_detail.text = movie?.original_title
            rating_film.numStars = 10
            rating_film.rating = movie?.vote_average?.toFloat() ?: 0.toFloat()
            val date_release = getString(R.string.release_date) + " " + movie?.release_date
            tv_date_release.text = date_release
            tv_overView_detail.text = movie?.overview
            tv_tb_title.text = movie?.original_title
        }
    }

    private fun bindings() {
        trailerViewModel.listTrailerLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                if (youTubeInit) {
                    youTubePlayerG.cueVideo(it[0].source)
                    listTrailerVideo.addAll(it)
                    trailerAdapter.notifyDataSetChanged()

                } else {
                    youTubeInit = true
                    movie?.id?.let { trailerViewModel.getTrailer(it) }
                }
            }
        })
    }

    override fun onItemMovieClick(item: Youtube?) {
        if (youTubeInit){
            youTubePlayerG.cueVideo(item?.source)
        }
    }


}
