package dev.kuromiichi.retrofitapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dev.kuromiichi.retrofitapi.databinding.ActivityMainBinding
import dev.kuromiichi.retrofitapi.services.DogService
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thedogapi.com/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: DogService by lazy { retrofit.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestDog()

        binding.button.setOnClickListener {
            requestDog()
        }
    }

    private fun requestDog() {
        lifecycleScope.launch {
            val dogs = service.getDogs(
                mapOf(
                    "api_key" to "live_FIgKoSUNS3MgM5NQo6D9mIwGmRRon7GhYh3Am7LHhqleEB0mKgbCwZUQcCJwn1Zw",
                    "limit" to "1",
                    "has_breeds" to "1"
                )
            )

            val doggie = dogs.first()

            Glide.with(this@MainActivity)
                .load(doggie.url)
                .placeholder(R.drawable.dog_placeholder)
                .into(binding.image)

            binding.raza.text = doggie.breeds.first().name
            binding.temperamento.text = doggie.breeds.first().temperament
        }
    }
}