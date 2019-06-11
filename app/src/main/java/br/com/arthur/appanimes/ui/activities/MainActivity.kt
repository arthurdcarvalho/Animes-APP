package br.com.arthur.appanimes.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arthur.appanimes.R
import br.com.arthur.appanimes.databinding.ActivityMainBinding
import br.com.arthur.appanimes.ui.adapters.FilmAdapter
import br.com.arthur.appanimes.ui.viewmodel.FilmViewModel

class MainActivity : BaseView() {

    private lateinit var bind: ActivityMainBinding

    private lateinit var model: FilmViewModel

    private var filmAdapter = FilmAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureData()
    }

    override fun onResume() {
        super.onResume()
        showLoadingDialog()
        configureClicks()
        if (!model.filmList.hasObservers()) {
            model.filmList.observe(this, Observer { films ->
                dismissLoadingDialog()
                bind.filmList.visibility = View.VISIBLE
                bind.errorView.visibility = View.GONE
                filmAdapter.setFilms(films)
            })
            model.errorMessage.observe(this, Observer { error ->
                dismissLoadingDialog()
                bind.filmList.visibility = View.GONE
                bind.errorView.visibility = View.VISIBLE
                bind.errorView.text = error
                Log.e(MainActivity::class.java.canonicalName, " Error $error")
            })
            model.getFilms()
        }
    }

    override fun onPause() {
        super.onPause()
        model.filmList.removeObservers(this)
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
    }

    private fun configureClicks() {
        bind.errorView.setOnClickListener {
            showLoadingDialog()
            model.getFilms()
        }
    }
}
