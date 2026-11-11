package com.example.books;                              // package da app

import android.content.Intent;                          // Intent: abrir a MainActivity
import android.os.Bundle;                               // estado da atividade
import android.util.Patterns;                           // padrão de email
import android.view.View;                               // parâmetro do onClick

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

import com.example.books.databinding.ActivityLoginBinding; // classe gerada pelo ViewBinding

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;               // acesso às Views do layout sem findViewById

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        binding = ActivityLoginBinding.inflate(getLayoutInflater()); // cria o binding a partir do XML
        setContentView(binding.getRoot());              // mostra o layout
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> { // código gerado (usa binding.main)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean isEmailValido(String email) {        // 2.3
        if (email == null) {
            return false;                                // sem texto = inválido
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches(); // valida o formato do email
    }

    private boolean isPasswordValida(String password) {  // 2.4
        if (password == null) {
            return false;                                // sem texto = inválida
        }
        return password.length() >= 4;                  // mínimo 4 caracteres
    }

    public void onClickLogin(View view) {               // 2.5 ligado ao android:onClick do botão
        String email = binding.etEmail.getText().toString();       // lê o email
        String password = binding.etPassword.getText().toString(); // lê a password

        if (!isEmailValido(email)) {                    // email inválido
            binding.etEmail.setError(getString(R.string.msg_email_invalido)); // mostra erro no campo
            return;                                     // pára aqui
        }

        if (!isPasswordValida(password)) {              // password inválida
            binding.etPassword.setError(getString(R.string.msg_password_invalida)); // mostra erro no campo
            return;                                     // pára aqui
        }

        Intent intent = new Intent(this, MainActivity.class); // 3.2 Intent explícito para a MainActivity
        intent.putExtra(MainActivity.EMAIL, email);     // envia o email como parâmetro
        startActivity(intent);                          // abre a MainActivity
    }
}
