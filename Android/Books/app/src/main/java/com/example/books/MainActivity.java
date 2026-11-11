package com.example.books;                              // package da app

import android.content.ActivityNotFoundException;       // erro lançado quando não há app para tratar o Intent
import android.content.Intent;                          // Intent: abrir atividades / pedir ações ao sistema
import android.net.Uri;                                 // Uri: endereço, ex.: "mailto:"
import android.os.Bundle;                               // estado da atividade
import android.util.Log;                                // Log: escrever mensagens no Logcat
import android.view.View;                               // tipo do parâmetro dos métodos onClick
import android.widget.TextView;                         // TextView
import android.widget.Toast;                            // Toast

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

public class MainActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";        // chave do extra com o email (usada também na LoginActivity)
    private static final String TAG = "CicloVida";      // etiqueta do Logcat

    private TextView tvEmail;                           // TextView onde aparece o email
    private String email;                               // email do utilizador (guardado para usar no passo 9)

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 1º método do ciclo de vida
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        setContentView(R.layout.activity_main);         // liga ao layout activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "onCreate");                         // passo 8: escreve na consola

        tvEmail = findViewById(R.id.tvEmail);          // vai buscar a TextView ao layout
        email = getIntent().getStringExtra(EMAIL);     // passo 3.2: lê o email enviado pela LoginActivity
        tvEmail.setText(email);                         // mostra o email
    }

    // ---------- Passo 4.2: navegação para os livros ----------

    public void onClickLivroEstatico(View view) {       // ligado ao android:onClick do botão Livro Estático
        Intent intent = new Intent(this, EstaticoActivity.class); // Intent explícito
        startActivity(intent);                          // abre a EstaticoActivity
    }

    public void onClickLivroDinamico(View view) {       // ligado ao android:onClick do botão Livro Dinâmico
        Intent intent = new Intent(this, DinamicoActivity.class); // Intent explícito
        startActivity(intent);                          // abre a DinamicoActivity
    }

    // ---------- Passo 9: enviar email (Intent implícito) ----------

    public void onClickEnviarEmail(View view) {         // ligado ao android:onClick do botão Enviar Email
        Intent intent = new Intent(Intent.ACTION_SENDTO); // ação: "enviar para" (Intent IMPLÍCITO)
        intent.setData(Uri.parse("mailto:"));           // "mailto:" faz com que só apareçam apps de email
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email}); // destinatário(s): tem de ser um array de Strings
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_assunto)); // assunto do email
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_texto));      // corpo do email

        try {                                           // tenta abrir a app de email...
            startActivity(intent);                      // o Android abre o Gmail/Outlook/... já preenchido
        } catch (ActivityNotFoundException e) {         // ...se não houver nenhuma app de email instalada
            Toast.makeText(this, R.string.msg_sem_email, Toast.LENGTH_SHORT).show(); // avisa o utilizador em vez de crashar
        }
    }

    // ---------- Passo 8: métodos do ciclo de vida ----------

    @Override
    protected void onStart() {                          // a atividade vai ficar visível
        super.onStart();                                // chama a classe mãe (obrigatório)
        Log.d(TAG, "onStart");                          // escreve na consola
    }

    @Override
    protected void onResume() {                         // a atividade está visível e o utilizador pode interagir
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {                          // outra atividade vai ficar à frente (perde o foco)
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {                           // a atividade deixou de estar visível
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {                        // a atividade estava parada (onStop) e vai voltar
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {                        // a atividade vai ser destruída
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
