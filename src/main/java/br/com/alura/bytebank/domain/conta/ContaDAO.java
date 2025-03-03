package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {
    //variável que chamará a conexão com o JDBC
    private Connection conn;

    //constructor
    public ContaDAO(Connection connection) {
            this.conn = connection;
    }

    //salva a nova conta criada
    public void salvar(DadosAberturaConta dadosDaConta){
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(),BigDecimal.ZERO, cliente, true);

        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, esta_ativa)"
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            //PreparedStatement - responsável por setar os valores do sql!
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
            preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            preparedStatement.setString(5, dadosDaConta.dadosCliente().email());
            preparedStatement.setBoolean(6, true);

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //lista as contas criadas com executeQuery
    public Set<Conta> listar(){
        Set<Conta> contas = new HashSet<>();
        PreparedStatement ps;
        ResultSet resultSet;

        String sql = "SELECT * FROM conta WHERE esta_ativa = true";

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                Integer numero = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                boolean estaAtiva = resultSet.getBoolean(6);
                DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(nome, cpf, email);

                Cliente cliente = new Cliente(dadosCadastroCliente);

                contas.add(new Conta(numero, saldo, cliente, estaAtiva));
            }
            resultSet.close();
            ps.close();
            conn.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contas;
    }

    //altera o valor do saldo (tanto para adicionar quanto para subtrair)
    public void alterar(BigDecimal valor, Integer numero) {
        PreparedStatement ps;
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            ps.execute();
            conn.commit();
            ps.close();
            conn.close();
        }
        catch (SQLException e){
            try {
                conn.rollback();
            }
            catch (SQLException ex){
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    //
    public void alterarLogico(Integer numeroDaConta){
        PreparedStatement ps;
        String sql = "UPDATE conta SET esta_ativa = false WHERE numero = ?";
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);

            ps.setInt(1, numeroDaConta);

            ps.execute();
            conn.commit();
            ps.close();
            conn.close();
        }
        catch (SQLException e){
            try {
                conn.rollback();
            }
            catch (SQLException ex){
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    //serve para verificar o número das contas disponíveis
    public Conta listarPorNumero(Integer numero) {
        String sql = "SELECT * FROM conta WHERE numero = ? AND esta_ativa = true";

        PreparedStatement ps;
        ResultSet resultSet;
        Conta conta = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numero);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Integer numeroRecuperado = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                boolean estaAtiva = resultSet.getBoolean(6);

                DadosCadastroCliente dadosCadastroCliente =
                        new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(dadosCadastroCliente);

                conta = new Conta(numeroRecuperado, saldo, cliente, estaAtiva);
            }
            resultSet.close();
            ps.close();
            conn.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;
    }

    //apaga da base de dados a conta criada (o saldo precisa estar zerado)
    public void encerrarContaNoBanco(Integer numeroDaConta){
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM conta WHERE numero = ?";

        try {
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, numeroDaConta);

            preparedStatement.execute();
            conn.commit();
            preparedStatement.close();
            conn.close();
        }

        catch (SQLException e) {
            try {
                conn.rollback();
            }
            catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }

    }

}
