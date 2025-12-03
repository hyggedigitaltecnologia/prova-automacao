# 📘 Prova de Automação – API + Web (Java / Maven)

Este projeto apresenta uma prova de conceito de automação usando **Java + Maven**, cobrindo:

* ✔ **Testes de API**

    * JSONPlaceholder (`/posts`)
    * RESTCountries (`/v3.1/all`)
* ✔ **Testes Web/UI** (Netshoes)

    * Buscar produto e adicionar ao carrinho
    * Remover produto e validar carrinho vazio

O foco é demonstrar **boas práticas de automação**, organização de projeto, page objects, testes confiáveis e integração total com **GitHub Actions**.

---

# 🧱 Tecnologias Utilizadas

| Área            | Tecnologias                                  |
| --------------- | -------------------------------------------- |
| Linguagem       | **Java 21+**                                 |
| Build           | **Maven 3.8+**                               |
| Testes API      | **JUnit 5 + Rest-Assured (via API clients)** |
| Testes Web      | **Selenium WebDriver + WebDriverManager**    |
| Arquitetura Web | **Page Object Model (POM)**                  |
| Relatórios      | **Allure Reports**                           |
| CI/CD           | **GitHub Actions**                           |

---

# 📂 Estrutura do Projeto

```
prova-automacao/
 ├─ src/
 │  ├─ main/java/br/com/jhonattan/automation/
 │  │      ├─ config/
 │  │      │   └─ TestConfig.java
 │  │      ├─ core/
 │  │      │   ├─ api/
 │  │      │   │   ├─ ApiClient.java
 │  │      │   │   ├─ ApiEndpoints.java
 │  │      │   │   ├─ JsonPlaceholderClient.java
 │  │      │   │   └─ RestCountriesClient.java
 │  │      │   └─ web/
 │  │      │       ├─ DriverFactory.java
 │  │      │       └─ WaitUtils.java
 │  │      └─ model/jsonplaceholder/
 │  │          └─ PostPayload.java
 │  │
 │  └─ test/java/br/com/jhonattan/automation/
 │          ├─ api/
 │          │   ├─ JsonPlaceholderTests.java
 │          │   └─ RestCountriesTests.java
 │          ├─ suite/
 │          │   ├─ ApiTestSuite.java
 │          │   └─ WebTestSuite.java
 │          └─ web/
 │              ├─ core/BasePage.java
 │              ├─ pages/
 │              │   ├─ HomePage.java
 │              │   ├─ SearchResultPage.java
 │              │   ├─ ProductPage.java
 │              │   └─ CartPage.java
 │              └─ tests/
 │                  ├─ BaseWebTest.java
 │                  └─ RegisterFlowTests.java
 ├─ pom.xml
 └─ README.md
```

---

# 🧪 Testes Implementados

## 🔹 Testes de API

### ✔ JSONPlaceholder – `/posts`

* GET listando posts
* POST criando um novo post
* Validações de status, schema e estrutura

📄 Arquivo: `api/JsonPlaceholderTests.java`

---

### ✔ RESTCountries – `/v3.1/all`

* GET retornando lista de países
* Validação de campos obrigatórios
* Garantia de resposta não vazia

📄 Arquivo: `api/RestCountriesTests.java`

---

## 🔹 Testes Web (Netshoes)

### ✔ Buscar produto e adicionar ao carrinho

Fluxo:

1. Abrir Netshoes
2. Pesquisar “Tênis”
3. Abrir primeiro item
4. Selecionar tamanho disponível
5. Adicionar ao carrinho
6. Validar item presente

---

### ✔ Remover produto e validar carrinho vazio

1. Adicionar item ao carrinho
2. Acessar o carrinho
3. Remover item
4. Validar mensagem:

```
Seu carrinho está vazio
```

📄 Arquivo: `web/tests/RegisterFlowTests.java`

---

# ▶ Como Rodar o Projeto

## 🔧 Pré-requisitos

* JDK 17+ (Java 21 recomendado)
* Maven 3.8+
* Google Chrome instalado

---

## ▶ Rodar todos os testes

```bash
mvn clean test
```

---

## ▶ Rodar somente API

```bash
mvn -Dtest=ApiTestSuite test
```

---

## ▶ Rodar somente Web

```bash
mvn -Dtest=WebTestSuite test
```

---

# 📊 Allure Report (opcional)

```bash
allure serve allure-results
```

---

# ⚙ Pipeline CI – Execução Automática (GitHub Actions)

Arquivo: `.github/workflows/ci.yml`

(…já incluído no projeto)

Esse pipeline:

* Builda o projeto
* Executa todos os testes
* Publica relatório nativo na aba **Checks**
* Faz upload dos artefatos (JUnit + Allure)

---

# 🚀 Execução Manual via GitHub Actions

Este projeto permite rodar **TESTES API ou WEB manualmente** pelo Actions usando `workflow_dispatch`.

Arquivo:
`.github/workflows/manual-run.yml`

### Como usar pelo GitHub:

1. Vá em **Actions**
2. Escolha o workflow: **Manual Run - Automation Tests**
3. Clique em **Run workflow**
4. Preencha:

    * `test_type` → `api` ou `web`
    * `suite` → nome da suíte (ex.: `ApiTestSuite` ou `WebTestSuite`)
5. Executar

### Comportamentos:

* Roda somente o tipo selecionado
* Gera relatório no check
* Publica artefatos
* Permite execução on-demand

---

# 🧩 Decisões de Arquitetura

* Page Object Model para desacoplamento e legibilidade
* API Clients isolados em `core/api`
* WebDriver encapsulado em `DriverFactory`
* Suítes independentes
* Execução confiável focada **somente nos fluxos estáveis da Netshoes**
* Login e Cadastro não foram automatizados devido a **recaptcha**, tornando o fluxo instável e não testável via automação sem violar políticas de segurança

---

# 👤 Autor

**Jhonattan Gomes**
QA | Automação | APIs | Web | CI/CD
🔗 LinkedIn: [https://www.linkedin.com/in/jhonattan-vinicios-de-oliveira-gomes-6502aa233/](https://www.linkedin.com/in/jhonattan-vinicios-de-oliveira-gomes-6502aa233/)
📧 Email: [hyggedigitaltecnologia@gmail.com](mailto:hyggedigitaltecnologia@gmail.com)