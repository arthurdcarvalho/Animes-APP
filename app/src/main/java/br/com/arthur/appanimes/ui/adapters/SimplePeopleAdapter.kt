package br.com.arthur.appanimes.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.arthur.appanimes.model.People

class SimplePeopleAdapter : RecyclerView.Adapter<SimplePeopleAdapter.SimplePeopleViewHolder>() {

    private var peoples: List<People>

    init {
        peoples = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimplePeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return SimplePeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimplePeopleViewHolder, position: Int) {
        val people = peoples[position]
        holder.filmeName.text = people.name
        holder.configureClick(people)
    }

    override fun getItemCount(): Int {
        return peoples.size
    }

    fun setPeoples(peoplesList: List<People>) {
        peoples = peoplesList
        this.notifyDataSetChanged()
    }

    inner class SimplePeopleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var filmeName: TextView = view.findViewById(android.R.id.text1)

        fun configureClick(people: People) {
            filmeName.setOnClickListener {
                Toast.makeText(itemView.context, people.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

}