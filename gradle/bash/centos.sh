#!/usr/bin/env bash

#CONFIGURO EL SERVICIO DE SSH PARA CONEXIÓN REMOTA Y SEGURA CON EL VPS
#mkdir ~/.ssh
#public_key=AAAAB3NzaC1yc2EAAAADAQABAAABAQCb6lWbfKtGzp4Qml1WiwLpTRx6JYbWjmJZh1mgeQO5HqehihWBzhLg+gv8Bq/DJf3hr51/wj2bTqvmyt/7DSNklafo+7aDglFt0KO3jQurwmb/Q72q8Gt2mFatlyM2oJi/eW0iT9e4b3yuzQxPjq5AOieBFPEuOFZZ5sw6ftKt1s27aMCB40tp+3s1l8kMqSdQTvCGJOmu9kesHf32O3hiOMYWMsbDsnru6wqVFpylOzX7Jer1rzx6LM4gVRNnFEkqyIDLhzB4H6JW5zHVQFYY1F7EgZyhdTtWt0+xpDD4ra7q6zQmhXSuC5TmrAj5uvxf2R97FvoqoP0rmU79f2/b
#echo ssh-rsa $public_key javiergimenezmoya@Mac-mini-de-JAVIER.local > ~/.ssh/authorized_keys

#INSTALO JAVA PARA PODER COMPILAR LAS FUENTES DEL REPOSITORIO (PASO PREVIO A LA CREACIÓN DEL DOCKER)
yum -y update
yum -y install epel-release nmap git java-1.8.0-openjdk java-1.8.0-openjdk-devel

#CREAMOS JERAQUIA DE CARPETAS
mkdir /root/ieseuropa
mkdir /root/ieseuropa/git
mkdir /root/ieseuropa/mysql
mkdir /root/ieseuropa/storage


#INICIALIZO EL REPOSITORIO REMOTO
cd /root/ieseuropa/git || exit
git init --bare

#INYECTO CODIGO DE AUTODESPLIEGUE PARA INTEGRACIÓN CONTINUA EN EL DISPARADOR POST-RECEIVE
#(QUE SE EJECUTA AL LLEGAR UN PUSH AL REPOSITORIO)
cd /root/ieseuropa/git/hooks
echo "
#!/usr/bin/env bash
rm -rf /root/ieseuropa/project
git clone -b master /root/ieseuropa/git /root/ieseuropa/project
bash -x /root/ieseuropa/project/gradle/bash/deploy.sh
" > post-receive

chmod a+x post-receive

#DESCARGO DE LA PAGINA OFICIAL EL CONTROLADOR DE DOCKERS
#CENTOS 8
dnf update -y
dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo
dnf install docker-ce --nobest
systemctl start docker
systemctl enable docker

#dnf install curl
sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
#PREPARO LAS BASES DE DATOS DEL PROYECTO Y LA NETWORK DE DOCKER
docker network create ieseuropa
docker run --name ieseuropadb -d --network ieseuropa -p 3306:3306 -e MYSQL_ROOT_PASSWORD=A9kr5ZT5pewv7rj395TCTQXEfMcNQ3X5 -e MYSQL_DATABASE=ieseuropadb -v /root/ieseuropa/mysql:/var/lib/mysql --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
