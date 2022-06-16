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

## Ejecución
``bash
docker build \
    -t firma_user_ms .  \
    -f Dockerfile.prod
```

```bash    
docker run -it --rm \
    --name firma_user_ms \
    --add-host=host.docker.internal:host-gateway \
    firma_user_ms
```