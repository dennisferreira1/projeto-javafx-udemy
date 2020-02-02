package controller.components;

import controller.DepartmentListController;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.entities.Department;

public class EditButton<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	private Object classeControladora; 

	public EditButton(Object classeControladora) {
		this.classeControladora = classeControladora;
	}

	@Override
	public TableCell<S, T> call(TableColumn<S, T> arg0) {
		
		return new TableCell<S, T>(){
			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setGraphic(null);
				}else {
					Button button = new Button("edit");
					setGraphic(button);
					setAlignment(Pos.CENTER);
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Object objeto = getTableView().getItems().get(getIndex());
							createDialogForm(objeto, event);
						}

					});
				}
			}
		};
	}
	
	private void createDialogForm(Object objDepartment, ActionEvent event) {
		if (this.classeControladora instanceof DepartmentListController) {
			((DepartmentListController) this.classeControladora).
				createDialogForm("/gui/DepartmentForm.fxml",(Department) objDepartment, Utils.currentStage(event));
		}

	}

}
