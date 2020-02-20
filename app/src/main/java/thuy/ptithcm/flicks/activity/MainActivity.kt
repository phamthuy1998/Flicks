package thuy.ptithcm.flicks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.adapter.MovieAdapter
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.viewmodel.MovieViewmodel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class MainActivity : AppCompatActivity() {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (instance == null) instance = MainActivity()
            return instance!!
        }
    }

    private var listMovies: List<Movie>? = null
    private var positionMovie = 0
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(this) { id, po ->
            movieViewModel.getTrailer(id)
            positionMovie = po
        }
    }

    val movieViewModel: MovieViewmodel by lazy {
        ViewModelProviders
            .of(this)
            .get(MovieViewmodel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // val binding = ActivityMainBinding.inflate(layoutInflater)
//        val binding: ActivityMainBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.movieViewModel = movieViewModel
//        // Specify the current activity as the lifecycle owner.
//        binding.lifecycleOwner = this@MainActivity

        addHanding()
        addEvent()

    }

    private fun addHanding() {
        rv_movies.run {
            rv_movies.adapter = movieAdapter
            rv_movies.layoutManager = LinearLayoutManager(context)
        }

        movieViewModel.listMovieLiveData.observe(this, Observer {
            listMovies =it
            movieAdapter.updateData(it)
            Log.d("ptumang", listMovies.toString() + "asdahsdhhd")
        })

            movieViewModel.listTrailerLiveData.observe(this, Observer { listYouTube ->
                listMovies?.getOrNull(positionMovie)?.listYoutube = listYouTube
                Log.d("aaaa", listMovies?.toString())
                listMovies?.let { movieAdapter.updateData(it) }

            })


        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_dark
        )
    }

    private fun addEvent() {
        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            movieViewModel.reFresh()
            val handle = Handler()
            handle.postDelayed(
                {

                    swipeContainer.isRefreshing = false
                    Log.d("ptumang", "refreesh xong")
                }, 200
            )
        }
    }


}
