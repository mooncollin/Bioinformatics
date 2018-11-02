package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import utils.Bioinformatics;

public class CalculationController
{
	private String data;
	private String functionName;
	private String type;
	private int readingFrame;
	
	@FXML
	private TextArea result;
	
	public CalculationController(String data, String functionName, String type, int readingFrame)
	{
		this.data = data;
		this.functionName = functionName;
		this.type = type;
		this.readingFrame = readingFrame;
	}

	public void initialize()
	{
		result.setText(getCalculation());
	}
	
	private String getCalculation()
	{
		String calculation = "";
		if(type.equals(MainController.DNA_MODE))
		{
			if(functionName.equals(MainController.COMPLIMENT))
				calculation = Bioinformatics.dnaSequenceCompliment(data);
			else if(functionName.equals(MainController.REVERSE_COMPLIMENT))
				calculation = Bioinformatics.dnaReverseCompliment(data);
			else if(functionName.equals(MainController.PROTEIN_CONVERSION))
				calculation = Bioinformatics.dnaSequenceToAminoAcids(data, readingFrame);
			else if(functionName.equals(MainController.DNA_TO_RNA))
				calculation = Bioinformatics.dnaToRna(data);
		}
		else if(type.equals(MainController.RNA_MODE))
		{
			if(functionName.equals(MainController.COMPLIMENT))
				calculation = Bioinformatics.rnaSequenceCompliment(data);
			else if(functionName.equals(MainController.REVERSE_COMPLIMENT))
				calculation = Bioinformatics.rnaReverseCompliment(data);
			else if(functionName.equals(MainController.PROTEIN_CONVERSION))
				calculation = Bioinformatics.rnaSequenceToAminoAcids(data, readingFrame);
		}
		else if(type.equals(MainController.PROTEIN_MODE))
		{
			if(functionName.equals(MainController.TO_THREE_LETTER))
				calculation = Bioinformatics.expandedAminoAcidSequence(data);
		}
		
		return calculation;
	}
}
