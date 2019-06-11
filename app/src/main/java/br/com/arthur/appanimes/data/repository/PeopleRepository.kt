package br.com.arthur.appanimes.data.repository

import android.content.Context
import br.com.arthur.appanimes.data.local.dao.PeopleDao
import br.com.arthur.appanimes.data.remote.ServiceFactory
import br.com.arthur.appanimes.model.People

class PeopleRepository(context: Context, private val dao: PeopleDao) : BaseRepository(context) {

    private val service = ServiceFactory.getService()

    @Throws(ConnectionException::class)
    suspend fun getPeoples(success: (List<People>) -> Unit, failed: (String) -> Unit) {
        val peoples = dao.getPeoples()
        when {
            peoples.isNotEmpty() -> success(peoples)
            checkConnection() -> {
                val response = service.getPeoplesAsync().await()
                if (response.isSuccessful) {
                    response.body().let { peoplesResponse ->
                        peoplesResponse?.forEach { people ->
                            run {
                                dao.saveFilm(people)
                                success(peoplesResponse)
                            }
                        }
                    }
                } else failed("Não foi possivel obter a lista de pessoas")
            }
            else -> throw ConnectionException("Sem conexão com a internet")
        }
    }

}
