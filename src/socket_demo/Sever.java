package socket_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Sever {
    public static void main(String[] args) throws Exception {
        //�ٴ���ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //��һ���˿�6666, �ڷ������˼���
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //����Ϊ������
        serverSocketChannel.configureBlocking(false);
        //�ڵõ�һ��Selector����
        Selector selector = Selector.open();
        //�۰� serverSocketChannel ע�ᵽ  selector ���� �¼�Ϊ OP_ACCEPT�����¼�
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("ע����selectionkey ����=" + selector.keys().size()); // 1
        //ѭ���ȴ��ͻ�������
        while (true) {
            //�������ǵȴ�1�룬���û���¼�����, ����
            if (selector.select(1000) == 0) { //û���¼�����
                System.out.println("�������ȴ���1�룬������");
                continue;
            }
            //������ص�>0, �ͻ�ȡ����ص� selectionKey����
            //1.������ص�>0�� ��ʾ�Ѿ���ȡ����ע���¼�
            //2. selector.selectedKeys() ���ع�ע�¼��ļ���
            // �ܢ�ͨ�� selectionKeys �����ȡͨ��
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys ���� = " + selectionKeys.size());
            //���� Set<SelectionKey>, ʹ�õ���������
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                //��ȡ��SelectionKey
                SelectionKey key = keyIterator.next();
                //����key ��Ӧ��ͨ���������¼�����Ӧ����
                if (key.isAcceptable()) { //����� OP_ACCEPT, ���µĿͻ�������
                    //�� �ÿͻ�������һ�� SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("�ͻ������ӳɹ� ������һ�� socketChannel " + socketChannel.hashCode());
                    //�� SocketChannel ����Ϊ������
                    socketChannel.configureBlocking(false);
                    //��socketChannel ע�ᵽselector, ��ע�¼�Ϊ OP_READ�� ͬʱ��socketChannel
                    //����һ��Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("�ͻ������Ӻ� ��ע���selectionkey ����=" + selector.keys().size()); //2,3,4..
                }
                if (key.isReadable()) {  //���� OP_READ
                    //ͨ��key �����ȡ����Ӧchannel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //��ȡ����channel������buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    int size = channel.read(buffer);
                    if(size > 0)
                    	System.out.println("form �ͻ��� " + new String(buffer.array()));
                    else if(size < 0) {
                    	key.cancel();
                    	channel.close();
                    }else {}
                    //while(buffer.hasRemaining())buffer.get();
                }
                //�ֶ��Ӽ������ƶ���ǰ��selectionKey, ��ֹ�ظ�����
                keyIterator.remove();
            }
        }
    }
}