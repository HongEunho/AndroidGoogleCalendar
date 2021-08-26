package com.example.architecturepractice.signin

import android.accounts.Account
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.architecturepractice.R
import com.example.architecturepractice.calendar.CalendarActivity
import com.example.architecturepractice.data.network.ApiProvider
import com.example.architecturepractice.databinding.ActivityLoginBinding
import com.example.architecturepractice.di.AppViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import com.google.firebase.auth.*
import dagger.android.support.DaggerAppCompatActivity
import java.lang.Exception
import javax.inject.Inject

public class LoginActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by viewModels<LoginViewModel> {viewModelFactory}

    lateinit var binding: ActivityLoginBinding
    private fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    private lateinit var credential: AuthCredential
    private lateinit var googleCredential: GoogleAccountCredential

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val gsc by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                task.getResult(ApiException::class.java)?.let { account->
                    viewModel.saveToken(account.idToken ?: throw Exception())
                } ?: throw Exception()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        checkLastLogin()
        observeData()
        initViews()
    }

    private fun initViews() = with(binding) {
        signInButton.setOnClickListener {
            signInGoogle()
        }
    }

    private fun handleLoadingState() = with(binding) {
    }

    private fun handleSuccessState(state: ProfileState.Success) = with(binding) {
        when (state) {
            is ProfileState.Success.Registered -> {
                handleRegisteredState(state)
            }
            is ProfileState.Success.NotRegistered -> {

            }
        }
    }

    private fun handleLoginState(state: ProfileState.Login) = with(binding) {
        credential = GoogleAuthProvider.getCredential(state.idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@LoginActivity) { task->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    createGoogleCredential(user)
                    viewModel.setUserInfo(user)

                } else {
                    viewModel.setUserInfo(null)
                    Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun handleRegisteredState(state: ProfileState.Success.Registered) {
        passToCalendarActivity(autoLogin = false)
    }

    private fun handleErrorState() {
        Toast.makeText(this, "오류가 발생했습니다. 잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
    }

    private fun signInGoogle() {
        val signInIntent = gsc.signInIntent
        loginLauncher.launch(signInIntent)
    }

    private fun checkLastLogin() {
        if(firebaseAuth.currentUser != null) {
            createGoogleCredential(firebaseAuth.currentUser)
            passToCalendarActivity(autoLogin = true)
        }
    }

    private fun passToCalendarActivity(autoLogin: Boolean) {
        intent = Intent(this@LoginActivity, CalendarActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(getString(R.string.autoLogin), autoLogin)
        }
        startActivity(intent)
        finish()
    }

    private fun createGoogleCredential(user: FirebaseUser?) {
        googleCredential = GoogleAccountCredential.usingOAuth2(
            applicationContext, listOf(CalendarScopes.CALENDAR)
        ).setBackOff(ExponentialBackOff())

        googleCredential.selectedAccount = Account(user?.email,packageName)
        createService(googleCredential)
    }

    private fun createService(credential: GoogleAccountCredential) {
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        ApiProvider.createService(transport, jsonFactory, credential)
    }

    private fun observeData() = viewModel.profileStateLiveData.observe(this) {
        when (it) {
            is ProfileState.Uninitialized -> initViews()
            is ProfileState.Loading -> handleLoadingState()
            is ProfileState.Login -> handleLoginState(it)
            is ProfileState.Success -> handleSuccessState(it)
            is ProfileState.Error -> handleErrorState()
        }
    }

}