#!/bin/bash

projeto=ConexaoH
pacoteDoProjeto=conexaoH
pacoteGeral=br.dominioL

jar=jar
java=java
construcao=construcao

class=${construcao}/class
contrucaoCompilacao=${construcao}/compilacao.txt

limpar() {
	echo ":limpar"
	rm -rf ${construcao}
}

criarEstrutura() {
	limpar
	echo ":criarEstrutura"
	mkdir -p ${jar}
	mkdir -p ${java}
	mkdir -p ${construcao}

	mkdir -p ${class}
}

atualizarBibliotecas() {
	criarEstrutura
	echo ":atualizarBibliotecas"
	cp -f ~/projetos/estruturados/construcao/estruturados.jar ${jar}
}

compilar() {
	atualizarBibliotecas
	echo ":compilar"
	touch ${contrucaoCompilacao}
	arquivosJava=$(find ${java} -name *.java)
	javac -classpath ${jar}/*:${class} -sourcepath ${java} -d ${class} -Werror -deprecation -g ${arquivosJava} -Xlint -Xmaxerrs 10 -Xmaxwarns 10 &> ${contrucaoCompilacao}
	less ${contrucaoCompilacao}
}

construir() {
	compilar
	echo ":construir"
	jar cf ${construcao}/${pacoteDoProjeto}.jar -C ${class} .
}

testar() {
	construir
	echo ":testar"
	arquivosTestesJava=$(find ${java} -name *Teste*.java)
	classesTestesJava=$(echo ${arquivosTestesJava} | sed -e s:${java}/::g -e s:^/::g -e "s:\s/: :g" -e s:/:.:g -e s:\.java::g -e s:[a-Z.]*figuracao[a-Z.]*::g)
	java -classpath ${jar}/*:${class} org.junit.runner.JUnitCore ${classesTestesJava}
}

executar() {
	construir
	echo ":executar"
}

echo :${pacoteDoProjeto}
if [ -n "$1" ]
then
	$1
else
	construir
fi
