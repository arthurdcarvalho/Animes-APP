package br.com.arthur.appanimes.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arthur.appanimes.data.local.AnimeDatabase
import br.com.arthur.appanimes.data.local.dao.PeopleDao
import br.com.arthur.appanimes.data.repository.PeopleRepository
import br.com.arthur.appanimes.model.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeopleViewModel(val context: Context) : ViewModel() {

    private var peopleRepository: PeopleRepository

    var peopleList: MutableLiveData<List<People>> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        val dao: PeopleDao = AnimeDatabase.getDatabase(context).getPeopleDao()
        peopleRepository = PeopleRepository(context, dao)
    }

    fun getPeoples() {
        viewModelScope.launch(Dispatchers.IO) {
            peopleRepository.getPeoples({ peoples ->
                peopleList.postValue(peoples)
            }, { error ->
                errorMessage.postValue(error)
            })
        }
    }

}