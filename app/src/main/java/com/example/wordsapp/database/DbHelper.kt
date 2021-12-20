package com.example.wordsapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.wordsapp.model.Words

class DbHelper(context: Context) : SQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION), Service {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table ${Constants.TABLE_NAME}(${Constants.ID} Integer primary key autoincrement, ${Constants.WORD} text, ${Constants.TRANSLATE} text)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addWord(words: Words) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constants.WORD, words.words)
        contentValues.put(Constants.TRANSLATE, words.translate)
        db.insert(Constants.TABLE_NAME, null, contentValues)
        db.close()
    }

    override fun listOfWords(): ArrayList<Words> {

        var list = ArrayList<Words>()
        var query = "select *from ${Constants.TABLE_NAME}"
        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {

                val id = cursor.getInt(0)
                val word = cursor.getString(1)
                val translate = cursor.getString(2)
                val wordObj = Words(id, word, translate)
                list.add(wordObj)

            } while (cursor.moveToNext())
        }
        return list
    }

    override fun deleteWord(words: Words) {

        val db = this.readableDatabase
        db.delete(Constants.TABLE_NAME, Constants.WORD + " LIKE ?", arrayOf(words.words))
        db.close()
    }

    override fun edit(words: Words, id: Int) {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.WORD, words.words)
        contentValues.put(Constants.TRANSLATE, words.translate)
        db.update(Constants.TABLE_NAME, contentValues, "${Constants.ID} = $id", null)
        db.close()
    }
}