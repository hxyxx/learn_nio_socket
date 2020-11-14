package chapter03.networkInterfaceTest;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class test01 {
    public static void main(String[] args) throws UnknownHostException {
        test6();
    }
    //获取网络接口的基本信息
    public static void test1(){
        try{
            Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
            while(networkInterface.hasMoreElements()){
                NetworkInterface eachNetworkInterface = networkInterface.nextElement();
                System.out.println("getName 获取网络设备名称："+eachNetworkInterface.getName());
                System.out.println("getDisplay 获取网络设备显示名称："+eachNetworkInterface.getDisplayName());
                System.out.println("getIndex 获取网络接口的索引："+eachNetworkInterface.getIndex());
                System.out.println("isUp 是否已经开启并运行："+eachNetworkInterface.isUp());
                System.out.println("isLoopback 是否为回调接口："+eachNetworkInterface.isLoopback());
                System.out.println();
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    //获取MTU大小
    public static void test2(){
        try{
            Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
            while(networkInterface.hasMoreElements()){
                NetworkInterface eachNetworkInterface = networkInterface.nextElement();
                System.out.println("getName 获取网络设备名称："+eachNetworkInterface.getName());
                System.out.println("getDisplay 获取网络设备显示名称："+eachNetworkInterface.getDisplayName());
                System.out.println("getMTU 获取最大传输单元："+eachNetworkInterface.getMTU());
                System.out.println();
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    //子接口的处理,网络接口虚拟子接口
    public static void test3(){
        try{
            Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
            while(networkInterface.hasMoreElements()){
                NetworkInterface eachNetworkInterface = networkInterface.nextElement();
                System.out.println("getName 获取网络设备名称："+eachNetworkInterface.getName());
                System.out.println("getDisplay 获取网络设备显示名称："+eachNetworkInterface.getDisplayName());
                System.out.println("isVirtual 是否为虚拟接口:" +eachNetworkInterface.isVirtual());
                System.out.println("getParent获得父接口："+eachNetworkInterface.getParent());
                System.out.println("getSubInterfaces:"+eachNetworkInterface.getSubInterfaces());
                System.out.println(eachNetworkInterface.getSubInterfaces().hasMoreElements());
                Enumeration<NetworkInterface> networkInterfaceSub = eachNetworkInterface.getSubInterfaces();
                while(networkInterfaceSub.hasMoreElements()){
                    NetworkInterface eachNetworkInterfaceSub = networkInterfaceSub.nextElement();
                    System.out.println("*******************************************");
                    System.out.println("getName 获取网络设备名称："+eachNetworkInterfaceSub.getName());
                    System.out.println("getDisplay 获取网络设备显示名称："+eachNetworkInterfaceSub.getDisplayName());
                    System.out.println("isVirtual 是否为虚拟接口:" +eachNetworkInterfaceSub.isVirtual());
                    System.out.println("getParent获得父接口："+eachNetworkInterfaceSub.getParent());
                    System.out.println("*******************************************");
                }
                System.out.println();
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    //获得硬件地址
    public static void test4(){
        try{
            Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
            while(networkInterface.hasMoreElements()){
                NetworkInterface eachNetworkInterface = networkInterface.nextElement();
                System.out.println("getName 获取网络设备名称："+eachNetworkInterface.getName());
                System.out.println("getDisplay 获取网络设备显示名称："+eachNetworkInterface.getDisplayName());
                System.out.println("getHardwareAddress 获取硬件地址：");
                byte[] hardwareAddress = eachNetworkInterface.getHardwareAddress();
                if(hardwareAddress!=null&&hardwareAddress.length!=0){
                    for(int i = 0 ; i < hardwareAddress.length;i++)
                        System.out.print(hardwareAddress[i]+" ");
                }
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    //获取本地地址和回环地址的基本信息
    public static void test5() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("localhost address :");
        byte[] address = localhost.getAddress();
        if(address != null && address.length!=0){
            for(int i = 0 ; i < address.length ; i++){
                System.out.print(address[i]+" ");
            }
        }
        System.out.println();
        System.out.println(localhost.getClass().getName());
        System.out.println();
        InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
        System.out.println("inetAddress loopbackAddress = ");
        byte[] loopbackAddressb = loopbackAddress.getAddress();
        for(int i = 0 ; i < loopbackAddressb.length ; i++){
            System.out.print(loopbackAddressb[i]+" ");
        }
    }
    //根据主机名获取地址
    public static void test6() throws UnknownHostException {
        InetAddress baidu  = InetAddress.getByName("www.baidu.com");
        byte[] baiduAddress = baidu.getAddress();
        for(int i = 0; i <baiduAddress.length ; i++){
            System.out.print(baiduAddress[i]+" ");
        }

        //getAllByName
        InetAddress[] baiduAddressArray = InetAddress.getAllByName("www.baidu.com");
        for(int i = 0 ; i<baiduAddressArray.length;i++){
            InetAddress eachbaiduAddress = baiduAddressArray[i];
            System.out.println(eachbaiduAddress.getHostAddress());
        }
    }
}
