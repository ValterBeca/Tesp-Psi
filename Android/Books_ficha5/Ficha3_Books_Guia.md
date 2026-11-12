# Ficha Prática 3 – Books: atividades e intents (AMSI, TeSP PSI)

Guia passo a passo com o código completo e **cada linha comentada**, para estudares para as avaliações.

> **Notas antes de começar**
> - O package usado aqui é `com.example.books`. Se o teu projeto tiver outro nome, troca-o em todas as linhas `package` e `import`.
> - As imagens vêm do `imagens.zip` (pasta *recursos* do Moodle): `logo_ulo.jpg`, `programarandroid1.jpg` (livro estático) e `programarandroid2.jpg` (livro dinâmico).
> - Todos os textos estão no `strings.xml` e as cores no `colors.xml`, como na ficha anterior.
> - A ficha **obriga a usar ViewBinding** na `LoginActivity` (passo 2.5). Nas outras atividades usei `findViewById()`, que já conheces, para teres os dois métodos no mesmo projeto.

---

## Índice
1. Criar o projeto (passo 1)
2. Layout e código do Login com ViewBinding (passo 2)
3. MainActivity e passar o email por Intent (passo 3)
4. EstaticoActivity e DinamicoActivity, tema com ActionBar e singleTop (passo 4)
5. Classe `Livro` no package `modelo` (passo 5)
6. `SingletonGestorLivros` (passo 6)
7. Código da `DinamicoActivity` (passo 7)
8. Ciclo de vida na `MainActivity` (passo 8)
9. Enviar email com Intent implícito (passo 9)
10. Ficheiros finais completos (`strings.xml`, `colors.xml`, `themes.xml`, `AndroidManifest.xml`)
11. Resumo para o teste

---

## Passo 1 – Criar o projeto “Books”

1. **File → New → New Project → Empty Views Activity**.
   - Name: `Books`
   - Package name: `com.example.books`
   - Language: **Java**
   - Build configuration language: Kotlin DSL (`build.gradle.kts`)
2. **(1.2) Imagens:** descompacta o `imagens.zip`, copia os 3 ficheiros `.jpg` (Ctrl+C) e no Android Studio clica com o botão direito em `res → drawable → Paste`. Se perguntar a pasta, escolhe `drawable` (e não `drawable-v24`).
   - Os nomes dos recursos só podem ter letras minúsculas, números e `_`. Estes já estão corretos.
3. **(1.3) Mudar o nome da atividade:**
   - Em `java → com.example.books`, botão direito em `MainActivity` → **Refactor → Rename** (Shift+F6) → `LoginActivity`. O Android Studio também atualiza o `AndroidManifest.xml`.
   - Em `res → layout`, botão direito em `activity_main.xml` → **Refactor → Rename** → `activity_login.xml`. Confirma que no código fica `R.layout.activity_login`.

---

## Passo 2 – Ecrã de Login

### 2.5.1 Ativar o ViewBinding

Ficheiro **Gradle Scripts → build.gradle.kts (Module :app)**. Dentro do bloco `android { ... }` acrescenta:

```kotlin
android {
    namespace = "com.example.books"          // nome do package da app (não mexer)
    // ... (resto que já lá está: compileSdk, defaultConfig, etc.)

    buildFeatures {                          // bloco de funcionalidades extra do build
        viewBinding = true                   // ativa o ViewBinding: gera uma classe "Binding" por cada layout
    }
}
```

Depois clica em **Sync Now** (barra amarela no topo). Sem o *sync*, a classe `ActivityLoginBinding` não existe e o código dá erro.

> **Como funciona o ViewBinding:** para o layout `activity_login.xml` o Android gera a classe `ActivityLoginBinding`. Cada View com `android:id` passa a ser um campo dessa classe. O nome do campo é o id em *camelCase*: `@+id/etEmail` → `binding.etEmail`; `@+id/et_email` → `binding.etEmail` também.

### 2.1 e 2.2 Layout `res/layout/activity_login.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- LinearLayout vertical: os elementos ficam uns por baixo dos outros -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    tools:context=".LoginActivity">
    <!-- id "main": usado no onCreate para tratar as margens do sistema (edge-to-edge) -->
    <!-- match_parent: ocupa o ecrã todo; padding: margem interior de 16dp -->

    <!-- 2.1 Imagem do ULO -->
    <ImageView
        android:id="@+id/ivLogo"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:contentDescription="@string/desc_logo"
        android:scaleType="fitCenter"
        android:src="@drawable/logo_ulo" />
    <!-- height 0dp + weight 1: a imagem ocupa todo o espaço que sobra, empurrando os campos para baixo -->
    <!-- contentDescription: texto lido pelo leitor de ecrã (acessibilidade) -->
    <!-- scaleType fitCenter: a imagem é redimensionada sem se deformar -->
    <!-- src: a imagem que copiámos para res/drawable (sem a extensão .jpg) -->

    <!-- 2.2 Campo do email -->
    <EditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:autofillHints="emailAddress"
        android:hint="@string/hint_email"
        android:inputType="textEmailAddress" />
    <!-- hint: texto cinzento que aparece quando o campo está vazio -->
    <!-- inputType textEmailAddress: o teclado mostra o @ -->

    <!-- 2.2 Campo da password -->
    <EditText
        android:id="@+id/etPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:autofillHints="password"
        android:hint="@string/hint_password"
        android:inputType="textPassword" />
    <!-- inputType textPassword: os caracteres aparecem como pontos -->

    <!-- 2.2 e 2.5 Botão de Login -->
    <Button
        android:id="@+id/btnLogin"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:onClick="onClickLogin"
        android:text="@string/btn_login" />
    <!-- onClick: nome do método Java chamado quando se clica no botão (tem de existir na LoginActivity) -->

</LinearLayout>
```

### 2.3, 2.4, 2.5 Código `LoginActivity.java` (versão com Toast)

```java
package com.example.books;                              // package onde está a classe

import android.os.Bundle;                               // Bundle: guarda o estado da atividade
import android.util.Patterns;                           // Patterns: tem expressões regulares prontas (ex.: email)
import android.view.View;                               // View: tipo do parâmetro dos métodos onClick
import android.widget.Toast;                            // Toast: mensagem rápida no fundo do ecrã

import androidx.activity.EdgeToEdge;                    // permite desenhar a app por baixo das barras do sistema
import androidx.appcompat.app.AppCompatActivity;        // classe base das atividades
import androidx.core.graphics.Insets;                   // margens das barras do sistema
import androidx.core.view.ViewCompat;                   // utilitários de compatibilidade para Views
import androidx.core.view.WindowInsetsCompat;           // informação sobre as barras do sistema

import com.example.books.databinding.ActivityLoginBinding; // classe gerada pelo ViewBinding para activity_login.xml

public class LoginActivity extends AppCompatActivity {  // a LoginActivity é uma atividade

    private ActivityLoginBinding binding;               // objeto que dá acesso a todas as Views do layout

    @Override                                           // estamos a reescrever um método da classe mãe
    protected void onCreate(Bundle savedInstanceState) { // método chamado quando a atividade é criada
        super.onCreate(savedInstanceState);             // chama o onCreate da classe mãe (obrigatório)
        EdgeToEdge.enable(this);                        // código gerado pelo Android Studio (edge-to-edge)

        binding = ActivityLoginBinding.inflate(getLayoutInflater()); // cria o binding a partir do XML (substitui o findViewById)
        setContentView(binding.getRoot());              // mostra o layout no ecrã; getRoot() é o LinearLayout principal

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {   // código gerado: trata as margens
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // lê o tamanho das barras
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // aplica como padding
            return insets;                              // devolve os insets ao sistema
        });
    }

    // 2.3 Verifica se o email é válido
    private boolean isEmailValido(String email) {        // recebe o email e devolve true/false
        if (email == null) {                             // se não houver texto...
            return false;                                // ...não é válido
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches(); // compara com o padrão de email do Android
    }

    // 2.4 Verifica se a password tem pelo menos 4 caracteres
    private boolean isPasswordValida(String password) {  // recebe a password e devolve true/false
        if (password == null) {                          // se não houver texto...
            return false;                                // ...não é válida
        }
        return password.length() >= 4;                  // válida se tiver 4 ou mais caracteres
    }

    // 2.5 Método associado ao android:onClick do botão (tem de ser public void e receber View)
    public void onClickLogin(View view) {
        String email = binding.etEmail.getText().toString();       // 2.5.2 lê o email através do binding
        String password = binding.etPassword.getText().toString(); // 2.5.2 lê a password através do binding

        if (!isEmailValido(email)) {                    // se o email não for válido...
            binding.etEmail.setError(getString(R.string.msg_email_invalido)); // mostra o erro no próprio campo
            return;                                     // sai do método sem continuar
        }

        if (!isPasswordValida(password)) {              // se a password não for válida...
            binding.etPassword.setError(getString(R.string.msg_password_invalida)); // mostra o erro no campo
            return;                                     // sai do método
        }

        // 2.5.3 Resultado da validação num Toast
        Toast.makeText(this, R.string.msg_login_sucesso, Toast.LENGTH_SHORT).show(); // mostra "Login efetuado com sucesso"
    }
}
```

> `setError()` é opcional (mostra um ícone vermelho no campo). Se o professor quiser só Toast, troca as duas linhas `setError` por `Toast.makeText(this, R.string.msg_email_invalido, Toast.LENGTH_SHORT).show();` (e o mesmo para a password).

**Testa:** email inválido → erro; password com 3 caracteres → erro; tudo correto → Toast.

---

## Passo 3 – MainActivity

### 3. Criar a atividade

Botão direito em `com.example.books` → **New → Activity → Empty Views Activity**:
- Activity Name: `MainActivity`
- Layout Name: `activity_main`
- **Não** marcar “Launcher Activity” (a que arranca é a `LoginActivity`).

### 3.1 Layout `res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- LinearLayout vertical com tudo centrado na horizontal -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    android:padding="16dp"
    tools:context=".MainActivity">
    <!-- gravity center_horizontal: centra os filhos na horizontal -->

    <!-- 1ª TextView: "Bem-Vindo!" a cor-de-rosa e a negrito -->
    <TextView
        android:id="@+id/tvBemVindo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="48dp"
        android:text="@string/bem_vindo"
        android:textColor="@color/rosa"
        android:textSize="24sp"
        android:textStyle="bold" />
    <!-- textColor: cor definida no colors.xml; sp: unidade para tamanho de texto -->

    <!-- 2ª TextView: aqui vai aparecer o email recebido da LoginActivity -->
    <TextView
        android:id="@+id/tvEmail"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="14sp" />
    <!-- sem android:text: o texto é posto no código Java -->

    <!-- Botão Livro Estático -->
    <Button
        android:id="@+id/btnLivroEstatico"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="96dp"
        android:onClick="onClickLivroEstatico"
        android:text="@string/btn_livro_estatico" />
    <!-- marginTop 96dp: afasta os botões dos textos, como na imagem da ficha -->

    <!-- Botão Livro Dinâmico -->
    <Button
        android:id="@+id/btnLivroDinamico"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="onClickLivroDinamico"
        android:text="@string/btn_livro_dinamico" />

    <!-- Botão Enviar Email -->
    <Button
        android:id="@+id/btnEnviarEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="onClickEnviarEmail"
        android:text="@string/btn_enviar_email" />

</LinearLayout>
```

### 3.2 LoginActivity: abrir a MainActivity com um Intent (em vez do Toast)

Na `LoginActivity.java`:

1. Acrescenta o import:
```java
import android.content.Intent;                          // Intent: "pedido" para abrir outra atividade
```

2. Substitui a linha do Toast de sucesso (no fim do `onClickLogin`) por:
```java
        Intent intent = new Intent(this, MainActivity.class); // Intent EXPLÍCITO: diz exatamente que atividade abrir
        intent.putExtra(MainActivity.EMAIL, email);     // junta o email ao Intent com a chave MainActivity.EMAIL
        startActivity(intent);                          // inicia a MainActivity
```

> Opcional: se puseres `finish();` a seguir ao `startActivity`, a `LoginActivity` fecha e o botão *back* na MainActivity já não volta ao login.

Na `MainActivity.java` (versão inicial; a versão final completa está no passo 9):

```java
package com.example.books;                              // package da app

import android.content.Intent;                          // Intent: para ler o que a LoginActivity enviou
import android.os.Bundle;                               // Bundle: estado da atividade
import android.widget.TextView;                         // TextView: para mostrar o email

import androidx.activity.EdgeToEdge;                    // edge-to-edge (código gerado)
import androidx.appcompat.app.AppCompatActivity;        // classe base das atividades
import androidx.core.graphics.Insets;                   // margens das barras do sistema
import androidx.core.view.ViewCompat;                   // compatibilidade de Views
import androidx.core.view.WindowInsetsCompat;           // informação das barras do sistema

public class MainActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";        // chave usada no putExtra/getStringExtra (constante para não haver erros de escrita)

    private TextView tvEmail;                           // referência para a TextView do email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        setContentView(R.layout.activity_main);         // liga esta atividade ao layout activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvEmail = findViewById(R.id.tvEmail);          // vai buscar a TextView do email ao layout

        Intent intent = getIntent();                    // obtém o Intent que abriu esta atividade
        String email = intent.getStringExtra(EMAIL);   // lê o email que a LoginActivity enviou
        tvEmail.setText(email);                         // mostra o email na 2ª TextView
    }
}
```

---

## Passo 4 – EstaticoActivity e DinamicoActivity

### 4. Criar as atividades

Duas vezes **New → Activity → Empty Views Activity**:
- `EstaticoActivity` (layout `activity_estatico`)
- `DinamicoActivity` (layout `activity_dinamico`)

### 4.1 Layout `res/layout/activity_estatico.xml`

Imagem à esquerda e, à direita, quatro linhas “Etiqueta: valor” (etiqueta a negrito).

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- LinearLayout HORIZONTAL: capa à esquerda, dados à direita -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:padding="16dp"
    tools:context=".EstaticoActivity">

    <!-- Capa do livro -->
    <ImageView
        android:id="@+id/ivCapa"
        android:layout_width="110dp"
        android:layout_height="150dp"
        android:contentDescription="@string/desc_capa"
        android:scaleType="fitCenter"
        android:src="@drawable/programarandroid1" />
    <!-- tamanho fixo em dp para todas as capas ficarem iguais -->

    <!-- Coluna com os dados do livro -->
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:orientation="vertical">
        <!-- marginStart: espaço entre a imagem e o texto -->

        <!-- Linha: Título -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/lbl_titulo"
                android:textStyle="bold" />
            <!-- etiqueta fixa, não precisa de id -->
            <TextView
                android:id="@+id/tvTitulo"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="4dp" />
            <!-- valor: preenchido no código, por isso tem id -->
        </LinearLayout>

        <!-- Linha: Série -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/lbl_serie"
                android:textStyle="bold" />
            <TextView
                android:id="@+id/tvSerie"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="4dp" />
        </LinearLayout>

        <!-- Linha: Autor -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/lbl_autor"
                android:textStyle="bold" />
            <TextView
                android:id="@+id/tvAutor"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="4dp" />
        </LinearLayout>

        <!-- Linha: Ano -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/lbl_ano"
                android:textStyle="bold" />
            <TextView
                android:id="@+id/tvAno"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="4dp" />
        </LinearLayout>

    </LinearLayout>
</LinearLayout>
```

### 4.1 Layout `res/layout/activity_dinamico.xml`

É **igual** ao `activity_estatico.xml`, só mudam duas linhas:
- `tools:context=".DinamicoActivity"`
- No ImageView, `android:src="@drawable/programarandroid2"` (na verdade a imagem vai ser trocada no código pela capa do Singleton).

Podes copiar o ficheiro todo e alterar essas duas linhas. Os ids (`ivCapa`, `tvTitulo`, `tvSerie`, `tvAutor`, `tvAno`) podem repetir-se porque estão em layouts diferentes.

### 4.2 MainActivity: abrir as atividades dos livros

Acrescenta estes dois métodos à `MainActivity` (ligados ao `android:onClick` dos botões):

```java
    // Clique no botão "Livro Estático"
    public void onClickLivroEstatico(View view) {
        Intent intent = new Intent(this, EstaticoActivity.class); // Intent explícito para a EstaticoActivity
        startActivity(intent);                                    // abre a atividade
    }

    // Clique no botão "Livro Dinâmico"
    public void onClickLivroDinamico(View view) {
        Intent intent = new Intent(this, DinamicoActivity.class); // Intent explícito para a DinamicoActivity
        startActivity(intent);                                    // abre a atividade
    }
```
(e o import `import android.view.View;`)

### 4.3 Tema com ActionBar

**4.3.1** Em `res → values → themes.xml` acrescenta o novo tema (o ficheiro final completo está no ponto 10):

```xml
    <!-- Tema só para as atividades dos livros: tem ActionBar (barra de cima com título e seta back) -->
    <style name="Theme.Books.WithActionBar"
        parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item> <!-- cor da ActionBar -->
    </style>
```

E em `res → values → colors.xml` tens de criar a cor `colorPrimary`, senão dá erro *resource not found* (ver ponto 10).

**4.3.2 e 4.3.3** No `AndroidManifest.xml` associa o tema e o *parent* às duas atividades:

```xml
        <activity
            android:name=".EstaticoActivity"
            android:exported="false"
            android:label="@string/titulo_estatico"
            android:parentActivityName=".MainActivity"
            android:theme="@style/Theme.Books.WithActionBar"
            android:windowSoftInputMode="adjustResize" />
        <!-- label: título que aparece na ActionBar ("Livro Estático") -->
        <!-- parentActivityName: a seta ← da ActionBar volta à MainActivity -->
        <!-- theme: usa o tema com ActionBar só nesta atividade -->

        <activity
            android:name=".DinamicoActivity"
            android:exported="false"
            android:label="@string/titulo_dinamico"
            android:parentActivityName=".MainActivity"
            android:theme="@style/Theme.Books.WithActionBar"
            android:windowSoftInputMode="adjustResize" />
```

### 4.4 Código `EstaticoActivity.java` (dados “escritos à mão” no onCreate)

```java
package com.example.books;                              // package da app

import android.os.Bundle;                               // estado da atividade
import android.widget.ImageView;                        // ImageView: para a capa
import android.widget.TextView;                         // TextView: para os dados do livro

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

public class EstaticoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        setContentView(R.layout.activity_estatico);     // liga ao layout activity_estatico.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView ivCapa = findViewById(R.id.ivCapa);   // vai buscar a ImageView da capa
        TextView tvTitulo = findViewById(R.id.tvTitulo); // vai buscar a TextView do título
        TextView tvSerie = findViewById(R.id.tvSerie);  // vai buscar a TextView da série
        TextView tvAutor = findViewById(R.id.tvAutor);  // vai buscar a TextView do autor
        TextView tvAno = findViewById(R.id.tvAno);      // vai buscar a TextView do ano

        // Modo ESTÁTICO: os dados são postos diretamente, vindos do strings.xml
        ivCapa.setImageResource(R.drawable.programarandroid1); // põe a imagem da capa
        tvTitulo.setText(R.string.livro_titulo);        // "Programar em Android AMSI"
        tvSerie.setText(R.string.livro_serie);          // "Android Saga"
        tvAutor.setText(R.string.livro_autor);          // "Equipa AMSI"
        tvAno.setText(R.string.livro_ano);              // "2019" (guardado como texto no strings.xml)
    }
}
```

### 4.5 Testar

Faz login, entra em **Livro Estático** e carrega na **seta ← da ActionBar**. O email da MainActivity **desaparece**.

**Porquê?** A seta usa o `parentActivityName`. Por defeito (`launchMode="standard"`) o Android destrói a MainActivity que existia e cria uma **nova**. Esta nova MainActivity é aberta por um Intent sem o extra `EMAIL`, por isso `getStringExtra(EMAIL)` devolve `null` e a TextView fica vazia.

(Se usares o botão *back* do telemóvel em vez da seta, o email não desaparece, porque aí o Android só volta à atividade que já estava na pilha.)

### 4.6 `launchMode="singleTop"`

No `AndroidManifest.xml`:

```xml
        <activity
            android:name=".MainActivity"
            android:exported="false"
            android:launchMode="singleTop" />
        <!-- singleTop: se a MainActivity já estiver no topo da pilha, é REUTILIZADA em vez de criada outra vez -->
```

Testa de novo: agora a seta ← volta à MainActivity que já existia e o email continua lá.

---

## Passo 5 – Package `modelo` e classe `Livro`

1. Botão direito em `com.example.books` → **New → Package** → escreve `modelo` (fica `com.example.books.modelo`).
2. Botão direito em `modelo` → **New → Java Class** → `Livro`.
3. Os getters e setters podem ser gerados com **Alt+Insert → Getter and Setter** (no setter, desmarca o `id`).

### `modelo/Livro.java`

```java
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
```

---

## Passo 6 – `SingletonGestorLivros`

**Singleton** = classe que só pode ter **uma única instância** em toda a app. Serve aqui para guardar a lista de livros num sítio só, acessível de qualquer atividade.

Regras para ser Singleton:
1. Construtor **private** (ninguém de fora pode fazer `new`).
2. Um atributo **static** que guarda a única instância.
3. Um método **static** `getInstance()` que cria a instância só da primeira vez e devolve-a sempre.
4. **Thread-safe**: o método é `synchronized`, para duas threads ao mesmo tempo não criarem duas instâncias.

### `modelo/SingletonGestorLivros.java`

```java
package com.example.books.modelo;                       // mesmo package que a classe Livro

import com.example.books.R;                             // para usar R.drawable (as imagens das capas)

import java.util.ArrayList;                             // ArrayList: lista que cresce conforme se adicionam elementos

public class SingletonGestorLivros {

    private static SingletonGestorLivros instance = null; // a ÚNICA instância; começa a null (ainda não foi criada)

    private ArrayList<Livro> livros;                    // 6.2 lista de livros gerida pelo Singleton

    // 6.1 Método de acesso: synchronized = thread-safe (só uma thread de cada vez entra aqui)
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
    private void gerarDadosDinamicos() {
        livros.add(new Livro(1, "Programar em Android AMSI", "AMSI Team", "2ª temporada", 2019, R.drawable.programarandroid2)); // livro 1
        livros.add(new Livro(2, "Programar em Android AMSI", "Android Saga", "Equipa AMSI", 2019, R.drawable.programarandroid1)); // livro 2
    }

    // 6.3 Devolve os livros da lista
    public ArrayList<Livro> getLivros() {
        return new ArrayList<>(livros);                 // devolve uma CÓPIA, para quem a usa não estragar a lista original
    }
}
```

> Os dados do livro 1 são os da imagem do “Livro Dinâmico” na ficha (Série: AMSI Team, Autor: 2ª temporada).

---

## Passo 7 – Código da `DinamicoActivity`

```java
package com.example.books;                              // package da app

import android.os.Bundle;                               // estado da atividade
import android.widget.ImageView;                        // capa
import android.widget.TextView;                         // dados do livro

import androidx.activity.EdgeToEdge;                    // código gerado
import androidx.appcompat.app.AppCompatActivity;        // classe base
import androidx.core.graphics.Insets;                   // código gerado
import androidx.core.view.ViewCompat;                   // código gerado
import androidx.core.view.WindowInsetsCompat;           // código gerado

import com.example.books.modelo.Livro;                  // classe Livro (package modelo)
import com.example.books.modelo.SingletonGestorLivros;  // Singleton (package modelo)

import java.util.ArrayList;                             // para a lista de livros

public class DinamicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);             // obrigatório
        EdgeToEdge.enable(this);                        // código gerado
        setContentView(R.layout.activity_dinamico);     // liga ao layout activity_dinamico.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // código gerado
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView ivCapa = findViewById(R.id.ivCapa);   // capa
        TextView tvTitulo = findViewById(R.id.tvTitulo); // título
        TextView tvSerie = findViewById(R.id.tvSerie);  // série
        TextView tvAutor = findViewById(R.id.tvAutor);  // autor
        TextView tvAno = findViewById(R.id.tvAno);      // ano

        // Modo DINÂMICO: os dados vêm do Singleton
        ArrayList<Livro> livros = SingletonGestorLivros.getInstance().getLivros(); // obtém a instância única e pede a lista
        Livro livro = livros.get(0);                    // escolhe o primeiro livro da lista (posição 0)

        ivCapa.setImageResource(livro.getCapa());       // mostra a capa (getCapa devolve o id R.drawable...)
        tvTitulo.setText(livro.getTitulo());            // mostra o título
        tvSerie.setText(livro.getSerie());              // mostra a série
        tvAutor.setText(livro.getAutor());              // mostra o autor
        tvAno.setText(String.valueOf(livro.getAno()));  // ATENÇÃO: o ano é int, tem de ser convertido para String!
    }
}
```

> **Erro clássico de teste:** `tvAno.setText(livro.getAno())` compila, mas a app **crasha** com `Resources$NotFoundException`. Quando o `setText` recebe um `int`, o Android pensa que é o id de uma string do `strings.xml` (como `R.string.x`). Por isso usa-se `String.valueOf(...)`.

---

## Passo 8 – Ciclo de vida na `MainActivity`

Ordem dos métodos: **onCreate → onStart → onResume** (atividade visível e a funcionar) … **onPause → onStop → onDestroy** (a sair). Quando se volta a uma atividade parada: **onRestart → onStart → onResume**.

Acrescenta à `MainActivity` (o código completo está no passo 9):

```java
    private static final String TAG = "CicloVida";      // etiqueta para filtrar as mensagens no Logcat

    @Override
    protected void onStart() {                          // a atividade vai ficar visível
        super.onStart();                                // obrigatório chamar o método da classe mãe
        Log.d(TAG, "onStart");                          // escreve "onStart" na consola (Logcat)
    }
    // ... e o mesmo para onResume, onPause, onStop, onRestart, onDestroy (ver código final)
```

Import: `import android.util.Log;`

**Ver na consola:** separador **Logcat** (em baixo) → na pesquisa escreve `tag:CicloVida`.
(Se o professor preferir, `System.out.println("onStart");` também aparece no Logcat, com a tag `System.out`.)

**Ordem que vais ver:**
| Ação | Métodos chamados na MainActivity |
|---|---|
| Login com sucesso | onCreate → onStart → onResume |
| Abrir Livro Estático | onPause → onStop |
| Voltar (seta ← com singleTop, ou back) | onRestart → onStart → onResume |
| Fechar a app com back | onPause → onStop → onDestroy |

**Debugger:**
1. Clica na margem cinzenta, ao lado do número da linha `Log.d(...)` de cada método, para pôr um **breakpoint** (bola vermelha).
2. Corre com **Run → Debug 'app'** (Shift+F9 ou o ícone do inseto).
3. Quando parar, no painel **Debug** vês as variáveis (`this`, `savedInstanceState`, etc.). Usa **Resume (F9)** para avançar até ao próximo método e **Step Over (F8)** para avançar linha a linha.

---

## Passo 9 (*) – Enviar email com Intent implícito

**Intent explícito** = dizes a classe a abrir (`new Intent(this, MainActivity.class)`).
**Intent implícito** = dizes a **ação** que queres fazer (enviar email) e o Android escolhe a app que sabe fazê-la (Gmail, Outlook…).

### `MainActivity.java` – versão FINAL completa (passos 3, 4.2, 8 e 9)

```java
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
```

> No emulador, para o email funcionar tem de haver uma conta Google configurada no Gmail. Se não houver app de email, aparece o Toast.

### `LoginActivity.java` – versão FINAL (depois do passo 3.2)

```java
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
```

---

## 10. Ficheiros de recursos e Manifest finais

### `res/values/strings.xml`

```xml
<resources>
    <!-- Nome da app -->
    <string name="app_name">Books</string>

    <!-- Login -->
    <string name="desc_logo">Logótipo da Universidade Leiria Oeste</string>
    <string name="hint_email">Email</string>
    <string name="hint_password">Password</string>
    <string name="btn_login">Login</string>
    <string name="msg_login_sucesso">Login efetuado com sucesso</string>
    <string name="msg_email_invalido">Email inválido</string>
    <string name="msg_password_invalida">A password deve ter pelo menos 4 caracteres</string>

    <!-- MainActivity -->
    <string name="bem_vindo">Bem-Vindo!</string>
    <string name="btn_livro_estatico">Livro Estático</string>
    <string name="btn_livro_dinamico">Livro Dinâmico</string>
    <string name="btn_enviar_email">Enviar Email</string>

    <!-- Títulos da ActionBar -->
    <string name="titulo_estatico">Livro Estático</string>
    <string name="titulo_dinamico">Livro Dinâmico</string>

    <!-- Etiquetas do livro -->
    <string name="desc_capa">Capa do livro</string>
    <string name="lbl_titulo">Título:</string>
    <string name="lbl_serie">Série:</string>
    <string name="lbl_autor">Autor:</string>
    <string name="lbl_ano">Ano:</string>

    <!-- Dados do livro estático (passo 4.4) -->
    <string name="livro_titulo">Programar em Android AMSI</string>
    <string name="livro_serie">Android Saga</string>
    <string name="livro_autor">Equipa AMSI</string>
    <string name="livro_ano">2019</string>

    <!-- Email (passo 9) -->
    <string name="email_assunto">Books - AMSI</string>
    <string name="email_texto">Olá! Este email foi enviado a partir da app Books.</string>
    <string name="msg_sem_email">Não existe nenhuma aplicação de email instalada</string>
</resources>
```

### `res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="black">#FF000000</color>         <!-- já vem no projeto -->
    <color name="white">#FFFFFFFF</color>         <!-- já vem no projeto -->
    <color name="colorPrimary">#FF6750A4</color>  <!-- roxo: cor da ActionBar (usada no tema WithActionBar) -->
    <color name="rosa">#FFE040FB</color>          <!-- cor-de-rosa do "Bem-Vindo!" -->
</resources>
```

### `res/values/themes.xml`

O Android Studio já cria as duas primeiras partes; só acrescentas o último `<style>`:

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Tema base da app (já existe): SEM ActionBar -->
    <style name="Base.Theme.Books" parent="Theme.Material3.DayNight.NoActionBar">
        <!-- Customize your light theme here. -->
    </style>

    <!-- Tema global aplicado a toda a app (já existe) -->
    <style name="Theme.Books" parent="Base.Theme.Books" />

    <!-- 4.3.1 NOVO: tema com ActionBar, só para EstaticoActivity e DinamicoActivity -->
    <style name="Theme.Books.WithActionBar"
        parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
    </style>
</resources>
```

### `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Books"
        tools:targetApi="31">
        <!-- android:theme aqui = tema GLOBAL (sem ActionBar) para todas as atividades -->

        <!-- Livro Dinâmico: tema com ActionBar e seta para voltar à MainActivity -->
        <activity
            android:name=".DinamicoActivity"
            android:exported="false"
            android:label="@string/titulo_dinamico"
            android:parentActivityName=".MainActivity"
            android:theme="@style/Theme.Books.WithActionBar"
            android:windowSoftInputMode="adjustResize" />

        <!-- Livro Estático: igual -->
        <activity
            android:name=".EstaticoActivity"
            android:exported="false"
            android:label="@string/titulo_estatico"
            android:parentActivityName=".MainActivity"
            android:theme="@style/Theme.Books.WithActionBar"
            android:windowSoftInputMode="adjustResize" />

        <!-- MainActivity: singleTop para não ser recriada ao voltar (passo 4.6) -->
        <activity
            android:name=".MainActivity"
            android:exported="false"
            android:launchMode="singleTop" />

        <!-- LoginActivity: é a atividade que arranca (LAUNCHER) -->
        <activity
            android:name=".LoginActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />          <!-- ponto de entrada da app -->
                <category android:name="android.intent.category.LAUNCHER" /> <!-- aparece no menu de apps -->
            </intent-filter>
        </activity>
    </application>

</manifest>
```

> Não copies o `<application>` às cegas: o teu pode ter atributos ligeiramente diferentes (ex.: `tools:targetApi`). O importante são os blocos `<activity>`.

---

## 11. Resumo para o teste

| Conceito | O que tens de saber |
|---|---|
| **Renomear atividade** | Refactor → Rename (Shift+F6), atualiza o Manifest sozinho |
| **ViewBinding** | `buildFeatures { viewBinding = true }` + Sync; `XxxBinding.inflate(getLayoutInflater())`; `setContentView(binding.getRoot())`; `binding.idDaView` |
| **findViewById** | `TextView tv = findViewById(R.id.tvX);` depois do `setContentView` |
| **Intent explícito** | `new Intent(this, Outra.class)` + `startActivity(intent)` |
| **Passar dados** | `intent.putExtra(CHAVE, valor)` → na outra: `getIntent().getStringExtra(CHAVE)` |
| **Intent implícito** | `new Intent(Intent.ACTION_SENDTO)` + `setData(Uri.parse("mailto:"))` + extras |
| **Validar email** | `Patterns.EMAIL_ADDRESS.matcher(email).matches()` |
| **ActionBar numa atividade** | tema próprio no `themes.xml` + `android:theme` e `android:parentActivityName` no Manifest |
| **singleTop** | reutiliza a atividade se já estiver no topo da pilha (não chama onCreate outra vez) |
| **Singleton** | construtor `private`, atributo `static instance`, `static synchronized getInstance()` |
| **Ciclo de vida** | onCreate → onStart → onResume → onPause → onStop → onDestroy (+ onRestart) |
| **setText com int** | `setText(R.string.x)` = recurso; para números usa `String.valueOf(n)` |
| **Imagem por código** | `imageView.setImageResource(R.drawable.nome)` |

### Erros comuns
- **`Cannot resolve symbol ActivityLoginBinding`** → falta o `viewBinding = true` ou o *Sync Now*. Experimenta também *Build → Rebuild Project*.
- **App fecha ao clicar no botão (`Could not find method onClickX`)** → o nome no `android:onClick` não é igual ao do Java, ou o método não é `public void nome(View view)`.
- **`Resources$NotFoundException`** → `setText(int)` com um número que não é recurso (ex.: o ano).
- **`resource color/colorPrimary not found`** → falta a cor no `colors.xml`.
- **Email aparece vazio ao voltar** → falta o `launchMode="singleTop"` na MainActivity.
- **Não arranca no Login** → o `<intent-filter>` LAUNCHER tem de estar na `LoginActivity`, não na `MainActivity`.
