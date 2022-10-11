# Users API

# Descrição

Este projeto é uma API RESTful para o recurso de usuários, com as operações de cadastro, atualização, busca de um único recurso, busca de vários recursos com a possibilidade de filtro por nome. 

## Índice

- [Users API](#users-api)
- [Descrição](#descrição)
  - [Índice](#índice)
  - [Começando](#começando)
    - [Tecnologias](#tecnologias)
  - [Banco de Dados     | PostgreSQL](#banco-de-dados------postgresql)
    - [Arquitetura](#arquitetura)
      - [Arquitetura Hexagonal (Portas e Adaptadores)](#arquitetura-hexagonal-portas-e-adaptadores)
    - [Instalação](#instalação)
    - [Padronização de Commits](#padronização-de-commits)
  - [Executando a aplicação](#executando-a-aplicação)
  - [Testes Unitários e de Integração](#testes-unitários-e-de-integração)
  - [Operações da API](#operações-da-api)
  - [Swagger - Documentação e Testes](#swagger---documentação-e-testes)

---

## Começando

Essas instruções fornecerão a você uma cópia do projeto em execução em sua máquina local para fins de desenvolvimento e teste.

### Tecnologias

Componente         | Tecnologia
---               | ---
API REST    | [Spring Boot]
REST Documentação | [Swagger UI / OpenAPI]
BD em memória     | H2 
Persistencia      | JPA (Utilizando Spring Data)
Serviço Build     | Maven(Java)
Conteinerização     | Docker Compose
Banco de Dados     | PostgreSQL
---

Links de apoio:

- [Git](https://git-scm.com/)
- [Java](https://www.java.com/pt-BR/download/) 
- [JDK](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) 
- [Spring Boot](https://start.spring.io) 
- [Docker](https://www.docker.com/) 
- [Docker Compose](https://docs.docker.com/compose/) 
- [PostgreSQL](https://www.postgresql.org)

### Arquitetura
- DDD - Modelar o domínio realizando Domínio de negócio (Domínio Ubíquo) da 
arquitetura corporativa
    - [Domain Driven Development](https://martinfowler.com/tags/domain%20driven%20design.html) 

- TDD - Desenvolvimento orientado a testes, garante a confiabilidade do código, 
testando unitariamente, consegue-se encontrar problemas rapidamente.
- RestFul API - Padrão de mercado (verbos, status code e recursos).
- Ferramentas e processos de DevOps - Conteinerização.

#### Arquitetura Hexagonal (Portas e Adaptadores)

- [Hexagonal Architecture (Ports & Adapters)](https://br.sensedia.com/post/use-of-the-hexagonal-architecture-pattern) 

- Agnóstico em relação ao mundo externo: A aplicação pode ser dirigida por qualquer número de controles diferentes. Você pode utilizar o núcleo interno partindo de requisições REST ou mensagens por exemplo. Basta escrever novos adaptadores de entrada.
- Independente de serviços externos: Pelo fato de ser agnóstica em relação ao exterior, a lógica do seu negócio pode ser 
desenvolvida e testada antes mesmo de você se preocupar com questões como a tecnologia da base de dados ou dos componentes de comunicação.

- Substituição tecnológica facilitada: O papel das portas e adaptadores é converter mensagens vindas do domínio da aplicação para o mundo externo e vice-versa. Esse processo de conversão habilita a substituição das tecnologias de comunicação sem afetar o negócio, pois respeita sempre as mesmas interfaces.

- Alta manutenibilidade: Como as modificações são muito isoladas sem afetar muito as áreas circundantes, as chances de introdução de bugs reduz bastante e o código fonte que representa o núcleo do negócio tende a permanecer mais estável.


### Instalação

1. Clonando o repositório

   ```bash
   git clone https://github.com/otaviocesar/users-api.git
   ```

2. Entre no diretório do projeto

   ```bash
   cd users-api/
   ```

3. Checkout para branch de trabalho

   ```bash
   git checkout <branch>
   ```

4. Instalando dependências

   ```bash
   ./mvnw install
   ```

5. Rename ```.env-example``` file to  ```.env```  (Substitua os valores das variáveis ​​pelos seus próprios)

---

### Padronização de Commits

Certifique-se de ter configurado seus Git Hooks locais:

```sh
git config core.hooksPath .githooks
```

Isso garantirá que suas mensagens de commit sigam o [Conventional Commits Specification](https://www.conventionalcommits.org/en/v1.0.0/).


---

## Executando a aplicação

```bash
$ ./mvnw spring-boot:run
```

---

## Testes Unitários e de Integração

```bash
# Testes Unitários
$ ./mvnw test

# Testes de Integração
$ ./mvnw test -Pintegration-tests

```

## Operações da API

Endpoint        | Método | Ação      
---               | ---    | ---
http://localhost:8080/users |  GET | Retorna a lista de usuários  
http://localhost:8080/users |  POST | Insere um novo usuário  
http://localhost:8080/users/{id} |  GET | Retorna o usuário com id = {id} 
http://localhost:8080/users/{id} |  PUT | Substitui os dados do usuário com id = {id} 
http://localhost:8080/users/{id} |  PATCH | Altera itens dos dados do usuário com id = {id}
http://localhost:8080/users/{id} |  DELETE | Remove o usuário com id = {id} 

## Swagger - Documentação e Testes


* O Swagger é uma ferramenta que permite criar documentação para APIs. Essa ferramenta permite que ao mesmo tempo que é criada a API também seja gerado a sua documentação e ainda possibilita para todos os utilizadores o entendimento claro do comportamento oferecido pelo serviço por que possui um fácil acesso, estruturas claras, é interativo e que permite fazer simulações. Após rodar a aplicação ela fica acessível pela seguinte url:

```
http://localhost:8080/swagger-ui.html

```
