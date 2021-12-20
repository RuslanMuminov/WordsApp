package com.example.wordsapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wordsapp.database.DbHelper
import com.example.wordsapp.databinding.FragmentSlideshowBinding
import com.example.wordsapp.model.Words

class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private lateinit var dbHelper:DbHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        dbHelper = DbHelper(requireContext())

        var list = dbHelper.listOfWords()
        binding.imgSave.setOnClickListener {
            val word = binding.englishWord.text.toString()
            val translate = binding.translateWord.text.toString()

            var index = -1
            if (word.trim().isNotBlank() && translate.trim().isNotEmpty()) {

                for (i in 0 until list.size)
                {
                    if (word.equals(list[i].words, true))
                    {
                        index = 1
                    }
                }
                if (index > -1)
                {
                    Toast.makeText(requireContext(), "Exist word", Toast.LENGTH_SHORT).show()
                }
                else{
                    val wordObj = Words(word, translate)
                    dbHelper.addWord(wordObj)
                    binding.englishWord.text.clear()
                    binding.translateWord.text.clear()

                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Word or Translate not added !!!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}