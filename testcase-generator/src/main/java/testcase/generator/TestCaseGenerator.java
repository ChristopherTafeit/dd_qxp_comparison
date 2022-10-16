package testcase.generator;

import com.google.gson.Gson;
import common.container.Element;
import common.container.JsonTest;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


@Slf4j
public class TestCaseGenerator {

    final private Gson gson;
    final private String filePath;
    final private int casesLimit;
    final private int failIDsAmount;

    TestCaseGenerator(int casesLimit, String filePath, int failIDsAmount) {
        log.trace("------- welcome to the TestCase-Generator test --------");

        this.filePath = filePath;
        this.casesLimit = casesLimit;
        this.failIDsAmount = failIDsAmount;
        gson = new Gson();
    }

    public static void main(String[] args) {
        if(args.length != 3){
            System.out.println("java -jar testcase-generator.java <amountOfTestcases> <generatorOutputPath> <amountOfFailIds>");
            return;
        }

        int amountOfTestcases = Integer.parseInt(args[0]);
        String generatorOutputPath = args[1];
        int amountOfFailIds = Integer.parseInt(args[2]);


        TestCaseGenerator tcg = new TestCaseGenerator(amountOfTestcases,
                generatorOutputPath,
                amountOfFailIds);
        //tcg.generate();
        tcg.generateTestSet(20, "test_1_", amountOfTestcases);
    }

    public void generateTestSet(int setSize, String testPrefix, int elemSize) {
        for (int i = 1; i <= setSize; i++) {
            generate(elemSize, failIDsAmount, filePath+testPrefix+i+".json");
        }
    }

    public void generate(){
        this.generate(this.casesLimit,this.failIDsAmount, this.filePath);
    }


    public void generate(int cases, int failIDsSize, String filePath, String filePathInput)
    {
        JsonTest jsonTest = new JsonTest();

        Random random = new Random();

        log.trace("FACTS of the random generator");
        log.trace("Total Cases: " + cases);
        log.trace("FailID Size: " + failIDsSize);

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePathInput));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //create elements
        try {

            for (int i = 0; i < cases; i++) {
                //Element a = new Element(i, "random_text");
                Element a = new Element(i, br.readLine());
                jsonTest.getElems().add(a);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<Integer> tmpFailIds = new ArrayList<>();
        for (int i = 0; i < failIDsSize; i++) {
            int tmpId = random.nextInt(cases - 1);

            //check if this id is already in the fail id list
            while (tmpFailIds.contains(tmpId)) {
                tmpId = random.nextInt(cases - 1);
            }
            tmpFailIds.add(tmpId);
        }
        //sort the fail ids
        Collections.sort(tmpFailIds);
        jsonTest.getFailIds().addAll(tmpFailIds);

        try {
            File file = new File(filePath);
            Writer writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(jsonTest, JsonTest.class, writer);
            writer.close();
        } catch (IOException e) {
            log.error("IO Exception in Creating the file for the generator.TestCaseGenerator output: " + e.getMessage());
        }
    }

    public void generate(int cases, int failIDsSize,  String filePath) {
        JsonTest jsonTest = new JsonTest();

        Random random = new Random();

        log.trace("FACTS of the random generator");
        log.trace("Total Cases: " + cases);
        log.trace("FailID Size: " + failIDsSize);

        //create elements
        //try {

            for (int i = 0; i < cases; i++) {
                Element a = new Element(i, "random_text");
                //Element a = new Element(i, br.readLine());
                jsonTest.getElems().add(a);
            }
        /*}
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }*/

        List<Integer> tmp_failIds = new ArrayList<>();
        for (int i = 0; i < failIDsSize; i++) {
            int tmpId = random.nextInt(cases - 1);

            //check if this id is already in the fail id list
            while (tmp_failIds.contains(tmpId)) {
                tmpId = random.nextInt(cases - 1);
            }
            tmp_failIds.add(tmpId);
        }
        //sort the fail ids
        Collections.sort(tmp_failIds);
        jsonTest.getFailIds().addAll(tmp_failIds);

        try {
            File file = new File(filePath);
            Writer writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(jsonTest, JsonTest.class, writer);
            writer.close();
        } catch (IOException e) {
            log.error("IO Exception in Creating the file for the generator.TestCaseGenerator output: " + e.getMessage());
        }
    }


}
