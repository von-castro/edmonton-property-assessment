/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms3;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
 *
 * @author vonpc
 */
public class MainScreenController implements Initializable {
    /**
     * browse texfield. displays the absolute path of a file
     */
    @FXML private TextField browseTextField;
    
    /**
     * Confirms whether the file chosen is valid
     */
    @FXML private Label loadVerificationLabel;
    
    /**
     * Filter textfield for accountnumber
     */
    @FXML private TextField accountNumberTF;
    
    /**
     * Filter textfield for address
     */
    @FXML private TextField addressTF;
    
    /**
     * Filter textfield for neighbourhood
     */
    @FXML private TextField neighbourhoodTF;
    
    /**
     * filter combobox for assessment class
     */
    @FXML private ComboBox assessmentClassCB;
    
    /**
     * textarea that displays all statistics
     */
    @FXML private TextArea statisticsTA;
    
    /**
     * Tableview that displays property data
     */
    @FXML private TableView<Property> propertyTableView;
    
    /**
     * account number column of propertyTableView
     */
    @FXML private TableColumn<Property, String> accountNumberColumn;
    
    /**
     * address column of propertyTableView
     */
    @FXML private TableColumn<Property, String> addressColumn;
    
    /**
     * assessed value column of propertyTableView
     */
    @FXML private TableColumn<Property, String> assessedValueColumn;
    
    /**
     * assessment class column of propertyTableView
     */
    @FXML private TableColumn<Property, String> assessmentClassColumn;
    
    /**
     * neighbourhood column of propertyTableView
     */
    @FXML private TableColumn<Property, String> neighbourhoodColumn;
    
    /**
     * latitude column of propertyTableView
     */
    @FXML private TableColumn<Property, String> latitudeColumn;
    
    /**
     * longitude column of propertyTableView
     */
    @FXML private TableColumn<Property, String> longitudeColumn;
    
    /**
     * BarChart of the Statistics Summary tab
     */
    @FXML private BarChart propertyBarChart;
    
    /**
     * Chosen csv file to load
     */
    private File CSVFile;
    
    /**
     * all property data of the chosen csv file
     */
    private ObservableList<Property> allData;
    
    /**
     * temporary property data that contains a filtered version of allData
     */
    private ObservableList<Property> data;
    
    /**
     * List of available assessment classes in csv file
     */
    private ObservableList assessmentClasses;
    
    /**
     * ArrayList of all property data in csv file
     */
    private ArrayList<Property> allProperties;
    
    
    /**
     * Open file chooser when browse is clicked. Then display absolute path
     * of chosen file into the browseTextField
     * @param event 
     */
    @FXML
    private void handleBrowseAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File chosenFile = fileChooser.showOpenDialog(node.getScene().getWindow());
        browseTextField.setText(chosenFile.getAbsolutePath());
    }
    
    /**
     * Ensure chosen file is valid. Parse the file, load data onto propertyTableView,
     * set statistics area to display all statistics of chosen csv file
     * @param event 
     */
    @FXML
    private void handleSubmitAction(ActionEvent event){
//        Check if browseTextField contains text
        if(!checkBrowseTextField(browseTextField.getText())){
//            if 'default' is typed, use the default csv file 'Property_Assessment_Data_2020.csv'
            if(browseTextField.getText().equals("default")){
                allProperties = CSVParse.parse("");
                allData = FXCollections.observableArrayList(allProperties);
                data = allData;
                statisticsTA.setText("Statistics of Assesed Values:\n" 
                                    + Statistics.displayAll(allProperties));
                propertyTableView.setItems(data);
                setLoadVerificationLabel("Default used");
                setAssessmentClassCB();
                return;
            }
            
//            Check if the absolute path entered in browseTextField is valid
            CSVFile = new File(browseTextField.getText());
            if(isValid(CSVFile)){
                allProperties = CSVParse.parse(CSVFile.getName());
                allData = FXCollections.observableArrayList(allProperties);
                data = allData;
                statisticsTA.setText("Statistics of Assesed Values:\n" 
                                    + Statistics.displayAll(allProperties));
                propertyTableView.setItems(data);
                setLoadVerificationLabel("Success!");
                setAssessmentClassCB();
                return;
            }
//            Displays invalid file if file is not valid
            else{
                setLoadVerificationLabel("Invalid file");
            }
        }
//        browseTextField is empty, display status
        else{
            setLoadVerificationLabel("Please select a file");
        }
    }
    
    /**
     * Resets the filters and propertyTableView data to display all properties
     */
    @FXML
    private void handleResetButton(){
        accountNumberTF.setText("");
        addressTF.setText("");
        neighbourhoodTF.setText("");
        assessmentClassCB.setValue(null);
        updateStatistics(allData);
        updateTableView(allData);
    }
    
    /**
     * Handles filtering data and display it on propertyTableView
     */
    @FXML
    private void handleSearchButton(){
        filterAccountNumber();
        filterAddress();
        filterNeighbourhoodOnly();
        filterAssessmentClassOnly();
        filterAssessmentClassAndNeighbourhood();
    }
    
    /**
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableView();
        loadBarChart();
    }
    
    /**
     * Checks if browseTextField is empty
     * 
     * TODO: ensure csv file has the correct format (columns)
     * @param text
     * @return 
     */
    public boolean checkBrowseTextField(String text){
        return text.isEmpty();
    }
    
    /**
     * Populates the assessmentClassCB with assessment class available in csv file
     */
    private void setAssessmentClassCB(){
        getAssessmentClasses();
        assessmentClassCB.setItems(assessmentClasses);
    }
    
    /**
     * Create columns for propertyTableView and set its property value
     */
    private void setTableView(){
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("accountNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("address"));
        assessedValueColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("dollarFormat"));
        assessmentClassColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("assessmentClass"));
        neighbourhoodColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("neighbourhood"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<Property, String>("longitude"));
    }
    
    /**
     * Sets the loadVerificationLabel to display correct message
     * @param text 
     */
    private void setLoadVerificationLabel(String text){
        loadVerificationLabel.setText(text);
        if(!loadVerificationLabel.isVisible()){
            loadVerificationLabel.setVisible(true);
        }
    }
    
    /**
     * Verify that a file exists and is the correct file extension(csv)
     * @param file
     * @return 
     */
    private boolean isValid(File file){
        return (CSVFile.exists() && "csv".equals(getFileExtension(CSVFile)));
    }
    
    /**
     * Grabs the file extension of the file
     * @param file
     * @return 
     */
    private String getFileExtension(File file){
        String fileName = CSVFile.getName();
        if(fileName.lastIndexOf(".") != -1 &&
                fileName.lastIndexOf(".") != 0){
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }
    
    /**
     * Updates the propertyTableView with the modified data
     * @param newData 
     */
    private void updateTableView(ObservableList<Property> newData){
        data = newData;
        propertyTableView.setItems(data);
    }
    
    /**
     * Grabs the assessment classes available for the chosen csv file
     */
    private void getAssessmentClasses(){
        assessmentClasses = FXCollections.observableArrayList();
        assessmentClasses.add(null);
        allData.forEach((Property)->{
            if(!(assessmentClasses.contains(Property.getAssessmentClass()))){
                assessmentClasses.add(Property.getAssessmentClass());
            }
        });
    }
    
    /**
     * Updates the statistics text area with updated property data
     * @param updatedStat 
     */
    private void updateStatistics(ObservableList<Property> updatedStat){
        ArrayList<Property> tempList = new ArrayList<Property>(updatedStat);
        if(tempList.size() < 2){
            statisticsTA.setText("");
            return;
        }
        statisticsTA.setText("Statistics of Assesed Values:\n" 
                                    + Statistics.displayAll(tempList));
    }
    
    /**
     * filter the property data by account number then update statistics text
     * area and the propertyTableView
     */
    private void filterAccountNumber(){
        ObservableList<Property> filteredData = FXCollections.observableArrayList();
        if(!accountNumberTF.getText().isEmpty()){
            allData.forEach((Property) -> {
                if(Integer.toString(Property.getAccountNumber()).equals(accountNumberTF.getText())){
                    filteredData.add(Property);
                    updateTableView(filteredData);
                    return;
                }
            });
            updateStatistics(filteredData);
            updateTableView(filteredData);
            return;
        }
    }
    
    /**
     * filter the property data by address then update statistics text
     * area and the propertyTableView
     */
    private void filterAddress(){
        ObservableList<Property> filteredData = FXCollections.observableArrayList();
        
        if(!addressTF.getText().isEmpty()){
            allData.forEach((Property)->{
                if(Property.getAddress().equalsIgnoreCase(addressTF.getText())){
                   filteredData.add(Property);
                   updateTableView(filteredData);
                   return;
                } 
            });
            updateStatistics(filteredData);
            updateTableView(filteredData);
            return;
        }
    }
    
    /**
     * filter the property data by neighbourhood then update statistics text
     * area and the propertyTableView
     */
    private void filterNeighbourhoodOnly(){
        ObservableList<Property> filteredData = FXCollections.observableArrayList();
        if(!neighbourhoodTF.getText().isEmpty() 
                && assessmentClassCB.getSelectionModel().getSelectedItem() == null){
            allData.forEach((Property)->{
                if(neighbourhoodTF.getText().equalsIgnoreCase(Property.getNeighbourhood())){
                    filteredData.add(Property);
                } 
            });
            updateStatistics(filteredData);
            updateTableView(filteredData);
            return;
        }
    }
    
    /**
     * filter the property data by assessment class then update statistics text
     * area and the propertyTableView
     */
    private void filterAssessmentClassOnly(){
        ObservableList<Property> filteredData = FXCollections.observableArrayList();
        if(assessmentClassCB.getSelectionModel().getSelectedItem() != null 
                && neighbourhoodTF.getText().isEmpty()){
            allData.forEach((Property)->{
                if(assessmentClassCB.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase(Property.getAssessmentClass())){
                    filteredData.add(Property);
                } 
            });
            updateStatistics(filteredData);
            updateTableView(filteredData);
            return;
        }
    }
    
    /**
     * filter the property data by assessment class and neighbourhood then update 
     * statistics text area and the propertyTableView
     */
    private void filterAssessmentClassAndNeighbourhood(){
        ObservableList<Property> filteredData = FXCollections.observableArrayList();
        if(assessmentClassCB.getSelectionModel().getSelectedItem() != null 
                && !neighbourhoodTF.getText().isEmpty()){
            allData.forEach((Property)->{
                if(assessmentClassCB.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase(Property.getAssessmentClass()) &&
                    neighbourhoodTF.getText().equalsIgnoreCase(Property.getNeighbourhood())){
                    filteredData.add(Property);
                }
            });
            updateStatistics(filteredData);
            updateTableView(filteredData);
        }
    }
    
    /**
     * load the bar chart data to display correct labels
     */
    @FXML
    private void loadBarChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Assessment Class");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Assessed Value");
        
        propertyBarChart = new BarChart(xAxis,yAxis);
        
        XYChart.Series dataChart = new XYChart.Series();
        dataChart.setName("test");
        
        dataChart.getData().add(new XYChart.Data("Other", 1000));
        dataChart.getData().add(new XYChart.Data("Test", 5000));
        
        propertyBarChart.getData().add(dataChart);
        
    }
}
