package com.chirikualii.materiapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chirikualii.materiapi.R
import com.chirikualii.materiapi.data.dummy.DataDummy
import com.chirikualii.materiapi.data.model.Movie
import com.chirikualii.materiapi.data.remote.ApiClient
import com.chirikualii.materiapi.databinding.ActivityMainBinding
import com.chirikualii.materiapi.ui.adapter.MovieListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup adapter
        adapter = MovieListAdapter()
        binding.rvMovie.adapter = adapter


        GlobalScope.launch(Dispatchers.IO) {
            val response = ApiClient.service.getMovies(
                page = "2"
            )
            response
            Log.d(MainActivity::class.java.name, "ListMovie: ${Gson().toJsonTree(response.body())}")

            val data = response.body()
            val listMovie = data?.results?.map { result ->
                Movie(
                    title = result.title,
                    genre = result.releaseDate,
                    imagePoster = result.posterPath
                )
            }

            withContext(Dispatchers.Main) {
                if (listMovie != null) {
                    adapter.addItem(listMovie)
                }
            }
        }
    }
}