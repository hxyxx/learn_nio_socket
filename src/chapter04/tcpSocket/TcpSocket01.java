package chapter04.tcpSocket;

import sun.jvm.hotspot.debugger.ThreadAccess;

import java.io.*;
import java.nio.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//socket中不应该使用bufferedReader，也不能使用。如果知道数据大小可以使用InputStreamReader
public class TcpSocket01 {
    //4.1.1验证ServerSocket类的accept方法具有阻塞特性
    public static void main(String[] args) throws InterruptedException, IOException {
        test11();
    }
    //建立一个socketServer和一个socket客户端进行连接
    public static void test01() throws InterruptedException {
        Thread serverThread = new Thread(){
            @Override
            public void run(){
                try{
                    ServerSocket server = new ServerSocket(8080);
                    System.out.println("Server 阻塞开始:"+System.currentTimeMillis());
                    server.accept();
                    System.out.println("Server 阻塞结束："+System.currentTimeMillis());
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread clientThread = new Thread(){
            @Override
            public void run(){
                try{
                    System.out.println("Client 连接准备:"+System.currentTimeMillis());
                    Socket socket = new Socket("localhost",8080);//服务器地址及端口
                    System.out.println("Client 连接结束："+System.currentTimeMillis());
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        serverThread.start();
        Thread.sleep(3000);
        clientThread.start();
//        Thread.sleep(3000);
    }
    //建立一个socket客户端进行连接域名
    public static void test02(){
        Socket socket = null;
        try{
            socket = new Socket("www.baidu.com",80);
            System.out.println("socket连接成功");
        } catch (UnknownHostException e) {
            System.out.println("socket连接失败");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //使用ServerSocket创建一个socket服务器
    public static void test03() throws IOException, InterruptedException {
        Thread t1 = new Thread() {
            @Override
                    public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(6666);
                    System.out.println("n");
                    Socket socket = serverSocket.accept();
                    System.out.println("a");
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String getStr = "";
                    System.out.println("b");
//                    while (!"".
//                            equals(getStr == bufferedReader.readLine())) {
//                        System.out.println(getStr);
//                    }
                    System.out.println("z");
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n\r\n".

                            getBytes());
                    outputStream.write("<html><body><a href='http://www.baidu.com'>i am baidu.com</a></body></html>".

                            getBytes());
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run(){
                try {
                    Socket socket = new Socket("127.0.0.1", 6666);
                    InputStream inputStream = socket.getInputStream();
                    System.out.println(new BufferedReader(new InputStreamReader(inputStream)).readLine());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        t1.start();
        Thread.sleep(100);
        t2.start();
    }
    //client与server传递字符串,正确的，读取socket数据使用ObjectInputStream
    public static void test04() throws InterruptedException {
        Thread serverThread = new Thread(){
            @Override
            public void run(){
                try {
                    char[] charArray = new char[3];
                    ServerSocket serverSocket = new ServerSocket(8080);
                    Socket socket = serverSocket.accept();
                    //输入开始
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    int byteLenth = objectInputStream.readInt();
                    byte[] byteArray = new byte[byteLenth];
                    objectInputStream.readFully(byteArray);
                    String newString = new String(byteArray);
                    System.out.println(newString);
                    //输入结束
                    //输出开始
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    String strA = "客户端你好A";
                    String strB = "客户端你好B";
                    String strC = "客户端你好C";
                    int allStrLenth = (strA+strB+strC).getBytes().length;
                    objectOutputStream.writeInt(allStrLenth);
                    objectOutputStream.flush();
                    objectOutputStream.write(strA.getBytes());
                    objectOutputStream.write(strB.getBytes());
                    objectOutputStream.write(strC.getBytes());
                    objectOutputStream.flush();
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run(){
                try {
                    Socket socket = new Socket("127.0.0.1",8080);
                    OutputStream outputStream = socket.getOutputStream();
                    InputStream inputStream = socket.getInputStream();
                    //输出开始
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    String strA = "服务端你好A";
                    String strB = "服务端你好B";
                    String strC = "服务端你好C";
                    int allStrLenth = (strA+strB+strC).getBytes().length;
                    objectOutputStream.writeInt(allStrLenth);
                    objectOutputStream.flush();
                    objectOutputStream.write(strA.getBytes());
                    objectOutputStream.write(strB.getBytes());
                    objectOutputStream.write(strC.getBytes());
                    objectOutputStream.flush();

                    //输入开始
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                    int byteLength = objectInputStream.readInt();
                    byte[] byteArray = new byte[byteLength];
                    System.out.println(byteLength);
                    objectInputStream.readFully(byteArray);
                    String str = new String(byteArray);
                    System.out.println(str);
                    objectOutputStream.close();
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        serverThread.start();
        Thread.sleep(1000);
        t2.start();
    }
    //第一次咋不行呢,用循环来读数据，循环无法判断socket流当前是否结束，所以就是要读一行？直接下一步
    public static  void test05() throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run(){
                ServerSocket serverSocket;
                Socket socket = null;
                OutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    serverSocket = new ServerSocket(8080);
                    socket = serverSocket.accept();
                    //输出
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStream.write("hello,我是徐".getBytes());
                    outputStream.flush();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    char[] a = new char[3];
                    int readLength = inputStreamReader.read(a);
                    System.out.println("?");
                    while(readLength!=-1){
                        System.out.print(new String(a,0,readLength));
                        readLength = inputStreamReader.read(a);
                        System.out.println(readLength);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        outputStream.close();
                        inputStream.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run(){
                Socket socket = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try{
                    socket = new Socket("127.0.0.1",8080);
                    //获取数据
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    InputStreamReader r = new InputStreamReader(inputStream);
                    char[] a = new char[3];
                    int readLength = r.read(a,0,3);
                    while(readLength>0){
                        System.out.print(new String(a,0,readLength));
                        readLength = r.read(a,0,readLength);
                        System.out.println(readLength);
//                        break;
                    }
                    //获取数据结束
                    //写入数据
                    System.out.println("hhh");

                    outputStream.write("咋的啦".getBytes());
                    outputStream.flush();
                    //写入数据结束
//                    outputStream.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try{
                        inputStream.close();
                        outputStream.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(3000);
    }
    //使用Socket传递PNG图片
    public static void test06() throws InterruptedException {
        Thread serverThread = new Thread(){
            @Override
            public void run(){
                try {
                    ServerSocket serverSocket = new ServerSocket(8080);
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    FileOutputStream fos = new FileOutputStream(new File("./src/testFile/b.gif"));
                    byte[] byteArray = new byte[2048];
                    int readLength = inputStream.read(byteArray);
                    while(readLength!=-1){
                        fos.write(byteArray);
                        readLength = inputStream.read(byteArray);
                    }
                    fos.close();
                    inputStream.close();
                    socket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread clientThread = new Thread(){
            @Override
            public void run(){
                try{
                    Socket socket = new Socket("127.0.0.1",8080);
                    OutputStream os = socket.getOutputStream();
                    FileInputStream fis = new FileInputStream(new File("./src/testFile/a.gif"));
                    byte[] byteArray = new byte[2048];
                    int readLength = fis.read(byteArray);
                    while(readLength!=-1){
                        os.write(byteArray,0,readLength);
                        readLength = fis.read(byteArray);
                    }
                    os.flush();
                    os.close();
                    fis.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        serverThread.start();
        Thread.sleep(1000);
        clientThread.start();
    }
    //TCP三次握手过程
    public static void test07() throws IOException {
        Thread serverThread = new Thread(){
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8080);
                    System.out.println("阻塞开始"+System.currentTimeMillis());
                    serverSocket.accept();
                    System.out.println("阻塞结束"+System.currentTimeMillis());
                    Thread.sleep(Integer.MAX_VALUE);
                    serverSocket.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread clientThread = new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println("连接准备"+System.currentTimeMillis());
                    Socket socket = new Socket("127.0.0.1", 8080);
                    System.out.println("连接结束"+System.currentTimeMillis());
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("11".getBytes());
                    outputStream.write("11111".getBytes());
                    outputStream.write("22222".getBytes());
                    Thread.sleep(5000000);
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        serverThread.start();
        clientThread.start();
    }
    //TCP四次挥手过程
    public static void test08() throws InterruptedException {
        new Thread(
                () -> {
                    try{
                        ServerSocket serverSocket = new ServerSocket(8080);
                        Socket socket = serverSocket.accept();

                        socket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        Thread.sleep(1000);
        Runnable r = () -> {
            try{
                Socket socket = new Socket("127.0.0.1",8080);
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        r.run();


    }
    //测试Server端，不需要accept就可以进行三次握手了
    public static void test09() {
        Runnable r = ()->{
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
//                Thread.sleep(Integer.MAX_VALUE);
            } catch (IOException  e) {
                e.printStackTrace();
            }
        };
        r.run();
        new Thread(()->{
            try{
                Socket socket = new Socket("127.0.0.1",8080);
                OutputStream os = socket.getOutputStream();
                for (int i = 0; i < 3; i++) {
                    os.write("123123".getBytes());
                }
                os.flush();
                os.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //多线程连接
    public static void test10() throws IOException, InterruptedException {
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                System.out.println(System.currentTimeMillis());
                for(int i = 0;i<10000;i++) {
                    Socket socket = serverSocket.accept();
                    Runnable eachR = () -> {
                        try {
                            InputStream is = socket.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(is);
                            char[] byteArray = new char[1000];
                            int readLength = 0;
                            while((readLength =inputStreamReader.read(byteArray))!=-1){
                                String str = new String(byteArray,0,readLength);
//                                System.out.println(str);
                            }
                            inputStreamReader.close();
                            is.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    new Thread(eachR).start();
                }
                System.out.println(System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
//        new Thread(r).start();
        Thread.sleep(100);
//        Socket socket1 = new Socket("127.0.0.1",8080);
//        System.out.println("aaa");
        for(int i = 0 ; i<10000;i++){
            Runnable r = ()->{
                Socket socket ;
                try{
                    socket = new Socket("127.0.0.1",8080);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("我是中国人".getBytes());
                    outputStream.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            r.run();
        }
    }
    //线程池连接
    public static void test11() throws IOException, InterruptedException {
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                ExecutorService pool = Executors.newFixedThreadPool(3);
                System.out.println(System.currentTimeMillis());
                for (int i = 0;i<10000;i++) {
                    Socket socket = serverSocket.accept();
                    Runnable eachR = () -> {
                        try {
                            InputStream is = socket.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(is);
                            char[] byteArray = new char[1000];
                            int readLength = 0;
                            while((readLength =inputStreamReader.read(byteArray))!=-1){
                                String str = new String(byteArray,0,readLength);
//                                System.out.println(str);
                            }
                            inputStreamReader.close();
                            is.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                    pool.execute(eachR);
                }
                System.out.println(System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
//        new Thread(r).start();
        Thread.sleep(100);
//        Socket socket1 = new Socket("127.0.0.1",8080);
//        System.out.println("aaa");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for(int i = 0 ; i<10000;i++){
            Runnable r = ()->{
                Socket socket ;
                try{
                    socket = new Socket("127.0.0.1",8080);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("我是中国人".getBytes());
                    outputStream.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            pool.execute(r);
        }
    }
}
