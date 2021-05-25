# OBO STADIUM WEBSITE

- shopping site for shoes
- ![badge4](https://img.shields.io/badge/docker-3.3.1-blue)
- [![Report an issue](https://img.shields.io/badge/Support-Issues-green)](https://github.com/tquangdo/spring-boot-docker-shopping-site/issues/new)

## deploy local
1. `docker compose up -d --build`
2. access browser `localhost:8005`
![demo](screenshot/demo.png)

![list](screenshot/list.png)

## db info
- `src/main/resources/application-dev.properties`
![db](screenshot/db.png)

## API info
- in `src/main/java/com/company/demo/controller/anonymous/*Controller.java` > `@GetMapping("/...")`

## login acc
- Admin account:
    - Username: admin@obostadium.com
    - Password: 123456
- Member account:
    - Username: monguyen@gmail.com
    - Password: 123456
    
## admin page
- access browser `localhost:8005/admin`

## note
- due to `Dockerfile` > `ENTRYPOINT ["/usr/local/bin/run_spring_boot_entrypoint.sh"]`, after edit docker info need `--build` & need wait until run spring boot finish
