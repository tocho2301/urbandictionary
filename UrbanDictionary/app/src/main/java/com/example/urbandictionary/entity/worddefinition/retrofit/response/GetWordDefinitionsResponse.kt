package com.example.urbandictionary.entity.worddefinition.retrofit.response

import com.example.urbandictionary.entity.WordDefinition

data class GetWordDefinitionsResponse(
    var list: ArrayList<WordDefinition>,
    var error : String)

