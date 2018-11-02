package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController
{
	public static final String DNA_MODE = "DNA";
	public static final String RNA_MODE = "RNA";
	public static final String PROTEIN_MODE = "Protein";
	public static final String COMPLIMENT = "Compliment";
	public static final String REVERSE_COMPLIMENT = "Reverse Compliment";
	public static final String PROTEIN_CONVERSION = "Protein Conversion";
	public static final String TO_THREE_LETTER = "Three Letter Conversion";
	public static final String DNA_TO_RNA = "Convert to RNA";
	private static final String[] DNA_FUNCTIONS_NAMES = {DNA_TO_RNA, COMPLIMENT, REVERSE_COMPLIMENT, PROTEIN_CONVERSION};
	private static final String[] RNA_FUNCTION_NAMES = {COMPLIMENT, REVERSE_COMPLIMENT, PROTEIN_CONVERSION};
	private static final String[] PROTEIN_FUNCTION_NAMES = {TO_THREE_LETTER};
	
	private Stage aboutStage;
	private Stage calculationStage;
	private boolean readingFrameValid;
	private boolean validOperation;
	
	@FXML
	private TextArea sequenceTextArea;
	
	@FXML
	private ToggleGroup sequenceSelection;
	
	@FXML
	private MenuButton operationsButton;
	
	@FXML
	private HBox readingFrameGroup;
	
	@FXML
	private TextField readingFrame;
	
	@FXML
	private Button calculateButton;
	
	public MainController()
	{
		aboutStage = new Stage();
		aboutStage.setResizable(false);
		aboutStage.setTitle("About");
		Scene scene;
		try
		{
			scene = Main.loadScene(MainController.class, null, Main.ABOUT_FXML);
		}
		catch(IOException ex)
		{
			Main.alert(Main.DEFAULT_ERROR_MESSAGE, AlertType.ERROR);
			return;
		}
		aboutStage.setScene(scene);
		
		calculationStage = new Stage();
		calculationStage.setResizable(false);
		calculationStage.setTitle("Results");
		readingFrameValid = false;
		validOperation = false;
	}
	
	@FXML
	public void initialize()
	{
		toggleReadingFrameGroup(false);
	}
	
	@FXML
	public void clearSequenceMenuItemClick(ActionEvent event)
	{
		sequenceTextArea.clear();
		calculateButtonCheck();
	}
	
	@FXML
	public void loadMenuItemClick(ActionEvent event)
	{
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open sequence data");
		File selectedFile = chooser.showOpenDialog(null);
		if(selectedFile == null)
			return;
		
		byte[] data;
		try
		{
			data = Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath()));
		}
		catch(IOException ex)
		{
			Main.alert("Error reading file: " + selectedFile.getName(), AlertType.ERROR);
			return;
		}
		
		sequenceTextArea.setText(new String(data));
	}
	
	@FXML
	public void aboutMenuItemClick(ActionEvent event)
	{
		aboutStage.show();
	}
	
	@FXML
	public void selectOperation(ActionEvent event)
	{
		MenuItem itemSelected = (MenuItem) event.getSource();
		operationsButton.setText(itemSelected.getText());
		if(operationsButton.getText().equals(PROTEIN_CONVERSION))
			toggleReadingFrameGroup(true);
		else
			toggleReadingFrameGroup(false);
		
		validOperation = true;
		calculateButtonCheck();
	}
	
	@FXML
	public void calculateButtonClick(ActionEvent event)
	{	
		String type = getType();
		int readingFrameAmount = 0;
		if(operationsButton.getText().equals(PROTEIN_CONVERSION))
			readingFrameAmount = Integer.parseInt(readingFrame.getText());
		
		CalculationController controller = new CalculationController(sequenceTextArea.getText(), operationsButton.getText(), type, readingFrameAmount);
		Scene scene;
		try
		{
			scene = Main.loadScene(MainController.class, controller, Main.CALCULATION_FXML);
		}
		catch(IOException ex)
		{
			Main.alert(Main.DEFAULT_ERROR_MESSAGE, AlertType.ERROR);
			return;
		}
		calculationStage.setScene(scene);
		calculationStage.show();
	}
	
	@FXML
	public void switchMode(ActionEvent event)
	{
		validOperation = false;
		calculateButtonCheck();
		operationsButton.setText("Select Operation");
		RadioButton radioButton = (RadioButton) event.getSource();
		String radioText = radioButton.getText();
		String[] requiredOperationNames = null;
		if(radioText.equals(DNA_MODE))
			requiredOperationNames = DNA_FUNCTIONS_NAMES;
		else if(radioText.equals(RNA_MODE))
			requiredOperationNames = RNA_FUNCTION_NAMES;
		else if(radioText.equals(PROTEIN_MODE))
			requiredOperationNames = PROTEIN_FUNCTION_NAMES;
		operationsButton.getItems().removeAll(operationsButton.getItems());
		for(String operation : requiredOperationNames)
		{
			MenuItem item = new MenuItem(operation);
			item.setOnAction(this::selectOperation);
			operationsButton.getItems().add(item);
		}
		toggleReadingFrameGroup(false);
	}
	
	@FXML
	public void checkReadingFrameTextField(KeyEvent event)
	{
		try
		{
			Integer.parseInt(readingFrame.getText());
			readingFrameValid = true;
		}
		catch(NumberFormatException ex)
		{
			readingFrameValid = false;
		}
		calculateButtonCheck();
	}
	
	@FXML
	public void checkSequenceTextArea(KeyEvent event)
	{
		calculateButtonCheck();
	}
	
	private void calculateButtonCheck()
	{
		if(sequenceTextArea.getText().isEmpty())
			calculateButton.setDisable(true);
		else if(operationsButton.getText().equals(PROTEIN_CONVERSION) && !readingFrameValid)
			calculateButton.setDisable(true);
		else if(!validOperation)
			calculateButton.setDisable(true);
		else
			calculateButton.setDisable(false);
	}
	
	private String getType()
	{
		return ((RadioButton) sequenceSelection.getSelectedToggle()).getText();
	}
	
	private void toggleReadingFrameGroup(boolean option)
	{
		readingFrameGroup.setVisible(option);
		readingFrameGroup.getChildren().forEach(n -> n.setManaged(option));
		readingFrameGroup.setManaged(option);
	}
}
