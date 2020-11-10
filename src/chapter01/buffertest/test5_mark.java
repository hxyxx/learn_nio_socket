package chapter01.buffertest;

import java.nio.CharBuffer;

public class test5_mark {
    //mark.会在调用reset方法之后，将mark的 值赋值给position
    public static void main(String[] args) {
        char[] charArray = new char[]{'a','b','c','d','e'};
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        charBuffer.position(2);
        charBuffer.mark();
        System.out.println("Capacity:"+charBuffer.capacity()
                +"    limit:"+charBuffer.limit()
                +"     position"+charBuffer.position());
        charBuffer.position(3);
        charBuffer.reset();
        System.out.println("Capacity:"+charBuffer.capacity()
                +"    limit:"+charBuffer.limit()
                +"     position"+charBuffer.position());
    }
}
