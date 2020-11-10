package chapter01.buffertest;

import java.nio.CharBuffer;

public class test2_limit {
    //limit测试
    public static void main(String[] args) {
        char[] charArray = new char[] {'a','b','c','d','e'};
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("charBuffer Capacity:"+charBuffer.capacity()+"   limit:"+charBuffer.limit());
        charBuffer.limit(3);
        System.out.println("charBuffer Capacity:"+charBuffer.capacity()+"   limit:"+charBuffer.limit());
        charBuffer.put(0,'o');
        charBuffer.put(1,'o');
        charBuffer.put(2,'o');
        charBuffer.put(3,'o');
        charBuffer.put(4,'o');
    }
}
