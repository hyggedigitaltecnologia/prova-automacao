# 📘 Prova de Automação – API + Web (Java / Maven)

Este projeto contém uma prova de conceito de automação desenvolvida em **Java + Maven**, cobrindo:

* ✔ **Testes de API**

    * JSONPlaceholder (`/posts`)
    * RESTCountries (`/v3.1/all`)
* ✔ **Testes Web/UI** (Netshoes)

    * Buscar produto e adicionar ao carrinho
    * Remover produto e validar carrinho vazio

O objetivo é demonstrar boas práticas de automação, arquitetura limpa e integração simples com pipelines CI/CD.

---

# 🧱 Tecnologias Utilizadas

| Componente     | Tecnologias                                        |
| -------------- | -------------------------------------------------- |
| Linguagem      | **Java 21+**                                       |
| Build          | **Maven 3.8+**                                     |
| Testes API     | **JUnit 5 + Rest-Assured (via clientes próprios)** |
| Testes Web     | **Selenium WebDriver + WebDriverManager**          |
| Design Pattern | **Page Object Model (POM)**                        |
| Relatórios     | **Allure Reports** (opcional)                      |
| CI             | GitHub Actions (pipeline incluída)                 |

---

# 📂 Estrutura do Projeto

```text
prova-automacao/
 ├─ src/
 │  ├─ main/
 │  │  └─ java/br/com/jhonattan/automation/
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
 │  └─ test/
 │      └─ java/br/com/jhonattan/automation/
 │          ├─ api/
 │          │   ├─ JsonPlaceholderTests.java
 │          │   └─ RestCountriesTests.java
 │          ├─ suite/
 │          │   ├─ ApiTestSuite.java
 │          │   └─ WebTestSuite.java
 │          └─ web/
 │              ├─ core/
 │              │   └─ BasePage.java
 │              ├─ pages/
 │              │   ├─ HomePage.java
 │              │   ├─ SearchResultPage.java
 │              │   ├─ ProductPage.java
 │              │   └─ CartPage.java
 │              └─ tests/
 │                  ├─ BaseWebTest.java
 │                  └─ RegisterFlowTests.java
 │
 ├─ pom.xml
 └─ README.md
```

---

# 🧪 Testes Implementados

## 🔹 Testes de API

### 1️⃣ JSONPlaceholder – `/posts`

* GET: validar estrutura de resposta
* POST: criar novo recurso usando `PostPayload`
* Validação de status code
* Validação de tipos e campos da resposta

**Arquivo:**

```
src/test/java/.../api/JsonPlaceholderTests.java
```

---

### 2️⃣ RESTCountries – `/v3.1/all`

* GET: verificar lista de países
* Validar campos obrigatórios (`name`, `region`, `population`)
* Validar que retorno não está vazio

**Arquivo:**

```
src/test/java/.../api/RestCountriesTests.java
```

---

## 🔹 Testes Web (Netshoes)

### 1️⃣ Buscar produto e adicionar ao carrinho

Fluxo:

* Abrir `https://www.netshoes.com.br`
* Buscar `"Tênis"`
* Abrir o primeiro resultado
* Selecionar tamanho disponível
* Clicar em **Adicionar ao Carrinho**
* Abrir mini-carrinho
* Validar que há item no carrinho

### 2️⃣ Remover produto e validar carrinho vazio

Fluxo:

* Adicionar produto ao carrinho (pré-condição)
* Clicar no ícone de remover
* Validar a mensagem:

```
"Seu carrinho está vazio"
```

**Arquivo:**

```
src/test/java/.../web/tests/RegisterFlowTests.java
```

---

# ▶ Como Rodar o Projeto

## 🔧 Pré-requisitos

* JDK 17+ (Java 21 recomendado)
* Maven 3.8+
* Chrome instalado (WebDriverManager baixa o driver automaticamente)

---

## ▶ Rodar todos os testes (API + Web)

```bash
mvn clean test
```

---

## ▶ Rodar apenas testes de API

```bash
mvn -Dtest=ApiTestSuite test
```

---

## ▶ Rodar apenas testes Web

```bash
mvn -Dtest=WebTestSuite test
```

---

# 📊 Allure Report (opcional)

Após rodar testes:

```bash
allure serve allure-results
```

---

# ⚙ Pipeline CI – GitHub Actions

Para rodar os testes automaticamente no GitHub, use:

Arquivo: `.github/workflows/ci.yml`

```yaml
name: Automation Tests - API & Web
run-name: Prova Automacao - API & Web Tests

on:
  push:
    branches: [ main, master ]
  pull_request:

jobs:
  api-web-tests:
    runs-on: ubuntu-latest

    permissions:
      contents: read
      checks: write
      pull-requests: write

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'

      - name: Run Maven tests (API + Web)
        run: mvn -B -Dmaven.test.failure.ignore=true clean test

      - name: Publish test results to GitHub
        if: always()
        uses: dorny/test-reporter@v1
        with:
          name: "Prova Automacao - Java API & Web Tests"  # nome que aparece no Check
          path: "target/surefire-reports/*.xml"           # XMLs do Surefire
          reporter: "java-junit"                          # tipo de reporter
          fail-on-error: true                             # falha o job se houver testes falhando

      - name: Package test artifacts
        if: always()
        run: |
          mkdir -p artifact
          cp -r target/surefire-reports artifact/surefire-reports || true
          cp -r target/allure-results artifact/allure-results || true

      - name: Upload test artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: automation-test-results
          path: artifact

```

---

# 🧩 Decisões de Arquitetura

* **Page Object Model** para testes Web (mais limpo e sustentável)
* Separação clara entre:

    * `core/api` → infraestrutura de API
    * `core/web` → infraestrutura de WebDriver
    * `pages/` → elementos + ações
    * `tests/` → somente lógica de teste
* Suítes independentes (`ApiTestSuite` e `WebTestSuite`)
* Evitei testes de login por conta de recaptcha/hardening da Netshoes
* Testes Web seletivos focando no fluxo mais estável (carrinho)

---

# 👤 Autor

**Jhonattan Gomes**
QA | Automação | APIs | Web | Integração Contínua
LinkedIn: *[https://www.linkedin.com/in/jhonattan-vinicios-de-oliveira-gomes-6502aa233/]*
Email: *[hyggedigitaltecnologia@gmail.com]*

---