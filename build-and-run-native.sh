set -e

GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${GREEN}1. Compilando aplicação nativa${NC}"
./mvnw clean package -Dnative -Dquarkus.native.container-build=true

echo -e "${GREEN}2. Construindo imagem Docker${NC}"
docker build -f src/main/docker/Dockerfile.native -t quarkus/altice-labs-quarkus .

echo -e "${GREEN}3. Executando contêiner${NC}"
docker run -i --rm --name labseq-native -p 8081:8081 quarkus/altice-labs-quarkus &