package br.com.arthur.appanimes.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arthur.appanimes.R
import br.com.arthur.appanimes.databinding.ActivityFilmBinding
import br.com.arthur.appanimes.model.Film
import br.com.arthur.appanimes.model.People
import br.com.arthur.appanimes.ui.adapters.SimplePeopleAdapter
import br.com.arthur.appanimes.ui.viewmodel.FilmViewModel
import br.com.arthur.appanimes.ui.viewmodel.PeopleViewModel

class FilmActivity : AppCompatActivity() {

    private lateinit var bind: ActivityFilmBinding

    private lateinit var filmViewModel: FilmViewModel
    private lateinit var peopleViewModel: PeopleViewModel

    private var peopleAdapter = SimplePeopleAdapter()

    private lateinit var film: Film

    companion object {
        val debug_tag = " ${FilmActivity::class.java.name} - DEBUG TAG : "
        lateinit var FILM_ID: String
        const val KEY_FOR_FILM_ID = "FilmId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_film)
    }

    override fun onResume() {
        super.onResume()
        configureData()
        configureObservers()
        requestInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        filmViewModel.mutableFilm.removeObservers(this)
    }

    private fun configureData() {
        FILM_ID = intent.getStringExtra(KEY_FOR_FILM_ID)
        filmViewModel = FilmViewModel(this)
        peopleViewModel = PeopleViewModel(this)

        bind.detailFilmPeoples.layoutManager = object : LinearLayoutManager(this) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams? {
                return null
            }
        }
        bind.detailFilmPeoples.itemAnimator = DefaultItemAnimator()
        bind.detailFilmPeoples.adapter = peopleAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun configureObservers() {
        filmViewModel.mutableFilm.observe(this, Observer { film ->
            this.film = film
            run {
                Log.v("$debug_tag -> First Debug", "Filme ${film.species?.get(0)}")
                bind.detailFilmTitle.text = film.title
                bind.detailFilmDate.text = film.releaseDate
                bind.detailFilmScore.text = "Score: ${film.rtScore}%"
                bind.detailFilmDirector.text = film.director
                bind.detailFilmProducer.text = film.producer
                bind.detailFilmDescription.text = film.description
            }
        })

        peopleViewModel.peopleList.observe(this, Observer { peoples ->
            val peoplesForAdapter = ArrayList<People>()
            for (people in peoples) {
                if (people.films?.get(0)?.equals(film.url)!!) {
                    peoplesForAdapter.add(people)
                }
                Log.v("$debug_tag -> Peoples List Debug", " Name: ${people.name}")
            }
            peopleAdapter.setPeoples(peoplesForAdapter)
        })

        peopleViewModel.errorMessage.observe(this, Observer { error ->
            Log.e("$debug_tag -> Error Debug", " Erro $error")
        })

    }

    private fun requestInfo() {
        filmViewModel.getFilmById(FILM_ID)
        peopleViewModel.getPeoples()
    }

}
