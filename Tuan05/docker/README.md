# Docker Compose Part 2 Lab

This workspace contains ready-to-run solutions for:

- Part 1: Basic Docker Compose commands
- Part 2: 15 Docker Compose exercises
- Part 3: 10 practical Docker Compose projects

## Quick Start

1. Open a terminal in this workspace.
2. Go to any exercise folder.
3. Start services:

   docker compose up -d

4. Check status:

   docker compose ps

5. Follow logs:

   docker compose logs -f

6. Stop services:

   docker compose down

7. Clean services and volumes:

   docker compose down -v

## Structure

- part1/commands.md
- part2/
- part3/

Each exercise folder has its own docker-compose.yml and required code/config files.

## Main Endpoints

- Part 2 - bai01: Nginx at http://localhost:8080
- Part 2 - bai03: phpMyAdmin at http://localhost:8081
- Part 2 - bai04: Node app at http://localhost:3000
- Part 2 - bai06: WordPress at http://localhost:8080
- Part 2 - bai07: Mongo Express at http://localhost:8081
- Part 2 - bai09: Flask at http://localhost:5000
- Part 2 - bai11: Adminer at http://localhost:8083
- Part 2 - bai12: Prometheus at http://localhost:9090, Grafana at http://localhost:3000
- Part 2 - bai13: React via Nginx at http://localhost:8080

- Part 3 - bt1: WordPress at http://localhost
- Part 3 - bt2: Node + Mongo API at http://localhost:3000
- Part 3 - bt3: Nginx load balancer at http://localhost:8080
- Part 3 - bt4: Prometheus at http://localhost:9090, Grafana at http://localhost:3000
- Part 3 - bt5: Vote at http://localhost:5000, Result at http://localhost:5001
- Part 3 - bt6 prod: Nginx at http://localhost:8080
- Part 3 - bt7: Elasticsearch at http://localhost:9200, Kibana at http://localhost:5601
- Part 3 - bt8: Django at http://localhost:8000
- Part 3 - bt9: Nextcloud at http://localhost:8080
- Part 3 - bt10: Traefik dashboard at http://localhost:8088, routes at /flask and /wp

## Special Notes

- Part 3 - bt6 uses two compose files:
  - Dev mode: docker compose -f docker-compose-dev.yml up -d
  - Prod mode: docker compose -f docker-compose-prod.yml up -d --build

- Validate compose before run:

  docker compose config

- Useful cleanup command during testing:

  docker compose down -v
