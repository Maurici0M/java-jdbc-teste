<h1 align='center'>Passos para rodar a aplicação:</h1>

Arquivos + modificados:

* ConnectionFactory.java
* ContaDAO.java

<br>

<p>⚠️ Importante!</p>

É necessário ter instalado o MYSQL e realizar os seguintes comandos no terminal:

<details>
  <summary> <b>Criando a tabela que o projeto usará: </b> </summary>
  <br>

* mysql -u root -p (pedirá uma senha, é a mesma que você utilizou quando criou o login do MySql)

* create database byte_bank;

* use byte_bank;

------------------------------------------------------------------------------------------

Após isso, copie o código de criação da tabela e cole no terminal:

* CREATE TABLE `conta` (
  `numero` int NOT NULL,
  `saldo` decimal(10,0) DEFAULT NULL,
  `cliente_nome` varchar(50) DEFAULT NULL,
  `cliente_cpf` varchar(11) DEFAULT NULL,
  `cliente_email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`numero`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

------------------------------------------------------------------------------------------

Se tudo ocorrer bem, faça o seguinte:

Cole o seguinte comando no terminal, para adicionar mais uma coluna na tabela:

* alter table conta add column esta_ativa boolean default true;

Após isso, a aplicação já estará configurada para uso! ⭐

</details>

--------------------------------------------------------------------------------------------

<details>
  <summary> <b>Comando simples para consultar dados na tabela via terminal: </b> </summary>
  <br>
  
SELECT * FROM conta; (retorna todos os dados da conta)

SELECT * FROM conta WHERE numero = 0000; (retorna todos os dados da conta em que o número da conta é igual a 0000 - você pode usar o where para verificar qualquer ítem dispinível na tabela!)

DELETE FROM conta WHERE numero = 0000; (deleta todos os dados da tabela, onde o número da conta seja igual a 0000 - cuidado ao usar o DELETE para não acabar apagando dados por engano!)

UPDATE conta SET saldo = 100 WHERE numero = 0000; (faz uma atualização na tabela, setando um novo valor para a coluna saldo, na conta que tem o número 0000 - use a criatividade para modificar os valores e parâmetros da requisição!)

</details>


--------------------------------------------------------------------------------------------
<details>
  <summary> <b>Dependências: </b> </summary>
  <br>
  
  * HikariCP (para controlar o pool de conexões simultâneas)
  * https://mvnrepository.com/artifact/com.zaxxer/HikariCP
</details>

--------------------------------------------------------------------------------------------
<details>
  <summary> <b>Materiais de pesquisa: </b> </summary>
  <br>
  
  * https://www.alura.com.br/artigos/join-em-sql
  * https://sae.unb.br/cae/conteudo/unbfga/sbd/new_mysql_delete.html
  * ...
</details>


  
