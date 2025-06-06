infra-up:
	docker-compose -f docker-compose.infra.yaml up -d

infra-down:
	docker-compose -f docker-compose.infra.yaml down

service-up:
	docker-compose -f docker-compose.yaml up -d --build

service-down:
	docker-compose -f docker-compose.yaml down

service-rerun:
	docker-compose -f docker-compose.yaml down
	docker-compose -f docker-compose.yaml up -d --build
