package com.example.images_gridlayout.models

/**
 * Sealed class which is used to convert received data to Item or Header
 */
sealed class CatUiModel {
    class CatItem(val catImage: CatImage) : CatUiModel()
    class CatHeader(val text: String) : CatUiModel()
}