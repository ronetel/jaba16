package com.example.pr16;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DescBookActivity extends AppCompatActivity {
    EditText editTextName, editTextAuthor;
    Button buttonDelete, buttonSave;
    DataBaseHelper dbHelper;
    int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_book);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonSave = findViewById(R.id.buttonUpdate);

        dbHelper = new DataBaseHelper(this);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        if (bookId == -1) {
            Toast.makeText(this, "Ошибка загрузки книги", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadBookDetails();

        buttonDelete.setOnClickListener(v -> {
            dbHelper.deleteBookById(bookId);
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            finish();
        });

        buttonSave.setOnClickListener(v -> {
            String newName = editTextName.getText().toString().trim();
            String newAuthor = editTextAuthor.getText().toString().trim();

            if (newName.isEmpty() || newAuthor.isEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = dbHelper.updateBook(bookId, newName, newAuthor);
            if (isUpdated) {
                Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при обновлении книги", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBookDetails() {
        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
            editTextName.setText(name);
            editTextAuthor.setText(author);
            cursor.close();
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}