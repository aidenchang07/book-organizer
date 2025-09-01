package com.example.starterbookorganizer.domain.model

import com.example.starterbookorganizer.presentation.add_book.Category

/**
 * Created by AidenChang on 2025/9/1
 */
data class Book(
    val title: String?,
    val author: String?,
    val publicationYear: Int,
    val isbn: String?,
    val genre: String?,
    val status: Category?
) {
    fun toEntity(): Book {
        return Book(
            title = title ?: "",
            author = author ?: "",
            publicationYear = publicationYear,
            isbn = isbn ?: "",
            genre = genre ?: "",
            status = status ?: Category.WISHLIST
        )
    }
}
