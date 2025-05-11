markdown# Labseq API

A high-performance API for calculating the labseq mathematical sequence.

## Sequence Definition

The labseq sequence is defined as follows:
n=0 => l(0) = 0
n=1 => l(1) = 1
n=2 => l(2) = 0
n=3 => l(3) = 1
n>3 => l(n) = l(n-4) + l(n-3)

## Architecture

This application implements a clean architecture approach:

- **Domain Layer**: Core business logic and entities
  - `SequenceValue`: Represents a calculated sequence value
  - `SequenceCalculator`: Implements the sequence algorithm

- **Service Layer**: Orchestration and caching
  - `SequenceService`: Manages calculation and performance monitoring

- **Presentation Layer**: API endpoints
  - `SequenceResource`: REST controller for sequence calculation

- **Infrastructure Layer**: Configuration and utilities
  - `OpenApiConfig`: API documentation setup
  - `ResponseUtils`: JSON serialization customization

## Technologies

- **Quarkus**: Supersonic Subatomic Java framework
- **Java 21**: Latest language features
- **RESTEasy**: JAX-RS implementation
- **Jackson**: JSON processing
- **Caffeine**: High-performance caching
- **SmallRye OpenAPI**: API documentation
- **GraalVM**: Native compilation
- **JUnit 5 & RestAssured**: Testing

## Running the Application

### Development Mode

```shell
./mvnw quarkus:dev
Development UI: http://localhost:8081/q/dev/
JVM Mode
shell./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
Native Mode
shell# Build native executable
./mvnw package -Dnative

# Run the native executable
./target/altice-labs-quarkus-1.0.0-SNAPSHOT-runner
Docker
shell# JVM mode
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/altice-labs-quarkus-jvm .
docker run -i --rm -p 8081:8081 quarkus/altice-labs-quarkus-jvm

# Native mode
./mvnw package -Dnative
docker build -f src/main/docker/Dockerfile.native -t quarkus/altice-labs-quarkus .
docker run -i --rm -p 8081:8081 quarkus/altice-labs-quarkus
API Documentation
OpenAPI/Swagger UI: http://localhost:8081/q/swagger-ui/
Endpoints

GET /labseq/{n} - Calculate sequence value at index n

Example response:
```{
  "index": 10,
  "value": "3",
  "calculationTimeMs": 5
}```

Technical Details
Performance Optimizations

Algorithm Efficiency: Uses sliding window technique instead of recursion
Caching: Implements Caffeine cache to store calculated values
BigInteger Support: Handles arbitrarily large sequence values
Native Compilation: GraalVM compilation for reduced startup time and memory usage

Configuration
The application uses the following configurations:

HTTP Port: 8081
Cache Maximum Size: 1000 entries
Cache Expiration: 1 hour

Testing
Run tests with:
shell./mvnw test
License
Apache License 2.0