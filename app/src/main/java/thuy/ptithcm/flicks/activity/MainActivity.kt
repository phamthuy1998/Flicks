package thuy.ptithcm.flicks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.adapter.MovieAdapter
import thuy.ptithcm.flicks.databinding.ActivityMainBinding
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.viewmodel.MovieViewmodel

class MainActivity : AppCompatActivity() {

    private var moiveList = ArrayList<Movie>()

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(this, moiveList)
    }

    val movieViewModel: MovieViewmodel by lazy {
        ViewModelProviders
            .of(this)
            .get(MovieViewmodel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // val binding = ActivityMainBinding.inflate(layoutInflater)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.movieViewModel = movieViewModel
        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this@MainActivity

        addHanding()
    }

    private fun addHanding() {
        rv_movies.run {
            rv_movies.adapter = MovieAdapter(context, moiveList)
            rv_movies.layoutManager = LinearLayoutManager(context)
        }

        movieViewModel.listMovieLiveData.observe(this, Observer {
            movieAdapter.updateData(it)
            Log.d("ptumang", it.size.toString()+"main")
        })
    }

}
