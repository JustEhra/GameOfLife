package Game;

import com.sun.media.jfxmedia.events.PlayerEvent;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JFrame Frame = new JFrame("Game of Life");
    Dimension frameSIZE;

    Frame(Option option){
        frameSIZE= new Dimension(
                option.getWindowSizeW()+200,
                option.getWindowSizeH()+42);
        this.setMinimumSize(frameSIZE);
        this.setVisible(true);

        //this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initialisation(CellTable cellTable) {
        this.add(cellTable);
    }


}
