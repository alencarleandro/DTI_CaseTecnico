# DTI Case Técnico - Sistema de Gerenciamento de Jogos

Este projeto foi desenvolvido como solução para o caso técnico do processo seletivo de estágio da dti digital. O objetivo é criar um sistema CRUD para cadastro, consulta, atualização e remoção de jogos, com persistência em banco de dados SQLite e testes unitários.

---

## Requisitos Atendidos

- Cadastro de jogos com título, gênero, data de lançamento e nota pessoal (opcional).
- Listagem de todos os jogos cadastrados.
- Busca de jogo por ID.
- Atualização de dados de um jogo existente.
- Remoção de jogo por ID.
- Validações de campos obrigatórios e regras de negócio.
- Persistência dos dados em banco SQLite.
- Testes unitários para a camada de serviço (`JogoService`), utilizando JUnit 5 e Mockito.

---

## Tecnologias Utilizadas

- **Java 21**
- **Maven** (gerenciamento de dependências)
- **JUnit 5** (testes unitários)
- **Mockito** (mocks para testes)
- **SQLite** (banco de dados embarcado)
- **SLF4J** (logs)

---

## Estrutura do Projeto

```
src/
  main/
    java/
      org/
        app/
          config/      # Configuração do banco SQLite
          controller/  # Lógica de orquestração (JogoController)
          dao/         # Acesso a dados (JogoDAO)
          model/       # Entidade Jogo
          service/     # Regras de negócio (JogoService)
          view/        # Interface de console (JogoView)
          Main.java    # Classe principal
  test/
    java/
      org/
        app/
          service/
            JogoServiceTest.java
pom.xml
README.md
```

---

## Como Executar

1. **Pré-requisitos:**  
   - Java 21 instalado  
   - Maven instalado

2. **Clone o repositório e acesse a pasta do projeto:**
   ```sh
   git clone <url-do-repositorio>
   cd DTI_CaseTecnico
   ```

3. **Compile o projeto:**
   ```sh
   mvn clean package
   ```

4. **Execute a aplicação:**
   ```sh
   mvn exec:java -Dexec.mainClass="org.app.Main"
   ```
   Ou rode diretamente pelo seu IDE.

---

## Como Executar os Testes

Para rodar os testes unitários (necessário para Java 21):

```sh
mvn test -Dnet.bytebuddy.experimental=true
```

---

## Observações e Decisões de Implementação

- O projeto segue o padrão MVC (Model-View-Controller).
- A camada de serviço (`JogoService`) foi testada com JUnit 5 e Mockito, incluindo cenários de sucesso e falha.
- Foram feitos ajustes para garantir compatibilidade do Mockito/Byte Buddy com Java 21.
- O sistema é totalmente interativo via console, permitindo CRUD completo dos jogos.
- O banco de dados é criado automaticamente na primeira execução.

---

## Possíveis Melhorias Futuras

- Implementar interface gráfica (GUI) ou API REST.
- Adicionar autenticação de usuário.
- Paginação e filtros na listagem de jogos.
- Mais testes automatizados (controller, DAO).

---

## Contato

Dúvidas ou sugestões:  
Leandro Alencar