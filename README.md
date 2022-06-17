# firma_user_ms
Repository for the user data management

## Compilación con Docker
Compilación de la imagen del contenedor compilador del `.jar`
```bash
docker build \
    -t mvnw .  \
    -f Dockerfile.dev
```
Lanzamiento del contenedor compilador del `.jar`
```bash    
docker run -it --rm \
    --name maven-project \
    -v "$(pwd)":/usr/src/app \
    -w /usr/src/app \
    mvnw
```

### Problemas
Para el mensaje `/bin/sh: 1: ./mvnw: Permission denied` 
```bash 
ls -al mvnw
```
Verificar en la salida las banderas de ejecución`__x__x__x` : 
```bash
-rwxrwxr-x 1 user user 10284 jun 16 00:23 mvnw
``` 

## Base de datos
Para importar la base de datos
```bash
sudo -u postgres psql -f sql/firma_user_db.export.sql
```

## Lanzamiento contenedor de producción
Compilación de la imagen del contenedor
``bash
docker build -t firma_user_ms . -f Dockerfile.prod
```
Lanzamiento del contenedor.
```bash    
docker run -it --rm \
    --name firma_user_ms \
    --add-host=host.docker.internal:host-gateway \
    --env-file=config/application.properties.docker \
    firma_user_ms
```

### Depuración
En el host verificamos que efectivamente este corriendo Postgres en el puerto `5432`. La IP de  `Local Address` debe estar en `0.0.0.0`, si está en `127.0.0.1` no recibirá conexiones remotas. 
```bash
sudo netstat -tulpn | grep 5432
```
Salida
```bash
tcp 0   0 127.0.0.1:5432    0.0.0.0:*   LISTEN  1401/postgres 
```

En el contenedor corremos `ping` y `telnet` para verificar conexión IP y luego TCP
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