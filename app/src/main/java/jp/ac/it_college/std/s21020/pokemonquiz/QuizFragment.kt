package jp.ac.it_college.std.s21020.pokemonquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import jp.ac.it_college.std.s21020.pokemonquiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private val args: QuizFragmentArgs by navArgs()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        Log.i("test", args.idList.joinToString(","))

        return binding.root
    }
}