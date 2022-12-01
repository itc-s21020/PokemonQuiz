package jp.ac.it_college.std.s21020.pokemonquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import jp.ac.it_college.std.s21020.pokemonquiz.databinding.ActivityMainBinding
import java.io.InputStreamReader

lateinit var pokemon: PokemonJson

lateinit var pokedex: PokedexJson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputStream =
            assets?.open("ordered_pokemon.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        pokemon = Gson().fromJson(jsonReader, PokemonJson::class.java)

        val inputStream2 =
            assets?.open("filtered_pokedex.json")
        val jsonReader2 = InputStreamReader(inputStream2, "UTF-8").readText()
        pokedex = Gson().fromJson(jsonReader2, PokedexJson::class.java)

    }

    override fun onBackPressed() {}
}