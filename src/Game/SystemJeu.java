package Game;
import javax.swing.*;
import java.awt.*;


public class SystemJeu {
    static Option option = new Option();
    static CellTable cellTable;
    static Frame frame;
    static Option_Controller option_controller;
    private static int iteration;


    //Fonction d'initialisation
    public static void initialisation() {
        iterationReset();
        frame = new Frame(option);
        cellTable = new CellTable(option);
        option_controller = new Option_Controller(option,cellTable);

        Box horizontal = new Box(0);
        horizontal.add(option_controller);
        horizontal.add(cellTable);

        frame.add(horizontal);
        frame.pack();
    }

    //
    public static void iterationReset(){iteration=0;}

    //rename
    private static void actualization(int iteration){
            cellTable.propagation(iteration,option.isMode());
    }

    //Fonction d'itération du jeu.
    public static void game() throws InterruptedException {
        Thread.sleep(750); //temps de refresh
        option_controller.stat_actualisation(cellTable,iteration);

        if(option.isOngoing()) {
            actualization(iteration);
            ++iteration;
        }
        game();


    }

    public static void main(String[] args) throws InterruptedException {
        initialisation();
        game();
    }
}





/*  //console print
    public static void output() {
        //System.out.println(Arrays.deepToString(cellTable));
        for (int i = 0; i < option.getNbOfCells(); i++) {
            System.out.print("|");
            for (int j = 0; j < option.getNbOfCells() ; j++) {
                if(!cellTable.getCell(i,j).getCellState())
                    System.out.print("x"+"|");
                else
                    System.out.print("o"+"|");
            }
            System.out.println();
        }
    }

}
*/