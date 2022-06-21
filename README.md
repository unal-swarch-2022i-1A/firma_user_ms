# firma_user_ms
Repository for the user data management

## Base de datos
El usuario y contraseña estan definidos en `src/main/resources/application.properties`. 
Para importar la base de datos
```bash
sudo -u postgres psql -f sql/firma_user_db.structure.sql
sudo -u postgres psql -d firma_user_db -f sql/firma_user_db.user.data.sql
```

## Lanzamiento
Leer https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-running-your-application.html

### Java Jar Vs. Maven run
> Short answer: `spring-boot:run` is a `java -jar` command on steroïd running as part of your Maven build, ensuring all required parameters are passed to your app (such as resources). `spring-boot:run `will also ensure that your project is compiled by executing test-compile lifecycle goals prior to running your app.

https://stackoverflow.com/a/47261923/11077105

## Compilación y lanzamiento con Docker para desarrollo
Compilación de la imagen del contenedor compilador del `.jar`. La imagen de `Dockerfile.dev` desde una imagen de *maven* precarga las dependencias del proyecto (archivo `pom.xml`). Al momento del lanzamiento compila el `jar` y lanza el servidor en modo `LiveReload`:
```bash
docker build -t firma_user_ms:dev . -f Dockerfile.dev
```
Lanzamiento del contenedor compilador del `.jar`
```bash    
docker run -it --rm \
    --name firma_user_ms_dev \
    -v "$(pwd)":/usr/src/app \
    -w /usr/src/app \
    -p 8090:8090 \
    --add-host=host.docker.internal:host-gateway \
    firma_user_ms:dev
```

## Lanzamiento contenedor de producción
La imagen de producción está basada en [`openjdk:8-jre-alpine`](https://hub.docker.com/layers/openjdk/library/openjdk/8-jdk-alpine/images/sha256-210ecd2595991799526a62a7099718b149e3bbefdb49764cc2a450048e0dd4c0?context=explore) cuyo peso comprimido es de 70.67 MB. Compilación de la imagen del contenedor:
``bash
docker build -t firma_user_ms:prod . -f Dockerfile
```
Lanzamiento del contenedor.
```bash    
docker run -it --rm \
    --name firma_user_ms \
    -p 8090:8090 \
    --add-host=host.docker.internal:host-gateway \
    --env-file=src/main/resources/application-prod.properties \
    firma_user_ms:prod
```

## Depuración para el contenedor de desarrollo
En el host verificamos que efectivamente este corriendo Postgres en el puerto `5432`. La IP de  `Local Address` debe estar en `0.0.0.0`, si está en `127.0.0.1` no recibirá conexiones remotas. 
```bash
sudo netstat -tulpn | grep 5432
```
Salida
```bash
tcp 0   0 127.0.0.1:5432    0.0.0.0:*   LISTEN  1401/postgres 
```

Nos conectamos al contenedor de desarrollo. Notar la bander `--name` en el comando `run`
```bash
docker exec -it firma_user_ms_dev /bin/bash
```
En el contenedor corremos `ping` y `telnet` (instalándolos con `apt update && apt install iputils-ping telnet postgresql-client -y`) para verificar conexión IP y luego TCP
Petición ICMP ECHO 
```bash
ping host.docker.internal -c 1
```
Salida
```bash
PING host.docker.internal (172.17.0.1) 56(84) bytes of data.
64 bytes from host.docker.internal (172.17.0.1): icmp_seq=1 ttl=64 time=0.090 ms
```
Conexión telnet al puerto Postgresql
```bash
telnet host.docker.internal 5432
```
Salida
```bash
Trying 172.17.0.1...
Connected to host.docker.internal.
Escape character is '^]'.
```
Probamos conexión con el cliente `psql`
```bash
psql -h host.docker.internal -U firma firma_user_db
```
```postgres
\dt
SELECT * FROM "user";
SELECT * FROM "user" AS u;
SELECT u FROM public."user" u WHERE u.user_id = 1;
SELECT * FROM public."user" u WHERE u.user_id = 1;
```