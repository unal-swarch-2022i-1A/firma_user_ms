# firma_user_ms
Repository for the user data management

## Compilación con Docker
```bash
docker build \
    -t mvnw .  \
    -f Dockerfile.dev
```

```bash    
docker run -it --rm \
    --name maven-project \
    -v "$(pwd)":/usr/src/app \
    -w /usr/src/app \
    mvnw
```

### Depuración
En host
```bash
sudo netstat -tulpn | grep 5432
```
En el contenedor
```bash
apt update
apt install iputils-ping telnet -y
ping host.docker.internal
telnet host.docker.internal 5432
```
## Lanzamiento contenedor de producción
``bash
docker build \
    -t firma_user_ms . \
    -f Dockerfile.prod
```

```bash    
docker run -it --rm \
    --name firma_user_ms \
    --add-host=host.docker.internal:host-gateway \
    --env-file=config/application.properties.docker \
    firma_user_ms
```

### Import SQL
```bash
sudo -u postgres psql -f sql/firma_user_db.export.sql
```