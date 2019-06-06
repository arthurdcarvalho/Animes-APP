package br.com.arthur.appanimes.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arthur.appanimes.data.local.AnimeDatabase
import br.com.arthur.appanimes.data.repository.FilmRepository
import br.com.arthur.appanimes.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmViewModel(context: Context) : ViewModel() {

    private var filmRepository: FilmRepository

    var films: MutableLiveData<List<Film>> = MutableLiveData()
    var mutableFilm: MutableLiveData<Film> = MutableLiveData()

    init {
        val dao = AnimeDatabase.getDatabase(context).getFilmDao()
        filmRepository = FilmRepository(dao)
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch(Dispatchers.IO) {

            filmRepository.getFilms {
                films.postValue(it)
            }
        }
    }

    fun getFilmById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filmRepository.getFilmById(id) { film ->
                mutableFilm.postValue(film)
            }
        }
    }

}