package com.example.books.modelo;                       // a classe está no package "modelo"

public class Livro {                                    // classe que representa um livro (modelo de dados)

    // 5.1 Atributos (private: só se acedem pelos getters/setters = encapsulamento)
    private int id;                                     // identificador do livro (inteiro)
    private String titulo;                              // título do livro
    private String serie;                               // série/coleção
    private String autor;                               // autor
    private int ano;                                    // ano de publicação (inteiro)
    private int capa;                                   // capa: é um int porque guarda o ID do recurso (ex.: R.drawable.programarandroid2)

    // 5.3 Construtor que recebe todos os atributos
    public Livro(int id, String titulo, String serie, String autor, int ano, int capa) {
        this.id = id;                                   // "this.id" é o atributo; "id" é o parâmetro
        this.titulo = titulo;                           // guarda o título
        this.serie = serie;                             // guarda a série
        this.autor = autor;                             // guarda o autor
        this.ano = ano;                                 // guarda o ano
        this.capa = capa;                               // guarda o id da imagem
    }

    // 5.2 Getters (devolvem o valor dos atributos)
    public int getId() {                                // só há getter: o id não pode ser alterado depois de criado
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSerie() {
        return serie;
    }

    public String getAutor() {
        return autor;
    }

    public int getAno() {
        return ano;
    }

    public int getCapa() {
        return capa;
    }

    // 5.2 Setters (alteram o valor dos atributos), exceto setId
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }
}
