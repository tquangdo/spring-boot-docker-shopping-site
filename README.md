# OBO STADIUM WEBSITE
Trang thương mại điện tử bán giày

## deploy local

1. `docker compose up -d --build`
2. access browser `localhost:8005`
![demo](screenshot/demo.png)

![list](screenshot/list.png)

## db info
- `src/main/resources/application-dev.properties`
![db](screenshot/db.png)

## API info
- in `src/main/java/com/company/demo/controller/anonymous/*Controller.java` > `@GetMapping("/...`

## login acc

- Admin account:
    - Username: admin@obostadium.com
    - Password: 123456
- Member account:
    - Username: monguyen@gmail.com
    - Password: 123456
    
## admin page

- Truy cập `/admin` để vào trang admin.