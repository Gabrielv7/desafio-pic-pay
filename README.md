
# Desafio PicPay simplificado

Essa API foi desenvolvida com base em um desafio do picpay disponivel no repositório: https://github.com/PicPay/picpay-desafio-backend.

## Tecnologias

- Java 17
- API REST
- Spring Framework 3
- Maven
- Banco de dados relacional PostegreSQL

## Funcionalidades
- Criar usuário
- Listar todos os usuários
- Buscar usuário por ID
- Criar Transação

## Endpoints
![image](https://github.com/Gabrielv7/desafio-pic-pay/assets/53438847/fa0553e8-bc19-41fc-8484-38978e47907d)

## Modelagem das classes de domínio
![image](https://github.com/Gabrielv7/desafio-pic-pay/assets/53438847/fb0defde-3c94-458d-8898-75d9a4e0b22c)

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
Pronto. Agora a documentação está acessivel:
```bash
http://localhost:8080/swagger-ui/index.html#/
```
Para parar o container com a aplicação execute o comando docker:
```bash
docker-compose down

