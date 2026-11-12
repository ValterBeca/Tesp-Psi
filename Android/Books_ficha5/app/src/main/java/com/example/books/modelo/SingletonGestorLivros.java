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
        gerarLivros();                          // enche a lista com 10 livros de exemplo
    }

    private void gerarLivros() {
        livros.add(new Livro(1, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 1", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(2, R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 2", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(3, R.drawable.logo_ulo, 2024, "Programar em Android AMSI - 3", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(4, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 4", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(5, R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 5", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(6, R.drawable.logo_ulo, 2024, "Programar em Android AMSI - 6", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(7, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 7", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(8, R.drawable.programarandroid1, 2024, "Programar em Android AMSI - 8", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(9, R.drawable.logo_ulo, 2024, "Programar em Android AMSI - 9", "AMSI TEAM", "2ª Temporada"));
        livros.add(new Livro(10, R.drawable.programarandroid2, 2024, "Programar em Android AMSI - 10", "AMSI TEAM", "2ª Temporada"));
    }

    // 6.3 Devolve os livros da lista
    public ArrayList<Livro> getLivros() {
        return new ArrayList<>(livros);                 // devolve uma CÓPIA, para quem a usa não estragar a lista original
    }

    // Ficha 5 (8.2.1): devolve o livro com este id, ou null se não existir
    public Livro getLivro(int id) {
        for (Livro livro : livros) {                    // percorre todos os livros da lista
            if (livro.getId() == id) {                  // encontrou o livro com este id
                return livro;
            }
        }
        return null;                                    // nenhum livro tem este id
    }
}
