package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exception.ValidationException;
import model.service.SellerService;

public class SellerFormController extends Observable  implements Initializable {
	
	private Seller seller;
	private SellerService sellerService;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker dpBirthDate;
	@FXML
	private TextField txtBaseSalary;
	@FXML
	private Label labelErrorName;
	@FXML
	private Label labelErrorEmail;
	@FXML
	private Label labelErrorBirthDate;
	@FXML
	private Label labelErrorBaseSalary;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(seller == null) {
			throw new IllegalStateException("Seller was null");
		}
		if(sellerService == null) {
			throw new IllegalStateException("SellerService was null");
		}
		try {
			getFormData();
			sellerService.saveOrUpdate(seller);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
			
		} catch (ValidationException e) {
			setErrorsMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		} 
		
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : this.dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	private void getFormData() {
		ValidationException exception = new ValidationException("Validation error");
		seller.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText() == null || txtName.getText() == "") {
			exception.addError("Name", "Field can't be empty");
		}
		seller.setName(txtName.getText());
		
		if(!exception.getErrors().isEmpty()) {
			throw exception;
		}
	}
	
	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();
		
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		if(seller == null) {
			throw new IllegalStateException("Seller was null");
		}
		txtId.setText(String.valueOf(this.seller.getId()));
		txtName.setText(this.seller.getName());
		txtEmail.setText(this.seller.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", this.seller.getBaseSalary()));
		if(this.seller.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDateTime.ofInstant(this.seller.getBirthDate().toInstant(), ZoneId.systemDefault()).toLocalDate());
		}

	}
	
	private void setErrorsMessages(Map<String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if(fields.contains("Name")) {
			labelErrorName.setText(errors.get("Name"));
		}
		
	}
}
