package FileSearcher.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import FileSearcher.Searching.QuickSort;
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

    private final static String alpha = "!._0123456789abcdefghijklmnopqrstuvwxyz";
    private final static char[] characters = alpha.toCharArray();

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

    public static List<FileData> quickSortFiles(List<FileData> list) {
        QuickSort qs = new QuickSort();
        double[] values = new double[list.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = alphabetIndex(list.get(i).name().getValue(), 4, i*0.00000000001);     // get a list of values corresponding to each file
        }
        HashMap<Double, FileData> hashMap = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            hashMap.put(values[i], list.get(i));    // pair each value with its file
        }
        if (values.length > 0) {
            qs.recursiveSort(values, 0, values.length-1);    // sort the values
            // qs.interativeSort(values);
        }
        list.clear();
        for (double i : values) {
            list.add(hashMap.get(i));   // get the files from the sorted values
        }
        return list;
    }

    public static double alphabetIndex(String str, int letterPos, double offset) { // find a strings position along the alphabet, letter checked is one at letterPos
        double index = -1;
        char letter;
        if (str != null) {
            for (int j = 0; j <= letterPos; j++) {
                if (j < str.length()) {
                    letter = str.toLowerCase().charAt(j);
                }
                else {
                    break;
                }
                for (int i = 0; i<characters.length; i++) {
                    if (letter == characters[i]) {
                        if (j == 0) {
                            index = i/(j+1);
                        }
                        else {
                            index += (double) i/(Math.pow(10, 2+(j*2)));
                        }
                    }
                }
            }
        }
        return index+offset;
    }

}