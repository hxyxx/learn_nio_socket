package chapter01.buffertest;

import java.nio.CharBuffer;

public class test4_remaininh {
    //方法remaining返回当前位置与limit之间的元素数
    public static void main(String[] args) {
        char[] charArray = new char[]{'a','b','c','d','e'};
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("Capacity:"+charBuffer.capacity()
                +"    limit:"+charBuffer.limit()
                +"     position"+charBuffer.position());

        System.out.println("remaining:"+charBuffer.remaining());
        charBuffer.position(2);
        System.out.println("Capacity:"+charBuffer.capacity()
                +"    limit:"+charBuffer.limit()
                +"     position"+charBuffer.position());
        System.out.println("remaining:"+charBuffer.remaining());
    }
}
