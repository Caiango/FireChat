package com.example.firechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


class SignInActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1

    private val signInProviders =
        listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .setAllowNewAccounts(true)
                .setRequireName(true)
                .build()
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        account_sign_in.setOnClickListener {

            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .setLogo(R.drawable.common_full_open_on_phone)
                .build()
            startActivityForResult(intent, RC_SIGN_IN)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("COnfigurando sua Conta")
                    //TODO: inicializar o firebase
                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                    progressDialog.dismiss()

            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK ->
                        signInLay.longSnackbar("Sem conexão com a internet")
                    ErrorCodes.UNKNOWN_ERROR ->
                        signInLay.longSnackbar("Erro desconhecido")
                }
            }
        }
    }


}





