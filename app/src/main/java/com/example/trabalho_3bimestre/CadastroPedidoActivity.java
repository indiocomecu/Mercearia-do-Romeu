package com.example.trabalho_3bimestre;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho_3bimestre.model.Cliente;
import com.example.trabalho_3bimestre.model.Pedido;
import com.example.trabalho_3bimestre.model.Produto;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CadastroPedidoActivity extends AppCompatActivity {

    private ArrayList<Cliente> listaClientes;
    private ArrayList<Produto> listaProdutos, listaProdutosSelecionados;
    private AutoCompleteTextView acClientes;
    private Spinner spProdutos;
    private ImageButton btAddProduto;
    private TextView tvListaProdutos, tvErroProduto, tvTotalQtdProdutos, tvTotalValorProdutos,
            tvValorTotal, tvRadioGroupError, tvListaPedidos;
    private EditText edQuantidadeProduto, edValorProduto, edQuantidadeParcelas;
    private int posicaoSelecionada = 0;
    private String texto = "";
    private int produtosSelecionados = 0;
    private double totalProdutosSelecionados = 0;
    private double totalProdutosSelecionadosAux = 0;
    private RadioGroup rgPagamento;
    private RadioButton rbAPrazo;
    private Button btSalvarPedido;
    private Cliente clienteSelecionado;
    private int numParcelas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        listaProdutosSelecionados = new ArrayList<>();

        acClientes = findViewById(R.id.acClientes);
        spProdutos = findViewById(R.id.spProdutos);
        btAddProduto = findViewById(R.id.btAddProduto);
        tvListaProdutos = findViewById(R.id.tvListaProdutos);
        edQuantidadeProduto = findViewById(R.id.edQuantidadeProduto);
        edValorProduto = findViewById(R.id.edValorProduto);
        edQuantidadeParcelas = findViewById(R.id.edQuantidadeParcelas);
        tvErroProduto = findViewById(R.id.tvErroProduto);
        tvTotalQtdProdutos = findViewById(R.id.tvTotalQtdProdutos);
        tvTotalValorProdutos = findViewById(R.id.tvTotalValorProdutos);
        rgPagamento = findViewById(R.id.rgPagamento);
        tvValorTotal = findViewById(R.id.tvValorTotal);
        btSalvarPedido = findViewById(R.id.btSalvarPedido);
        rbAPrazo  = findViewById(R.id.rbAPrazo);
        tvRadioGroupError = findViewById(R.id.tvRadioGroupError);
        tvListaPedidos = findViewById(R.id.tvListaPedidos);
        getClientes();
        getProdutos();
        atualizarListaProduto();

        acClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clienteSelecionado = listaClientes.get(position);
            }
        });

        btAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { adicionarProduto(); }
        });

        spProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    posicaoSelecionada = i;
                    tvErroProduto.setVisibility(View.GONE);
                }
                if (l >  0 && listaProdutos.size() > 0) {
                    edValorProduto.setText(Double.toString(listaProdutos.get(i - 1).getValorProduto()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rgPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbAVista) {
                    calcularAVista();
                    edQuantidadeParcelas.setVisibility(View.INVISIBLE);
                } else if (i == R.id.rbAPrazo) {
                    tvValorTotal.setText("");
                    edQuantidadeParcelas.setVisibility(View.VISIBLE);
                }
            }
        });

        edQuantidadeParcelas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    calcularAPrazo();
                }
            }
        });

        btSalvarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { salvarPedido(); }
        });
    }

    private void getClientes() {
        listaClientes = DataManagerSingleton.getInstance().retornarClientes();
        String[]vetClientes = new String[listaClientes.size() + 1];
        vetClientes[0] = "Selecione o cliente";
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            vetClientes[i+1] = cliente.getNome();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetClientes);

        acClientes.setAdapter(adapter);
    }

    private void getProdutos() {
        listaProdutos = DataManagerSingleton.getInstance().retornarItemsVenda();
        String[]vetProdutos = new String[listaProdutos.size() + 1];
        vetProdutos[0] = "Selecione o produto";
        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto prod = listaProdutos.get(i);
            vetProdutos[i+1] = prod.getDescProduto();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetProdutos);

        spProdutos.setAdapter(adapter);
    }

    private void adicionarProduto() {

        if (posicaoSelecionada <=0) {
            tvErroProduto.setVisibility(View.VISIBLE);
            return;
        }

        if (edQuantidadeProduto.getText().toString().isEmpty() ||
                Integer.parseInt(edQuantidadeProduto.getText().toString()) <= 0) {
            edQuantidadeProduto.setError("Quantidade do produto deve ser informada!");
            edQuantidadeProduto.requestFocus();
            return;
        }

        if (edValorProduto.getText().toString().isEmpty() ||
                Double.parseDouble(edValorProduto.getText().toString()) <= 0) {
            edValorProduto.setError("Valor do produto deve ser informado!");
            edValorProduto.requestFocus();
            return;
        }

        Produto produto = listaProdutos.get(posicaoSelecionada-1);
        listaProdutosSelecionados.add(produto);
        texto += "Produto: " + produto.getDescProduto() + "\n" +
                "Valor UN: R$ " + edValorProduto.getText().toString() + "\n" +
                "Quantidade: " + edQuantidadeProduto.getText().toString() + "\n" +
                "_____________________________________\n";

        tvListaProdutos.setText(texto);
        produtosSelecionados += Integer.parseInt(edQuantidadeProduto.getText().toString());
        totalProdutosSelecionados += Double.parseDouble(edValorProduto.getText().toString())
                * Integer.parseInt(edQuantidadeProduto.getText().toString());
        totalProdutosSelecionadosAux = totalProdutosSelecionados;
        tvTotalQtdProdutos.setText("Qtd Produtos: " + produtosSelecionados);
        tvTotalValorProdutos.setText("Total: R$ " + totalProdutosSelecionados);
        limparCampos();
    }

    private void calcularAVista() {
        totalProdutosSelecionados = totalProdutosSelecionadosAux;
        Double desconto = (double)totalProdutosSelecionados * 0.05;
        totalProdutosSelecionados = totalProdutosSelecionados - desconto;
        tvValorTotal.setText("Valor total: " + totalProdutosSelecionados + "\n" +
                "Desconto: " + desconto);
    }
    private void calcularAPrazo() {
        if (edQuantidadeParcelas.getText().toString().isEmpty() ||
                Integer.parseInt(edQuantidadeParcelas.getText().toString()) <= 0) {
            edQuantidadeParcelas.setError("Informe a quantidade de parcelas");
            edQuantidadeParcelas.requestFocus();
            return;
        }
        numParcelas = Integer.parseInt(edQuantidadeParcelas.getText().toString());
        totalProdutosSelecionados = totalProdutosSelecionadosAux;
        Double acrescimo = 5.0 / 100.0 * totalProdutosSelecionados;
        totalProdutosSelecionados = totalProdutosSelecionados + acrescimo;
        Double totalParcela = totalProdutosSelecionados
                / Integer.parseInt(edQuantidadeParcelas.getText().toString());
        tvValorTotal.setText("Valor total: " + totalProdutosSelecionados + "\n" +
                "Valor parcela: " + totalParcela + "\n" +
                "Acréscimo: " + acrescimo + "\n" +
                "Qtd parcelas: " + edQuantidadeParcelas.getText().toString());
        edQuantidadeParcelas.setText("");
    }

    private void salvarPedido() {
        if (clienteSelecionado == null) {
            acClientes.setError("Selecione um cliente!");
            acClientes.requestFocus();
            return;
        }

        if (listaProdutosSelecionados.isEmpty()) {
            tvErroProduto.setVisibility(View.VISIBLE);
            return;
        }

        if (rgPagamento.getCheckedRadioButtonId() == -1) {
            tvRadioGroupError.setVisibility(View.VISIBLE);
            return;
        }

        if (rbAPrazo.isChecked()) {
            if (numParcelas <= 0) {
                edQuantidadeParcelas.setError("Informe a quantidade de parcelas!");
                edQuantidadeParcelas.requestFocus();
                return;
            }
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteSelecionado);
        pedido.setListaProdutos(listaProdutosSelecionados);
        pedido.setValorTotal(totalProdutosSelecionados);
        pedido.setTotalProdutos(produtosSelecionados);

        DataManagerSingleton.getInstance().salvarPedido(pedido);

        Toast.makeText(this, "Pedido salvo com sucesso!", Toast.LENGTH_LONG).show();

        this.finish();
    }


    private void atualizarListaProduto() {
        ArrayList<Pedido> lista = DataManagerSingleton.getInstance().retornarPedidos();
        String texto = "";
        for (Pedido pedido : lista) {
            texto += "Cliente: " + pedido.getCliente().getNome() + "\n" +
                    "CPF: " + pedido.getCliente().getCPF() + "\n" +
                    "Valor Total: " + pedido.getValorTotal() + "\n" +
                    "Total Produtos: " + pedido.getTotalProdutos() + "\n" +
                    "Lista Produtos" + pedido.getListaProdutos().toString() + "\n";
        }
        tvListaPedidos.setText(texto);
    }

    private void limparCampos() {
        spProdutos.setSelection(0);
        posicaoSelecionada = 0;
        edQuantidadeProduto.setText("");
        edValorProduto.setText("");
    }

}
