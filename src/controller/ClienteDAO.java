/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.HeadlessException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jdbc.ModuloConexao;
import model.Cliente;

/**
 *
 * @author GERAL
 */
public class ClienteDAO {

    Connection con;

    public ClienteDAO() {
        this.con = ModuloConexao.conectar();
    }
    
     public List<Cliente> listarCliente() {
        try {

            //1 passo criar a lista
            List<Cliente> lista = new ArrayList<>();

            //2 passo - criar o sql , organizar e executar.
            String sql = "select * from tbclientes";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente obj = new Cliente();

                obj.setId(rs.getInt("idcli"));
                obj.setNome(rs.getString("nomecli"));
                obj.setEndereco(rs.getString("endcli"));
                obj.setFone(rs.getString("fonecli"));
                obj.setEmail(rs.getString("emailcli"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro :" + erro);
            return null;
        }

    }


    public void adicionarClientes(Cliente obj) {
        try {
            String sql = "insert into tbclientes( nomecli, endcli,fonecli ,emailcli ) values (?,?,?,?,?)";
            con = ModuloConexao.conectar();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(3, obj.getNome());
                stmt.setString(4, obj.getEndereco());
                stmt.setString(5, obj.getFone());
                stmt.setString(6, obj.getEmail());
                
                stmt.execute();
            }
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!!");

        } catch (SQLIntegrityConstraintViolationException el) {
            JOptionPane.showMessageDialog(null, "Login em uso.\nEscolha outro login.");
        } catch (HeadlessException | SQLException e) {
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }

    }

    public Cliente buscarCliente(int IdCli) {
        try {
            String sql = "select * from tbclientes WHERE idcli = ;";
            con = ModuloConexao.conectar();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, IdCli);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente clientes = new Cliente();
                clientes.setId(rs.getInt("idcli"));
                clientes.setNome(rs.getString("cliente"));
                clientes.setFone(rs.getString("fone"));
                clientes.setEmail(rs.getString("email"));
                clientes.setEndereco(rs.getString("endereco"));

                return clientes;
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado!!!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;

    }

    public void alterarCliente(Cliente obj, int parseInt) {
        try {
            String sql = "update tbclientes set nome = ?, fone = ?, senha = ?,  WHERE idcli = ?";
            con = ModuloConexao.conectar();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, obj.getNome());
                stmt.setString(2, obj.getFone());
                stmt.setString(3, obj.getEmail());
                stmt.setString(4, obj.getEndereco());
                stmt.setInt(5, obj.getId());
                
                stmt.execute();
            }
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");

        } catch (SQLIntegrityConstraintViolationException el) {
            JOptionPane.showMessageDialog(null, "Login em uso.\nEscolha outro login.");

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
        public List<Cliente> listarClienteNome(String nome) {
        try {

            //1 passo criar a lista
            List<Cliente> lista = new ArrayList<>();

            //2 passo - criar o sql , organizar e executar.
            String sql = "select idcli as id, nomecli as nome,endcli as endereco, fonecli as fone,emailcli as email from tbclientes where nomecli like ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente obj = new Cliente();

                obj.setId(rs.getInt("idcli"));
                obj.setNome(rs.getString("nomecli"));
                obj.setEndereco(rs.getString("endcli"));
                obj.setFone(rs.getString("fonecli"));
                obj.setEmail(rs.getString("emailcli"));
                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro :" + erro);
            return null;
        }

    }
         public void deletarCliente(int idcli) {
       
        try{
        String sql = "delete from tbcliente where idcli = ?";
            con = ModuloConexao.conectar();
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, idcli);
                
                stmt.execute();
            }
            JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!!!");
        } catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try{
                con.close();
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
}
