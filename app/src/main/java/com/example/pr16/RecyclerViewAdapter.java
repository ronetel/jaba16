package com.example.pr16;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookArrayList;
    private OnBookClickListener onBookClickListener;

    public interface OnBookClickListener {
        void onBookClick(int bookId);
    }

    public RecyclerViewAdapter(Context context, ArrayList<Book> bookArrayList, OnBookClickListener listener) {
        this.context = context;
        this.bookArrayList = bookArrayList;
        this.onBookClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.bookName.setText(book.getBook_Name());
        holder.bookAuthor.setText(book.getBook_Author());

        holder.itemView.setOnClickListener(v -> {
            if (onBookClickListener != null) {
                onBookClickListener.onBookClick(book.getID_Book());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public void updateBookList(ArrayList<Book> newBookList) {
        this.bookArrayList.clear();
        this.bookArrayList.addAll(newBookList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName, bookAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.b_name);
            bookAuthor = itemView.findViewById(R.id.b_author);
        }
    }
}
