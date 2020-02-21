package thuy.ptithcm.flicks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
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

    private var listMovies: List<Movie>? = null
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(this, listMovies, this)
    }

    private var page: Int = 1

    val movieViewModel: MovieViewmodel by lazy {
        ViewModelProviders
            .of(this)
            .get(MovieViewmodel::class.java)
    }


    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding()
        addEvent()
        setRVLayoutManager()
        setRVScrollListener()
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(this)
        rv_movies.layoutManager = mLayoutManager
        rv_movies.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(this)
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        rv_movies.addOnScrollListener(scrollListener)
    }

    private fun LoadMoreData() {
        movieAdapter.addLoadingView()
        movieAdapter.removeLoadingView()
        page++
        Log.d("ptumang", page.toString() + "page")
        movieViewModel.getMovie(page)
        scrollListener.setLoaded()
        rv_movies.post {
            movieAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemMovieClick(item: Movie?) {
        val intent = Intent(this, DetailPosterFilmActivity.getInstance().javaClass)
        intent.putExtra("movie", item)
        startActivity(intent)
    }

    override fun onMoviePopularClick(item: Movie?) {
        val intent = Intent(this, DetailPosterFilmActivity.getInstance().javaClass)
        intent.putExtra("movie", item)
        intent.putExtra("play", true)
        startActivity(intent)
    }

    private fun binding() {
        rv_movies.run {
            rv_movies.adapter = movieAdapter
            rv_movies.layoutManager = LinearLayoutManager(context)
        }
        rv_movies.setHasFixedSize(true)

        movieViewModel.listMovieLiveData.observe(this, Observer {
            listMovies = it
            // movieViewModel.getMovie(page)
            movieAdapter.updateData(it)
            Log.d("ptumang", it.size.toString() + "asdahsdhhd")

        })

        movieViewModel.listMovieLiveData.observe(this, Observer {
            listMovies = it
            // movieViewModel.getMovie(page)
            movieAdapter.updateData(it)
            Log.d("ptumang", it.size.toString() + "asdahsdhhd")

        })
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_dark
        )
    }

    private fun addEvent() {
        swipeContainer.setOnRefreshListener {
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
