# 🎮 DTI Case Técnico - Sistema de Gerenciamento de Jogos

Este projeto é uma solução para o desafio técnico da dti digital, consistindo em um sistema CRUD completo para cadastro, consulta, atualização e remoção de jogos, com persistência em banco SQLite, arquitetura MVC, testes unitários e pronto para execução via Docker.

---

## 📑 Sumário
- [🕹️ Recurso Principal: Jogo](#recurso-principal-jogo)
- [🛠️ Arquitetura e Tecnologias](#arquitetura-e-tecnologias)
- [⬇️ Instalação e Dependências](#instalação-e-dependências)
- [🐳 Como Executar com Docker](#como-executar-com-docker)
- [⚙️ Execução Manual (Javamaven)](#execução-manual-javamaven)
- [✨ Funcionalidades e Exemplos de Uso](#funcionalidades-e-exemplos-de-uso)
- [📝 Exemplo de Arquivo de Log](#exemplo-de-arquivo-de-log)
- [🗄️ Queries SQL Utilizadas](#queries-sql-utilizadas)
- [🚀 Possíveis Melhorias Futuras](#possíveis-melhorias-futuras)
- [📞 Contato](#contato)

---

## Recurso Principal: Jogo

A aplicação gerencia o recurso **Jogo**, que possui as seguintes propriedades:

| Campo           | Obrigatório | Tipo de Dado | Descrição                                 |
|-----------------|-------------|--------------|--------------------------------------------|
| id              | Sim         | Inteiro      | Identificador único (gerado automaticamente)|
| titulo          | Sim         | String       | Título do jogo                             |
| genero          | Sim         | String       | Gênero do jogo                             |
| dataLancamento  | Sim         | Data         | Data de lançamento (formato dd/MM/yyyy)    |
| notaPessoal     | Não         | Double       | Nota pessoal atribuída ao jogo (0 a 10)    |

- **Obrigatórios:** `id`, `titulo`, `genero`, `dataLancamento`
- **Opcionais:** `notaPessoal`

Validações são aplicadas para garantir que os campos obrigatórios sejam preenchidos e que a nota pessoal, se informada, esteja entre 0 e 10.

---

## Linguagem Escolhida
- **Java 21**

---

## Arquitetura e Tecnologias
- ☕ **Java 21**
- 📦 **Maven** (gerenciamento de dependências)
- 🧪 **JUnit 5** (testes unitários)
- 🕵️‍♂️ **Mockito** (mocks para testes)
- 🗄️ **SQLite** (banco de dados embarcado)
- 📝 **SLF4J** (logs)
- 🐳 **Docker** (containerização)

### 📁 Estrutura do Projeto
```
src/
  main/
    java/
      org/app/
        config/      # Configuração do banco SQLite
        controller/  # Orquestração (JogoController)
        dao/         # Acesso a dados (JogoDAO)
        model/       # Entidade Jogo
        service/     # Regras de negócio (JogoService)
        view/        # Interface de console (JogoView)
        Main.java    # Classe principal
  test/
    java/org/app/service/JogoServiceTest.java
Dockerfile
pom.xml
README.md
```

### Diagrama de Pacotes

![Diagrama de Pacotes](diagramas/DiagramaDePacotes.PNG)

---

## Instalação e Dependências

### 1. Dependências
- ☕ **Java 21**: [Download](https://www.oracle.com/br/java/technologies/downloads/#java21)
- 📦 **Maven**: [Download](https://maven.apache.org/download.cgi)
- 🐳 **Docker** (opcional, para execução via container): [Download](https://www.docker.com/get-started/)

As dependências do projeto são gerenciadas automaticamente pelo Maven (veja `pom.xml`).

### 2. Instalação
- Clone o repositório:
  ```sh
  git clone <url-do-repositorio>
  cd DTI_CaseTecnico
  ```
- Para rodar manualmente, siga para a seção de execução manual.
- Para rodar via Docker, siga para a próxima seção.

---

## Como Executar com Docker

> **Pré-requisito:** Docker instalado

Execute o comando abaixo para rodar a aplicação em modo interativo no terminal:

```sh
docker run -it --rm ad3ln0r/dti-casetecnico-leandroalencar:1.0
```

O banco de dados será criado automaticamente no primeiro uso.

---

## Execução Manual com Maven

> **Pré-requisitos:** Java 21 e Maven instalados

### 1. Compilando o projeto
Execute o comando abaixo para baixar as dependências e compilar o projeto:
```sh
mvn clean package
```
Isso irá gerar os arquivos JAR na pasta `target/`.

### 2. Executando via Maven (modo recomendado para desenvolvimento)
Você pode rodar a aplicação diretamente pelo Maven, que cuida do classpath automaticamente:
```sh
mvn exec:java -Dexec.mainClass="org.app.Main"
```
Ou rode diretamente pelo seu IDE (IntelliJ, Eclipse, VSCode, etc).

### 3. Executando o JAR gerado

- **JAR principal:**
  ```sh
  java -jar target/CaseTecnico_LeandroAlencar_DTI-1.0-SNAPSHOT.jar
  ```
  > **Atenção:** Esse JAR não inclui as dependências. Só funciona se você rodar via Maven ou IDE.

- **JAR com dependências (recomendado para produção/entrega):**
  ```sh
  java -jar target/CaseTecnico_LeandroAlencar_DTI-1.0-SNAPSHOT-jar-with-dependencies.jar
  ```
  > **Esse JAR inclui todas as bibliotecas necessárias** (inclusive o driver do SQLite). Use esse comando para rodar em qualquer máquina, sem precisar de Maven ou IDE.

### 4. Erros comuns
- Se aparecer o erro `No suitable driver found for jdbc:sqlite:jogos.db`, significa que você está rodando o JAR principal sem dependências. Use o JAR `-jar-with-dependencies.jar`.
- Se não conseguir deletar a pasta `target`, feche todos os programas que possam estar usando arquivos dela e tente novamente.

---

## Funcionalidades e Exemplos de Uso

A aplicação é totalmente interativa via console. Veja como utilizar cada funcionalidade:

### 🏠 Menu Principal
```
=== MENU DE JOGOS ===
1 - Cadastrar Jogo
2 - Listar Jogos
3 - Buscar Jogo por ID
4 - Atualizar Jogo
5 - Deletar Jogo
0 - Sair
```

### ➕ 1. Cadastrar Jogo
- Preencha os campos obrigatórios (título, gênero, data de lançamento). Nota pessoal é opcional.
- Exemplo:
```
--- Cadastrar Jogo ---
Título: The Legend of Zelda
Gênero: Aventura
Data de lançamento (dd/MM/yyyy): 21/02/1986
Nota pessoal (0-10, opcional): 9.5
✅ Jogo cadastrado com sucesso!
```

### 📋 2. Listar Jogos
- Exibe todos os jogos cadastrados:
```
--- Lista de Jogos ---
ID: 1 | Título: The Legend of Zelda | Gênero: Aventura | Data de Lançamento: 21/02/1986 | Nota Pessoal: 9.5
```

### 🔍 3. Buscar Jogo por ID
- Informe o ID do jogo para visualizar seus detalhes:
```
ID do jogo: 1
ID: 1 | Título: The Legend of Zelda | Gênero: Aventura | Data de Lançamento: 21/02/1986 | Nota Pessoal: 9.5
```

### ✏️ 4. Atualizar Jogo
- Informe o ID do jogo e os novos dados:
```
--- Atualizar Jogo ---
ID do jogo: 1
Novo título: The Legend of Zelda: Remastered
Novo gênero: Aventura
Nova data de lançamento (dd/MM/yyyy): 21/02/1986
Nova nota pessoal (0-10, opcional): 10
✅ Jogo atualizado com sucesso!
```

### 🗑️ 5. Deletar Jogo
- Informe o ID do jogo para removê-lo:
```
ID do jogo: 1
🗑️ Jogo deletado com sucesso!
```

### ⚠️ 6. Validações e Mensagens de Erro
- Campos obrigatórios não preenchidos:
```
Título: 
❌ Campo obrigatório, operação cancelada!
```
- Busca por ID inexistente:
```
ID do jogo: 99
❌ Nenhum jogo encontrado com ID 99
```

Esses exemplos ilustram o fluxo principal do sistema, incluindo validações e mensagens amigáveis para o usuário. O sistema é totalmente interativo e orienta o usuário em cada etapa.

---

## Exemplo de Arquivo de Log

Ao final da execução, será gerado um arquivo `logs.txt` com o seguinte formato:

```
==== LOGS DA EXECUÇÃO ====
[LOG] Tabela 'jogo' criada ou já existe.
[LOG] Recebida solicitação para cadastrar jogo: sekiro
[LOG] Cadastrando jogo: sekiro
[LOG] Jogo inserido: sekiro
[LOG] Jogo cadastrado com sucesso: sekiro
[LOG] Jogo cadastrado via controller: sekiro
```

---

## Queries SQL Utilizadas

Abaixo estão as principais queries SQL utilizadas pelo sistema para manipulação do banco de dados SQLite:

```sql
-- Criação da tabela
CREATE TABLE IF NOT EXISTS jogo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo TEXT NOT NULL,
    genero TEXT NOT NULL,
    data_lancamento TEXT NOT NULL,
    nota_pessoal REAL
);

-- Inserção de novo jogo
INSERT INTO jogo(titulo, genero, data_lancamento, nota_pessoal) VALUES (?, ?, ?, ?);

-- Listar todos os jogos
SELECT * FROM jogo;

-- Buscar jogo por ID
SELECT * FROM jogo WHERE id = ?;

-- Atualizar jogo existente
UPDATE jogo SET titulo=?, genero=?, data_lancamento=?, nota_pessoal=? WHERE id=?;

-- Deletar jogo
DELETE FROM jogo WHERE id=?;
```

---

## Executando o JAR com Dependências (Standalone)

> **Recomendado para rodar fora do Maven/IDE**

Após compilar o projeto com `mvn clean package`, utilize o JAR gerado com todas as dependências:

```sh
java -jar target/CaseTecnico_LeandroAlencar_DTI-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Esse comando garante que todos os drivers e bibliotecas necessários estarão disponíveis, evitando erros como `No suitable driver found for jdbc:sqlite:jogos.db`.

---

## Possíveis Melhorias Futuras
- 🖥️ Interface gráfica (GUI) ou API REST
- 🔒 Autenticação de usuário
- 📑 Paginação e filtros na listagem de jogos
- 🧪 Mais testes automatizados (controller, DAO)

---

## Contato
Dúvidas ou sugestões:
- **Leandro Alencar**
- 📧 E-mail: Leandro130333.dev@gmail.com
- 📱 WhatsApp/Telefone: (31) 98347-9067