package com.librerianova.modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {

    private final List<Cliente> clientes;

    public GestorClientes() {
        clientes = new ArrayList<>();
    }

    public boolean existeDni(String dni) {

        for (Cliente cliente : clientes) {

            if (cliente.getDni().equals(dni)) {
                return true;
            }
        }

        return false;
    }

    public void agregarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        if (existeDni(cliente.getDni())) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con ese DNI."
            );
        }

        clientes.add(cliente);
    }

    public Cliente buscarPorDni(String dni) {

        for (Cliente cliente : clientes) {

            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }

        return null;
    }

    public Cliente buscarPorId(int id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId() == id) {
                return cliente;
            }
        }

        return null;
    }

    public boolean eliminarPorDni(String dni) {

        Cliente cliente = buscarPorDni(dni);

        if (cliente != null) {
            return clientes.remove(cliente);
        }

        return false;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public int cantidadClientes() {
        return clientes.size();
    }

    public void listarClientes() {

        if (clientes.isEmpty()) {

            System.out.println(
                    "No hay clientes registrados."
            );

            return;
        }

        for (Cliente cliente : clientes) {
            cliente.mostrarDatos();
        }
    }
}