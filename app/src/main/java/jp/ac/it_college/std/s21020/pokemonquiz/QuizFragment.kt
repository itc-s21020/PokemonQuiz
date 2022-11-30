package jp.ac.it_college.std.s21020.pokemonquiz

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import jp.ac.it_college.std.s21020.pokemonquiz.databinding.FragmentQuizBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader

private const val BASE_URL = "https://pokeapi.co/api/v2/"

class QuizFragment : Fragment() {
    private val args: QuizFragmentArgs by navArgs()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.poke.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)

        val idpoke = args.idList
        val seikai = idpoke.random()
        showPokemonInfo(seikai)

        binding.button.text = pokemon.pokemon.filter { p -> p.id == seikai }[0].name

        binding.button2.text = pokemon.pokemon.filter { p -> p.id != seikai }.random().name
        binding.button3.text = pokemon.pokemon.filter { p -> p.id != seikai }.random().name
        binding.button4.text = pokemon.pokemon.filter { p -> p.id != seikai }.random().name



        return binding.root
    }
    @UiThread
    private fun showPokemonInfo(id: Int) {
        lifecycleScope.launch {
            val info = getPokemonInfo(id)
            val url = info.sprites.other.officialArtwork.frontDefault
            Picasso.get().load(url).into(binding.poke)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @WorkerThread
    private  suspend fun getPokemonInfo(id: Int): PokemonResponce{
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
            }.build()
            val service: PokeService = retrofit.create(PokeService::class.java)
            service.fetchPoke(id).execute().body() ?: throw IllegalStateException("ポケモンが見つかりません")
        }
    }
}