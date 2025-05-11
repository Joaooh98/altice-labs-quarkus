#!/bin/bash
set -e

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN}1. Compilando aplicação no modo JVM${NC}"
./mvnw clean package

echo -e "${GREEN}2. Construindo imagem Docker${NC}"
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/altice-labs-quarkus-jvm .

echo -e "${GREEN}3. Executando contêiner${NC}"
docker run -i --rm --name labseq-jvm -p 8081:8081 quarkus/altice-labs-quarkus-jvm &