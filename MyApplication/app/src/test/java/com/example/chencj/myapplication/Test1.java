package com.example.chencj.myapplication;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.R.attr.start;
import static com.blankj.utilcode.util.FileUtils.getFileName;
import static com.blankj.utilcode.util.StringUtils.isSpace;

/**
 * Created by CHENCJ on 2021/1/28.
 */

public class Test1 {
    @Test
    public void f1(){
        String s = "<pti:timert1=\"1\"";
        System.out.println(s);


        Calendar   cDay1   =   Calendar.getInstance();
        cDay1.set(Calendar.YEAR,2021);
        cDay1.set(Calendar.MONTH,3);
        int actualMaximum = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        int first = cDay1.getActualMinimum(Calendar.DAY_OF_MONTH);
        System.out.println(actualMaximum);
        System.out.println(first);

        // 设置日历对象的年月日
        Calendar c = Calendar.getInstance();
        c.set(2021, 5, 1); // 其实是这一年的3月1日
        // 把时间往前推一天，就是2月的最后一天
        c.add(Calendar.DATE, -1);

        // 获取这一天输出即可
        System.out.println(c.get(Calendar.DATE));
    }

    @Test
    public void f2(){
        String s = "<pti:timert1=\"1\">";
        String s1 = "<PTI: local ring status=\"1\">";

        s1="<PTI:SMS num2=”abcde”>";
        /*for (int i = 0; i < s1.length(); i++) {
            System.out.println(i+":"+s1.charAt(i)+" "+(new Integer(s1.charAt(i))));
        }*/

//        String regex = new String(new char[]{160,32});
//        System.out.println("'"+ regex +"'"+"\\s");
//
//        s1 = s1.replaceAll("\\u00A0", "");
//
//        String[] split = s1.split(">");
//
//        for (int i = 0; i < split.length; i++) {
//            System.out.println(i+":"+split[i]);
//        }
//
//        char space_1 = new Character(' ');//英文空格
//
//        char space_2 = new Character('');//中文空格
//
//        System.out.println((int)space_1);
//
//        System.out.println((int)space_2);
/*
        for (int i = 0; i < s.length(); i++) {
            System.out.println(i+ ":"+s.charAt(i));
        }
        System.out.println(s);*/
    }

    @Test
    public void f3(){
        int a = 1 << 1;// 0000 0001  << 1  -->  0000 0010 2
                        // ~  1111 1101 取反操作  a+ (~a)= -1  0000 0010 0000 0011
        int b = 0b11111101;
        System.out.println(a + "  "+ (~a)+"  "+(~b));

        new Demo1();
    }
    class Demo<T>{
        public Demo(){
            Type genericSuperclass = this.getClass().getGenericSuperclass();
            if(genericSuperclass!=null) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

                if(parameterizedType!=null) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

                    if(actualTypeArguments!=null) {
                        for (int i = 0; i <actualTypeArguments.length; i++) {
                            System.out.println(actualTypeArguments[i]);
                            Class classes = (Class) actualTypeArguments[i];
                            try {
                                Object o = classes.newInstance();
                                System.out.println(o);
                            } catch (InstantiationException e) {

                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
    class Demo1 extends Demo<String>{

    }

    @Test
    public void f4() throws Exception {
        synchronized (Test1.class){
            System.out.println("123456");
            Test1.class.notify();
            System.out.println("123");
        }

        Class<?> aClass = Class.forName("java.lang.Integer");
        Field digits = aClass.getDeclaredField("digits");
        digits.setAccessible(true);
        char[] o = (char[]) digits.get(null);
        System.out.println(digits.getModifiers());

    }

    /**
     * socket的服务端
     * @throws Exception
     */
    @Test
    public void fSocketServerFunc() throws Exception {

        //创建一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        //创建一个server
        InetSocketAddress inetAddress = new InetSocketAddress("127.0.0.5",8080);
        ServerSocket serverSocket = new ServerSocket();


        serverSocket.bind(inetAddress);
        System.out.println("服务器启动");
        while (true) {
            //等待客户端链接
            final Socket socket = serverSocket.accept();
            threadPool.execute(new Runnable() {
                public void run() {
                    //可以和客户端通讯了
                    handler(socket);
                }
            });
        }
    }


    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            //获取socket的输入流
            InputStream inputStream = socket.getInputStream();
            while (true) {
                //将输入流的内容读取到字节数组中，一次最多读取1024字节
                int readCount = inputStream.read(bytes);
                if (readCount != -1) {
                    String value = new String(bytes, 0, readCount);
                    System.out.println("客户端的数据："+value);
                    System.out.println("当前客户端的线程ID:  " + Thread.currentThread()
                            .getId() + "  当前线程Name:  " + Thread.currentThread().getName());
                }else break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * socket的客户端
     * @throws Exception
     */
    @Test
    public void fSocketClientFunc() throws Exception {
        Socket socket = new Socket("127.0.0.5",8080);
        OutputStream outputStream =
                socket.getOutputStream();
        outputStream.write("123dddddd".getBytes());
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 构造Channel最主要的使用
     *
     * 第一个FileChannel可以通过file流获得。
     *
     *
     * 第二个是SocketChannel通过SocketChannel.open();获取
     * 第三个是ServerSocketChannel通过ServerSocketChannel.open();获取
     * 第四个是DatagramChannel通过DatagramChannel.open();获取
     * 需要使用 Selector selector=Selector.open();来选择
     * Selector有四种模式 write read connect accept
     */


    @Test
    public void fNIOFileFunc() throws Exception {
        String str = "Hello，World";

        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\CHENCJ\\Desktop\\strings2.text");
        //获取输出流的Channel
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer writebyteBuffer = ByteBuffer.allocate(1024);

        //将数据放入到缓冲区
        writebyteBuffer.put(str.getBytes());

        //此时fileChannel要从byteBuffer中读取数据
        //将byteBuffer置换为读模式
        writebyteBuffer.flip();

        //将byteBuffer写到fileChannel中去
        fileChannel.write(writebyteBuffer);

        fileOutputStream.close();

        /*=============================================*/


        //nio读数据
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\CHENCJ\\Desktop\\strings2.text");

        FileChannel fileInputStreamChannelChannel = fileInputStream.getChannel();

        ByteBuffer readbyteBuffer = ByteBuffer.allocate(1024);

        //将通道中的数据放入到缓冲区
        fileInputStreamChannelChannel.read(readbyteBuffer);

        String s = new String(readbyteBuffer.array());
        System.out.println(s);
        fileInputStreamChannelChannel.close();
    }

    @Test
    public void fNIOServerFunc() throws Exception {
        ServerSocketChannel serverChannel=ServerSocketChannel.open();//创建服务端的channel
        serverChannel.configureBlocking(false);//设置非阻塞
        ServerSocket serverSocket = serverChannel.socket();//得到ServerSocket对象
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8080));//绑定ip port
        Selector selector=Selector.open();//实例化Selector
        //selector.close();//关闭资源
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);//把selector注册到serverChannel,并设置感兴趣的selector事件
        System.out.println("服务器启动完毕!!");

        while(true){
            int status=selector.select();//不建议设置超时 阻塞 直到有感兴趣的事件完成  返回感兴趣的事件个数
            if(status>0){
                Iterator<SelectionKey> keys=selector.selectedKeys().iterator();//遍历感兴趣的事件个数
                while(keys.hasNext()){
                    SelectionKey key=keys.next();
                    keys.remove();
                    if(key.isAcceptable()){//得到有可接受ACCEPT感兴趣的事件，
                        System.out.println("可接受客户端请求:"+status);
                        //得到绑定的ServerSocketChannel  channel
                        ServerSocketChannel ssc=(ServerSocketChannel)key.channel();//selectkey持有一个channel
                        //key.selector();//selectkey持有一个当前的Selector的实例
                        //通过ServerSocketChannel，accept得到，SocketChannel，并关注READ的selector事件
                        SocketChannel channel=ssc.accept();
                        ByteBuffer bf=ByteBuffer.wrap(channel.getRemoteAddress().toString().getBytes("utf-8"));
                        /*
                        * Buffer
                        * 标记、位置、限制和容量值遵守以下不变式：
                        0 <= 标记(mark) <= 位置（position） <= 限制（limit） <= 容量（capacity）

                        buffer实例的方法：
                        allocate(capacity);: 在堆上创建大小的对象
                        allocateDirect(capacity);在堆外空间上创建指定大小的对象
                        wrap（byte[]）通过存在的数组创建对象
                        wrap（byte[],offerset,length）通过存在的数组创建对象

                        方法：
                        buffer.put() :往Buffer中写入数据 pos 位置移动
                        buffer.flip() ：读写模式切换 -》lim指向pos,pos指向mark
                        buffer.get() ：从Buffer中读取数据 ->pos 位置移动
                        buffer.clear(): 清空Buffer缓存 mark=-1,pos=0, lim=cap=capacity
                        * */
                        int size=channel.write(bf);
                        if(size<=0){
                            key.interestOps(SelectionKey.OP_WRITE); //注册写事件
                            channel.write(bf);
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);//取消注册写事件
                        }
                        //SocketChannel设置非阻塞
                        channel.configureBlocking(false);
                        //SocketChannel，并关注READ的selector事件
                        channel.register(selector,SelectionKey.OP_READ);
                    }else if(key.isReadable()){//对READ事件 感兴趣的Selector
                        System.out.println("可读客户端流:"+status);
                        //获取SocketChannel
                        SocketChannel sc=(SocketChannel)key.channel();
                        //分配缓存大侠
                        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
                        String msg="";
                        //循环去读 SocketChannel内容到写到buffer中
                        while(sc.read(buffer)>0){
                            //读写反转，
                            buffer.flip();//开始读出来
                            //解析缓存中的字节，编译字符串
                            msg+= Charset.forName("utf-8").decode(buffer).toString();
                        }
                        //清除buffer内容
                        (buffer).clear();
                        System.out.println("服务端接收到客户端"+sc.getRemoteAddress()+"消息"+msg);
                    }
                }
            }

        }

    }

    @Test
    public void fNIOClientFunc() throws Exception {
        //创建一个线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        int i = 1;
        //打开客户端的Channel
        SocketChannel clientChannel=SocketChannel.open();
        //构造ip地址
        SocketAddress address=new InetSocketAddress("127.0.0.2", 8080);
        //设置非阻塞
        clientChannel.configureBlocking(false);
        //连接服务端
        clientChannel.connect(address);
        //打开selector
        Selector selector=Selector.open();
        //selector注册到clientChannel并关注连接事件
        clientChannel.register(selector, SelectionKey.OP_CONNECT);
        while(true){
            int status=selector.select();//将会阻塞，直到感兴趣的IO发生
            //selectNow不会阻塞，调用后立即返回状态 需要在循环中不停获取
            //status=selector.selectNow();
            if(status>0){
                System.out.println("channel:"+i+"获得服务器响应，响应状态"+status);
                //遍历感兴趣的selectorkey
                Set<SelectionKey> keys=selector.selectedKeys();
                Iterator<SelectionKey> its=keys.iterator();
                while(its.hasNext()){
                    SelectionKey key=its.next();
                    if(key.isConnectable()){//得到连接的事件
                        //拿到channel
                        SocketChannel sc=(SocketChannel)key.channel();
                        //到客户端 与服务器端建立连接，完成连接
                        sc.finishConnect();
                        //设置非阻塞
                        sc.configureBlocking(false);
                        System.out.println("channel:"+i+"连接建立完毕!!");
                        //写入到buffer
                        ByteBuffer bf=ByteBuffer.wrap(("channel:"+i).getBytes("utf-8"));
                        //读出buffer 并 写到channel
                        int size=sc.write(bf);
                        if(size<=0){//说明channel中
                            ////selectkey 感兴趣  写事件
                            key.interestOps(SelectionKey.OP_WRITE);
                            //再次写入channel
                            sc.write(bf);
                            //取消注册写事件
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
                        }
                        //注册读感兴趣事件
                        sc.register(selector,SelectionKey.OP_READ);
                    }else if(key.isReadable()){//读的感兴趣事件 触发

                        SocketChannel sc=(SocketChannel)key.channel();
                        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
                        String msg="";
                        while(sc.read(buffer)>0){//从channel读出来，写到buffer
                            //调整读写
                            buffer.flip();
                            //解析
                            msg+=Charset.forName("utf-8").decode(buffer).toString();
                        }
                        buffer.clear();
                        System.out.println("客户端"+i+"接收到服务器消息"+msg);
                    }
                }
            }
            status=-1;//因为设置了500毫秒的超时 时间
        }
    }

    private static  final  int THREAD_COUNT = 3;
    @Test
    /**
     * 断点续传的例子
     */


    public void f5() throws Exception {

        String path = "http://192.168.2.128:8180/down_file.txt";
        URL url  = new URL(path);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setConnectTimeout(10 * 1000);

        urlConnection.setRequestMethod("GET");

        urlConnection.connect();

        int responseCode = urlConnection.getResponseCode();

        //得到文件的总长度，为断点续传设置 startinde和endindex
        if(responseCode == 200){
            int contentLength = urlConnection.getContentLength();

            //创建文件，等大小的
            RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");

            file.setLength(contentLength);


            //设计断点续传，多线程
            //使用3个线程来设计，得到开始和结束的位置
            int downSize = contentLength/THREAD_COUNT;
            for (int i = 0; i < THREAD_COUNT; i++) {
                int start = i * downSize;
                int end = (i+1) * downSize - 1;
                if(i == THREAD_COUNT - 1){
                    end = contentLength - 1;
                }
                System.out.println(1111);
                new MultiDownThread(path,start,end).start();
            }


        }
    }

    class MultiDownThread extends Thread{

        private int startIndex;
        private int endIndex;

        private String path;
        public MultiDownThread(String path,int startIndex,int endIndex){

            this.path = path;
            this.startIndex = startIndex;
            this.endIndex = endIndex;

        }

        @Override
        public void run() {
            URL url  = null;
            try {
                url = new URL(path);


                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setConnectTimeout(10 * 1000);

                urlConnection.setRequestMethod("GET");

                // 断点下载，从指定位置 继续下载
                urlConnection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

                int responseCode = urlConnection.getResponseCode();
                System.out.println(toString());
                System.out.println(responseCode);
                //设置了setRequestProperty响应码是206
                if(responseCode == 206){

                    //获取文件
                    RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
                    //为文件设置当前下载的开始位置
                    file.seek(startIndex);
                    InputStream inputStream = urlConnection.getInputStream();
                    byte[] buff = new byte[1024];
                    int len = -1;
                    while ((len=inputStream.read(buff) )!= -1){
                        //写入内容到文件中
                        file.write(buff,0,len);
                    }
                    file.close();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "MultiDownThread{" +
                    "startIndex=" + startIndex +
                    ", endIndex=" + endIndex +
                    ", path='" + path + '\'' +
                    ", threadname='" + Thread.currentThread().getName() + '\'' +
                    '}';
        }
    }

    /**
     * Return the name of file.
     *
     * @param filePath The path of file.
     * @return the name of file
     */
    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) return "";
        int lastSep = filePath.lastIndexOf("/");
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

}
