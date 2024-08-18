# 使用带有必要 Java 版本的基础 Maven 镜像
FROM maven:3.9.4-eclipse-temurin-17

# 设置容器中的工作目录
WORKDIR /app

# 将项目文件复制到容器中
COPY . /app

# 设置环境变量，用于数据库和 Redis 配置
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/sale
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=123456
ENV SPRING_REDIS_HOST=redis
ENV SPRING_REDIS_PORT=6379

# 暴露 Spring Boot 应用程序运行的端口
EXPOSE 8808

# 使用 Maven 构建并运行应用程序
CMD ["mvn", "clean", "package", "spring-boot:run"]
