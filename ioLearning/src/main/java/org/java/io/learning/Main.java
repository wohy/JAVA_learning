package org.java.io.learning;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/***
 * java io 学习
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        // 使用 java.io.File 构造一个 File 对象 可以传入绝对路径，也可以传入相对路径
        File file = new File("./test.txt");
        System.out.println(File.separator); // 根据当前平台打印"\"或"/" 系统文件路径分隔符
        System.out.println("绝对路径：" + file.getAbsolutePath() + "，构造方法传入路径：" + file.getPath() + "规范路径（与绝对路径类似）：" + file.getCanonicalPath());
        System.out.println(file.isFile()); // true 判断是否是已经存在的文件

        File dir = new File("..");
        System.out.println(dir.getCanonicalPath());
        System.out.println(dir.isDirectory()); // true 是否是一个已存在的目录
        System.out.println(Arrays.toString(dir.listFiles())); // listFiles() 获取目录下的子目录名； list() 获取目录下的文件名
        // 使用 FilenameFilter 过滤出想要的文件
        File[] fs2 = dir.listFiles(new FilenameFilter() { // 仅列出文件名称中包含 java 的文件
            public boolean accept(File dir, String name) {
                return name.contains("java");
            }
        });
        System.out.println(Arrays.toString(fs2)); // [../java_learning, ../java]

        // 其他方法
        // boolean canRead()：是否可读；
        // boolean canWrite()：是否可写；
        // boolean canExecute()：是否可执行；
        // long length()：文件字节大小
        // 当File对象表示一个文件时，可以通过 createNewFile() 创建一个新文件，用 delete() 删除该文件
        // createTempFile()来创建一个临时文件，以及deleteOnExit()在JVM退出时自动删除该文件
        // boolean mkdir()：创建当前File对象表示的目录；
        // boolean mkdirs()：创建当前File对象表示的目录，并在必要时将不存在的父目录也创建出来；
        // boolean delete()：删除当前File对象表示的目录，当前目录必须为空才能删除成功。

        // 如果需要对目录进行复杂的拼接、遍历等操作，使用Path对象更方便。
        Path p1 = Paths.get("..", "java"); // 构造一个Path对象
        System.out.println(p1); // ../java

        // InputStream 基本输入流
        // 总是使用 try(resource) 来保证 InputStream 正确关闭。
        // 编译器只看 try(resource = ...) 中的对象是否实现了 java.lang.AutoCloseable 接口
        // 如果实现了，就自动加上 finally 语句并调用 close() 方法。InputStream 和 OutputStream 都实现了这个接口，因此，都可以用在 try(resource) 中。
        System.out.println(readerToString("./test.txt")); // 123 hello world

        try (InputStream input = new FileInputStream("./test.txt")) {
            byte[] buffer = new byte[20];
            int i;
            while ((i = input.read(buffer)) != -1) { // 读取到缓冲区
                System.out.println("读取了 " + i + " 个字节");  // 读取了 15 个字节.
            }
            System.out.println(Arrays.toString(buffer)); // 49, 50, 51, 32, 104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 0, 0, 0, 0, 0]
        }

        // 基本输出流 OutputStream
        try (OutputStream output = new FileOutputStream("./test.txt")) {
            output.write(new byte[]{'M', 'y', ' ', 'n', 'a', 'm', 'e', ' ', 'i', 's', ' ', 'H', 'U', ' ', 'Y', 'I'});
        }
        System.out.println(readerToString("./test.txt")); // My name is HU YI
        writeFileFromString("./test.txt", "123 hello world");
        // copyFile("./test.txt");
        copyFileInBuffer("./test.txt");
        // copyLargeFile("./ChineseTextContent.txt", "./ChineseTextContent(copy).txt");
        // copyLargeFile("./img.png", "./img(1).png");

        // 序列化 和 反序列化
        // 序列化：将一个 Java 对象变成 二进制内容，本质上就是一个 byte[] 数组
        // 反序列化：把一个二进制内容（也就是 byte[] 数组）变回 Java 对象。
        // 有了反序列化，保存到文件中的 byte[] 数组又可以“变回” Java对象，或者从网络上读取 byte[] 并把它“变回” Java 对象。
        // 存在一定的安全性问题
        // 一个精心构造的byte[]数组被反序列化后可以执行特定的Java代码，从而导致严重的安全漏洞。
        // 可以直接使用 JSON 传输

        //可序列化的Java对象必须实现java.io.Serializable接口，类似Serializable这样的空接口被称为“标记接口”（Marker Interface）；
        //反序列化时不调用构造方法，可设置serialVersionUID作为版本号（非必需）；
        //Java的序列化机制仅适用于Java，如果需要与其它语言交换数据，必须使用通用的序列化方法，例如JSON。


        // Reader 字符流读取、Writer字符流写入，以char为单位

        //对于大多数情况，使用NIO（New Input/Output）包中的 java.nio.file.Files 类和 java.nio.channels.FileChannel 类可以提供高效的文件读取性能


    }
    static String readerToString(String path) throws IOException {
        String s;
        try (InputStream input = new FileInputStream(path)) {
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = input.read()) != -1) {
                sb.append((char) n);
            }
            s = sb.toString();
        }
        return s;
    }

    static void writeFileFromString(String path, String content) throws IOException {
        try (OutputStream output = new FileOutputStream(path)) {
            output.write(content.getBytes());
        }
    }

    // 复制文件 简易
    static String generateCopyPath(String from) {
        String toPath = from.substring(0, from.lastIndexOf("."));
        String ext = from.substring(from.lastIndexOf("."));
        return toPath + "(copy)" + ext;
    }

    static void copyFile(String from) throws IOException {
        String content = readerToString(from);
        writeFileFromString(generateCopyPath(from), content);
    }

    // 复制文件 利用缓冲区
    static void copyFileInBuffer(String from) throws IOException {
        // 目标文件的路径
        String destinationFilePath = generateCopyPath(from);

        // 使用try-with-resources语句自动管理资源
        try (InputStream inputStream = new FileInputStream(from);
             OutputStream outputStream = new FileOutputStream(destinationFilePath)) {

            // 缓冲区
            byte[] buffer = new byte[1024];
            int length;
            // 读取源文件并写入目标文件
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            System.out.println("文件复制完成。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 高效大文件复制
    public static void copyLargeFile(String sourcePath, String destinationPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);

        try (FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);
             FileChannel destinationChannel = FileChannel.open(destination, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW)) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(8 * 1024 * 1024); // 8MB缓冲区

            while (sourceChannel.read(buffer) != -1) {
                buffer.flip(); // 准备读取缓冲区中的数据
                destinationChannel.write(buffer); // 写入数据到目标文件
                buffer.clear(); // 清空缓冲区，准备下一次读取
            }
        }
    }
}