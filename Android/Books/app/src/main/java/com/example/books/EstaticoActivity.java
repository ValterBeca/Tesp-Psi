package com.example.books;                              // package da app

import android.os.Bundle;                               // estado da atividade
import android.widget.ImageView;                        // ImageView: para a capa
import android.widget.TextView;                         // TextView: para os dados do livro

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

import com.example.books.databinding.ActivityEstaticoBinding;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;

import java.util.ArrayList;

public class EstaticoActivity extends AppCompatActivity {

    private ActivityEstaticoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);    // código gerado
        binding=ActivityEstaticoBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_estatico);     // liga ao layout activity_estatico.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Livro Estático");

        ImageView ivCapa = findViewById(R.id.ivCapa);   // vai buscar a ImageView da capa
        TextView tvTitulo = findViewById(R.id.tvTitulo); // vai buscar a TextView do título
        TextView tvSerie = findViewById(R.id.tvSerie);  // vai buscar a TextView da série
        TextView tvAutor = findViewById(R.id.tvAutor);  // vai buscar a TextView do autor
        TextView tvAno = findViewById(R.id.tvAno);      // vai buscar a TextView do ano


    }

    public void carregarLivro(){
        // Modo DINÂMICO: os dados vêm do Singleton
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros(); // obtém a instância única e pede a lista
        Livro livro;                     // escolhe o primeiro livro da lista (posição 0)
        if(livros!=null && livros.size()>0){
            livro=livros.get(0);
            binding.tvTitulo.setText(livro.getTitulo());            // mostra o título
            binding.tvSerie.setText(livro.getSerie());              // mostra a série
            binding.tvAutor.setText(livro.getAutor());              // mostra o autor
            binding.tvAno.setText(String.valueOf(livro.getAno()+""));  // ATENÇÃO: o ano é int, tem de ser convertido para String!
            binding.ivCapa.setImageResource(R.drawable.programarandroid1);
        }
    }

}
