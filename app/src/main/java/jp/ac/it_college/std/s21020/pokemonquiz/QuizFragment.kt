package jp.ac.it_college.std.s21020.pokemonquiz

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import jp.ac.it_college.std.s21020.pokemonquiz.databinding.FragmentQuizBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.poke.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)


        val idpoke = args.idList
        val seikai = idpoke.random()
        showPokemonInfo(seikai)

        val qCount = args.qCount
        var score = args.score
        binding.tvQCount.text = getString(R.string.q_count, qCount)

        val list = listOf(
            binding.button,
            binding.button2,
            binding.button3,
            binding.button4
        ).shuffled()



        list[0].text = pokemon.pokemon.filter { p -> p.id == seikai }[0].name
        list[1].text = pokemon.pokemon.filter { p -> p.id != seikai }.random().name
        list[2].text = pokemon.pokemon.filter { p -> p.id != seikai }.random().name
        list[3].text = pokemon.pokemon.filter { p -> p.id != seikai }.random().name
        var clicked = false

        class ClickListener(val right: Boolean) : View.OnClickListener {

            override fun onClick(v: View?) {
                clicked = true
                if (right) {
                    score++
                } else {
                }

                if (qCount < 10) {
                    Navigation.findNavController(view).navigate(
                        QuizFragmentDirections.quizToQuiz(
                            args.idList
                        ).apply {
                            setQCount(args.qCount + 1)
                            setScore(score)
                        }
                    )
                } else {
                    Navigation.findNavController(view).navigate(
                        QuizFragmentDirections.quizToResult(score)
                    )
                }
            }
        }
        list[0].setOnClickListener(ClickListener(true))
        list[1].setOnClickListener(ClickListener(false))
        list[2].setOnClickListener(ClickListener(false))
        list[3].setOnClickListener(ClickListener(false))
        val h = Handler(Looper.getMainLooper())
        h.postDelayed(object : Runnable {
            var count = 10
            override fun run() {
                if (clicked) {
                    return
                }
                if (count <= 0) {
                    ClickListener(false).onClick(null)
                    return
                }
                binding.tvTimer.text = getString(R.string.timer, count)
                count--
                h.postDelayed(this, 1000L)
            }
        }, 0L)
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
    private suspend fun getPokemonInfo(id: Int): PokemonResponce {
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