# spring-cloud-solutions
服务的启动顺序：

（1）注册中心。

（2）serviceB。因serviceA中通过FeignClient来使用ServiceB，在启动的时候需要用到ServiceB。

（3）serviceA。

（4）zuul网关。

##  solutions
1、详情见：[SpringCloud之个性化日志追踪](https://blog.csdn.net/yaowwwww7071/article/details/85769505)
1、详情见：[Spring Cloud之zipkin日志文件化](https://blog.csdn.net/yaowwwww7071/article/details/85999153)

