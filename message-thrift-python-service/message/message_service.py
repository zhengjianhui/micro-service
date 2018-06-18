# coding: utf-8

from message.api import MessageService
# 引入 thrift 依赖包
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = "815168733@qq.com"
authCode = "agpzyseloebkbdag"


class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        # 没钱假装发了短信
        print(
            "send Mobile Message" + ", " + "mobile:[" + mobile + "]" + "-" + "message:[" + message + "]")
        return True

    def sendEmailMessage(self, email, message):
        print(
            "send Email Message" + ", " + "email:[" + email + "]" + "-" + "message:[" + message + "]")

        try:
            messageObj = MIMEText(message, "plain", "utf-8")
            messageObj["From"] = sender
            messageObj["To"] = email
            messageObj["Subject"] = Header("测试邮件", "utf-8")
            smtpObj = smtplib.SMTP("smtp.qq.com")
            smtpObj.login(sender, authCode)
            smtpObj.send(sender, [email], messageObj.as_string())
        except smtplib.SMTPException as ex:
            print("send error")
            print(ex)
            return False

        return True


if __name__ == "__main__":
    handler = MessageServiceHandler()
    # 将实现类, 传入 thrift 的接口中
    processor = MessageService.Processor(handler)

    # 监听本地端口
    transport = TSocket.TServerSocket("127.0.0.1", "9090")
    # 传输方式, 按帧传输
    tfactory = TTransport.TFramedTransportFactory()
    # 传输协议 二进制传输
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    # 创建 service
    # 第一个参数 消息来了谁负责处理, 具体服务
    # 第二个参数 监听端口
    # 第三个参数 传输方式
    # 第四个参数 传输协议
    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    print("python thrift server start")
    server.serve()
    print("python thrift server close")
