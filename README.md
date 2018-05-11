
Spring Boot整合dubbo和zookeeper
---------

这个demo是基于[dubbo-spring-boot-starter](https://github.com/apache/incubator-dubbo-spring-boot-project "悬停显示")写的，官方的包本身就能达到集成zookeeper的，但是官方并没有给出一个demo。

## 准备  
### zookeeper安装启动  
### 下载dubbo源码打包  
因为官方包使用的dubbo是源码的最新版本2.6.2-SNAPSHOT，这个版本并没有正式发布，所以需要你自己下载源码打包到本地。
```
git clone git@github.com:apache/incubator-dubbo.git
cd incubator-dubbo
mvn clean install -DskipTests -Drat.skip=true
```
### 下载[dubbo-spring-boot-starter](https://github.com/apache/incubator-dubbo-spring-boot-project "悬停显示")源码打包  
如果不想下载打包也行，直接用官方的包，不过官方只提供了0.1.0版本的包，只能兼容Spring Boot 1.5.x的版本  

```
官方版本
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>

手动打包
git clone https://github.com/apache/incubator-dubbo-spring-boot-project.git
cd incubator-dubbo-spring-boot-project
mvn clean install -DskipTests -Drat.skip=true
```


### 使用demo
```
git clone https://github.com/liusxg/springboot-dubbo-zookeeper-demo.git
```
打包api
```
cd springboot-dubbo-zookeeper-demo/spring-boot-api
mvn clean install
启动服务提供者
cd ../springboot-dubbo-zookeeper-demo/spring-boot-provider
mvn spring-boot:run
启动消费端
cd ../springboot-dubbo-zookeeper-demo/spring-boot-cosumer
mvn spring-boot:run
```

### spring-boot-provider实现和配置
```
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DubboProvider implements IDubboProvider {

    @Override
    public Response sayHello() {
        return new Response("connect success!!");
    }
```
application.properties配置
```
# Spring boot application
spring.application.name = dubbo-provider-demo
server.port = 9090


# DemoService service version
demo.service.version = 1.0.0

# Base packages to scan Dubbo Component: @com.alibaba.dubbo.config.annotation.Service
dubbo.scan.basePackages  = com.liusxg.springbootprovider.server


# Dubbo Config properties
## ApplicationConfig Bean
dubbo.application.id = dubbo-provider-demo
dubbo.application.name = dubbo-provider-demo

## ProtocolConfig Bean
dubbo.protocol.id = zookeeper
dubbo.protocol.name = dubbo
dubbo.protocol.port = 20081
dubbo.protocol.status = server

## RegistryConfig Bean
dubbo.registry.id = my-registry
dubbo.registry.address = 192.168.1.9:2181
dubbo.registry.protocol = zookeeper
dubbo.registry.timeout = 30000
dubbo.protocol.threads = 10
```

### spring-boot-cosumer实现和配置
```
    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private IDubboProvider dubboProvider;

    @RequestMapping("/sayHello")
    public Response sayHello() {
        return dubboProvider.sayHello();
    }
```
application.properties配置
```
# Spring boot application
spring.application.name = dubbo-consumer-demo
server.port = 8080
management.server.port = 8081

# DemoService service version
demo.service.version = 1.0.0

# Dubbo Config properties
## ApplicationConfig Bean
dubbo.application.id = dubbo-consumer-demo
dubbo.application.name = dubbo-consumer-demo

## ProtocolConfig Bean
dubbo.protocol.id = dubbo
dubbo.protocol.name = dubbo
dubbo.protocol.port = 20080

dubbo.registry.id = my-registry1
dubbo.registry.address = 192.168.1.9:2181
dubbo.registry.protocol = zookeeper
dubbo.registry.timeout = 30000
dubbo.protocol.threads = 10
```

示例 Controller : http://localhost:8080/sayHello
