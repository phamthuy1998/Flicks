package thuy.ptithcm.flicks.activity

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import thuy.ptithcm.flicks.R
import thuy.ptithcm.flicks.model.Movie
import thuy.ptithcm.flicks.utils.YOUTUBE_API


class QuickPlayActivity : YouTubeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_play)
        var idVideo = intent.getStringExtra("ID_VIDEO")
        var movie = intent?.getParcelableExtra<Movie>("MOVIE")
        Log.d("anna", movie.toString())
        val youTubePlayerView = findViewById(R.id.player) as YouTubePlayerView
        youTubePlayerView.initialize(
            YOUTUBE_API,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) { // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(idVideo)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })
    }
}
