# kotlin-example
Exemplo de api rest em kotlin utilizando springboot e mongodb

### Criando e subindo os containers do mongo e mongo-express
No diretório raiz do projeto execute o comando abaixo:
```
docker-compose up -d
```
O container mongo se refere à instância do MongoDB a ser criada para acesso na porta 27017.

Já o container mongo-express corresponde ao container que permitirá a execução da 
aplicação Web para administração da instância do MongoDB (imagem mongo-express) a partir da porta 8081.
