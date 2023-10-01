package com.example.trabalho_3bimestre.model;

import java.util.ArrayList;

public class Pedido {

    private Cliente cliente;
    private ArrayList<Produto> listaProdutos;
    private double valorTotal;
    private int totalProdutos;
    private int quantidadeParcelas;
    private int formaPagamento;

    public Pedido() { }

    public Pedido(Cliente cliente, ArrayList<Produto> listaProdutos, double valorTotal, int totalProdutos, int quantidadeParcelas) {
        this.cliente = cliente;
        this.listaProdutos = listaProdutos;
        this.valorTotal = valorTotal;
        this.totalProdutos = totalProdutos;
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(ArrayList<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(int totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public int getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
