package com.liusxg.springbootcosumer.consumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.liusxg.springbootapi.IDubboProvider;
import com.liusxg.springbootapi.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo Consumer Controller (REST)
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see
 * @since 1.0.0
 */
@RestController
public class DemoConsumerController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private IDubboProvider dubboProvider;

    @RequestMapping("/sayHello")
    public Response sayHello() {
        return dubboProvider.sayHello();
    }

}
