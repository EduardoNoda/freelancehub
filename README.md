# FreelanceHub (nome provisório)
## Sobre o Projeto

FreelanceHub é uma aplicação web voltada para freelancers que precisam de controle real sobre seus projetos, prazos e ganhos.

Diferente de ferramentas genéricas como Notion ou planilhas, o sistema é totalmente focado no fluxo de trabalho de freelancers, eliminando complexidade desnecessária e reduzindo o esforço de organização.

O objetivo não é apenas organizar - mas também gerar insights sobre perdas, atrasos e performance financeira, incentivando decisões mais inteligentes ao longo do tempo.

---

## Problema que Resolve

Freelancers frequentemente enfrentam:

 - Perda de prazos importantes
 - Falta de visibilidade sobre ganhos reais
 - Dificuldade em organizar contratos e projetos
 - Falta de análise sobre projetos cancelados ou atrasados

O FreelanceHub centraliza tudo isso em um único sistema.

---

## Funcionalidades
MVP Atual
 - Cadastro e autenticação de usuários (JWT + Spring Security)
 - Criação e listagem de projetos
 - Paginação de projetos (10 por página)
 - Atualização de status de projetos com controle de transições
 - Soft delete de projetos
 - Dashboard com:
   - Total ganho
   - Projetos ativos, concluídos, cancelados
 - API documentada com Swagger

---

## Em Desenvolvimento / Roadmap
 - Notificações por e-mail para prazos próximos (48h)
 - Upload e gerenciamento de contratos em PDF
 - Dashboard financeiro avançado (lucro vs custo)
 - Recuperação de projetos deletados (soft delete com retenção por plano)
 - Transformação em SaaS com planos

---

## Arquitetura & Decisões Técnicas

A arquitetura do projeto evoluiu de forma orgânica, baseada em boas práticas, e hoje se aproxima de um modelo inspirado em Arquitetura Hexagonal (Ports & Adapters).

Estrutura em camadas
 - Domain
   - Regras de negócio
   - Entidades, enums, value objects
 - Application
   - Orquestração de use cases
 - Infrastructure
   - Implementações técnicas (DB, segurança, etc.)
 - Controller
   - Exposição da API REST

✔ O domínio é isolado de infraestrutura, garantindo maior testabilidade e clareza.

---

## Decisões
###  Controle de Estado com Enum (State Machine)

O ciclo de vida dos projetos é controlado diretamente no domínio:

    public boolean thisValidateTransaction(ProjectStatus newStatus)

Essa abordagem funciona como uma máquina de estados, garantindo que apenas transições válidas sejam permitidas:

 - IN_NEGOTIATION → IN_PROGRESS / CANCELED
 - IN_PROGRESS → COMPLETED / CANCELED
 - Estados finais não podem ser alterados

✔ Isso evita inconsistências e mantém regras críticas centralizadas no domínio.

---

## Soft Delete com Auditoria

Projetos não são removidos fisicamente do banco:

 - Uso de deleted_at para controle 
 - Permite:
   - Auditoria
   - Recuperação futura
 - Implementação de planos SaaS com retenção de dados

---

## JDBC ao invés de JPA

Escolha intencional para:

 - Controle total sobre queries
 - Melhor entendimento de SQL
 - Otimização de performance
 - Evitar abstrações desnecessárias

Trade-off:

 - Mais verboso
 - Menor velocidade de desenvolvimento

---

## Paginação Manual

Paginação implementada diretamente via SQL:

 - Evita sobrecarga no banco
 - Escala melhor com múltiplos usuários
 - Reduz consumo de memória

---

## Segurança
 - Autenticação com JWT
 - Proteção de rotas com Spring Security
 - Uso de filtros customizados
 - Credenciais e configs via variáveis de ambiente

---

## Tecnologias
### Backend
 - Java 17
 - Spring Boot
 - PostgreSQL
 - JDBC
 - Flyway (versionamento de banco)
### Infra
 - Render (deploy backend)
 - Neon (PostgreSQL cloud)
### Testes
 - JUnit
 - Mockito
### API
 - Swagger / OpenAPI
### Frontend (em desenvolvimento)
 - React
 - TypeScript

---

## API

A API está disponível via Swagger:

https://freelancehub-ldsl.onrender.com/swagger-ui/index.html

---

## Como rodar o projeto
### Pré-requisitos
 - Java 17+
 - PostgreSQL
### Passos
    git clone <repo>
    cd freelancehub
    ./mvnw spring-boot:run

Configurar variáveis de ambiente para conexão com banco.

## Testes
 - Testes unitários focados em:
   - Regras de domínio
   - Use cases
 - Uso de mocks para controle de fluxo

---

## Visão de Produto

Este projeto foi desenvolvido como projeto de portfólio com visão real de produto SaaS.

A ideia é evoluir para uma plataforma completa para freelancers, com:

 - Controle financeiro avançado
 - Automação de prazos e contratos

---

## 🤝 Contato

Desenvolvido por Eduardo Noda
📫 ([Email](mailto:noda.faleiros.eduardo@gmail.com) / [LinkedIn](https://www.linkedin.com/in/eduardo-noda))