package dev.kuromiichi.examenfirebase.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.kuromiichi.examenfirebase.R
import dev.kuromiichi.examenfirebase.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.toolbar.inflateMenu(R.menu.menu_toolbar)
        binding.toolbar.title = auth.currentUser?.email
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.actionLogout) {
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            false
        }
        return true
    }
}