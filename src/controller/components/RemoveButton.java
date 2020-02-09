package controller.components;

import controller.DepartmentListController;
import controller.SellerListController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;

public class RemoveButton<S,T> implements Callback<TableColumn<S,T>, TableCell<S,T>> {

	private Object classeControladora; 

	public RemoveButton(Object classeControladora) {
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
					Button button = new Button("remove");
					setGraphic(button);
					setAlignment(Pos.CENTER);
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Object objeto = getTableView().getItems().get(getIndex());
							removeEntity(objeto);
						}

					});
				}
			}
		};
	}
	
	private void removeEntity(Object obj) {
		if (this.classeControladora instanceof DepartmentListController) {
			((DepartmentListController) this.classeControladora).
			removeEntity((Department) obj);
		}
		
		if (this.classeControladora instanceof SellerListController) {
			((SellerListController) this.classeControladora).
			removeEntity((Seller) obj);
		}

	}

}
