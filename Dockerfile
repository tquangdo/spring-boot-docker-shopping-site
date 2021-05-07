FROM maven:latest
RUN mkdir /obo
WORKDIR /obo
COPY . .
COPY run_spring_boot_entrypoint.sh /usr/local/bin/run_spring_boot_entrypoint.sh
EXPOSE 8080
# CMD [ "mvn", "spring-boot:run" ]
ENTRYPOINT ["/usr/local/bin/run_spring_boot_entrypoint.sh"]
