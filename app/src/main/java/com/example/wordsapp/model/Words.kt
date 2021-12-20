package com.example.wordsapp.model

class Words {
    var id: Int = 0
    var words:String = ""
    var translate: String = ""


    constructor(words: String, translate: String) {
        this.words = words
        this.translate = translate
    }

    constructor(id: Int, words: String, translate: String) {
        this.id = id
        this.words = words
        this.translate = translate
    }

}
