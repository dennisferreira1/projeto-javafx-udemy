package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.service.DepartmentService;

public class DepartmentListController implements Initializable {
	
	@FXML
	TableView<Department> tableViewDepartment;
	@FXML
	TableColumn<Department, Integer> tableColumnId;
	@FXML
	TableColumn<Department, String> tableColumnName;
	@FXML
	Button buttonNew;
	
	private DepartmentService departmentService;
	
	private ObservableList<Department> obsDepartmentList;
	
	public void setDepartmentService(DepartmentService service) {
		this.departmentService = service;
	}
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}


	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(departmentService == null) {
			throw new IllegalArgumentException("Servie was null");
		}
		
		List<Department> list = departmentService.findAll();
		obsDepartmentList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsDepartmentList);
	}

}
