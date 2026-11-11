package com.example.ficha1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        System.out.println("R:"+isMaior(5,2));
        eSwitch('C');
        //Aceder ao componente TextView para mudar o texto
        TextView tvHello= findViewById(R.id.tvHello);
        tvHello.setText("MAMAS");
        tvHello.setTextColor(getColor(R.color.red));
        sequenciaFor(5,2);



    }

    //comparar o maior com IF
    public boolean isMaior(int v1, int v2){
        if (v1>v2)
            return  true;
        //else
        return false;

    }

    //usando dois valores usando o operador trenario
    public boolean Maior(int v1, int v2) {
        return v1 > v2 ? true : false;
    }

    //criar um metodo para verficiar se o cacter é uma vogal, consolante ou estrangeiro. Por defsult consoante. dpois estranegiro e depois vogal

    public void eSwitch(char c) {
        // Converte para minúscula para não ter de testar maiúsculas à parte
        c = Character.toLowerCase(c);

        switch (c) {
            // Vogais
            case 'a': case 'e': case 'i': case 'o': case 'u':
                System.out.println("Vogal");
                break;

            // Letras estrangeiras
            case 'k': case 'w': case 'y':
                System.out.println("Estrangeira");
                break;

            default:
                // Letra de a-z que não caiu nos casos acima é consoante
                if (c >= 'a' && c <= 'z')
                    System.out.println("Consoante");
                else;

        }
    }

    //criar um metedo para apresentar todos os numeros inteiros entre
    // dois numeros (ordem crescente) com for, while e do...while
    //apresentar 2 nuemros inteiros e apresentar a sequencia entre os dois numeros
    //3 funcçoes distintas mas com cuidado que se pode por um numero maior e outro pequeno e ao contrario
    //é preciso ter conta com isso

    // Com for

    // Com for
    public void sequenciaFor(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        for (int i = min; i <= max; i++) {
            System.out.println("Seq"+i);
        }
    }

    // Com while
    public void sequenciaWhile(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        int i = min;
        while (i <= max) {
            System.out.println("Seq"+i);
            i++;
        }
    }

    // Com do...while
    public void sequenciaDoWhile(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        int i = min;
        do {
            System.out.println("Seq"+i);
            i++;
        } while (i <= max);
    }

//criar um metodo para apresentar todos os numeros de um array
    //receber um array como fizemos no exercico anterior

    void eForeach(){
        int[] numeros = {8,2,13,4,1,2,7};
        for(int n : numeros){
            System.out.println("Num = " + n);
        }
    }
}
