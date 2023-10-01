package com.example.trabalho_3bimestre;

import com.example.trabalho_3bimestre.model.Cliente;
import com.example.trabalho_3bimestre.model.Pedido;
import com.example.trabalho_3bimestre.model.Produto;

import java.util.ArrayList;

public class DataManagerSingleton {

    private static DataManagerSingleton instance;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Pedido> listaPedidos;
    private ArrayList<Produto> listaItemsVenda;

    private DataManagerSingleton() {
        listaClientes = new ArrayList<>();
        listaPedidos = new ArrayList<>();
        listaItemsVenda = new ArrayList<>();
    }

    public void salvarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    public ArrayList<Pedido> retornarPedidos() {
        return listaPedidos;
    }

    public void salvarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> retornarClientes() {
        return listaClientes;
    }

    public void salvarItemVenda(Produto produto) {
        listaItemsVenda.add(produto);
    }

    public ArrayList<Produto> retornarItemsVenda() {
        return listaItemsVenda;
    }

    public static DataManagerSingleton getInstance() {
        if (instance == null) {
            instance = new DataManagerSingleton();
        }
        return instance;
    }
}
