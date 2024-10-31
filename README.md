
# Desafio PicPay simplificado

Essa API foi desenvolvida com base em um desafio do picpay disponivel no repositório: https://github.com/PicPay/picpay-desafio-backend.

## Tecnologias

- Java 17
- Maven
- REST
- Spring Framework 3
- Spring Data JPA
- Banco de dados PostegreSQL | H2
- FlyWay Migration
- Junit | Mockito | Jacoco
- Swagger
- Docker
- CI/CD com deploy no EC2
- AWS EC2
- AWS RDS 

## Funcionalidades
- Criar usuário
- Listar todos os usuários
- Buscar usuário por ID
- Criar Transação

## Endpoints
### Endpoints de Usuário

#### Criar um novo usuário
- **URL:** `/users`
- **Método:** `POST`
- **Corpo da Requisição:**
  ```json
  {
    "name": "string",
    "document": "string",
    "email": "string",
    "password": "string",
    "userType": "COMMON | STORE",
    "balance": "number"
  }
  ```
- **Respostas:**
  - `201 Created`: Usuário criado com sucesso.
  - `400 Bad Request`: Dados do usuário inválidos ou usuário já existe.

#### Obter todos os usuários
- **URL:** `/users`
- **Método:** `GET`
- **Respostas:**
  - `200 OK`: Lista de usuários.

#### Obter um usuário por ID
- **URL:** `/users/{id}`
- **Método:** `GET`
- **Respostas:**
  - `200 OK`: Usuário encontrado.
  - `404 Not Found`: Usuário não encontrado.

### Endpoints de Transação

#### Criar uma nova transação
- **URL:** `/transactions`
- **Método:** `POST`
- **Corpo da Requisição:**
  ```json
  {
    "amount": "number",
    "payerId": "integer",
    "payeeId": "integer"
  }
  ```
- **Respostas:**
  - `201 Created`: Transação criada com sucesso.
  - `400 Bad Request`: Dados da transação inválidos ou pagador sem saldo suficiente.
  - `404 Not Found`: Pagador ou recebedor não encontrado.

### Respostas de Erro

- **400 Bad Request:** A requisição não pôde ser entendida ou estava faltando parâmetros obrigatórios.
- **404 Not Found:** Recurso não encontrado.

### Exemplos de Requisições

#### Criar um novo usuário
```bash
curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{
  "name": "John Doe",
  "document": "123456789",
  "email": "john.doe@example.com",
  "password": "password123",
  "userType": "COMMON",
  "balance": 100.00
}'
```

#### Obter todos os usuários
```bash
curl -X GET "http://localhost:8080/users"
```

#### Criar uma nova transação
```bash
curl -X POST "http://localhost:8080/transactions" -H "Content-Type: application/json" -d '{
  "amount": 10.00,
  "payerId": 1,
  "payeeId": 2
}'
```

## Modelagem das classes de domínio
![challangepicpay](https://github.com/user-attachments/assets/60a5f79f-f094-4516-b29a-d107b539a572)

# Docker
Para subir a aplicação local via docker, execute os seguintes comandos maven e docker na pasta raiz do projeto.

Gereo JAR da aplicação:
```bash
mvn package
```
Após gerar o JAR, execute o comando para startar a aplicação via container:
```bash
docker-compose up -d
```
Pronto. Agora o Swagger está acessivel:
```bash
http://localhost:8080/swagger-ui/index.html#/
```
Para parar o container com a aplicação execute o comando docker:
```bash
docker-compose down

