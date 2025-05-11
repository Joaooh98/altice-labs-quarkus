# Labseq API

Uma API de alto desempenho para cálculo da sequência matemática labseq.

## Definição da Sequência

A sequência labseq é definida como:
- n=0 => l(0) = 0
- n=1 => l(1) = 1
- n=2 => l(2) = 0
- n=3 => l(3) = 1
- n>3 => l(n) = l(n-4) + l(n-3)

## Arquitetura

Esta aplicação implementa uma abordagem de arquitetura limpa:

- **Camada de Domínio**: Lógica de negócio central e entidades
  - `SequenceValue`: Representa um valor calculado da sequência
  - `SequenceCalculator`: Implementa o algoritmo da sequência

- **Camada de Serviço**: Orquestração e cache
  - `SequenceService`: Gerencia o cálculo e o monitoramento de desempenho

- **Camada de Apresentação**: Endpoints da API
  - `SequenceResource`: Controlador REST para cálculo da sequência

- **Camada de Infraestrutura**: Configuração e utilitários
  - `OpenApiConfig`: Configuração da documentação da API
  - `ResponseUtils`: Personalização da serialização JSON

## Tecnologias

- **Quarkus**: Framework Java Supersônico Subatômico
- **Java 21**: Recursos mais recentes da linguagem
- **RESTEasy**: Implementação JAX-RS
- **Jackson**: Processamento JSON
- **Caffeine**: Cache de alto desempenho
- **SmallRye OpenAPI**: Documentação da API
- **GraalVM**: Compilação nativa
- **JUnit 5 & RestAssured**: Testes

## Requisitos do Projeto

1. **Java 21** (definido no pom.xml)
2. **Maven** (incluído via Maven Wrapper)
3. **Docker** (opcional, para execução em contêineres)

## Executando a Aplicação

### Modo Desenvolvimento

```shell
./mvnw quarkus:dev
```
Interface de desenvolvimento: http://localhost:8081/q/dev/

### Modo JVM
```shell
./mvnw package

java -jar target/quarkus-app/quarkus-run.jar
```

### Modo Nativo
```shell
# Compilação do executável nativo
./mvnw package -Dnative

# Executar o arquivo nativo
./target/altice-labs-quarkus-1.0.0-SNAPSHOT-runner
```

### Docker

#### Modo JVM
```shell
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/altice-labs-quarkus-jvm .
docker run -i --rm -p 8081:8081 quarkus/altice-labs-quarkus-jvm
```

#### Modo Nativo
```shell
./mvnw package -Dnative
docker build -f src/main/docker/Dockerfile.native -t quarkus/altice-labs-quarkus .
docker run -i --rm -p 8081:8081 quarkus/altice-labs-quarkus
```

### Utilizando os Scripts

1. **Modo JVM** (mais rápido para compilar, adequado para desenvolvimento):
   ```bash
   chmod +x build-and-run-jvm.sh  # Tornar o script executável
   ./build-and-run-jvm.sh
   ```

2. **Modo Nativo** (otimizado para produção, compilação mais demorada):
   ```bash
   chmod +x build-and-run-native.sh  # Tornar o script executável,
   ./build-and-run-native.sh
   ```

## Documentação da API

- **OpenAPI/Swagger UI**: http://localhost:8081/q/swagger-ui/

## Endpoints

- **GET /labseq/{n}** - Calcula o valor da sequência no índice n

Exemplo de resposta:
```json
{
  "index": 10,
  "value": 3,
  "calculationTimeMs": 5
}
```

## Detalhes Técnicos

### Otimizações de Performance

- **Eficiência do Algoritmo**: Usa técnica de janela deslizante em vez de recursão
- **Cache**: Implementa cache Caffeine para armazenar valores calculados
- **Suporte a BigInteger**: Manipula valores de sequência arbitrariamente grandes
- **Compilação Nativa**: Compilação GraalVM para redução do tempo de inicialização e uso de memória

### Configuração

A aplicação usa as seguintes configurações:

- **Porta HTTP**: 8081
- **Tamanho Máximo do Cache**: 1000 entradas
- **Expiração do Cache**: 1 hora

## Testes

Execute os testes com:
```shell
./mvnw test
```

## Licença

Apache License 2.0