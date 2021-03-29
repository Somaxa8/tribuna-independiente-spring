#!/usr/bin/env bash

docker stop tribunaindependienteapi
docker rm -f tribunaindependienteapi
docker rmi tribunaindependiente

cd /home/centos/tribuna-independiente/project
bash -x gradlew buildDocker --no-daemon --stacktrace -Dprod -Pprofile=prod -x test -Dkotlin.compiler.execution.strategy=in-process -Dkotlin.incremental=false

docker logs -f tribunaindependienteapi