package com.example.books;                              // package da app

import android.os.Bundle;                               // estado da atividade
import android.widget.Toast;                            // aviso se o livro não existir

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

import com.example.books.databinding.ActivityDetalhesLivroBinding; // gerada a partir de activity_detalhes_livro.xml
import com.example.books.modelo.Livro;                  // classe Livro
import com.example.books.modelo.SingletonGestorLivros;  // Singleton

public class DetalhesLivroActivity extends AppCompatActivity {

    public static final String ID_LIVRO = "ID_LIVRO";  // 8.2.2 chave do extra com o id do livro

    private ActivityDetalhesLivroBinding binding;       // acesso às Views sem findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        binding = ActivityDetalhesLivroBinding.inflate(getLayoutInflater()); // 1º cria o binding
        setContentView(binding.getRoot());              // 2º mostra o layout
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 8.2.3 Lê o id enviado pelo ListaLivrosFragment (-1 = valor por defeito se não vier nada)
        int id = getIntent().getIntExtra(ID_LIVRO, -1);
        Livro livro = SingletonGestorLivros.getInstance().getLivro(id); // 8.2.1 vai buscar o livro ao Singleton

        if (livro != null) {
            carregarLivro(livro);                       // mostra os dados
        } else {                                        // id inválido: avisa e fecha o ecrã
            Toast.makeText(this, R.string.msg_livro_nao_encontrado, Toast.LENGTH_SHORT).show();
            finish();                                   // fecha esta atividade e volta à lista
        }
    }

    private void carregarLivro(Livro livro) {
        setTitle(getString(R.string.titulo_detalhes, livro.getTitulo())); // 8.2.4 "Detalhes: [título]"
        binding.ivCapa.setImageResource(livro.getCapa());
        binding.etTitulo.setText(livro.getTitulo());
        binding.etSerie.setText(livro.getSerie());
        binding.etAutor.setText(livro.getAutor());
        binding.etAno.setText(String.valueOf(livro.getAno())); // int → String (nunca setText(int)!)
    }
}