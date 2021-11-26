package rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
    public static void main(String[] args) throws IOException {

        System.out.println(System.getProperty("user.dir"));
        Properties config = new Properties();
        Properties OR = new Properties();
        FileInputStream fileInputStream =
                new FileInputStream
                        (System.getProperty("user.dir") + "\\src\\main\\resources\\properties\\Config.properties");
        config.load(fileInputStream);

        fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\properties\\OR.properties");
        OR.load(fileInputStream);
        System.out.println(config.getProperty("browser"));
        System.out.println(OR.getProperty("bmlBtn"));

    }
}
