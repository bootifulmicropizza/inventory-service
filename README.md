# inventory-service

JPA backed microservice to store inventory for the pizza store.

## Kubernetes secrets

The following secrets are required:

```
$ kubectl create secret generic inventory-service \
	--from-literal=db.url=[DB_URL] \
	--from-literal=db.port=[DB_PORT] \
	--from-literal=db.name=[DB_NAME] \
	--from-literal=db.username=[DB_USERNAME] \
	--from-literal=db.password=[DB_PASSWORD]
```
