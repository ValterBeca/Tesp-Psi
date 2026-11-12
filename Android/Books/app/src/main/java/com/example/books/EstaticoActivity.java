package com.example.books;                              // package da app

import android.os.Bundle;                               // estado da atividade


import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

import com.example.books.databinding.ActivityEstaticoBinding;




public class EstaticoActivity extends AppCompatActivity {

    private ActivityEstaticoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);    // código gerado
        binding=ActivityEstaticoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());     // liga ao layout activity_estatico.xml
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarLivro();

    }

    public void carregarLivro(){
        // Modo ESTÁTICO: os dados vêm do strings.xml, não do Singleton
        binding.ivCapa.setImageResource(R.drawable.programarandroid1);            // capa do livro
        binding.tvTitulo.setText(R.string.livro_titulo);   // "Programar em Android AMSI"
        binding.tvSerie.setText(R.string.livro_serie);     // "Android Saga"
        binding.tvAutor.setText(R.string.livro_autor);     // "Equipa AMSI"
        binding.tvAno.setText(R.string.livro_ano);         // "2019" (já é texto no strings.xml
    }
}


