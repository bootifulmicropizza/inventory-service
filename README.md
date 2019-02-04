# inventory-service

JPA backed microservice to store inventory for the pizza store.

## Kubernetes secrets

The following secret is required for the Aurora database:

```
$ kubectl create secret generic inventory-service \
	--from-literal=db.url=[DB_URL] \
	--from-literal=db.port=[DB_PORT] \
	--from-literal=db.name=[DB_NAME] \
	--from-literal=db.username=[DB_USERNAME] \
	--from-literal=db.password=[DB_PASSWORD]
```


kubectl create secret generic inventory-service \
	--from-literal=db.url=192-168-128-100 \
	--from-literal=db.port=3306 \
	--from-literal=db.name=pizza_inventory \
	--from-literal=db.username=inventory-user \
	--from-literal=db.password=GpRDMNApCh3Zb6