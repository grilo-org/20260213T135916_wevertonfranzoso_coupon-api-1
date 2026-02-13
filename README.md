# Coupon API

API para gerenciamento de cupons de desconto, construída com *Java 17, **Spring Boot* e *H2* (banco em memória).

Este projeto é um desafio técnico que implementa regras de negócio para criação e deleção de cupons.

---

## 📝 Regras de Negócio

### Create
- Um cupom pode ser cadastrado a qualquer momento.
- Campos obrigatórios:
    - code (6 caracteres, alfanumérico)
    - description
    - discountValue (mínimo 0.5)
    - expirationDate (não pode ser no passado)
- O código do cupom aceita caracteres especiais na entrada, mas são removidos antes de salvar e retornar na resposta.
- O cupom pode ser criado já publicado (published = true).

### Delete
- Soft delete: o cupom é marcado como deletado, mas não é removido do banco.
- Não é possível deletar um cupom já deletado.

---

## ⚙️ Tecnologias

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (em memória)
- Spring Validation
- Springdoc OpenAPI (Swagger)
- JUnit 5

---

## 🚀 Rodando o projeto

### Pré-requisitos

- Java 17
- Maven
- (Opcional) Docker

### Banco H2

- http://localhost:8080/h2-console
- JDBC URL jdbc:h2:mem:testdb
- USER NAME sa
- PASSWORD deixe vazio

### Swagger

- http://localhost:8080/swagger-ui/index.html#/

### Com Maven

```bash
# Build do projeto
mvn clean install

# Rodar a aplicação
mvn spring-boot:run

