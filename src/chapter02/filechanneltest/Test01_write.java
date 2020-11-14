package chapter02.filechanneltest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test01_write {
    public static void main(String[] args) throws IOException, InterruptedException {
        write2();
    }
    //验证write是从通道当前位置开始写入的
    public static void write1() throws IOException {
        System.out.println("aaa");
        FileOutputStream fosRef = new FileOutputStream(new File("./a.txt"));
        FileChannel fileChannel = fosRef.getChannel();
        try{
            ByteBuffer buffer = ByteBuffer.wrap("abcde".getBytes());
            System.out.println("A channel position:"+fileChannel.position());
            System.out.println("write 返回值："+fileChannel.write(buffer));
            System.out.println("A channel position:");
            fileChannel.position(2);
            buffer.rewind();//还原position为0
            System.out.println("write() 2返回值："+fileChannel.write(buffer));
            System.out.println("filechannel position:"+fileChannel.position());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileChannel.close();
        fosRef.close();
    }
    //验证write(buf)是将buf的remaining写入通道,remaining = limit-position
    //write的同步特性
    public static void write2() throws IOException, InterruptedException{
        FileOutputStream fosRef = new FileOutputStream(new File("./src/testFile/write2.txt"));
        FileChannel fileChannel = fosRef.getChannel();
        for(int i = 0;i<10;i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    try{
                        ByteBuffer buffer = ByteBuffer.wrap("abcde\r\n".getBytes());
                        fileChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread2 = new Thread(){
                @Override
                public void run(){
                    try{
                        ByteBuffer buffer = ByteBuffer.wrap("我是中国人\r\n".getBytes());
                        fileChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread3 = new Thread(){
                @Override
                public void run(){
                    try{
                        ByteBuffer buffer = ByteBuffer.wrap("猜猜我是谁\r\n".getBytes());
                        fileChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread4 = new Thread(){
                @Override
                public void run(){
                    try{
                        ByteBuffer buffer = ByteBuffer.wrap("小霸王！\r\n".getBytes());
                        fileChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread2.start();
            thread3.start();
            thread4.start();
        }
        Thread.sleep(3000);
        fileChannel.close();
        fosRef.close();
    }
}
