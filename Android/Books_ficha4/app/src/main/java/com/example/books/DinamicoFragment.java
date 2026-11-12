package com.example.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.books.databinding.FragmentDinamicoBinding; // gerada a partir de fragment_dinamico.xml
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;

import java.util.ArrayList;

public class DinamicoFragment extends Fragment {

    private FragmentDinamicoBinding binding;      //Acesso às Views sem findViewById
    public DinamicoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDinamicoBinding.inflate(inflater, container, false); //6.4 cria o binding

        // Modo DINÂMICO: os dados vêm do Singleton (igual à DinamicoActivity da Ficha 3)
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros();
        Livro livro = livros.get(0);                    // primeiro livro da lista

        binding.ivCapa.setImageResource(livro.getCapa());
        binding.tvTitulo.setText(livro.getTitulo());
        binding.tvSerie.setText(livro.getSerie());
        binding.tvAutor.setText(livro.getAutor());
        binding.tvAno.setText(String.valueOf(livro.getAno())); // int → String

        return binding.getRoot();                       // a raiz do layout
    }

    @Override
    public void onDestroyView() {                       // a View do fragmento vai ser destruída
        super.onDestroyView();
        binding = null;                                 // liberta a memória (regra da Google para binding em fragmentos)
        }
}