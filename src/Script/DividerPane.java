package Script;

import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class DividerPane extends SplitPane {
    
    private Searcher searcher;

    DividerPane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 0, 0, 0));
        FilePane[] panes = {new FilePane(searcher, FilePane.Type.NAME), new FilePane(searcher, FilePane.Type.PATH), new FilePane(searcher, FilePane.Type.EXT), new FilePane(searcher, FilePane.Type.SIZE)};
        this.getItems().addAll(panes[0], panes[1], panes[2], panes[3]);
        this.setDividerPositions(.3, .7, .85);
        searcher.setPanes(panes);
        // name, last modified, file type/extension, size, path
    }

}
