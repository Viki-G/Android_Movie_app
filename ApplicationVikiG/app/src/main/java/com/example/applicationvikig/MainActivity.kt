package com.example.applicationvikig

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationvikig.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()

    private var allSections: List<MovieSection> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecycler.layoutManager = LinearLayoutManager(this)

        viewModel.sections.observe(this) { sections ->
            allSections = sections
            setupAdapter(sections)
        }

        setupSearch()
        viewModel.fetchMovies()
    }

    private fun setupAdapter(sections: List<MovieSection>) {
        binding.mainRecycler.adapter = SectionAdapter(sections) { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("movie_id", movie.id)
            intent.putExtra("title", movie.title)
            intent.putExtra("overview", movie.overview)
            intent.putExtra("poster", movie.poster_path)
            intent.putExtra("rating", movie.vote_average)
            intent.putExtra("release_date", movie.release_date)
            startActivity(intent)
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().trim()

                if (query.isEmpty()) {
                    setupAdapter(allSections)
                } else {
                    val filteredSections = allSections.map { section ->
                        MovieSection(
                            section.title,
                            section.movies.filter {
                                it.title.contains(query, ignoreCase = true)
                            }
                        )
                    }.filter { it.movies.isNotEmpty() }

                    setupAdapter(filteredSections)
                }
                return true
            }
        })
    }
}