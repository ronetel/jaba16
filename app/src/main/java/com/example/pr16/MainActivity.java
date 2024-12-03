package com.example.pr16;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    private ArrayList<Book> bookArrayList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        bookArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this, bookArrayList, bookId -> {
            Intent intent = new Intent(MainActivity.this, DescBookActivity.class);
            intent.putExtra("BOOK_ID", bookId);
            startActivity(intent);

        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_book);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivityForResult(intent, 1);
        });
        loadBooks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadBooks();
        }
    }

    private void loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
                books.add(new Book(id, name, author));
            }
            cursor.close();
        }

        adapter.updateBookList(books);
    }


    @Override
    protected void onResume(){
        super.onResume();
        loadBooks();
    }
}