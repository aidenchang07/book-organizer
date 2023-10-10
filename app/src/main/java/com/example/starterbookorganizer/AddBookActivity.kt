package com.example.starterbookorganizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddBookActivity : AppCompatActivity() {

    private lateinit var bookTitleEditText: EditText
    private lateinit var bookAuthorEditText: EditText
    private lateinit var bookPublicationYearEditText: EditText
    private lateinit var bookIsbnEditText: EditText
    private lateinit var bookGenreEditText: EditText
    private lateinit var statusRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        bookTitleEditText = findViewById(R.id.bookTitleEditText)
        bookAuthorEditText = findViewById(R.id.bookAuthorEditText)
        bookPublicationYearEditText = findViewById(R.id.bookPublicationYearEditText)
        bookIsbnEditText = findViewById(R.id.bookIsbnEditText)
        bookGenreEditText = findViewById(R.id.bookGenreEditText)
        statusRadioGroup = findViewById(R.id.statusRadioGroup)


        findViewById<Button>(R.id.saveBookButton).setOnClickListener {
            val title = bookTitleEditText.text.toString()
            val author = bookAuthorEditText.text.toString()
            val publicationYear = bookPublicationYearEditText.text.toString().toIntOrNull()
            val isbn = bookIsbnEditText.text.toString()
            val genre = bookGenreEditText.text.toString()

            val selectedStatus: String = when(statusRadioGroup.checkedRadioButtonId) {
                R.id.readRadioButton -> "READ"
                R.id.readingRadioButton -> "READING"
                R.id.wishlistRadioButton -> "WISHLIST"
                else -> ""  // Default value or error handling if needed
            }
            // Here you should add logic to save this book information, for example:
            // - Save to a local database
            // - Send to a server
            // For this example, we're just returning the title as a result:

            val resultIntent = Intent().apply {
                putExtra("title", title)
                putExtra("author", author)
                putExtra("publicationYear", publicationYear)
                putExtra("isbn", isbn)
                putExtra("genre", genre)
                putExtra("status", selectedStatus)

            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
