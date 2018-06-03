/*
  Remove the copy right header from all files inside project.
*/
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class RemoveHeaders {

  private static String readEntireFile(String filePath) {
    String content = "";

    try {
      content = new String(Files.readAllBytes(Paths.get(filePath)));
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return content;
  }

  private static void saveFileToDisk(String filePath, String content) {
    File file = new File(filePath);
    Path path = Paths.get(filePath);

    try (FileWriter writer = new FileWriter(file)) {
      if (Files.isWritable(path) && !Files.isSymbolicLink(path) && !Files.isHidden(path)) {
        writer.write(content);
        writer.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String removeMatchingText(String content, Pattern pattern) {
    return pattern.matcher(content).replaceAll("");
  }

  public static void recursivelyGetFilesAndRemoveHeaders(String path) {
    Pattern copyrightHeader = Pattern.compile("(?i)/\\*(?:\r?\n|\r) ?\\*.*?Copyright[\\S\\s]*?\\*/");
    File currentDirectory = new File(path);
    File[] files = currentDirectory.listFiles();

    if (files == null) {
        return;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        recursivelyGetFilesAndRemoveHeaders(file.getAbsolutePath());
      } else {
        String filePath = file.getAbsolutePath();
        String fileContents = readEntireFile(filePath);
        String updatedContents = removeMatchingText(fileContents, copyrightHeader);
        if (fileContents != updatedContents) {
          saveFileToDisk(filePath, updatedContents);
        }
      }
    }
  }

  public static void main(String args[]) {
    String rootPath = System.getProperty("user.dir");
    recursivelyGetFilesAndRemoveHeaders(rootPath);
  }
}
