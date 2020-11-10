package chapter01.buffertest;

import java.nio.ByteBuffer;

public class test6_clear {
    //clear并不是真的将数据clear，而是将position置零，limit = capacity,mark = -1
    public static void main(String[] args) {
        byte[] byteArray = new byte[] {1,2,3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.position(2);
        byteBuffer.limit(3);
        byteBuffer.mark();
        byteBuffer.clear();
        System.out.println("position:"+byteBuffer.position());
        System.out.println("limit"+byteBuffer.limit());
        try {
            byteBuffer.reset();
        }catch (java.nio.InvalidMarkException e){
            System.out.println("mark丢失");
        }
    }
}
