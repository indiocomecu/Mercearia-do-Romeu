package com.example.trabalho_3bimestre;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho_3bimestre.model.Cliente;

    public class CadastroClienteActivity extends AppCompatActivity {

        private EditText edtCpfCliente;
        private EditText edtNomeCliente;
        private TextView tvClientesCadastrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        Button btCadastrarCliente = findViewById(R.id.btCadastrarCliente);

        edtCpfCliente = findViewById(R.id.cpfCliente); // Corrigido para cpfCliente
        edtNomeCliente = findViewById(R.id.nomeCliente); // Corrigido para nomeCliente

        tvClientesCadastrados = findViewById(R.id.listaClientes);

        atualizarListaClientes();

        btCadastrarCliente.setOnClickListener(view -> salvarCliente());
    }

    private void salvarCliente() {
        if (edtCpfCliente.getText().toString().isEmpty()) {
            edtCpfCliente.setError("Informe o CPF!");
            return;
        }

        if (edtNomeCliente.getText().toString().isEmpty()) {
            edtNomeCliente.setError("Informe o Nome!");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(edtCpfCliente.getText().toString());
        cliente.setNome(edtNomeCliente.getText().toString());

        DataManagerSingleton.getInstance().salvarCliente(cliente);

        Toast.makeText(CadastroClienteActivity.this,
                "Cliente cadastrado com sucesso!!",
                Toast.LENGTH_LONG).show();

        this.finish();
    }

    private void atualizarListaClientes() {
        String texto = "";
        for (Cliente cliente : DataManagerSingleton.getInstance().retornarClientes()) {
            texto += "CPF: " + cliente.getCPF() + "\n" +
                    "Nome: " + cliente.getNome() + "\n" + "\n";
        }

        tvClientesCadastrados.setText(texto);
    }
}


