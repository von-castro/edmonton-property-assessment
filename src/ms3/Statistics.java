/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author vonpc
 */
public class Statistics {
    
    /**
     * Perform statistical calculations and display results
     * @param data List of property data to perform statistical calculations on
     * @return string representations of the results of statistical calculations
     */
    public static String displayAll(ArrayList<Property> data){
        StringBuilder statistics = new StringBuilder();
        
        statistics.append("\nNumber of Properties: ");
        statistics.append(Statistics.N(data));
        statistics.append("\nMin: ");
        statistics.append(getDollarFormat(Statistics.Min(data)));
        statistics.append("\nMax: ");
        statistics.append(getDollarFormat(Statistics.Max(data)));
        statistics.append("\nRange: ");
        statistics.append(getDollarFormat(Statistics.Range(data)));
        statistics.append("\nMean: ");
        statistics.append(getDollarFormat(Statistics.Mean(data)));
        statistics.append("\nMedian: ");
        statistics.append(getDollarFormat(Statistics.Median(data)));
        statistics.append("\nStandard deviation: ");
        statistics.append(getDollarFormat(Statistics.SD(data)));
        
        
        return statistics.toString();
    }
    
    /**
     * Convert integer value to a string representation in currency format
     * @param value integer value to be converted to a string representation in currency format
     * @return string representation of value in currency format
     */
    private static String getDollarFormat(int value){
          
          NumberFormat format = NumberFormat.getCurrencyInstance();
          return format.format(value).replace(".00", "");
        
    }
    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return string representation of the results of statistical calculations
     */
    public static String displayAll(ArrayList<Property> data, int fieldType, String fieldValue) {
        StringBuilder statistics = new StringBuilder();
        statistics.append("n = ");
        statistics.append(Statistics.N(data, fieldType, fieldValue));
        statistics.append("\nMin = ");
        statistics.append(Statistics.Min(data, fieldType, fieldValue));
        statistics.append("\nMax = ");
        statistics.append(Statistics.Max(data, fieldType, fieldValue));
        statistics.append("\nRange = ");
        statistics.append(Statistics.Range(data, fieldType, fieldValue));
        statistics.append("\nMean = ");
        statistics.append(Statistics.Mean(data, fieldType, fieldValue));
        statistics.append("\nsd = ");
        statistics.append(Statistics.SD(data, fieldType, fieldValue));
        statistics.append("\nMedian = ");
        statistics.append(Statistics.Median(data, fieldType, fieldValue));
        
        return statistics.toString();
    }
    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param accNumber account number given to filter data
     * @return the property with the given account number. Null if it does not exist
     */
    public static Property getPropertyByAccountNumber(ArrayList<Property> data, String accNumber){
        int accountNumber = Integer.parseInt(accNumber);
        for(Property each : data){
            if( accountNumber == each.getAccountNumber()){
                return each;
            }
        }
        
        return null;
    }
//  ------------------------------------------------------------------------------------  
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return the sample size of the dataset
     */
    public static int N(ArrayList<Property> data){
        return data.size();
    }
    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return the sample size of the filtered dataset
     */
    public static int N(ArrayList<Property> data, int fieldType, String fieldValue){
        int count = 0;
        for(Property each : data){
            if(isEqual(each, fieldType, fieldValue)){
                count += 1;
            }
        }
        return count;
    }
//  ------------------------------------------------------------------------------------     
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return minimum assessed value of given data
     */
    public static int Min(ArrayList<Property> data){
        int min = data.get(0).getAssessedValue();
        for(Property each : data){
            if(min > each.getAssessedValue()){
                min = each.getAssessedValue();
            }
        }
        return min;
    }
    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return the minimum assessed value of the filtered dataset
     */
    public static int Min(ArrayList<Property> data, int fieldType, String fieldValue){
        int getIndex = 0;
        int count = 0;
        
        for(Property each : data){
            if(isEqual(each, fieldType, fieldValue)){
                getIndex = count;
                break;
            }
            count += 1;
        }
        
        int min = data.get(getIndex).getAssessedValue();
        for(Property each : data){
            if(min > each.getAssessedValue() && isEqual(each, fieldType, fieldValue)){
                min = each.getAssessedValue();
            }
        }
        return min;
    }
//  ------------------------------------------------------------------------------------     
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return max assessed value of given dataset
     */
    public static int Max(ArrayList<Property> data){
        int current = data.get(0).getAssessedValue();
        for(Property each : data){
            if(current < each.getAssessedValue()){
                current = each.getAssessedValue();
            }
        }
        return current;
    }
    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return max assessed value of the filtered dataset
     */
    public static int Max(ArrayList<Property> data, int fieldType, String fieldValue){
        int getIndex = 0;
        int count = 0;
        
        for(Property each : data){
            if(isEqual(each, fieldType, fieldValue)){
                getIndex = count;
                break;
            }
            count += 1;
        }
        
        int current = data.get(getIndex).getAssessedValue();
        for(Property each : data){
            if(current < each.getAssessedValue() && isEqual(each, fieldType, fieldValue)){
                current = each.getAssessedValue();
            }
        }
        return current;
    }
//  ------------------------------------------------------------------------------------    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return the range of the given dataset
     */
    public static int Range(ArrayList<Property> data){
        return Statistics.Max(data) - Statistics.Min(data);
    }
    
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return the range of the filtered dataset
     */
    public static int Range(ArrayList<Property> data, int fieldType, String fieldValue){
        return Max(data, fieldType, fieldValue) - Min(data, fieldType, fieldValue);
    }
//  ------------------------------------------------------------------------------------  
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return the mean of the given dataset
     */
    public static int Mean(ArrayList<Property> data){
        double sum = 0;
        for (Property each : data){
            sum += each.getAssessedValue();
        }
        return (int) (sum/data.size());
    }
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return the mean of the filtered dataset
     */
    public static int Mean(ArrayList<Property> data, int fieldType, String fieldValue){
        double sum = 0;
        int count = 0;
        for (Property each : data){
            if(isEqual(each, fieldType, fieldValue)){
                sum += each.getAssessedValue();
                count += 1;
            }
        }
        
        return (int) (sum/count);
    }
//  ------------------------------------------------------------------------------------      
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return the standard deviation of the given dataset
     */
    public static int SD(ArrayList<Property> data){
        double sumDistanceToMean = 0;
        double mean = Statistics.Mean(data);
        for(Property each : data){
            sumDistanceToMean += Math.pow(each.getAssessedValue() - mean, 2);
        }
        
        return (int) Math.sqrt(sumDistanceToMean/(data.size()-1));
    }
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return the standard deviation of the filtered dataset
     */
    public static int SD(ArrayList<Property> data, int fieldType, String fieldValue) {
        double sumDistanceToMean = 0;
        double mean = Mean(data, fieldType, fieldValue);
        
        int count = 0;
        for(Property each : data){
            if(isEqual(each, fieldType, fieldValue)){
                sumDistanceToMean += Math.pow((each.getAssessedValue() - mean), 2);
                count += 1;
            }
        }
        
        return (int) Math.sqrt( (sumDistanceToMean/(count-1)) );
    }
//  ------------------------------------------------------------------------------------      
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @return the median of the given dataset
     */
    public static int Median(ArrayList<Property> data){
        double median;
        Collections.sort(data);
        if(data.size() % 2 == 0){
            median = ((data.get(data.size()/2)).getAssessedValue() + 
                    (data.get(data.size()/2 - 1)).getAssessedValue())/2;
            return (int) median;
        }
        median = data.get(data.size()/2).getAssessedValue();
        return (int) median;
    }
    /**
     * 
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return the median of the filtered dataset
     */
    public static int Median(ArrayList<Property> data, int fieldType, String fieldValue) {
        ArrayList<Property> assessmentClassData = new ArrayList();
        for (Property each : data){
            if(isEqual(each, fieldType, fieldValue)){
                assessmentClassData.add(each);
            }
        }
        
        double median;
        Collections.sort(assessmentClassData);
        if(assessmentClassData.size() % 2 == 0){
            median = ((assessmentClassData.get(assessmentClassData.size()/2)).getAssessedValue() + 
                    (assessmentClassData.get(assessmentClassData.size()/2 - 1)).getAssessedValue())/2;
            return (int) median;
        }
        median = assessmentClassData.get(assessmentClassData.size()/2).getAssessedValue();
        return (int) median;
    }  
//  ------------------------------------------------------------------------------------     
    /**
     *  
     * @param data List of property data to perform statistical calculations on
     * @param fieldType The field chosen as a filter (see Property.PropertyField)
     * @param fieldValue The value given to filter data
     * @return whether the given values of the property are equal
     */
    public static boolean isEqual(Property data, int fieldType, String fieldValue){
        return fieldValue.equals(data.getField(fieldType));
    }
}