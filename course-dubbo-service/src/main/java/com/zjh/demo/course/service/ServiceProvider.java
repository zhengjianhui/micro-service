package com.zjh.demo.course.service;

import com.zjh.demo.thrift.user.UserService;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhengjianhui on 6/17/18
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;

    @Value("${thrift.user.port}")
    private int servicePort;

    public UserService.Client getUserService() {
        TSocket socket = new TSocket(serverIp, servicePort, 10000);
        TTransport tTransport = new TFramedTransport(socket);
        try {
            tTransport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TProtocol tProtocol = new TBinaryProtocol(tTransport);
        UserService.Client client = new UserService.Client(tProtocol);
        return client;
    }

}
