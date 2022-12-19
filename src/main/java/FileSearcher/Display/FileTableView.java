package FileSearcher.Display;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class FileTableView extends TableView<FileData> {

    public FileTableView(ObservableList<FileData> list) {
        super(list);
        setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        setPadding(new Insets(0, 0, 0, 0));
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setup();
    }

    private void setup() {
        List<TableColumn<FileData, String>> columns = new ArrayList<>();
        TableColumn<FileData, String> name = new TableColumn<>("File Name");
        TableColumn<FileData, String> path = new TableColumn<>("File Path");
        TableColumn<FileData, String> extension = new TableColumn<>("File Type");
        TableColumn<FileData, String> dateModified = new TableColumn<>("Date Modified");
        TableColumn<FileData, String> size = new TableColumn<>("File Size");
        name.setCellValueFactory(data -> data.getValue().name());
        path.setCellValueFactory(data -> data.getValue().path());
        extension.setCellValueFactory(data -> data.getValue().extension());
        dateModified.setCellValueFactory(data -> data.getValue().dateModified());
        size.setCellValueFactory(data -> data.getValue().size());
        name.setCellFactory(new Callback<TableColumn<FileData,String>, TableCell<FileData,String>>() {
            @Override 
            public TableCell<FileData,String> call(TableColumn<FileData,String> param) {
            return new TableCell<FileData,String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item == null ? "" : item);
                    FileData data = this.getTableRow().getItem();
                    if (data == null) return;
                    ImageView img = (data.file().isDirectory()) ? new ImageView(new Image(getClass().getResourceAsStream("/images/foldericon.png"))) : new ImageView(new Image(getClass().getResourceAsStream("/images/fileicon.png")));
                    setGraphic(img);
                }
            };};
        });
        columns.addAll(List.of(name, path, dateModified, extension, size));
        getColumns().setAll(columns);
    }

}