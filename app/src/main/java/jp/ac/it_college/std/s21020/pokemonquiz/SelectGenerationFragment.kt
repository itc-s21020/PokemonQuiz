package jp.ac.it_college.std.s21020.pokemonquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s21020.pokemonquiz.databinding.FragmentSelectGenerationBinding

class SelectGenerationFragment : Fragment() {
    private var _binding: FragmentSelectGenerationBinding? = null
    private val binding get() = _binding!!
    private val args: SelectGenerationFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectGenerationBinding.inflate(inflater, container, false)
//        val list = json.pokedex.map { g -> g.name }
        val list = pokedex.pokedex
        for (i in list) {
            val button = Button(binding.root.context)
            button.text = i.name
            binding.generations.addView(button)
            button.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    SelectGenerationFragmentDirections.generationToQuiz(
                        i.entries.map { p -> p.pokemon_id }.toIntArray(),
                        args.hardmode
                    )
                )
            }

        }
        return binding.root
    }
}