package com.zjh.demo.user.thrift;

import com.zjh.demo.thrift.user.UserService;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zhengjianhui on 6/17/18
 */
@Configuration
public class ThriftService {

    @Value("${service.port}")
    private Integer servicePort;

    @Autowired
    private UserService.Iface userIface;

    @PostConstruct
    public void startThriftServer() {
        // 初始化接口
        TProcessor tProcessor = new UserService.Processor<>(userIface);

        // 监听端口
        TNonblockingServerSocket socket = null;
        try {
            socket = new TNonblockingServerSocket(servicePort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        // 初始化监听和配置
        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
        // 监听后转给具体 service 处理
        args.processor(tProcessor);
        // 传输方式
        args.transportFactory(new TFramedTransport.Factory());
        // 传输协议, 二进制
        args.protocolFactory(new TBinaryProtocol.Factory());

        // 开始监听
        TServer tServer = new TNonblockingServer(args);
        tServer.serve();

    }
}
