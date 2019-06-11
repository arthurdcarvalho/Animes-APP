package br.com.arthur.appanimes.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arthur.appanimes.data.local.AnimeDatabase
import br.com.arthur.appanimes.data.repository.ConnectionException
import br.com.arthur.appanimes.data.repository.FilmRepository
import br.com.arthur.appanimes.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmViewModel(context: Context) : ViewModel() {

    private var filmRepository: FilmRepository

    var filmList: MutableLiveData<List<Film>> = MutableLiveData()
    var mutableFilm: MutableLiveData<Film> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        val dao = AnimeDatabase.getDatabase(context).getFilmDao()
        filmRepository = FilmRepository(dao, context)
        getFilms()
    }

    fun getFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                filmRepository.getFilms(success = {
                    filmList.postValue(it)
                }, failed = {
                    errorMessage.postValue(it)
                })
            } catch (e: ConnectionException) {
                errorMessage.postValue(e.error)
            }
        }
    }

    fun getFilmById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                filmRepository.getFilmById(id = id, success = { film ->
                    mutableFilm.postValue(film)
                }, failed = { error ->
                    errorMessage.postValue(error)
                })
            } catch (e: ConnectionException) {
                errorMessage.postValue(e.error)
            }
        }
    }


}