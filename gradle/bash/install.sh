#!/usr/bin/env bash

docker network create tribunaindependiente
docker run --name tribunaindependientedb -d --network tribunaindependiente -p 3306:3306 -e MYSQL_ROOT_PASSWORD=A9kr5ZT5pewv7rj395TCTQXEfMcNQ3X5 -e MYSQL_DATABASE=tribunaindependientedb -v /root/tribunaindependiente/mysql:/var/lib/mysql --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
#docker run --name tribunaindependientedb -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=tribunaindependientedb --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
#docker run --name tribunaindependientemongo -d --network tribunaindependiente -p 27017:27017 -v /root/tribunaindependiente/mongo/:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=1234 --restart always mongo

#docker run --name ${project}mongoclient -d -p 3000:3000 --restart always --link ${project}mongo mongoclient/mongoclient