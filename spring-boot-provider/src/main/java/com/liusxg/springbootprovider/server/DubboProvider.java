package com.liusxg.springbootprovider.server;

import com.alibaba.dubbo.config.annotation.Service;
import com.liusxg.springbootapi.IDubboProvider;
import com.liusxg.springbootapi.Response;

/**
 * Created by liusxg on 2018/5/10.
 */
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
}
