package chapter01.buffertest;

import java.nio.ByteBuffer;

public class test1_useBuffer {
    public static void main(String[] args){
        byte[] byteArray = new byte[]{1,2,3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.getClass().getName());
    }
}
