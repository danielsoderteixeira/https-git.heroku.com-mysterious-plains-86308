package br.upf.ads.appgym.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;
import br.upf.ads.appgym.dao.GymDAO;
import br.upf.ads.appgym.model.Aluno;
import br.upf.ads.appgym.model.GymIMC;
import br.upf.ads.appgym.model.Instrutor;

@Path("services")
public class Resources {

	private GymDAO dao = new GymDAO();

	// ALUNOS
	// 1- LISTAR ALUNOS CADASTRADOS / OK
	@GET
	@Path("aluno")
	@Produces(MediaType.APPLICATION_JSON) // tipo de retorno---- manda de volta pro cliente
	public String listarAlunos() { // sempre retorna texto
		return new Gson().toJson(dao.lista());
	}

	// 2- LISTAR ALUNOS POR NOME / OK
	/// aluno/nome?nomeAluno=Douglas
	@GET
	@Path("aluno/nome")
	@Produces(MediaType.APPLICATION_JSON)
	public String listarPorNome(@QueryParam("nomeAluno") String nomeAluno) {
		return new Gson().toJson(dao.listaBuscarNome(nomeAluno));
	}

	/*
	 * // RETORNA O NÚMERO TOTAL DE ALUNOS VER //
	 * http://localhost:8080/ServicosREST/api/v1/Servicos/totalServicos
	 * 
	 * @GET
	 * 
	 * @Path("totalServicos")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String totalDeServicos() { return
	 * searchNumero("SELECT count(id) as contagem FROM servico;"); }
	 **/

	// 3 - LISTAR ALUNOS POR SEXO / OK
	// /aluno/genero?pesquisa=Masculino
	@GET
	@Path("/aluno/genero")
	@Produces(MediaType.APPLICATION_JSON)
	public String listaAlunoGenero(@QueryParam("pesquisa") String pesquisa) {
		return new Gson().toJson(dao.listaAlunoGenero(pesquisa));
	}

	// 4 - INSERIR ALUNOS POR JSON / OK
	@POST
	@Path("aluno")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insereByJson(String alunoJson) {
		Aluno aluno = new Gson().fromJson(alunoJson, Aluno.class);
		return insere(aluno);

	}

	// 5 - INSERIR ALUNOS VIA FORM / OK
	@POST
	@Path("aluno")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response insereByForm(@FormParam("nome") String nome, @FormParam("sexo") String sexo,
			@FormParam("idade") int idade, @FormParam("peso") Float peso, @FormParam("altura") Float altura,
			@FormParam("cpf") String cpf, @FormParam("telefone") String telefone,
			@FormParam("endereco") String endereco, @FormParam("numero") String numero,
			@FormParam("bairro") String bairro, @FormParam("cep") String cep, @FormParam("email") String email) {

		Aluno aluno = new Aluno();
		aluno.nome = nome;
		aluno.sexo = sexo;
		aluno.idade = idade;
		aluno.peso = peso;
		aluno.altura = altura;
		aluno.cpf = cpf;
		aluno.telefone = telefone;
		aluno.endereco = endereco;
		aluno.numero = numero;
		aluno.bairro = bairro;
		aluno.cep = cep;
		aluno.email = email;

		return insere(aluno);

	}

	// TESTAR SE ENVIOU OU NÃO
	private Response insere(Aluno aluno) {

		Status status = Status.OK;
		String msg = "{'msg': 'Aluno criado com sucesso!'}";

		if (aluno.nome == null || aluno.telefone == null || aluno.cpf == null) {
			status = Status.BAD_REQUEST;
			msg = "{'msg': 'Campos Nome, Telefone e CPF são obrigatórios'}";
		} else {
			boolean success = dao.insere(aluno);

			if (!success) {
				status = Status.NOT_MODIFIED;
				msg = "{'msg': 'Ocorreu algum erro na inserção'}";
			}
		}

		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();
	}

	// 5 - ALTERAR ALUNO / OK
	@PUT
	@Path("aluno")
	@Consumes(MediaType.APPLICATION_JSON) // consome json pelo body
	public Response alterarByJson(String alunoJson) {

		Aluno aluno = new Gson().fromJson(alunoJson, Aluno.class); // Intancia Servico atraves do JSON recebido.
		return alterarAluno(aluno); // Insere o servico no BD, mas antes valida. Retorna response.
	}

	// Valida o produto e insere no BD
	private Response alterarAluno(Aluno aluno) {
		// Response.Status status = null;
		Status status = Status.OK;

		String msg = "{'msg': 'Aluno editado com sucesso'}";

		if (aluno.nome == null || aluno.telefone == null || aluno.cpf == null) {
			status = Status.BAD_REQUEST;
			msg = "{'msg': 'Campos Nome, Telefone e CPF são obrigatórios'}";
		} else {
			boolean success = dao.altera(aluno);

			if (!success) {
				status = Status.NOT_MODIFIED;
				msg = "{'msg': 'Ocorreu algum erro na alteração'}";
			}
		}
		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();

	}

	// 6 - DELETAR ALUNO / OK
	@Path("/aluno/{key}")
	@DELETE
	public Response deleta(@PathParam("key") int id) {
		Status status = Status.OK;
		String msg = "{'msg': 'Aluno deletado com sucesso!'}";

		if (id < 1) {
			status = Status.BAD_REQUEST;
			msg = "{'msg': 'O id do aluno deve ser maior que zero'}";
		} else {
			boolean success = dao.deleta(id);

			if (!success) {
				status = Status.NOT_MODIFIED;
				msg = "{'msg': 'Ocorreu algum erro ao deletar'}";
			}
		}

		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();
	}

	// GYM RESOURCES

	// 7 - LISTAR PRODUTOS DA ACADEMIA / OK
	@GET
	@Path("produtos")
	@Produces(MediaType.APPLICATION_JSON) // tipo de retorno---- manda de volta pro cliente
	public String listaProduto() { // sempre retorna texto
		return new Gson().toJson(dao.listaProduto());
	}

	// 8- CALCULADORA SIMPLES IMC / OK
	@POST
	@Path("imc")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response calcImc(String gymimcJson) {
		GymIMC gymimc = new Gson().fromJson(gymimcJson, GymIMC.class);
		return calcByJson(gymimc);
	}

	private Response calcByJson(GymIMC gymimc) {

		Float imc = gymimc.peso / (gymimc.altura * gymimc.altura);

		int status = 200;
		String msg = null;

		if (imc < 17) {
			status = 428;
			msg = "{'msg': 'Muito abaixo do peso!'}";
		}

		if (imc > 17 && imc < 18.49) {
			msg = "{'msg': 'Abaixo do peso!'}";
		}

		if (imc > 18.5 && imc < 24.99) {
			msg = "{'msg': 'Peso normal!'}";
		}

		if (imc > 25 && imc < 29.99) {
			msg = "{'msg': 'Acima do peso!'}";
		}

		if (imc > 30 && imc < 34.99) {
			msg = "{'msg': 'Obesidade I!'}";
		}

		if (imc > 35 && imc < 39.99) {
			msg = "{'msg': 'Obesidade II (severa)!'}";
		} 
		if(imc > 40){
			msg = "{'msg': 'Obesidade III (mórbida)!'}";
		}

		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();
	}

	
	  // 9- CALCULADORA ESCOLHENDO ALUNO POR NOME //imc?nomeAluno=descricao
	  
	  @GET
	  @Path("imc/aluno")
	  
	  @Produces(MediaType.APPLICATION_JSON) 
	  public Response calcImcAluno(@QueryParam("pesquisa") String nomeAluno) { 
		 
		  Aluno aluno = new Gson().fromJson(nomeAluno, Aluno.class); 
		  return calcAlunoByJson(aluno); }
	    
	  private Response calcAlunoByJson(Aluno nomeAluno) {
	  
	  Float imc = nomeAluno.peso / (nomeAluno.altura * nomeAluno.altura);
	  
	  int status = 200; String msg = null;
	  
	  if (imc < 17) { status = 428; msg = "{'msg': 'Muito abaixo do peso!'}"; }
	  
	  if (imc > 17 && imc < 18.49) { msg = "{'msg': 'Abaixo do peso!'}"; }
	  
	  if (imc > 18.5 && imc < 24.99) { msg = "{'msg': 'Peso normal!'}"; }
	  
	  if (imc > 25 && imc < 29.99) { msg = "{'msg': 'Acima do peso!'}"; }
	  
	  if (imc > 30 && imc < 34.99) { msg = "{'msg': 'Obesidade I!'}"; }
	  
	  if (imc > 35 && imc < 39.99) { msg = "{'msg': 'Obesidade II (severa)!'}"; }
	  
	  if(imc > 40){
			msg = "{'msg': 'Obesidade III (mórbida)!'}";
		}
	  
	  return Response.status(status) .entity(msg) .type(MediaType.APPLICATION_JSON)
	  .build(); }
	 

	// 9 - CALCULAR IMC ESCOLHENDO O ALUNO NA URL
	/*
	 * ///busca?nome=aluno
	 * 
	 * @GET
	 * 
	 * @Path("busca") public Response calcImcAluno(@QueryParam("nome") String nome)
	 * { GymIMC gymimc = new Gson().fromJson(gymimcJson, GymIMC.class); return
	 * calcByJson(gymimc); String msg = null;
	 * 
	 * 
	 * if (nome == null) { msg =
	 * "{'mensagem': 'O query parameter campo não foi enviado'}"; }
	 * 
	 * private Response calcByJsonAluno(Aluno aluno) {
	 * 
	 * Float imc = aluno.peso / (aluno.altura * aluno.altura);
	 * 
	 * int status = 200; String msg = null;
	 * 
	 * if (imc < 17) { status = 428; msg = "Aluno: " + aluno +
	 * " Muito abaixo do peso!"; }
	 * 
	 * if (imc > 17 && imc < 18.49) { msg = "Aluno: " + aluno + " Abaixo do peso!";
	 * }
	 * 
	 * if (imc > 18.5 && imc < 24.99) { msg = "Aluno: " + aluno + " Peso normal!"; }
	 * 
	 * if (imc > 25 && imc < 29.99) { msg = "Aluno: " + aluno + " Acima do peso!"; }
	 * 
	 * if (imc > 30 && imc < 34.99) { msg = "Aluno: " + aluno + "Obesidade I"; }
	 * 
	 * if (imc > 35 && imc < 39.99) { msg = "Aluno: " + aluno +
	 * " Obesidade II (severa)!"; } else { msg = "Aluno: " + aluno +
	 * " Obesidade III (mórbida)!'}"; }
	 * 
	 * return Response.status(status) .entity(msg) .type(MediaType.APPLICATION_JSON)
	 * .build(); }
	 */

	// 10- LISTAR INSTRUTORES CADASTRADOS / OK
	@GET
	@Path("instrutor")
	@Produces(MediaType.APPLICATION_JSON) // tipo de retorno---- manda de volta pro cliente
	public String listarInstrutores() {
		return new Gson().toJson(dao.listaInstrutor());
	}

	// 11 - LISTAR INSTRUTORES POR TURNOS / OK
	// /instrutor/turno?pesquisa=Tarde
	@GET
	@Path("/instrutor/turno")
	@Produces(MediaType.APPLICATION_JSON) // tipo de retorno---- manda de volta pro cliente
	public String listaInstrutorTurno(@QueryParam("pesquisa") String pesquisa) { // sempre retorna texto
		return new Gson().toJson(dao.listaInstrutorTurno(pesquisa));
	}

	// INSERIR INSTRUTOR
	@POST
	@Path("instrutor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insereInstrutorByJson(String instrutorJson) {
		Instrutor instrutor = new Gson().fromJson(instrutorJson, Instrutor.class);
		return insereInstrutor(instrutor);
	}

	// ALTERAR INSTRUTOR
	@PUT
	@Path("instrutor")
	@Consumes(MediaType.APPLICATION_JSON) // consome json pelo body
	public Response alterarInstrutorByJson(String instrutorJson) {

		Instrutor instrutor = new Gson().fromJson(instrutorJson, Instrutor.class); // Intancia Servico
																					// atraves do JSON
																					// recebido.
		return alterarInstrutor(instrutor); // Insere o servico no BD, mas antes valida. Retorna response.
	}

	// Valida o produto e insere no BD
	private Response alterarInstrutor(Instrutor instrutor) {
		// Response.Status status = null;
		Status status = Status.OK;

		String msg = "{'msg': 'Instrutor editado com sucesso'}";

		if (instrutor.nome == null || instrutor.telefone == null || instrutor.horarioDisponivel == null) {
			status = Status.BAD_REQUEST;
			msg = "{'msg': 'Campos Nome, Telefone e horário disponível são obrigatórios'}";
		} else {
			boolean success = dao.alteraInstrutor(instrutor);

			if (!success) {
				status = Status.NOT_MODIFIED;
				msg = "{'msg': 'Ocorreu algum erro na alteração'}";
			}
		}
		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();

	}

	// DELETAR instrutor pela URL passando o id
	@Path("/instrutor/{key}")
	@DELETE
	public Response deletaInstrutor(@PathParam("key") int id) {
		Status status = Status.OK;
		String msg = "{'msg': 'Instrutor deletado com sucesso!'}";

		if (id < 1) {
			status = Status.BAD_REQUEST;
			msg = "{'msg': 'O id do instrutor deve ser maior que zero'}";
		} else {
			boolean success = dao.deletaInstrutor(id);

			if (!success) {
				status = Status.NOT_MODIFIED;
				msg = "{'msg': 'Ocorreu algum erro ao deletar'}";
			}
		}

		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();
	}

	// TESTAR SE ENVIOU OU NÃO
	private Response insereInstrutor(Instrutor instrutor) {

		Status status = Status.OK;
		String msg = "{'msg': 'Instrutor criado com sucesso'}";

		if (instrutor.nome == null || instrutor.telefone == null || instrutor.horarioDisponivel == null) {
			status = Status.BAD_REQUEST;
			msg = "{'msg': 'Campos Nome, Telefone e horário disponível são obrigatórios'}";
		} else {
			boolean success = dao.insereInstrutor(instrutor);

			if (!success) {
				status = Status.NOT_MODIFIED;
				msg = "{'msg': 'Ocorreu algum erro na inserção'}";
			}
		}

		return Response.status(status).entity(msg).type(MediaType.APPLICATION_JSON).build();
	}

	// 12 - LISTAR OS PLANOS DA ACADEMIA
	@Path("/planos")
	@GET
	@Produces(MediaType.APPLICATION_JSON) // tipo de retorno---- manda de volta pro cliente
	public String listaPlanos() { // sempre retorna texto
		return new Gson().toJson(dao.listaPlanos());
	}

	// 13 - LISTAR AS PROMOÇÕES DE MENSALIDADE
	@Path("/planos/promocao")
	@GET
	@Produces(MediaType.APPLICATION_JSON) // tipo de retorno---- manda de volta pro cliente
	public String listaPromocao() { // sempre retorna texto
		return new Gson().toJson(dao.listaPromocao());
	}

	// 14 - TOTAL DE ALUNOS CADASTRADOS
	@GET
	@Path("/aluno/qtd")
	@Produces(MediaType.APPLICATION_JSON)
	public String listaQtd() {
		// return dao.listaQtdAluno();
		return new Gson().toJson(dao.listaQtdAluno());
	}
	// 15 -
}
