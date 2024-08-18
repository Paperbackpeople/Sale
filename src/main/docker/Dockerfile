# 使用包含 Maven 的基础镜像
FROM maven:3.9.4-eclipse-temurin-17

# 设置工作目录
WORKDIR /app

# 复制项目文件到容器中
COPY . /app

# 设置环境变量
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysqk-db:3306/sale
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=123456
ENV SPRING_REDIS_HOST=redis
ENV SPRING_REDIS_PORT=6379

# 暴露应用端口
EXPOSE 8808

# 运行 Maven 命令
CMD ["mvn", "spring-boot:run"]
