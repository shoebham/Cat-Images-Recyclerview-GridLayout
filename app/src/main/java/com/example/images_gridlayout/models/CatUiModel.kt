package com.example.images_gridlayout.models

sealed class CatUiModel {
    class CatItem(val catImage: CatImage) : CatUiModel()
    class CatHeader(val text: String) : CatUiModel()
}