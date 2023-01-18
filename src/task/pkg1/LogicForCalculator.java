/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Joshua
 */
public class LogicForCalculator {

    public List<Operation> processFile(File fileToProcess) {

        String Line;
        String Text = "";
        boolean MasSimbolos = true;
        String[] allOperations = null;
        List<String> allOperationsList = null;
         List<Operation> operations = new ArrayList<Operation>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(fileToProcess));
            while ((Line = br.readLine()) != null) {
                if (Line.equals("=")) {
                    break;
                }

                if (!Text.equals("")) {
                    Text = Text + "\n";
                }
                if (Text.equals("=")) {
                    break;
                }

                Text = Text + Line;
            }

            allOperations = Text.split("\n");
            allOperationsList = Arrays.asList(allOperations);
            if (!allOperationsList.isEmpty()) {
                operations= processOperations(allOperationsList);

            }

        } catch (IOException ioe) {
            System.out.println("no existe archivo");
            return null;

        }
        return operations;
    }

    private List<Operation> processOperations(List<String> allOperationsList) {
        /* All posible operations  based in simbol*/

        List<Operation> operations = new ArrayList<Operation>();
        try {
            for (String operation : allOperationsList) {
                Boolean operationAdded = false;
                if (operation.contains("+")) {
                    String[] operationString = operation.split("\\+");
                    List<String> operationFormated = Arrays.asList(operationString);
                    operations.add(executeOperation(operationFormated, 1));
                    operationAdded = true;
                }
                if (operation.contains("-")) {
                    String[] operationString = operation.split("\\-");
                    List<String> operationFormated = Arrays.asList(operationString);
                    operations.add(executeOperation(operationFormated, 2));
                    operationAdded = true;
                }
                if (operation.contains("*")) {
                    String[] operationString = operation.split("\\*");
                    List<String> operationFormated = Arrays.asList(operationString);
                    operations.add(executeOperation(operationFormated, 3));
                    operationAdded = true;
                }
                if (operation.contains("/")) {
                    String[] operationString = operation.split("/");
                    List<String> operationFormated = Arrays.asList(operationString);
                    operations.add(executeOperation(operationFormated, 4));
                    operationAdded = true;
                }
                if (!operationAdded) {

                    operations.add(new Operation("", "", "", "Error"));

                }

            }

           return operations;
        } catch (Exception ex) {
          return null;
        }
       
    }

    private Operation executeOperation(List<String> operationString, int type) {
        boolean validOperation = true;
        String num1 = operationString.get(0).trim();
        String num2 = operationString.get(1).trim();
        double result = 0;
        Operation operation = null;
        if (num1 == null || !isNumeric(num1)) {
            validOperation = false;
        }

        if (num2 == null || !isNumeric(num2)) {
            validOperation = false;
        }

        if (validOperation) {

            switch (type) {
                case 1:
                    result = Integer.parseInt(num1) + Integer.parseInt(num2);
                    operation = new Operation(num1, num2, "+", Double.toString(result));
                    break;

                case 2:
                    result = Integer.parseInt(num1) - Integer.parseInt(num2);
                    operation = new Operation(num1, num2, "-", Double.toString(result));
                    break;

                case 3:
                    result = Integer.parseInt(num1) * Integer.parseInt(num2);
                    operation = new Operation(num1, num2, "*", Double.toString(result));
                    break;

                case 4:
                    result = Double.parseDouble(num1) / Double.parseDouble(num2);
                    operation = new Operation(num1, num2, "/", Double.toString(result));
                    break;
            }

        } else {
            operation = new Operation(num1, num2, "", "Error");
        }
        return operation;
    }

    private  boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
     public void   writeOnFile(String fileName,List<Operation> operationsReady){
        try {
        FileWriter fileWriter = new FileWriter(fileName);
        for(Operation operationResolved :operationsReady){
            if(operationResolved.result.equals("Error")){
                fileWriter.write("Error" +  "\n");
            }else{
                fileWriter.write(operationResolved.number1 +" " + operationResolved.operator +" " +  operationResolved.number2  + " = " + operationResolved.result  +  "\n");
            }  
        }
         fileWriter.close();
        System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
        
    }
   
    
}
