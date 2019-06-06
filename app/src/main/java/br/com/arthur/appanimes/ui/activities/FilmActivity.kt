package br.com.arthur.appanimes.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.arthur.appanimes.R
import br.com.arthur.appanimes.ui.FilmViewModel

class FilmActivity : AppCompatActivity() {

    private lateinit var model: FilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)

        model = FilmViewModel(this)
        val filmId = intent.getStringExtra("FilmId")

        model.getFilmById(filmId)
        model.mutableFilm.observe(this, Observer { film ->
            run {
                Log.v("TAG ", "Filme ${film.species?.get(0)}")
            }

        })
    }
}
