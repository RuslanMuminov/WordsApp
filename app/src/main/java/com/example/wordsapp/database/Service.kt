package com.example.wordsapp.database

import com.example.wordsapp.model.Words

interface Service {

    fun addWord(words: Words)
    fun listOfWords(): ArrayList<Words>
    fun deleteWord(words: Words)
    fun edit(words: Words, id: Int)

}