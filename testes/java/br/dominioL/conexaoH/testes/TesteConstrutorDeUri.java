package br.dominioL.conexaoH.testes;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import br.dominioL.conexaoH.ConstrutorDeUri;

import org.junit.Test;

public class TesteConstrutorDeUri {
	@Test
	public void criarPadrao() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/"));
	}

	@Test
	public void criarComProtocolo() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.protocolo("https");
		assertThat(construtor.construirAbsoluto(), is("https://localhost:80/"));
	}

	@Test
	public void criarComEndereco() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.endereco("dominiol.com.br");
		assertThat(construtor.construirAbsoluto(), is("http://dominiol.com.br:80/"));
	}

	@Test
	public void criarComPorta() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.porta(7000);
		assertThat(construtor.construirAbsoluto(), is("http://localhost:7000/"));
	}

	@Test
	public void criarComCaminhoVazio() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/"));
	}

	@Test
	public void criarComCaminhoVazioComBarra() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("/");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/"));
	}

	@Test
	public void criarComCaminho() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("recurso");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/"));
	}

	@Test
	public void criarComBarraNoFim() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("recurso/");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/"));
	}

	@Test
	public void criarComCaminhoComBarra() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("/recurso");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/"));
	}

	@Test
	public void criarComCaminhoDuplo() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("recurso/qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}

	@Test
	public void criarComCaminhoDuploComBarra() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("/recurso/qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}

	@Test
	public void criarComCaminhoDuploComBarraDupla() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("/recurso//qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}

	@Test
	public void criarComCaminhoDuploComBarraTripla() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("/recurso///qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}

	@Test
	public void criarComCaminhoDuploComBarraQuadrupla() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("/recurso////qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}

	@Test
	public void criarComCaminhoAdicionadoMultiplasVezes() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("recurso");
		construtor.caminho("qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}

	@Test
	public void criarComCaminhoAdicionadoMultiplasVezesComBarra() {
		ConstrutorDeUri construtor = ConstrutorDeUri.criarCom();
		construtor.caminho("recurso");
		construtor.caminho("/qualquer");
		assertThat(construtor.construirAbsoluto(), is("http://localhost:80/recurso/qualquer/"));
	}
}
