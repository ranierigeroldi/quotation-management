docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi ranierigeroldi/quotation-management
mvn -DskipTests clean install
docker compose up -d
