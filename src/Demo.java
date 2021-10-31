import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.nio.Buffer;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) {
        int i = 3;
        switch (i) {
            default:
                System.out.println("default");
            case 0:
                System.out.println(0);
            case 1:
                System.out.println(1);
        }
        String str = "abc_@host+com";
        String pattern = "^[a-zA-Z0-9_]+@[(a-zA-Z)+y]+com$";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }

    public static int func() throws Exception {
        for (int i = 1; i < 3; i++) {
            try {
                throw new Exception("bb");
            } catch (Exception e) {
                System.out.println("e");
                throw e;
            } finally {
                System.out.println("fin");
                continue;
            }
        }
        return 0;
    }
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    static int[] arr = new int[10];
    String str;
    StringBuilder sb;
    Process p;
    Class c;
    PreparedStatement ps;
    Connection conn;
    File f;
    Files fs;
    RandomAccessFile raf;
    FileOutputStream fos;
    BufferedReader br;
    Runtime rt;
    ThreadGroup tg;
    Semaphore sm;
    LinkedBlockingQueue lbq;
    ClassLoader cl;
    URLClassLoader ucl;
    Class clas;
    Optional op;
    Buffer bf;
    Integer in;
    Field field;
    Method method;
    ArrayList arrl;
    TreeMap tm;
    CountDownLatch cdl;
}
