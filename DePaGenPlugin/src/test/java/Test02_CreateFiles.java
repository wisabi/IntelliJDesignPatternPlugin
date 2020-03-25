import com.DesignPatternFactory.CreateFile;
import org.junit.Test;
import java.io.File;
import java.util.Scanner;
import org.junit.Assert;
import static org.junit.Assert.fail;

public class Test02_CreateFiles {
    @Test
    public void CreateFiles() {
        String testFileContents = "!\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        String testFileName = "Sample";
        CreateFile createFile = new CreateFile();
        String folderPath = createFile.CreateFolder("TestFolder");
        createFile.CreateFiles(testFileName, testFileContents);

        try {
            String data = "";
            File folder = new File(folderPath);
            File file = new File(folderPath+"/"+testFileName+".java");
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                data =  data +  myReader.nextLine();
            }
            myReader.close();

            Assert.assertTrue(data.equals(testFileContents));
            file.delete();
            folder.delete();
        } catch (Exception e) {
            fail();
        }

    }
}
