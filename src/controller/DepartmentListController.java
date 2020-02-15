package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import controller.components.EditButton;
import controller.components.RemoveButton;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.service.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener {

	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnName;
	@FXML
	private TableColumn<Department, Department> tableColumnEdit;
	@FXML
	private TableColumn<Department, Department> tableColumnRemove;
	@FXML
	private Button buttonNew;

	private DepartmentService departmentService;
	private ObservableList<Department> obsDepartmentList;

	public void setDepartmentService(DepartmentService service) {
		this.departmentService = service;
	}

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department objDepartment = new Department();
		createDialogForm("/gui/DepartmentForm.fxml", objDepartment, parentStage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(new EditButton<>(this));
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(new RemoveButton<>(this));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (departmentService == null) {
			throw new IllegalArgumentException("Service was null");
		}

		List<Department> list = departmentService.findAll();
		obsDepartmentList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsDepartmentList);
		tableViewDepartment.refresh();
		
	}

	public void createDialogForm(String absoluteName, Department objDepartment, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartmentFormController controller = loader.getController();
			controller.setDepartment(objDepartment);
			controller.setDepartmentService(this.departmentService);
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error lading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void removeEntity(Department department) {
		Optional<ButtonType> result = Alerts.showAlertConfirmation("Confirmation", "Are you sure to delete?");
		if(result.get() == ButtonType.OK) {
			if(this.departmentService == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				this.departmentService.remove(department);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
		
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
