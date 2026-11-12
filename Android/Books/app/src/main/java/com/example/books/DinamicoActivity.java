package com.example.books;                              // package da app

import android.os.Bundle;                               // estado da atividade


import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

import com.example.books.databinding.ActivityDinamicoBinding;
import com.example.books.modelo.Livro;                  // classe Livro (package modelo)
import com.example.books.modelo.SingletonGestorLivros;  // Singleton (package modelo)

import java.util.ArrayList;                             // para a lista de livros

public class DinamicoActivity extends AppCompatActivity {

    private ActivityDinamicoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        binding = ActivityDinamicoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarLivro();
    }

    public void carregarLivro() {
        // Modo DINÂMICO: os dados vêm do Singleton
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros(); // obtém a instância única e pede a lista
        Livro livro;                     // escolhe o primeiro livro da lista (posição 0)
        if (livros != null && livros.size() > 0) {
            livro = livros.get(0);
            binding.tvTitulo.setText(livro.getTitulo());            // mostra o título
            binding.tvSerie.setText(livro.getSerie());              // mostra a série
            binding.tvAutor.setText(livro.getAutor());              // mostra o autor
            binding.tvAno.setText(String.valueOf(livro.getAno() + ""));  // ATENÇÃO: o ano é int, tem de ser convertido para String!
            binding.ivCapa.setImageResource(livro.getCapa());
        }
    }
}
