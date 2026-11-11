package com.example.books.modelo;                       // mesmo package que a classe Livro

import com.example.books.R;                             // para usar R.drawable (as imagens das capas)

import java.util.ArrayList;                             // ArrayList: lista que cresce conforme se adicionam elementos

public class SingletonGestorLivros {

    private static SingletonGestorLivros instance = null; // a ÚNICA instância; começa a null (ainda não foi criada)

    private ArrayList<Livro> livros;                    // 6.2 lista de livros gerida pelo Singleton

    // 6.1 Métod-o de acesso: synchronized = thread-safe (só uma thread de cada vez entra aqui)
    public static synchronized SingletonGestorLivros getInstance() {
        if (instance == null) {                         // se ainda não existe nenhuma instância...
            instance = new SingletonGestorLivros();     // ...cria-a (só acontece uma vez)
        }
        return instance;                                // devolve sempre a mesma instância
    }

    // 6.1 Construtor PRIVATE: impede "new SingletonGestorLivros()" fora desta classe
    private SingletonGestorLivros() {
        livros = new ArrayList<>();                     // 6.2 a lista é iniciada no construtor
        gerarDadosDinamicos();                          // enche a lista com livros de exemplo
    }

    // Simulação de dados (mais tarde virão de uma API/base de dados)
    //private void gerarDadosDinamicos() {

        //livros.add(new Livro(2, "Programar em Android AMSI", "Android Saga", "Equipa AMSI", 2019, R.drawable.programarandroid1)); // livro 2
    //}

    // 6.3 Devolve os livros da lista
    public ArrayList<Livro> getLivros() {
        return new ArrayList<>(livros);                 // devolve uma CÓPIA, para quem a usa não estragar a lista original
    }
}
