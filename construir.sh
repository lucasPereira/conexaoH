#!/bin/bash

projeto=ConexaoH
pacoteDoProjeto=conexaoH

bibliotecas=bibliotecas
binarios=binarios
construcao=construcao
fontes=fontes
recursos=recursos
testes=testes

bibliotecasJava=${bibliotecas}/jar
binariosJava=${binarios}/class
fontesJava=${fontes}/java
testesJava=${testes}/java
contrucaoCompilacao=${construcao}/compilacao.txt
contrucaoTeste=${construcao}/teste.txt

arquivosFontesJava=$(find ${fontesJava} -name *.java)
arquivosTestesJava=$(find ${testesJava} -name *.java)
classesTestesJava=$(echo ${arquivosTestesJava} | sed -e s:${testesJava}::g -e s:^/::g -e "s:\s/: :g" -e s:/:.:g -e s:[.]java::g -e s:[a-Z.]*figuracao[a-Z.]*::g)

limpar() {
	echo ":limpar";
	rm -rf ${binarios};
	rm -rf ${construcao};
}

criarEstrutura() {
	echo ":criarEstrutura";
	mkdir -p ${bibliotecasJava};
	mkdir -p ${binariosJava};
	mkdir -p ${construcao};
	mkdir -p ${fontesJava};
	mkdir -p ${testesJava};
}

adicionarBibliotecas() {
	echo ":adicionarBibliotecas";
	cp -f ~/projetos/estruturados/construcao/estruturados.jar -t ${bibliotecasJava};
}

compilar() {
	limpar;
	criarEstrutura;
	adicionarBibliotecas;
	echo ":compilar";
	touch ${contrucaoCompilacao};
	javac -classpath ${bibliotecasJava}/*:${binariosJava} -sourcepath ${fontesJava} -d ${binariosJava} -Werror -deprecation -g ${arquivosFontesJava} -Xlint -Xmaxerrs 10 -Xmaxwarns 10 &> ${contrucaoCompilacao};
	javac -classpath ${bibliotecasJava}/*:${binariosJava} -sourcepath ${testesJava} -d ${binariosJava} -Werror -deprecation -g ${arquivosTestesJava} -Xlint -Xmaxerrs 10 -Xmaxwarns 10 &>> ${contrucaoCompilacao};
	less ${contrucaoCompilacao};
}

construir() {
	compilar;
	echo ":construir";
	jar -cf ${construcao}/${pacoteDoProjeto}.jar -C ${binariosJava} .;
}

testar() {
	construir;
	echo ":testar";
	touch ${contrucaoTeste};
	java -classpath ${bibliotecasJava}/*:${binariosJava} org.junit.runner.JUnitCore ${classesTestesJava} &> ${contrucaoTeste};
	less ${contrucaoTeste};
}

depurar() {
	construir;
	echo ":depurar";
	jdb -classpath ${bibliotecasJava}:${binariosJava} org.junit.runner.JUnitCore ${classesTestesJava};
}

executar() {
	construir;
	echo ":executar";
}

echo :${pacoteDoProjeto}
if [ -n "$1" ]
then
	$1;
else
	construir;
fi
