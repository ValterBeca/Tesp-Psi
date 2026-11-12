package com.example.books.adaptadores;
import com.example.books.modelo.Livro;                  // classe Livro

// 5.7.1 Interface: "contrato" que o fragmento tem de cumprir para saber qual o livro clicado
public interface OnLivroClickListener {
    void onLivroClick(Livro livro);                     // chamado quando se clica numa linha
}