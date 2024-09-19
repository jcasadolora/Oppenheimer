# Use the official OpenJDK image as the base image: https://hub.docker.com/_/ibm-semeru-runtimes/tags
FROM ibm-semeru-runtimes:open-21.0.3_9-jre

# Create the directory for the application
RUN mkdir -p /app

# Set the working directory
WORKDIR /app

# Copy the jar file to the container
COPY ../../build/libs/Oppenheimer-1.0.0.jar /app/oppenheimer-svc.jar

# Expose the port the application will run on
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=local

HEALTHCHECK --interval=5s --retries=3 CMD nc -vz -w 3 localhost 8080 || exit 1

# Command to run the application
ENTRYPOINT ["java", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5092","-Dserver.port=8080","-jar", "oppenheimer-svc.jar"]