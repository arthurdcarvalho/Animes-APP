package br.com.arthur.appanimes.ui.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity

abstract class BaseView : AppCompatActivity() {

    private lateinit var loadingDialog: ProgressDialog

    protected fun showLoadingDialog() {
        loadingDialog = ProgressDialog.show(this, "", "Carregando", true)
        loadingDialog.setCancelable(true)
    }

    protected fun dismissLoadingDialog() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

}