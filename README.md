
# Desafio PicPay simplificado

Essa API foi desenvolvida com base em um desafio do picpay disponivel no repositório: https://github.com/PicPay/picpay-desafio-backend.

## Tecnologias

- Java 17
- REST
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

Para subir a aplicação via docker, execute o seguinte comando maven na pasta raiz do projeto para gerar o JAR:

```bash
mvn package
```
Após gerar o JAR, execute o comando para startar a aplicação:
```bash
docker-compose up -d
```
Pronto. Agora a documentação está acessivel para teste:
```bash
http://localhost:8080/swagger-ui/index.html#/
```
Para parar a aplicação execute o comando docker:
```bash
docker-compose down

