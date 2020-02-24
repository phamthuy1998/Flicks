package thuy.ptithcm.flicks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.adapter.MovieAdapter
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.viewmodel.MovieViewmodel
import thuy.ptithcm.flicks.adapter.MovieAdapterEvent
import thuy.ptithcm.flicks.adapter.OnLoadMoreListener
import thuy.ptithcm.flicks.adapter.RecyclerViewLoadMoreScroll


class MainActivity : AppCompatActivity(), MovieAdapterEvent {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (instance == null) instance = MainActivity()
            return instance!!
        }
    }

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(this, movieAdapterEvent = this)
    }

    val movieViewModel: MovieViewmodel by lazy {
        ViewModelProviders
            .of(this)
            .get(MovieViewmodel::class.java)
    }

    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            R.color.colorLoading
        )
        // Hidden keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        binding()
        addEvent()
    }

    override fun onLoadMore() {
        progressBar.visibility = View.VISIBLE
        Handler().postDelayed({
            page++
            movieViewModel.getMovie(page)
            progressBar.visibility = View.GONE
        }, 1000)
    }

    override fun onItemMovieClick(item: Movie?) {
        val intent = Intent(this, DetailPosterFilmActivity.getInstance().javaClass)
        intent.putExtra("movie", item)
        startActivity(intent)
    }

    private fun binding() {
        rv_movies.run {
            rv_movies.adapter = movieAdapter
            rv_movies.layoutManager = LinearLayoutManager(context)
        }
        rv_movies.setHasFixedSize(true)

        movieViewModel.listMovieLiveData.observe(this, Observer {
            movieAdapter.updateData(it)
        })
    }

    private fun addEvent() {
        swipeContainer.setOnRefreshListener {
            movieViewModel.reFresh()
            Handler().postDelayed(
                {
                    swipeContainer.isRefreshing = false
                }, 500
            )
        }
    }
}
