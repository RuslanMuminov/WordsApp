package com.example.wordsapp.ui

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordsapp.R
import com.example.wordsapp.adapter.OnClick
import com.example.wordsapp.adapter.WordsListAdapter
import com.example.wordsapp.database.DbHelper
import com.example.wordsapp.databinding.FragmentHomeBinding
import com.example.wordsapp.model.Words
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), OnClick {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: WordsListAdapter
    private lateinit var dbHelper: DbHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        dbHelper = DbHelper(requireContext())

        setData(dbHelper.listOfWords())

        binding.fabAddBtn.setOnClickListener {
            findNavController().navigate(R.id.nav_slideshow)
        }

        return binding.root
    }
    private fun setData(list: List<Words>){
        adapter =WordsListAdapter(this)
        adapter.list = list
        binding.rv.adapter = adapter
    }

    override fun onItemViewClick(wordd: Words) {

        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogView = layoutInflater.inflate(R.layout.menu_dialog, null, false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val wordsEnglish = dialogView.findViewById<TextView>(R.id.word)
        val translateWord = dialogView.findViewById<TextView>(R.id.translate)
        val deleteWord = dialogView.findViewById<LinearLayout>(R.id.delete)
        val editWord = dialogView.findViewById<LinearLayout>(R.id.edit)
        val shareWord = dialogView.findViewById<LinearLayout>(R.id.share)

        wordsEnglish.text = wordd.words
        translateWord.text = wordd.translate

        deleteWord.setOnClickListener {
            dbHelper.deleteWord(wordd)
            dialog.cancel()
            setData(dbHelper.listOfWords())
        }

        editWord.setOnClickListener {
            editDialog(wordd)
            dialog.cancel()
        }

        shareWord.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, wordd.words + "\n" + wordd.translate)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
            dialog.cancel()
        }

        dialog.setView(dialogView)
        dialog.show()
    }

    fun editDialog(soz: Words)
    {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogView =layoutInflater.inflate(R.layout.edit_dialog, null, false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val word = dialogView.findViewById<TextView>(R.id.wordEdit)
        val translate = dialogView.findViewById<TextView>(R.id.translateEdit)
        val save = dialogView.findViewById<Button>(R.id.saveEdit)

        word.text = soz.words
        translate.text = soz.translate

        save.setOnClickListener {
            var list = dbHelper.listOfWords()
            val word = dialogView.findViewById<TextView>(R.id.wordEdit).text.toString()
            val translate = dialogView.findViewById<TextView>(R.id.translateEdit).text.toString()

            var index = -1

            if (word.trim().isNotEmpty() && translate.trim().isNotEmpty()) {
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
                else {
                    val wordObj = Words(word, translate)
                    dbHelper.edit(wordObj, soz.id)
                    dialog.cancel()

                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Word or Translate not added !!!", Toast.LENGTH_SHORT).show()
            }
            dialog.cancel()
            setData(dbHelper.listOfWords())
        }
        dialog.setView(dialogView)
        dialog.show()
    }
}