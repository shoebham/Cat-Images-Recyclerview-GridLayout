package com.example.images_gridlayout.models

/**
 * Model class for Cat Category
 */
class CatsCategory(
    var id: Int = 0,
    var name: String
) {


    override fun toString(): String {
        return String.format("id: %s, name:%s", id, name)
    }
}