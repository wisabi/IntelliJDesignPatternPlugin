import com.DesignPatternFactory.NameCheck;
import org.junit.Assert;
import org.junit.Test;

public class Test03_DirectoryNaming {

    final String[] invalidDirectoryNames = {
            "./.", "/.", "./", "/dir", "dir/", "/dir/" , ".", ".." ,""
    };

    final String[] validDirectoryNames = {
            "dir", ".dir", "dir01", "00", "01dir", "dir.", " ", "...", "..dir"
    };


    @Test
    public void testDirectoryNames05(){
        for(String identifier : invalidDirectoryNames) {
            Assert.assertFalse(NameCheck.checkFolderName(identifier));
        }
    }

    @Test
    public void testDirectoryNames06() {
        for (String identifier : validDirectoryNames) {
            Assert.assertTrue(NameCheck.checkFolderName(identifier));
        }
    }
}
