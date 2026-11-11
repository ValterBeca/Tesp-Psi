package com.example.guessanumber;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MIN =1, MAX =10;
    //declaração
    private EditText etNumero; //declaração
    private TextView tvAcertou, tvTentativas;

    private static final Random gerador=new Random();

    private int numGerado;

    //método_incial do ciclo de vida de uma atividade (é criado de forma automática)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //define o layout associado
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Inicialização
        etNumero=findViewById(R.id.etNumero);
        tvAcertou=findViewById(R.id.tvAcertou);
        tvTentativas=findViewById(R.id.tvTentativas);

        // Chamar método_gerarValor
        numGerado=gerarValorAleatorio(MIN, MAX);
    }

    public void onClickAdivinha(View view) {
        if(etNumero.getText().toString().trim().isEmpty())
            return;
        // Toast.makeText(context: this, text: "o nº selecionado foi: " + etNumero.getText(),Toast.LENGTH_LONG.show());
        int num=Integer.parseInt(etNumero.getText().toString());
        if(numGerado==num){
            tvAcertou.setText("Acertou!!");
            tvAcertou.setTextColor(Color.GREEN);
        }
        else if (numGerado>num) {
            tvAcertou.setText("Valor gerado está acima");
            tvAcertou.setTextColor(Color.RED);
        }
        else {
            tvAcertou.setText("Valor gerado está abaixo");
            tvAcertou.setTextColor(Color.BLUE);
        }

    //if(num==numGerado)
        //Toast.makeText(this , "Acertou!!", Toast.LENGTH_LONG).show();
    //else
        //Toast.makeText(this , "Não acertou!!, O nº gerado foi: " +numGerado,Toast.LENGTH_LONG).show();

    }
    // Ex 3.3
    private int gerarValorAleatorio(int min, int max){
        int valor=gerador.nextInt(max-min+1)+min;
        return valor;
    }
}


