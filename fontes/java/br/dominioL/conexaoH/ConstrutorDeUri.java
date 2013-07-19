package br.dominioL.conexaoH;

import java.util.regex.Pattern;

public final class ConstrutorDeUri {
	private static final String PROTOCOLO_PADRAO = "http"; 
	private static final String ENDERECO_PADRAO = "localhost"; 
	private static final Integer PORTA_PADRAO = 80; 
	private static final String CAMINHO_PADRAO = ""; 
	private String protocolo;
	private String endereco;
	private Integer porta;
	private String caminho;

	private ConstrutorDeUri(String protocolo, String endereco, Integer porta, String caminho) {
		this.protocolo = protocolo;
		this.endereco = endereco;
		this.porta = porta;
		this.caminho = caminho;
	}

	public static ConstrutorDeUri criarCom() {
		return new ConstrutorDeUri(PROTOCOLO_PADRAO, ENDERECO_PADRAO, PORTA_PADRAO, CAMINHO_PADRAO);
	}

	public ConstrutorDeUri protocolo(String protocolo) {
		this.protocolo = protocolo;
		return this;
	}

	public ConstrutorDeUri endereco(String endereco) {
		this.endereco = endereco;
		return this;
	}

	public ConstrutorDeUri porta(Integer porta) {
		this.porta = porta;
		return this;
	}

	public ConstrutorDeUri caminho(String novoCaminho) {
		this.caminho = String.format("%s/%s", caminho, novoCaminho);
		return this;
	}

	public String construirRelativo() {
		return String.format("%s", caminho);
	}

	public String construirAbsoluto() {
		String caminhoFormatado = String.format("%s/", caminho);
		caminhoFormatado = caminhoFormatado.replaceAll("/{2,}", "/");
		return String.format("%s://%s:%s%s", protocolo, endereco, porta, caminhoFormatado);
	}
}
