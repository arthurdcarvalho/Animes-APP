package br.com.arthur.appanimes.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.arthur.appanimes.databinding.FilmListBinding
import br.com.arthur.appanimes.model.Film
import br.com.arthur.appanimes.ui.activities.FilmActivity


class FilmAdapter : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    private var list: List<Film>

    init {
        list = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val holder = FilmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = list[position]
        holder.bind.filmTitle.text = film.title
        holder.bind.filmDescription.text = film.description
        holder.bind.filmRate.text = "Pontuação: ${film.rtScore}"
        holder.bind.filmDate.text = "Data: ${film.releaseDate}"
        holder.bind.filmDirector.text = film.director
        holder.bind.filmProducer.text = film.producer
        holder.configureEvents(film)
    }

    fun setFilms(films: List<Film>) {
        list = films
    }

    inner class FilmViewHolder(internal var bind: FilmListBinding) : RecyclerView.ViewHolder(bind.root) {

        fun configureEvents(film: Film) {
            bind.root.setOnClickListener { cell ->
                val filmIntent = Intent(cell.context, FilmActivity::class.java)
                filmIntent.putExtra("FilmId", film.id)
                cell.context.startActivity(filmIntent)
                Toast.makeText(cell.context, "Item " + bind.filmTitle.text, Toast.LENGTH_LONG).show()
            }
        }
    }

}