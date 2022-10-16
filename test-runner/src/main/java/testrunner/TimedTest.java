package testrunner;


import com.google.gson.Gson;
import common.algorithm.DDAlgorithm;
import common.algorithm.QXPAlgorithm;
import common.container.Element;
import common.container.JsonTest;
import common.utils.Timer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;


@Slf4j
public class TimedTest {

    private static String fileName;

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("java -jar test-runner.jar <testCaseDirectory>");
            return;
        }

        File dir = new File(args[0]);
        File[] testInputs = Objects.requireNonNull(dir.listFiles());

        for(File file: testInputs) {
            TimedTest timedTest = new TimedTest();
            timedTest.runTimedTest(file);
        }

    }

    @SneakyThrows
    public void runTimedTest(File inputFile)
    {
        Timer ddTimer = new Timer();
        Timer qxpTimer = new Timer();
        Gson gson = new Gson();

        JsonTest test = gson.fromJson(new FileReader(inputFile.getAbsolutePath()), JsonTest.class);
        test.generateElements();
        List<Element> elements = test.getElems();
        TestProgram t = new TestProgram(test);

        DDAlgorithm ddAlgorithm = new DDAlgorithm(t);
        QXPAlgorithm qxpAlgorithm = new QXPAlgorithm(t);

        t.setDd(false);

        ddTimer.startTimer();
        List<Element> solutionDD = ddAlgorithm.startDDAlgorithm(elements);
        ddTimer.endTimer();

        t.setDd(false);

        qxpTimer.startTimer();
        List<Element> solutionQXP = qxpAlgorithm.qxStarter(elements, List.of());
        qxpTimer.endTimer();

        System.out.println("DD: Contains all error causing elements: " +solutionDD.containsAll(test.getFailElems()));
        System.out.println("QXP: Contains all error causing elements: " + solutionQXP.containsAll(test.getFailElems()));
        System.out.println("Test name: " + inputFile.getName() + " DD=" + ddTimer.getDuration() + " QXP=" + qxpTimer.getDuration());
    }
}
