package br.upf.ads.appgym.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.google.gson.Gson;
import br.upf.ads.appgym.model.Aluno;
import br.upf.ads.appgym.model.GymIMC;
import br.upf.ads.appgym.model.Instrutor;
import br.upf.ads.appgym.model.Planos;
import br.upf.ads.appgym.model.Produto;

public class GymDAO {

	private static Connection conn; // Pertence a classe

	// Bloco estático
	static {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		String url = "jdbc:postgresql://localhost/dbgym";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "masterkey");
		try {
			conn = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// MÉTODOS ALUNOS
	public boolean insere(Aluno aluno) {

		try {
			PreparedStatement prst = conn.prepareStatement(
					"insert into aluno (nome, sexo, idade,  peso, altura, cpf, telefone, endereco, numero, bairro, cep, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			prst.setString(1, aluno.nome);
			prst.setString(2, aluno.sexo);
			prst.setInt(3, aluno.idade);
			prst.setFloat(4, aluno.peso);
			prst.setFloat(5, aluno.altura);
			prst.setString(6, aluno.cpf);
			prst.setString(7, aluno.telefone);
			prst.setString(8, aluno.endereco);
			prst.setString(9, aluno.numero);
			prst.setString(10, aluno.bairro);
			prst.setString(11, aluno.cep);
			prst.setString(12, aluno.email);

			prst.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}

	public boolean altera(Aluno aluno) {

		try {
			PreparedStatement prst = conn.prepareStatement(
					"UPDATE aluno SET nome = ?, sexo = ?, idade = ?, peso = ?, altura = ?, cpf = ?, telefone = ?, endereco = ?, numero = ?, bairro = ?, cep = ?, email = ? WHERE id = ?");

			prst.setString(1, aluno.nome);
			prst.setString(2, aluno.sexo);
			prst.setInt(3, aluno.idade);
			prst.setFloat(4, aluno.peso);
			prst.setFloat(5, aluno.altura);
			prst.setString(6, aluno.cpf);
			prst.setString(7, aluno.telefone);
			prst.setString(8, aluno.endereco);
			prst.setString(9, aluno.numero);
			prst.setString(10, aluno.bairro);
			prst.setString(11, aluno.cep);
			prst.setString(12, aluno.email);
			prst.setInt(13, aluno.id);
			;

			prst.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}

	public boolean deleta(int id) {
		try {
			PreparedStatement prst = conn.prepareStatement("delete from aluno where id = ?");

			prst.setInt(1, id);
			prst.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}

	public List<Aluno> lista() {

		List<Aluno> alunos = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from aluno");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.id = resultSet.getInt("id");
				aluno.nome = resultSet.getString("nome");
				aluno.sexo = resultSet.getString("sexo");
				aluno.idade = resultSet.getInt("idade");
				aluno.peso = resultSet.getFloat("peso");
				aluno.altura = resultSet.getFloat("altura");
				aluno.cpf = resultSet.getString("cpf");
				aluno.telefone = resultSet.getString("telefone");
				aluno.endereco = resultSet.getString("endereco");
				aluno.numero = resultSet.getString("numero");
				aluno.bairro = resultSet.getString("bairro");
				aluno.cep = resultSet.getString("cep");
				aluno.email = resultSet.getString("email");

				alunos.add(aluno);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return alunos;
	}

	// BUSCAR ALUNO POR NOME
	public List<Aluno> listaBuscar(String buscarNome) {

		List<Aluno> alunos = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from aluno where nome =" + "'" + buscarNome + "'");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.id = resultSet.getInt("id");
				aluno.nome = resultSet.getString("nome");
				aluno.sexo = resultSet.getString("sexo");
				aluno.idade = resultSet.getInt("idade");
				aluno.peso = resultSet.getFloat("peso");
				aluno.altura = resultSet.getFloat("altura");
				aluno.cpf = resultSet.getString("cpf");
				aluno.telefone = resultSet.getString("telefone");
				aluno.endereco = resultSet.getString("endereco");
				aluno.numero = resultSet.getString("numero");
				aluno.bairro = resultSet.getString("bairro");
				aluno.cep = resultSet.getString("cep");
				aluno.email = resultSet.getString("email");

				alunos.add(aluno);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return alunos;
	}

	// LISTAR ALUNOS POR GÊNERO
	public List<Aluno> listaAlunoGenero(String genero) {

		List<Aluno> alunos = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from aluno where sexo =" + "'" + genero + "'");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.id = resultSet.getInt("id");
				aluno.nome = resultSet.getString("nome");
				aluno.sexo = resultSet.getString("sexo");
				aluno.idade = resultSet.getInt("idade");
				aluno.peso = resultSet.getFloat("peso");
				aluno.altura = resultSet.getFloat("altura");
				aluno.cpf = resultSet.getString("cpf");
				aluno.telefone = resultSet.getString("telefone");
				aluno.endereco = resultSet.getString("endereco");
				aluno.numero = resultSet.getString("numero");
				aluno.bairro = resultSet.getString("bairro");
				aluno.cep = resultSet.getString("cep");
				aluno.email = resultSet.getString("email");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return alunos;
	}

	// LISTAR ALUNOS POR GÊNERO
	public List<Aluno> listaAlunoBairro(String genero) {

		List<Aluno> alunos = new ArrayList<>();

		try {
			PreparedStatement prst = conn
					.prepareStatement("select * from instrutor where genero =" + "'" + genero + "'");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.id = resultSet.getInt("id");
				aluno.nome = resultSet.getString("nome");
				aluno.sexo = resultSet.getString("sexo");
				aluno.idade = resultSet.getInt("idade");
				aluno.peso = resultSet.getFloat("peso");
				aluno.altura = resultSet.getFloat("altura");
				aluno.cpf = resultSet.getString("cpf");
				aluno.telefone = resultSet.getString("telefone");
				aluno.endereco = resultSet.getString("endereco");
				aluno.numero = resultSet.getString("numero");
				aluno.bairro = resultSet.getString("bairro");
				aluno.cep = resultSet.getString("cep");
				aluno.email = resultSet.getString("email");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return alunos;
	}

	// BUSCAR ALUNO POR NOME
	public List<Aluno> listaBuscarNome(String buscarNome) {

		List<Aluno> alunos = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from aluno where nome =" + "'" + buscarNome + "'");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Aluno aluno = new Aluno();

				aluno.id = resultSet.getInt("id");
				aluno.nome = resultSet.getString("nome");
				aluno.sexo = resultSet.getString("sexo");
				aluno.idade = resultSet.getInt("idade");
				aluno.peso = resultSet.getFloat("peso");
				aluno.altura = resultSet.getFloat("altura");
				aluno.cpf = resultSet.getString("cpf");
				aluno.telefone = resultSet.getString("telefone");
				aluno.endereco = resultSet.getString("endereco");
				aluno.numero = resultSet.getString("numero");
				aluno.bairro = resultSet.getString("bairro");
				aluno.cep = resultSet.getString("cep");
				aluno.email = resultSet.getString("email");

				alunos.add(aluno);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return alunos;
	}

	// QUANTIDADE ALUNOS CADASTRADOS
	public int listaQtdAluno() {

		List<Aluno> alunos = new ArrayList<>();
		int cont = 0;
		try {
			PreparedStatement prst = conn.prepareStatement("select * from aluno");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				cont++;

			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return cont;
	}

	// Lista produtos do birl disponível para compra na academia
	public List<Produto> listaProduto() {

		List<Produto> produtos = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from produto");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Produto produto = new Produto();

				produto.produto = resultSet.getString("produto");
				produto.marca = resultSet.getString("marca");
				produto.qtdDisponivel = resultSet.getInt("qtdDisponivel");
				produto.valor = resultSet.getFloat("valor");

				produtos.add(produto);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return produtos;
	}

	// Métodos para realizar o cálculo IMC

	public void calcByJson(GymIMC gymimc) {
		String msg = null;
		Float imc = gymimc.peso / (gymimc.altura * gymimc.altura);

	}

	// CRUD Instrutor

	public boolean insereInstrutor(Instrutor instrutor) {

		try {
			PreparedStatement prst = conn.prepareStatement(
					"insert into instrutor (nome, telefone, horarioDisponivel, turno) values (?, ?, ?, ?)");
			prst.setString(1, instrutor.nome);
			prst.setString(2, instrutor.telefone);
			prst.setString(3, instrutor.horarioDisponivel);
			prst.setString(4, instrutor.turno);

			prst.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}

	public boolean alteraInstrutor(Instrutor instrutor) {
		try {
			PreparedStatement prst = conn.prepareStatement(
					"update instrutor set nome = ?, telefone = ?, horarioDisponivel = ?, turno = ? where id = ?");
			prst.setString(1, instrutor.nome);
			prst.setString(2, instrutor.telefone);
			prst.setString(3, instrutor.horarioDisponivel);
			prst.setString(4, instrutor.turno);
			prst.setInt(5, instrutor.id);

			prst.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}

	public boolean deletaInstrutor(int id) {
		try {
			PreparedStatement prst = conn.prepareStatement("delete from instrutor where id = ?");

			prst.setInt(1, id);
			prst.execute();
			return true;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
			return false;
		}
	}

	// LISTAR INSTRUTORES
	public List<Instrutor> listaInstrutor() {

		List<Instrutor> instrutores = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from instrutor");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Instrutor instrutor = new Instrutor();

				instrutor.id = resultSet.getInt("id");
				instrutor.nome = resultSet.getString("nome");
				instrutor.telefone = resultSet.getString("telefone");
				instrutor.horarioDisponivel = resultSet.getString("horarioDisponivel");
				instrutor.turno = resultSet.getString("turno");

				instrutores.add(instrutor);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return instrutores;
	}

	// LISTAR INSTRUTORES POR TURNO
	public List<Instrutor> listaInstrutorTurno(String turno) {

		List<Instrutor> instrutores = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from instrutor where turno =" + "'" + turno + "'");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Instrutor instrutor = new Instrutor();
				instrutor.id = resultSet.getInt("id");
				instrutor.nome = resultSet.getString("nome");
				instrutor.telefone = resultSet.getString("telefone");
				instrutor.horarioDisponivel = resultSet.getString("horarioDisponivel");
				instrutor.turno = resultSet.getString("turno");

				instrutores.add(instrutor);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return instrutores;
	}

	// ------------------------------------------------------------------------------

	// LISTAR PLANOS DA ACADEMIA

	public List<Planos> listaPlanos() {

		List<Planos> planos = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from planos");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Planos plano = new Planos();

				plano.id = resultSet.getInt("id");
				plano.tipo = resultSet.getString("tipo");
				plano.valor = resultSet.getFloat("valor");
				plano.periodo = resultSet.getString("periodo");

				planos.add(plano);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return planos;
	}

	// LISTA PROMOÇÕES DOS PLANOS
	public List<Planos> listaPromocao() {

		List<Planos> promocoes = new ArrayList<>();

		try {
			PreparedStatement prst = conn.prepareStatement("select * from planos where tipo = 'promoção'");

			ResultSet resultSet = prst.executeQuery();

			while (resultSet.next()) {
				Planos promocao = new Planos();

				promocao.id = resultSet.getInt("id");
				promocao.tipo = resultSet.getString("tipo");
				promocao.valor = resultSet.getFloat("valor");
				promocao.periodo = resultSet.getString("periodo");

				promocoes.add(promocao);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);

		}
		return promocoes;
	}

}