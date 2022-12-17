package FileSearcher.Display;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class FileTableView extends TableView<FileData> {

    public FileTableView(ObservableList<FileData> list) {
        setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        setPadding(new Insets(0, 0, 0, 0));
        setItems(list);
        setup();
    }

    private void setup() {
        ObservableList<FileData> items = getItems();
        TableColumn<FileData, String> name = new TableColumn<>("File Name");
        name.setCellValueFactory(new PropertyValueFactory<>(items.get(0).name().get()));
        TableColumn<FileData, String> path = new TableColumn<>("File Path");
        path.setCellValueFactory(new PropertyValueFactory<>(items.get(0).path().get()));
        getColumns().setAll(name, path);
    }

}