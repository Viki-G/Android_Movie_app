package com.example.applicationvikig
import com.example.applicationvikig.databinding.ItemMovieBinding
import com.example.applicationvikig.databinding.ActivityMainBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load

class MovieAdapter(
    private val movies: List<Movie>,
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemMovieBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.binding.poster.load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
        holder.binding.title.text = movie.title


        holder.itemView.setOnClickListener {
            onClick(movies[position])
        }

    }

    override fun getItemCount() = movies.size
}