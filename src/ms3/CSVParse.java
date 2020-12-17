package ms3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 *
 * @author vonpc
 */
public class CSVParse {
    public static final String DEFAULT_FILE = "Property_Assessment_Data_2020.csv";
    /**
     * Parses a CSV file containing rows of properties into an Array List. <br>
     * CSV file required columns:<br>
     * <pre>
     *  Account Number: identification number of the property as shown in city records
     *  Suite: the suite of the property
     *  House Number: house number of the property
     *  Street Name: the street name or number the property is on
     *  Neighbourhood ID: unique integer identifier the property is in
     *  Ward: the municipality/city ward that the property is in
     *  Garage: Does the property have a garage? Y-yes, N-no
     *  Longitude: longitude coordinate for the property
     *  Latitude: langitude coordinate for the property
     * </pre>
     * @param fileName the CSV file of properties with the required columns
     * @return an ArrayList of properties
     */
    public static ArrayList parse(String fileName){
        InputStream input;
        
//        use default if fileName is empty
        if("".equals(fileName)){
            input = CSVParse.class.getResourceAsStream(DEFAULT_FILE);
        }
        
        else{
            input = CSVParse.class.getResourceAsStream(fileName);
        }
        if(input == null){
            System.out.println("File was not found within the class directory!");
            System.exit(0);
        }
        ArrayList<Property> properties = new ArrayList();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String readFile = br.readLine();
            readFile = br.readLine(); // skip over row of categories
            while(readFile != null){
                String[] tokenize = readFile.split(",");
                
                int tempAccountNumber = Integer.parseInt(tokenize[0]);
                
                String tempSuite = tokenize[1];
//                Suite is empty
                if("".equals(tempSuite)){
                    tempSuite = " ";
                }
                
                String tempHouseNumber = tokenize[2];
//                House Number is empty
                if("".equals(tempHouseNumber)){
                    tempHouseNumber = " ";
                }
                
                String tempStreetName = tokenize[3];
//                Street Name is empty
                if("".equals(tempStreetName)){
                    tempStreetName = " ";
                }
                
                int tempAssessedValue = Integer.parseInt(tokenize[4]);
                String tempAssessmentClass = tokenize[5];
                
//                Neighbourhood ID is empty
                if("".equals(tokenize[6])){
                    tokenize[6] = "-1";
                }
                int tempNeighbourhoodID = Integer.parseInt(tokenize[6]);
                
                String tempNeighbourhood = tokenize[7];
                String tempWard = tokenize[8];
                char tempGarage = tokenize[9].charAt(0);
                double tempLatitude = Double.parseDouble(tokenize[10]);
                double tempLongitude = Double.parseDouble(tokenize[11]);
                
                Property tempProperty = new Property(tempAccountNumber,
                                                     tempSuite,
                                                     tempHouseNumber,
                                                     tempStreetName,
                                                     tempAssessedValue,
                                                     tempAssessmentClass,
                                                     tempNeighbourhoodID,
                                                     tempNeighbourhood,
                                                     tempWard,
                                                     tempGarage,
                                                     tempLatitude,
                                                     tempLongitude);
                
                properties.add(tempProperty);
                
                readFile = br.readLine();
            }
            
            br.close();
            
        }
        catch (FileNotFoundException fnfe){
            System.out.println(fnfe);
            System.exit(0);
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
        return properties;
    }
}