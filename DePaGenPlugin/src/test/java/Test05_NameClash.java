import com.Checker.Checker;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.util.HashSet;

public class Test05_NameClash {

    @Test
    public void test01_parseDirectory(){
        Checker checker = new Checker();
        File file = new File(System.getProperty("user.dir") +"/src/test/");
        checker.parseDirectory(file);
        System.out.println(checker.set);
        HashSet<String> x = new HashSet<String>();
        x.add("testInterface");
        x.add("testClass");
        x.add("testClass03");
        Assert.assertTrue(checker.set.get(System.getProperty("user.dir") +"/src/test/resources/testPackage01").containsAll(x));
    }

    @Test
    public void test02_parseDirectory(){
        Checker checker = new Checker();
        File file = new File(System.getProperty("user.dir") +"/src/test/");
        checker.parseDirectory(file);
        System.out.println(checker.set);
        HashSet<String> x = new HashSet<String>();
        x.add("testInterface");
        x.add("testClass");
        x.add("testClass03");
        Assert.assertTrue(checker.set.get(System.getProperty("user.dir") +"/src/test/resources/testPackage02").containsAll(x));
    }

    @Test
    public void test03_check(){
        Checker checker = new Checker();
        File file = new File(System.getProperty("user.dir") +"/src/test/");
        checker.parseDirectory(file);
        System.out.println(checker.set);
        HashSet<String> x = new HashSet<String>();
        x.add("testInterface");
        x.add("testClass");
        x.add("testClass03");
        Assert.assertTrue(checker.set.get(System.getProperty("user.dir") +"/src/test/resources/testPackage02").containsAll(x));
    }
}