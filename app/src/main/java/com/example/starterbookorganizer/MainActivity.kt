package com.example.starterbookorganizer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.starterbookorganizer.domain.model.Book
import com.example.starterbookorganizer.presentation.add_book.AddBookActivity
import com.example.starterbookorganizer.presentation.add_book.Category

const val REQUEST_CODE_ADD_BOOK = 1001
class MainActivity : AppCompatActivity() {

    inner class BookAdapter(context: Context, private val books: List<Book>) : ArrayAdapter<Book>(context, 0, books) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val book = getItem(position)
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false)

            val titleTextView: TextView = view.findViewById(R.id.bookTitle)
            val authorTextView: TextView = view.findViewById(R.id.bookAuthor)
            val publicationYearTextView: TextView = view.findViewById(R.id.bookPublicationYear)
            val isbnTextView: TextView = view.findViewById(R.id.bookIsbn)
            val genreTextView: TextView = view.findViewById(R.id.bookGenre)

            titleTextView.text = book?.title
            authorTextView.text = book?.author
            publicationYearTextView.text = book?.publicationYear?.toString()
            isbnTextView.text = book?.isbn
            genreTextView.text = book?.genre

            return view
        }
    }
    private lateinit var categorySpinner: Spinner
    private lateinit var bookListView: ListView
    private lateinit var books: MutableList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        books = mutableListOf()
        categorySpinner = findViewById(R.id.categorySpinner)
        bookListView = findViewById(R.id.bookListView)

        val categories = listOf(Category.READING, Category.READ, Category.WISHLIST)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Fetch the books based on the selected category and update `books` list.
                // Update the bookListView with the filtered books.
                val selectedCategory = categories[position]
                // 過濾 books 列表
                val filteredBooks = books.filter { it.status == selectedCategory }
                val bookAdapter = BookAdapter(this@MainActivity, filteredBooks)
                bookListView.adapter = bookAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        bookListView.setOnItemClickListener { _, _, position, _ ->
            // Handle book item click, navigate to details/edit page.
        }

        findViewById<Button>(R.id.addBookButton).setOnClickListener {
            // Handle add book click, navigate to add book page.
            val intent = Intent(this@MainActivity, AddBookActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_BOOK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 將書籍資料賦值給 bookListView 變數
        bookListView.adapter = BookAdapter(this, books)

        if (resultCode == Activity.RESULT_OK) {
            // Handle the result from book details/add page, update the `books` list and the bookListView.
            // Check if the data Intent is not null
            data?.let {
                if (it.hasExtra("title")) {
                    val title = it.getStringExtra("title")
                    val author = it.getStringExtra("author")
                    val publicationYear = it.getIntExtra("publicationYear", -1)
                    val isbn = it.getStringExtra("isbn")
                    val genre = it.getStringExtra("genre")

                    val statusName = it.getStringExtra("status")
                    val status = Category.valueOf(statusName!!)

                    val newBook = Book(title!!, author!!, publicationYear, isbn!!, genre!!, status)
                    books.add(newBook)

                    // Notify the ListView's adapter of the data change
                    (bookListView.adapter as ArrayAdapter<Book>).notifyDataSetChanged()
                }
            }
        }
    }
}