package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	
	public static void setTextFieldInteger(TextField textField){
		
		textField.textProperty().addListener((obsValue, oldValue, newValue) -> {
			if(newValue != null && !newValue.matches("\\d*")) {
				textField.setText(oldValue);
			}
		});
	}
	
	public static void setTextFieldMaxLength(TextField textField, int max) {
		textField.textProperty().addListener((obsValue, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > 30) {
				textField.setText(oldValue);
			}
		});	
	}
	
	public static void setTextFieldDouble(TextField textField) {
		textField.textProperty().addListener((obsValue, oldValue, newValue) -> {
			if(newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				textField.setText(oldValue);
			}
		});	
	}

}
