package jp.ac.it_college.std.s21020.pokemonquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s21020.pokemonquiz.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args: ResultFragmentArgs by navArgs()



    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {

        _binding = FragmentResultBinding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            Navigation.findNavController(it).navigate(
                ResultFragmentDirections.resultToSelect()
            )
        }
        val score = args.score
        binding.resultCount.text = getString(R.string.score, score)

        return binding.root
    }
}