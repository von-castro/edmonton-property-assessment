package ms3;

import java.text.NumberFormat;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Property implements Comparable<Property>{
    /**
     * Constructor to initialize Property
     */
    public Property() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Enum to enumerate the property's field and obtain the value for the chosen
     * enumeration
     * 
     * <pre>
     * (0) Account Number
     * (1) Suite Number
     * (2) House Number
     * (3) Street Name
     * (4) Assessed Value
     * (5) Assessment Class
     * (6) Neighbourhood ID
     * (7) Neighbourhood
     * (8) Ward
     * (9) Garage
     * (10) Latitude
     * (11) Longitude
     * </pre>
     */
    public enum PropertyField{
        ACCOUNTNUMBER(0),
        SUITENUMBER(1),
        HOUSENUMBER(2),
        STREETNAME(3),
        ASSESSEDVALUE(4),
        ASSESSMENTCLASS(5),
        NEIGHBOURHOODID(6),
        NEIGHBOURHOOD(7),
        WARD(8),
        GARAGE(9),
        LATITUDE(10),
        LONGITUDE(11);
        
        private int value;
        
        private PropertyField(int val){
            this.value = val;
        }
        
        public int getValue(){
            return this.value;
        }
        
        
    }
    
    private int accountNumber;
    private String suiteNumber;
    private String houseNumber;
    private String streetName;
    private int assessedValue;
    private String assessmentClass;
    private int neighbourhoodID;
    private String neighbourhood;
    private String ward;
    private char garage;
    private double latitude;
    private double longitude;
    private final String address;
    private String dollarFormat;
    
//      Constructor
    Property(int accountNumber,
                            String suiteNumber,
                            String houseNumber,
                            String streetName,
                            int assessedValue,
                            String assessmentClass,
                            int neighbourhoodID,
                            String neighbourhood,
                            String ward,
                            char garage,
                            double latitude,
                            double longitude){
        this.accountNumber = accountNumber;
        this.suiteNumber = suiteNumber;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.assessedValue = assessedValue;
        this.assessmentClass = assessmentClass;
        this.neighbourhoodID = neighbourhoodID;
        this.neighbourhood = neighbourhood;
        this.ward = ward;
        this.garage = garage;
        this.latitude = latitude;
        this.longitude = longitude;   
        this.address = getAddress();
        this.dollarFormat = getDollarFormat();
    }
    /**
     * Getter method for account number
     * @return account number of property
     */
    public int getAccountNumber(){return this.accountNumber;}
    /**
     * getter method for suite number
     * @return suite number of property
     */
    public String getSuiteNumber(){return this.suiteNumber;}
    /**
     * getter method for house number
     * @return house number of property
     */
    public String getHouseNumber(){return this.houseNumber;}
    /**
     * getter method for street name
     * @return street name of property
     */
    public String getStreetName(){return this.streetName;}
    /**
     * getter method for assessed value
     * @return assessed value of property
     */
    public int getAssessedValue(){return this.assessedValue;} 
    /**
     * getter method for assessment class
     * @return assessment class of property
     */
    public String getAssessmentClass(){return this.assessmentClass;}  
    /**
     * getter method for neighbourhood id
     * @return neighbourhood id of property
     */
    public int getNeighbourhoodID(){return this.neighbourhoodID;}   
    /**
     * getter method for neighbourhood
     * @return neighbourhood of property
     */
    public String getNeighbourhood(){return this.neighbourhood;}
    /**
     * getter method for ward
     * @return ward of property
     */
    public String getWard(){return this.ward;}
    /**
     * getter method for garage
     * @return garage value of property
     */
    public char getGarage(){return this.garage;}
    /**
     * getter method for latitude
     * @return latitude coordinate of property
     */
    public double getLatitude(){return this.latitude;}
    /**
     * getter method for longitude
     * @return longitude coordinate of property
     */
    public double getLongitude(){return this.longitude;}
    
    /**
     * getter method for the property's address
     * @return string representation of property's address
     */
    public String getAddress(){
        StringBuilder address = new StringBuilder();
        
        // there is a value in suite number
        if( !("".equals(this.suiteNumber.trim())) ){
            address.append("Suite ");
            address.append(this.suiteNumber);
            address.append(" ");
        }
        // there is a value in house number
        if( !("".equals(this.houseNumber.trim())) ){
            address.append(this.houseNumber);
            address.append(" ");
        }
        address.append(this.streetName);
        
        return address.toString();
    }
    
    /**
     * Converts property's assessed value to a string representation in currency format
     * @return string representation of asssessed value in currency format
     */
    public String getDollarFormat(){
          NumberFormat format = NumberFormat.getCurrencyInstance();
          return format.format(this.assessedValue).replace(".00", "");
        
    }
    
    /**
     * Returns the value of the field chosen (see enum PropertyField)
     * @param n the enumeration chosen
     * @return the value of given enumeration
     */
    public String getField(int n){
        switch(n){
//            Account Number
            case 0:
                return String.valueOf(this.accountNumber);

//            Suite Number
            case 1:
                return this.suiteNumber;

//            House Number
            case 2:
                return this.houseNumber;
                
//            Street Name    
            case 3:
                return this.streetName;
            
//            Assessed Value
            case 4:
                return String.valueOf(this.assessedValue);
                
//            Assessment Class    
            case 5:
                return this.assessmentClass;
                
//            Neighbourhood ID
            case 6:
                return String.valueOf(this.neighbourhoodID);
                
//            Neighbourhood    
            case 7:
                return this.neighbourhood;

//            Ward
            case 8:
                return this.ward;
            
//            Garage
            case 9:
                return String.valueOf(this.garage);
            
//            Longitude
            case 10:
                return String.valueOf(this.longitude);
            
//            Latitude
            case 11:
                return String.valueOf(this.latitude);
                
            default:
                return "Invalid field";
        }
    }

    @Override
    public String toString(){
        StringBuilder property = new StringBuilder();
        property.append("Account Number: ");
        property.append(this.accountNumber);
        property.append("\nAddress: ");
        property.append(this.houseNumber);
        property.append(" ");
        property.append(this.streetName);
        property.append("\nAssessed Value: $");
        property.append(this.assessedValue);
        property.append("\nAssessment Class: ");
        property.append(this.assessmentClass);
        property.append("\nNeighbourhood: ");
        property.append(this.neighbourhood);
        property.append(" (");
        property.append(this.ward);
        property.append(")");
        property.append("\nGarage: ");
        property.append(this.garage);
        property.append("\nLatitude: ");
        property.append(this.latitude);
        property.append("\tLongitude: ");
        property.append(this.longitude);
        
        return property.toString();
    }
   
    @Override
    public int compareTo(Property prop){
        if(this.assessedValue > prop.assessedValue) return 1;
        else if (this.assessedValue < prop.assessedValue) return -1;
        else return 0;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Property){
            return Integer.toString(this.accountNumber).equals(Integer.toString(((Property) obj).accountNumber));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.accountNumber;
        return hash;
    }
}