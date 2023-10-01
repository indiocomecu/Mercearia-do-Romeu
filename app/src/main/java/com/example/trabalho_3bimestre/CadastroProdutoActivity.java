package com.example.trabalho_3bimestre;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import com.example.trabalho_3bimestre.model.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText edtCodProduto;
    private EditText edtDescProduto;
    private EditText edtValorProduto;
    private TextView tvListaProdutos;
    private Button btSalvarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        edtCodProduto = findViewById(R.id.codProduto);
        edtDescProduto = findViewById(R.id.descProduto);
        edtValorProduto = findViewById(R.id.valorProduto);
        tvListaProdutos = findViewById(R.id.listaProdutos);
        btSalvarProduto = findViewById(R.id.btSalvarProduto);

        atualizarListaProdutos();

        btSalvarProduto.setOnClickListener(view -> salvarProduto());
    }

    private void salvarProduto() {
        String codigo = edtCodProduto.getText().toString();
        String descricao = edtDescProduto.getText().toString();
        double valor = Double.parseDouble(edtValorProduto.getText().toString());

        if (edtDescProduto.getText().toString().isEmpty()) {
            edtDescProduto.setError("Informe uma descrição!");
            return;
        }

        if (edtValorProduto.getText().toString().isEmpty()) {
            edtValorProduto.setError("Informe o valor unitário!");
            return;
        }

        double valorUn;
        if (edtValorProduto.getText().toString().isEmpty()) {
            edtValorProduto.setError("O valor unitário deve ser informado!!");
            edtValorProduto.requestFocus();
            return;
        } else {
            valorUn = Double.parseDouble(edtValorProduto.getText().toString());
            if (valorUn <= 0) {
                edtValorProduto.setError("O valor deve ser maior que zero!!");
                edtValorProduto.requestFocus();
                return;
            }
        }

        Produto produto = new Produto(codigo, descricao, valor);

        DataManagerSingleton.getInstance().salvarItemVenda(produto);

        atualizarListaProdutos();
    }

    private void atualizarListaProdutos() {
        List<Produto> produtos = DataManagerSingleton.getInstance().retornarItemsVenda();
        StringBuilder lista = new StringBuilder("Lista de Produtos:\n");

        for (Produto produto : produtos) {
            lista.append("Código: ").append(produto.getCodProduto()).append("\n");
            lista.append("Descrição: ").append(produto.getDescProduto()).append("\n");
            lista.append("Valor Unitário: ").append(produto.getValorProduto()).append("\n\n");
        }

        tvListaProdutos.setText(lista.toString());
    }
}
