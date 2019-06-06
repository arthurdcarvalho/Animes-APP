package br.com.arthur.appanimes.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arthur.appanimes.R
import br.com.arthur.appanimes.databinding.ActivityMainBinding
import br.com.arthur.appanimes.model.Film
import br.com.arthur.appanimes.ui.FilmViewModel
import br.com.arthur.appanimes.ui.adapters.FilmAdapter

class MainActivity : BaseView() {

    private lateinit var bind: ActivityMainBinding

    private lateinit var model: FilmViewModel

    private var filmAdapter = FilmAdapter()

    private lateinit var filmList: List<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureData()
    }

    override fun onResume() {
        super.onResume()
        showLoadingDialog()
        if (!model.films.hasObservers()) {
            model.films.observe(this, Observer { films ->
                dismissLoadingDialog()
                filmAdapter.setFilms(films)
                filmAdapter.notifyDataSetChanged()
                filmList = films
            })
        }
        if (filmList.isEmpty()) {
            model.getFilms()
        }
    }

    override fun onPause() {
        super.onPause()
        model.films.removeObservers(this)
    }

    private fun configureData() {
        model = FilmViewModel(this)
        bind.filmList.layoutManager = object : LinearLayoutManager(this) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams? {
                return null
            }
        }
        bind.filmList.itemAnimator = DefaultItemAnimator()
        bind.filmList.adapter = filmAdapter
        filmList = ArrayList()
    }
}
