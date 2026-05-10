# Part 1 - Basic Docker Compose Commands

1. docker compose version
   Show Docker Compose version.

2. docker compose up
   Create and start services in foreground.

3. docker compose up -d
   Create and start services in detached mode.

4. docker compose ps
   List running services/containers.

5. docker compose down
   Stop and remove containers, default network.

6. docker compose restart
   Restart all services.

7. docker compose logs -f
   Follow logs from all services.

8. docker compose build
   Build/rebuild service images.

9. docker compose exec <service_name> <command>
   Execute a command in a running container.

10. docker compose down -v
    Stop and remove containers, networks, and named/anonymous volumes.

11. docker compose run <service_name> <command>
    Run one-off command for a service.

12. docker compose stop <service_name>
    Stop one service.

13. docker compose rm <service_name>
    Remove stopped service container.

14. docker compose config
    Validate and view final compose configuration.

15. docker compose up -d --build
    Build images and start services in detached mode.
