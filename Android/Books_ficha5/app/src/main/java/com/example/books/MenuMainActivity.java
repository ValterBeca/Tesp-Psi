package com.example.books;                              // package da app

import android.content.ActivityNotFoundException;       // erro quando não há app de email
import android.content.Intent;                          // Intent implícito para o email
import android.net.Uri;                                 // "mailto:"
import android.os.Bundle;                               // estado da atividade
import android.util.Log;                                // escrever no Logcat
import android.view.MenuItem;                           // item do menu que foi clicado
import android.view.View;                               // cabeçalho do NavigationView
import android.widget.TextView;                         // TextView do email no cabeçalho
import android.widget.Toast;                            // Toast

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.annotation.NonNull;                     // parâmetro nunca é null
import androidx.appcompat.app.ActionBarDrawerToggle;    // botão "hamburger" que abre/fecha o menu
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.content.ContextCompat;             // ir buscar cores
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.GravityCompat;                // GravityCompat.START = menu do lado esquerdo
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado
import androidx.fragment.app.Fragment;                  // classe base dos fragmentos

import com.example.books.databinding.ActivityMenuMainBinding; // classe gerada pelo ViewBinding
import com.google.android.material.navigation.NavigationView; // menu lateral

public class MenuMainActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";        // chave do extra (antes estava na MainActivity)
    private static final String TAG = "CicloVida";      // etiqueta do Logcat

    private ActivityMenuMainBinding binding;            // acesso às Views do layout sem findViewById
    private String email;                               // email recebido da LoginActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        binding = ActivityMenuMainBinding.inflate(getLayoutInflater()); // cria o binding
        setContentView(binding.getRoot());              // mostra o layout (a raiz é o DrawerLayout)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarToolbar();                              // 5.3 liga a Toolbar ao DrawerLayout
        carregarCabecalho();                            // 5.2 põe o email no cabeçalho do menu

        // 5.4 CLICK NOS ITENS DO MENU
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return selecionarItemMenu(menuItem);    // trata o clique num métod- à parte
            }
        });

        // 8. Ao abrir, a opção "Livro Estático" já fica selecionada
        if (savedInstanceState == null) {               // só na 1ª vez (ao rodar o ecrã o Android repõe o fragmento sozinho)
            MenuItem item = binding.navigationView.getMenu().findItem(R.id.navLista); // vai buscar o item
            selecionarItemMenu(item);                   // faz o mesmo que um clique nesse item
        }
    }

    // 5.2 Cabeçalho: o header não faz parte do binding, por isso vai-se buscar a View com getHeaderView(0)
    private void carregarCabecalho() {
        email = getIntent().getStringExtra(EMAIL);      // lê o email enviado pela LoginActivity
        View header = binding.navigationView.getHeaderView(0); // 0 = primeiro (e único) cabeçalho
        TextView tvEmail = header.findViewById(R.id.tvEmail); // TextView do email dentro do cabeçalho
        tvEmail.setText(email);                         // mostra o email
    }

    // 5.3 Associa a MaterialToolbar à ActionBar e liga-a ao DrawerLayout
    private void carregarToolbar() {
        setSupportActionBar(binding.toolbar);           // a Toolbar passa a ser a ActionBar da atividade
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.menu_abrir, R.string.menu_fechar); // botão ☰
        binding.drawerLayout.addDrawerListener(drawerToggle); // o botão reage ao abrir/fechar do menu
        drawerToggle.syncState();                       // desenha o ícone de acordo com o estado atual
        drawerToggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, android.R.color.white)); // ícone branco
    }

    // 7.1 O que acontece quando se escolhe uma opção do menu
    private boolean selecionarItemMenu(MenuItem menuItem) {
        int id = menuItem.getItemId();                  // id do item clicado

        if (id == R.id.navLista) {
            setTitle(menuItem.getTitle());              // título da Toolbar = "Livros (Lista)"
            Log.d(TAG, "Opção selecionada: Livros (Lista)"); // mensagem na consola
            carregarFragmento(new ListaLivrosFragment());  // 7.1 mostra a lista
        } else if (id == R.id.navGrelha) {
            setTitle(menuItem.getTitle());
            Log.d(TAG, "Opção selecionada: Livros (Grelha)");
            //Toast.makeText(this, R.string.nav_grelha, Toast.LENGTH_SHORT).show(); // provisório 9 (*) depois troca o Toast por:
            carregarFragmento(new GrelhaLivrosFragment());
        } else if (id == R.id.navEmail) {
            Log.d(TAG, "Opção selecionada: Email");
            enviarEmail();                              // abre a app de email (não é um fragmento)
        }

        menuItem.setChecked(true);                      // o item fica marcado
        binding.drawerLayout.closeDrawer(GravityCompat.START); // fecha o menu lateral
        return true;                                    // true = o clique foi tratado
    }

    // 7.1 Troca o fragmento que está no FrameLayout fragmentContainer
    private void carregarFragmento(Fragment fragment) {
        getSupportFragmentManager()                     // gestor de fragmentos da atividade
                .beginTransaction()                     // começa uma transação (conjunto de alterações)
                .setReorderingAllowed(true)             // otimiza a transação (recomendado pela Google)
                .replace(R.id.fragmentContainer, fragment) // substitui o fragmento atual pelo novo
                .commit();                              // aplica as alterações
    }

    // Igual ao passo 9 da Ficha 3 (antes estava na MainActivity)
    private void enviarEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // Intent IMPLÍCITO
        intent.setData(Uri.parse("mailto:"));           // só aparecem apps de email
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email}); // destinatário
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_assunto)); // assunto
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_texto));      // corpo

        try {
            startActivity(intent);                      // abre o Gmail/Outlook/...
        } catch (ActivityNotFoundException e) {         // não há app de email
            Toast.makeText(this, R.string.msg_sem_email, Toast.LENGTH_SHORT).show();
        }
    }
}