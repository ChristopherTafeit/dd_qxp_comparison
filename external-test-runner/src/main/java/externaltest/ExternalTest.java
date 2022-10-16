package externaltest;

import com.google.gson.Gson;
import common.Program;
import common.algorithm.DDAlgorithmExternal;
import common.algorithm.QXPAlgorithmExternal;
import common.container.Element;
import common.container.JsonTest;
import common.utils.Timer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
public class ExternalTest {

    public static void main(String[] args) {

        String testProgram = args[0];
        String testInputFile = args[1];
        String resultDD = "result_dd_"+System.currentTimeMillis()+".json";
        String resultQXP = "result_qxp_" + System.currentTimeMillis() + ".json";

        ExternalTest externalTest = new ExternalTest();
        try
        {

            File source = new File(testInputFile);
            File destDd = new File(resultDD);

            File destQxp = new File(resultQXP);

            //copy file conventional way using Stream
            Files.copy(source.toPath(), destDd.toPath());
            Files.copy(source.toPath(), destQxp.toPath());

            externalTest.testExternalProgramDD(testProgram, resultDD);
            externalTest.testExternalProgramQXP(testProgram, resultQXP);
        }catch(IOException exception)
        {
            log.error("An exception occured!", exception);
        }
    }
    public void testExternalProgramDD(String testProgram, String filePath) throws FileNotFoundException {

        Gson gson = new Gson();
        JsonTest test = gson.fromJson(new FileReader(filePath), JsonTest.class);

        test.generateElements();

        List<Element> elements = test.getElems();

        Program<Element> externalTestProgram = new ExternalTestProgram(filePath, testProgram);

        DDAlgorithmExternal dd = new DDAlgorithmExternal(externalTestProgram);

        //SET TIMER
        Timer ddTimer = new Timer();
        ddTimer.startTimer();

        dd.startDDAlgorithm(elements);

        ddTimer.endTimer();

        System.out.println("External DD, ms spent: " + ddTimer.getDuration());
    }

    public void testExternalProgramQXP(String testProgram, String filePath)throws FileNotFoundException {

        Gson gson = new Gson();

        JsonTest test = gson.fromJson(new FileReader(filePath), JsonTest.class);

        test.generateElements();

        List<Element> elements = test.getElems();

        Program<Element> externalTestProgram = new ExternalTestProgram(filePath, testProgram);

        QXPAlgorithmExternal qxp = new QXPAlgorithmExternal(externalTestProgram);

        //SET TIMER
        Timer qxpTimer = new Timer();
        qxpTimer.startTimer();

        qxp.qxStarter(elements, List.of());

        qxpTimer.endTimer();

        System.out.println("External QXP, ms spent: " + qxpTimer.getDuration());

    }
}
