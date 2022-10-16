package externaltest;

import common.Program;
import common.TestResult;
import common.container.Element;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static common.TestResult.*;

public class ExternalTestProgram implements Program<Element> {
  private BufferedReader error;
  private BufferedReader op;

  final String filePath;
  final String programPath;

  public ExternalTestProgram(String filePath, String programPath) {
    this.filePath = filePath;

    this.programPath = programPath;
  }

  @Override
  public TestResult test(List<Element> input) {

    StringBuilder inData = new StringBuilder();
    for (Element element : input) {
      inData.append(element.toString());
    }

    try {
      FileWriter fw = new FileWriter(filePath);
      fw.write(inData.toString());
      fw.close();

      final Runtime re = Runtime.getRuntime();

      final List<String> actualArgs = new ArrayList<>();
      actualArgs.add(0, "java");
      actualArgs.add(1, "-jar");
      actualArgs.add(2, programPath);
      actualArgs.add(3, filePath);

      final Process command = re.exec(actualArgs.toArray(new String[0]));

      // Wait for the application to Finish
      command.waitFor();
      int exitCode = command.exitValue();

      if(exitCode == -1 || exitCode == 255){
        return FAIL;
      } else if (exitCode == -2 || exitCode == 254)
        throw new FileNotFoundException();
    } catch (IOException ex) {
      ex.printStackTrace();
      return FAIL;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return FAIL;
    }

    return PASS;
  }
}
