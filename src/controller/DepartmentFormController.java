package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exception.ValidationException;
import model.service.DepartmentService;

public class DepartmentFormController extends Observable  implements Initializable {
	
	private Department department;
	private DepartmentService departmentService;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelError;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(department == null) {
			throw new IllegalStateException("Department was null");
		}
		if(departmentService == null) {
			throw new IllegalStateException("DepartmentService was null");
		}
		try {
			getFormData();
			departmentService.saveOrUpdate(department);
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
		department.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText() == null || txtName.getText().trim().isEmpty()) {
			exception.addError("Name", "Field can't be empty");
		}
		department.setName(txtName.getText());
		
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
	}
	
	public void updateFormData() {
		if(department == null) {
			throw new IllegalStateException("Department was null");
		}
		txtId.setText(String.valueOf(department.getId()));
		txtName.setText(department.getName());
	}
	
	private void setErrorsMessages(Map<String,String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if(fields.contains("Name")) {
			labelError.setText(errors.get("Name"));
		}
		
	}
}
