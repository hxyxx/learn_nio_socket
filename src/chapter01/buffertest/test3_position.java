package chapter01.buffertest;

import java.nio.CharBuffer;

public class test3_position {
    //位置获取与设置
    public static void main(String[] args) {
        char[] charArray = new char[]{'a','b','c','d'};
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("Capacity:"+charBuffer.capacity()
                +"    limit:"+charBuffer.limit()
        +"     position"+charBuffer.position());
        charBuffer.position(2);
        System.out.println("Capacity:"+charBuffer.capacity()
                +"    limit:"+charBuffer.limit()
                +"     position"+charBuffer.position());
        charBuffer.put('z');
        for(int i = 0;i<charArray.length;i++){
            System.out.println(charArray[i]+"   ");
        }


    }
}
