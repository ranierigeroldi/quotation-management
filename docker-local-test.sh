sudo docker stop quotationmanagement
sudo docker rm quotationmanagement
sudo docker rmi ranierigeroldi/quotation-management
mvn -Pprod -DskipTests clean install
sudo docker build -t ranierigeroldi/quotation-management .
sudo docker container run --name quotationmanagement --network=inatel -p 8081:8081 -d ranierigeroldi/quotation-management