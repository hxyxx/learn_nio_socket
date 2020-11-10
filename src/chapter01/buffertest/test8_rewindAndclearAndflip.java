package chapter01.buffertest;

import java.nio.ByteBuffer;

public class test8_rewindAndclearAndflip {
    //rewind用于重新写入或读取缓冲区，侧重点是重新
    //clear一清除缓冲区，侧重点是还原一切状态
    //flip存储完进行读取,侧重点是substring的读取
    public static void main(String[] args) {
        byte[] byteArray = new byte[]{1,2,3,4,5};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.position(1);
        byteBuffer.limit(3);
        byteBuffer.mark();
        System.out.println("capacity:"+byteBuffer.capacity()+
                " limit:"+byteBuffer.limit()+
                 " position"+byteBuffer.position());
        byteBuffer.rewind();
        System.out.println("capacity:"+byteBuffer.capacity()+
                " limit:"+byteBuffer.limit()+
                " position"+byteBuffer.position());
        byteBuffer.reset();
    }
}
