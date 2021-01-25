package com.example.urbandictionary.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class WordDefinition (

    @ColumnInfo(name = "definition")
    @SerializedName("definition")
    var definition: String? = null,

    @ColumnInfo(name = "permalink")
    @SerializedName("permalink")
    var permalink: String? = null,

    @ColumnInfo(name = "thumbs_up")
    @SerializedName("thumbs_up")
    var thumbsUp: Int? = null,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String? = null,

    @ColumnInfo(name = "word")
    @SerializedName("word")
    var word: String? = null,

    @PrimaryKey
    @SerializedName("defid")
    var defid: Int? = null,

    @ColumnInfo(name = "current_vote")
    @SerializedName("current_vote")
    var currentVote: String? = null,

    @ColumnInfo(name = "written_on")
    @SerializedName("written_on")
    var writtenOn: String? = null,

    @ColumnInfo(name = "example")
    @SerializedName("example")
    var example: String? = null,

    @ColumnInfo(name = "thumbs_down")
    @SerializedName("thumbs_down")
    var thumbsDown: Int? = null
)
