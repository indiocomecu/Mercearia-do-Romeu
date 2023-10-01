package com.example.trabalho_3bimestre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btCadastrarCliente;
    private Button btCadastrarProduto;
    private Button btCadastrarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastrarCliente = findViewById(R.id.btCadastrarCliente);
        btCadastrarProduto = findViewById(R.id.btCadastrarProduto);
        btCadastrarPedido = findViewById(R.id.btCadastrarPedido);

        btCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroClienteActivity.class);
                startActivity(intent);
            }
        });

        btCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                startActivity(intent);
            }
        });

        btCadastrarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroPedidoActivity.class);
                startActivity(intent);
            }
        });
    }
}
