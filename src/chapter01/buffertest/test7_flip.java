package chapter01.buffertest;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class test7_flip {
    //flip是对缓冲区进行反转，一般是存储完数据，要读出的时候用
    //limit=position position = 0 ,mark = -1
    public static void main(String[] args) {
        byte[] byteArray = new byte[] {1,2,3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.position(2);
        byteBuffer.mark();
        byteBuffer.flip();
        System.out.println("position:"+byteBuffer.position()
        +"     limit"+byteBuffer.limit());
        try{
            byteBuffer.reset();
        }catch (java.nio.InvalidMarkException e){//<=0的时候会抛出异常
            System.out.println("mark丢失");
        }
        System.out.println("=========================");
        demo();
    }
    //读取数据demo
    public static void demo(){
        CharBuffer charBuffer = CharBuffer.allocate(20);
        System.out.println("position:"+charBuffer.position()+" limit:"+charBuffer.limit());
        charBuffer.put("我是中国人我在中华人名共和国");
        System.out.println("position:"+charBuffer.position()+" limit:"+charBuffer.limit());
        charBuffer.position(0);
        System.out.println("position:"+charBuffer.position()+" limit:"+charBuffer.limit());
        for(int i = 0;i < charBuffer.limit();i++){
            System.out.println(charBuffer.get());
        }
        //上边是错误读取的代码

        System.out.println("position:"+charBuffer.position()+" limit:"+charBuffer.limit());
        charBuffer.clear();
        System.out.println("position:"+charBuffer.position()+" limit:"+charBuffer.limit());

        charBuffer.put("我是美国人");
        System.out.println("position:"+charBuffer.position()+" limit:"+charBuffer.limit());
        charBuffer.flip();
        for(int i = 0 ; i < charBuffer.limit();i++){
            System.out.println(charBuffer.get(i));
        }
    }
}
