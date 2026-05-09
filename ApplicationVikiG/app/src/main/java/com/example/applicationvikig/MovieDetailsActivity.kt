package com.example.applicationvikig

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.bumptech.glide.Glide
import com.example.applicationvikig.databinding.ActivityMovieDetailsBinding

import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val apiKey = "d8a07dedcb5bf27a66c3c3621fb2cb84"

    private var trailerVideoId: String? = null
    private var backdropPath: String = ""
    private var poster: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movie_id", 0)


        if (movieId == 0) {
            finish()
            return
        }

        val title = intent.getStringExtra("title") ?: ""
        val overview = intent.getStringExtra("overview") ?: ""

        val rating = intent.getDoubleExtra("rating", 0.0)
        val releaseDate = intent.getStringExtra("release_date") ?: ""
        poster = intent.getStringExtra("poster") ?: ""




        binding.tvTitle.text = title
        binding.tvOverview.text = overview
        binding.tvRating.text = "⭐ $rating"
        binding.tvReleaseDate.text = releaseDate

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$poster")
            .into(binding.ivPoster)

        fetchTrailer(movieId)
    }

    private fun fetchTrailer(movieId: Int) {

        lifecycleScope.launch {

            try {
                backdropPath = intent.getStringExtra("backdrop") ?: ""
                poster = intent.getStringExtra("poster") ?: ""

                val response = RetrofitInstance.api.getMovieVideos(movieId, apiKey)

                val trailer = response.results.firstOrNull {
                    it.site == "YouTube" && it.type == "Trailer"
                }

                trailerVideoId = trailer?.key


              if (backdropPath.isNotEmpty()) {

                val imageUrl = "https://image.tmdb.org/t/p/w780$backdropPath"

                    Glide.with(this@MovieDetailsActivity)
                        .load(imageUrl)
                        .into(binding.trailerThumbnail)
                }

                else {
                  val imageUrl = "https://image.tmdb.org/t/p/w500$poster"

                  Glide.with(this@MovieDetailsActivity)
                      .load(imageUrl)
                      .into(binding.trailerThumbnail)

                }

                binding.trailerThumbnail.setOnClickListener {

                    trailerVideoId?.let { videoId ->

                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/watch?v=$videoId")
                        )

                        startActivity(intent)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}