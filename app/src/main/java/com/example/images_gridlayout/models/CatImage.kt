package com.example.images_gridlayout.models

class CatImage(
    var id: String,
    var url: String,
    var categories: List<CatsCategory>? = null,
    var breeds: List<CatBreed>? = null
) {
    override fun toString(): String {
        return String.format(
            "id: %s\nurl: %s\ncategories: %s\nbreeds: %s",
            id,
            url,
            if (categories != null) categories.toString() else null,
            if (breeds != null) breeds.toString() else null
        )
    }
}