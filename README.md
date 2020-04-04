# kotlin-api

### Criando e subindo os containers do mongo e mongo-express
No diretório raiz do projeto execute o comando abaixo:
```
docker-compose up -d
```
O container mongo se refere à instância do MongoDB na porta 27017.

Já o mongo-express corresponde ao container que permitirá a execução da 
aplicação Web para administração da instância do MongoDB (imagem mongo-express) a partir da porta 8081.

### Swagger URL
http://localhost:8082/api/swagger-ui.html
