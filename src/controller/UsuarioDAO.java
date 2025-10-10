/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.*;
import jdbc.ModuloConexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.TelaLogin;
import view.TelaPrincipal;

/**
 *
 * @author GERAL
 */
public class UsuarioDAO {
      private Connection con;
      
      public UsuarioDAO(){
          this.con = ModuloConexao.conectar();
    }
       public void efetuarLogin(String email, String senha ) {
       
        try {

            //1 passo - SQL
            String sql = "select * from tbusuarios where usuario = ? and senha = ?";
            PreparedStatement stmt;
            stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //Usuario logou
                String perfil = rs.getString(6);
                if(perfil.equals("Admin")){
                     TelaPrincipal tela = new TelaPrincipal();
                     tela.setVisible(true);
                     tela.jMnItmUsuario.setEnabled(true);   
                     tela.jMnRelatorio.setEnabled(true);
                     tela.jLblUsuario.setText(rs.getString(2));
                     tela.jLblUsuario.setForeground(Color.RED);
                }else{
                     TelaPrincipal tela = new TelaPrincipal();
                     tela.setVisible(true);
                }
            } else {
                //Dados incorretos
                JOptionPane.showMessageDialog(null, "Dados incorretos!");
                new TelaLogin().setVisible(true);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro : " + erro);
        }

    }
       public void adicionarUsuario(Usuario obj){
           try{
               String sql = "insert into tbusuarios(iduser, usuario, fone, login, senha, perfil) values (?,?,?,?,?,?,?)";
               con = ModuloConexao.conectar();
               PreparedStatement stmt = con.prepareStatement(sql);
               stmt.setInt(1, obj.getIdUser());
               stmt.setString(2, obj.getUsuario());
               stmt.setString(3, obj.getFone());
               stmt.setString(4, obj.getLogin());
               stmt.setString(5, obj.getSenha());
               stmt.setString(6, obj.getPerfil());
               
               stmt.execute();
               stmt.close();
               JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!!");
               
           }catch (SQLIntegrityConstraintViolationException el){
               JOptionPane.showMessageDialog(null, "Login em uso.\nEscolha outro login.");
           }catch (HeadlessException | SQLException e){
           }finally{
               try{
                    con.close();
               }catch (SQLException ex){
                   JOptionPane.showMessageDialog(null, ex);
           }
       }

    }
       /**
        * Método que buscar o usuário pelo ID
        * @param IdUser
        * @return 
        */
       
   
       public Usuario buscarUsuario(int IdUser){
           try{
               String sql  = "select * from tbusuarios WHERE iduser = ;"; 
               con = ModuloConexao.conectar();
               PreparedStatement stmt = con.prepareStatement(sql);
               stmt.setInt(1, IdUser);
               
               ResultSet rs = stmt.executeQuery();
               
               if(rs.next()){
                   Usuario usuario = new Usuario();
                   usuario.setIdUser(rs.getInt("iduser"));
                   usuario.setUsuario(rs.getString("usuario"));
                   usuario.setFone(rs.getString("fone"));
                   usuario.setLogin(rs.getString("login"));
                   usuario.setSenha(rs.getString("senha"));
                   usuario.setPerfil(rs.getString("perfil"));
                   
                   return usuario;
               }else{
                   JOptionPane.showMessageDialog(null,"Usuário não encontrado!!!");
               }
               }catch (SQLException e){
                   JOptionPane.showMessageDialog(null, e);
           }
           return null;
    
        }   
        public void alterarUsuario(Usuario obj){
            try{
                String sql = "update tbusuarios set  usuario = ?, fone = ?, login = ?, senha = ?, perfil = ?,  WHERE iduser = ?";
                 con = ModuloConexao.conectar();
                PreparedStatement stmt = con.prepareStatement(sql);
              
                stmt.setString(1, obj.getUsuario());
                stmt.setString(2, obj.getFone());
                stmt.setString(3, obj.getLogin());
                stmt.setString(4, obj.getSenha());
                stmt.setString(5, obj.getPerfil());
                stmt.setInt(6, obj.getIdUser());
                   
                stmt.execute();
                
                stmt.close();
                JOptionPane.showMessageDialog(null,"Usuário cadastrado com sucesso");
                
            }catch(SQLIntegrityConstraintViolationException el){
                JOptionPane.showMessageDialog(null,"Login em uso.\nEscolha outro login.");
                   
            }catch(HeadlessException | SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }finally{
                try{
                    con.close();
                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, ex);
                }
            }    
    }
        
}       
