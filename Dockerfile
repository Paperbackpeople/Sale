# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk

# 设置工作目录
WORKDIR /app

# 暴露端口
EXPOSE 8808

# 添加用于 Maven 的缓存
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

# 复制项目的全部源码
COPY . .

# 使用 Maven 打包并运行
CMD ["./mvnw", "spring-boot:run"]