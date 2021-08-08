package socket_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        //�õ�һ������ͨ��
        SocketChannel socketChannel = SocketChannel.open();
        //���÷�����
        socketChannel.configureBlocking(false);
        //�ṩ�������˵�ip �� �˿�
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //���ӷ�����
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("��Ϊ������Ҫʱ�䣬�ͻ��˲�����������������������..");
            }
        }
        //...������ӳɹ����ͷ�������
        String str = "hello, �й��~";
        //Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //�������ݣ��� buffer ����д�� channel
        socketChannel.write(buffer);
        socketChannel.close();
        //System.in.read();
    }
}
