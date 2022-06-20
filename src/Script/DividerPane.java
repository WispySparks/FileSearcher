package Script;

import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class DividerPane extends SplitPane {
    
    private Searcher searcher;
    private SlidePane slidePane;

    DividerPane(Searcher searcher, SlidePane slidePane) {
        this.searcher = searcher;
        this.slidePane = slidePane;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 0, 0, 0));
        FilePane[] panes = {new FilePane(searcher, FilePane.Type.NAME, slidePane), new FilePane(searcher, FilePane.Type.DATE, slidePane), new FilePane(searcher, FilePane.Type.EXT, slidePane), new FilePane(searcher, FilePane.Type.SIZE, slidePane)};
        this.getItems().addAll(panes[0], panes[1], panes[2], panes[3]);
        this.setDividerPositions(.4, .65, .85);
        searcher.setPanes(panes, null);
        // name, last modified, file type/extension, size, path
    }

}
