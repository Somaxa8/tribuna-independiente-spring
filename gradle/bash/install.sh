#!/usr/bin/env bash

docker network create ieseuropa
docker run --name ieseuropadb -d --network ieseuropa -p 3306:3306 -e MYSQL_ROOT_PASSWORD=A9kr5ZT5pewv7rj395TCTQXEfMcNQ3X5 -e MYSQL_DATABASE=ieseuropadb -v /root/ieseuropa/mysql:/var/lib/mysql --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
#docker run --name ieseuropadb -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=ieseuropadb --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
#docker run --name ieseuropamongo -d --network ieseuropa -p 27017:27017 -v /root/ieseuropa/mongo/:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=1234 --restart always mongo

#docker run --name ${project}mongoclient -d -p 3000:3000 --restart always --link ${project}mongo mongoclient/mongoclient