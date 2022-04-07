package com.example.images_gridlayout.models

import com.google.gson.annotations.SerializedName

class CatBreed {
    var id: String = ""
    var name: String = ""

    @SerializedName("life_span")
    var lifespan: String = ""
    var origin: String = ""

    @SerializedName("wikipedia_url")
    var wikipediaUrl: String = ""

    override fun toString(): String {
        return String.format(
            "id: %s, name: %s, " +
                    "lifespan: %s, origin: %s, wikipediaUrl: %s",
            id, name, lifespan, origin, wikipediaUrl
        )
    }
}