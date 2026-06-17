# Learning Java — Migração de Stack em 20 dias

Repositório de aprendizado estruturado para desenvolvedor pleno migrando para Java/Spring Boot.

## Projeto Central: OrderHub

Sistema de gerenciamento de pedidos com microsserviços — cobre todos os tópicos da jornada.

```
learning-java/
├── java-fundamentals/        # Dias 1–4: Java puro (tipos, streams, OOP, concorrência)
└── orderhub/                 # Dias 5–20: microsserviços Spring Boot
    ├── api-gateway/          #   Spring Cloud Gateway + JWT
    ├── user-service/         #   Spring Boot + PostgreSQL + Spring Security
    ├── order-service/        #   Spring Boot + PostgreSQL + Flyway + Kafka producer
    ├── product-service/      #   Spring Boot + MongoDB
    ├── notification-service/ #   Kafka consumer
    ├── docker-compose.yml    #   Ambiente local completo
    └── k8s/                  #   Manifests Kubernetes + Argo CD
```

## Plano de Estudos

| Dias   | Tema                               | Módulo                                  |
|--------|------------------------------------|-----------------------------------------|
| 1–2    | Tipos, Coleções, Streams, Optional | `java-fundamentals`                     |
| 3–4    | OOP, Generics, Exceptions, Records | `java-fundamentals`                     |
| 5–6    | Spring Boot, IoC/DI, JPA/Hibernate | `user-service`                          |
| 7–8    | Spring Security + JWT + Gateway    | `user-service` + `api-gateway`          |
| 9–10   | REST, OpenAPI/Swagger, Validações  | todos os serviços                       |
| 11     | PostgreSQL + Flyway + MongoDB      | `order-service` + `product-service`     |
| 12–14  | Kafka: Producer + Consumer         | `order-service` + `notification-service`|
| 15–16  | Docker + Docker Compose            | raiz do projeto                         |
| 17     | Kubernetes: Deployments + Services | `k8s/`                                  |
| 18     | Prometheus + Grafana + Actuator    | todos os serviços                       |
| 19     | GitHub Actions CI/CD + Argo CD     | `.github/workflows/`                    |
| 20     | Review, refactor, documentação     | projeto completo                        |

## Como executar os exercícios (Dias 1–4)

```bash
cd java-fundamentals
mvn compile exec:java -Dexec.mainClass="br.com.orderhub.fundamentals.Dia01_TiposEColecoes"
mvn compile exec:java -Dexec.mainClass="br.com.orderhub.fundamentals.Dia02_StreamsEOptional"
```

Ou abra no IntelliJ IDEA e execute com o botão ▶ em cada `main()`.

## Stack

- Java 21 (LTS)
- Spring Boot 3.x
- Spring Cloud (Gateway, Eureka)
- PostgreSQL + MongoDB
- Apache Kafka
- Docker + Kubernetes + Argo CD
- GitHub Actions CI/CD
- Prometheus + Grafana
- OpenAPI 3.1 (springdoc)
