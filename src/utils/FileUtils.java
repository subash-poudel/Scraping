package utils;

import com.oracle.tools.packager.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Subash on 8/2/16.
 * <p>
 * /Users/Subash/Desktop/filename.txt
 */
public class FileUtils {
    public static final String DESKTOP_PATH = "/Users/Subash/Desktop/";

    public static File createFile(String path) {
        File f = new File(path);
        try {
            if (f.createNewFile()) {
                return f;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static void writeToFile(File f, String data) {
        writeToFile(f.getAbsolutePath(), data);
    }

    public static void writeToFile(String path, String data) {
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.print(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String path) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


}
