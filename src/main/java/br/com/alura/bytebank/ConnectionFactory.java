package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectionFactory {

    private Scanner scanner;
    private String mySqlUserName;
    private String mySqlUserPassword;

    //metodos para utilizar na montagem da url jdbc
    public void setMySqlUserName(String mySqlUserName) {
        this.mySqlUserName = mySqlUserName;
    }

    public void setMySqlUserPassword(String mySqlUserPassword) {
        this.mySqlUserPassword = mySqlUserPassword;
    }

    public String getMySqlUserName() {
        return mySqlUserName;
    }

    public String getMySqlUserPassword() {
        return mySqlUserPassword;
    }

    public String scannerString (){
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    //criando a url de conexão do jdbc
    public String urlLocalJdbc(){
        return "jdbc:mysql://localhost:3306/byte_bank?user=" + getMySqlUserName() + "&password=" + getMySqlUserPassword();
    }

    //RecuperarConexao
    public Connection mySqlConnection(){
        System.out.println("Digite o seu nome de usuário do MySQL: ");
        setMySqlUserName(scannerString());
        System.out.println("Agora, digite sua senha do MySQL: ");
        setMySqlUserPassword(scannerString());

        try {
            //System.out.println("\nConectando ao servidor JDBC...");
            //Connection connection = DriverManager.getConnection(urlLocalJdbc());
            var hikariMethod = createDataSource().getConnection();
            //System.out.println("Conexão bem sucedida!");
            return hikariMethod;
            //connection.close();
        }
        catch (SQLException e) {
            System.out.println("\nConexão falhada! Código de erro: " + e.getErrorCode());
            throw new RuntimeException(e);
        }
    }

    public Connection recuperarConexao(){
        return mySqlConnection();
    }

    //usando o driver facilitador mvn repository
    private HikariDataSource createDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(urlLocalJdbc());
        config.setUsername(getMySqlUserName());
        config.setPassword(getMySqlUserPassword());
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }

}
