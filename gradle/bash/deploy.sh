#!/usr/bin/env bash

docker stop ieseuropaapi
docker rm -f ieseuropaapi
docker rmi ieseuropa

cd /root/ieseuropa/project
bash -x gradlew buildDocker --no-daemon --stacktrace -Dprod -Pprofile=prod -x test -Dkotlin.compiler.execution.strategy=in-process -Dkotlin.incremental=false

docker logs -f ieseuropaapi