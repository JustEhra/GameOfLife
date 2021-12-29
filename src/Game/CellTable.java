package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CellTable extends JPanel implements MouseListener,MouseMotionListener {



    private Cell[][] Table; //table qui contient les cellules
    private static int tableSize; //taille de la table

    private int numberOfAliveCells;
    private int numberOfImmuneCells;
    private int numberOfInfectedCells;
    private int numberOfDeadCells;

    List<Cell> toChange = new ArrayList<>(); //array de cellule ou le type change à la prochaine rotation

    Dimension frameSIZE;
    Dimension cellSIZE;

    Option option_test;

    // color les cases
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < tableSize; i++) {
           for (int j = 0; j < tableSize; j++) {

               if(i<tableSize-1 && j<tableSize-1){
                   g.setColor(Color.black);
                   g.fillRect(Table[i][j].placex,
                           Table[i][j].placey,
                           (int) Table[i][j].cellSIZE.getWidth()+1,
                           (int) Table[i][j].cellSIZE.getHeight()+1);
              }


                g.setColor(Table[i][j].cellColor);
                g.fillRect(Table[i][j].placex,
                        Table[i][j].placey,
                        1-(int) Table[i][j].cellSIZE.getWidth(),
                        1-(int) Table[i][j].cellSIZE.getHeight());
            }
        }
    }

    CellTable(Option option) {
        addMouseListener(this);
        addMouseMotionListener(this);

        option_test = option;

        this.setMaximumSize(frameSIZE);
        this.setPreferredSize(frameSIZE);
        this.setMinimumSize(frameSIZE);

        initialition(option);


    }

    public Cell getCell(int i, int j) {
        return Table[i][j];
    }
    public int getNumberOfCells(int type) {
        if(option_test.isOngoing()) {
            switch (type) {
                case 1:
                    return numberOfAliveCells;
                case 2:
                    return numberOfInfectedCells;
                case 3:
                    return numberOfImmuneCells;
                case 4:
                   return numberOfDeadCells;
                default:
                    return 0;
            }
        }
        else {
            int tempnumber=0;
            for (int i = 0; i < tableSize; i++) {
                for (int j = 0; j < tableSize; j++) {
                    switch (type) {
                        case 1:
                            if (Table[i][j].isAlive())
                                tempnumber=++tempnumber;
                            break;
                        case 2:
                            if (Table[i][j].isInfected())
                                tempnumber=++tempnumber;
                            break;
                        case 3:
                            if (Table[i][j].isImmune())
                                tempnumber=++tempnumber;
                            break;
                        case 4:
                            if (Table[i][j].isDead())
                                tempnumber=++tempnumber;
                            break;
                    }
                }
            }
            return tempnumber;
        }
    }
    public int getTableSize() {
        return tableSize;
    }
    public Cell[][] getTable() {
        return Table;
    }
    public void setTable(Cell[][] table) {
        Table = table;
    }

    public void initialition(Option option) {
        int placex = 0;
        int placey = 0;

        numberOfAliveCells=option.getNbOfCells()*option.getNbOfCells();
        numberOfInfectedCells=0;
        numberOfImmuneCells=0;
        numberOfDeadCells=0;

        Table = new Cell[option.getNbOfCells()][option.getNbOfCells()];
        tableSize = option.getNbOfCells();

        frameSIZE= new Dimension(option.getWindowSizeW(),option.getWindowSizeH());
        cellSIZE=new Dimension(
                (int)frameSIZE.getWidth() / option.getNbOfCells(),
                (int)frameSIZE.getHeight() / option.getNbOfCells());

        for (int i = 0; i < option.getNbOfCells(); i++) {
            for (int j = 0; j < option.getNbOfCells(); j++) {
                Table[i][j] = new Cell(placex++, placey,cellSIZE);
            }
            placex = 0;
            placey++;
        }


        repaint();
    }



    //Recupere le nombre de cellule du type state adjacente à une cellule
    public int getCloseCells(int i, int j,int state) {
        int nbNeighbor = 0;
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if ((k != i || l != j)
                        && k >= 0 && k < getTableSize() - 1
                        && l >= 0 && l < getTableSize() - 1) {
                    if (Table[k][l].getCellStateAsint() == state) {
                        nbNeighbor++;
                    }
                }
            }
        }

        return nbNeighbor;
    }


    public void propagation(int iteration,boolean mode)  {


        List<Cell> liveCells = new ArrayList<>();
        List<Cell> deadCells = new ArrayList<>();
        List<Cell> infectedCells = new ArrayList<>();
        List<Cell> immuneCells = new ArrayList<>();

        for (int i = 0; i < getTableSize() ; ++i) {
            for (int j = 0; j < getTableSize() ; ++j) {
                Cell cell = Table[i][j];


                //Game of life Rules : //
                if (mode) {
                    int closeAliveCells = getCloseCells(i, j, 1);

                    // rule 1, rule 3
                    if (cell.isAlive() && (closeAliveCells < 2 || closeAliveCells > 3)) {
                        deadCells.add(cell);
                    }
                    // rule 2, rule 4
                    if (cell.isAlive() && (closeAliveCells == 3 || closeAliveCells == 2) || !cell.isAlive() && closeAliveCells == 3) {
                        liveCells.add(cell);
                    }

                }
                //Game of life Rules END //


                //Virus Rules: //
                if(!mode){
                    //If infected ->dead or immune
                    Random rand = new Random();

                    if (cell.isInfected() ) {
                        if(cell.getLifetime()<=0) { //si le temps d'infection est inferieur ou égale à 0
                            if (rand.nextInt(100) >= option_test.getChanceToKill()) { //Immunise la cellule si la chance de tuer est inferrieur à un rand entre 1 et 100
                                numberOfImmuneCells++;
                                immuneCells.add(cell);
                            } else {
                                numberOfDeadCells++; //sinon la tue
                                deadCells.add(cell);
                            }
                            numberOfInfectedCells--;
                        }
                        else
                            cell.setLifetime(cell.getLifetime()-1);
                    }

                    // If nearby infected -> get infected
                    if (cell.isAlive()) {
                        int closeInfectedCells = getCloseCells(i, j, 2);

                        if (closeInfectedCells * option_test.getChanceToInfect()*100/8 >=1+rand.nextInt(99) ) { //1+rand 99 sinon les cellules ont une chance d'etre infecter à 0% de chance
                            numberOfAliveCells--;
                            numberOfInfectedCells++;

                            infectedCells.add(cell);
                        }
                    }
                }
                //Virus Rules END //
            }
        }

        //actualisation de toutes les cellules qui change
        for (Cell cell : liveCells) {
            cell.revive();
        }
        for (Cell cell : deadCells) {
            cell.kill();
        }
        for (Cell cell : infectedCells) {
            cell.setInfected();
        }
        for (Cell cell : immuneCells) {
            cell.setImmune();
        }




        repaint();
    }
    //Change la couleur de la case qui est cliqué et ajoute la case cliqué aux statitiques.
    public void changeColor(int x, int y) {
        //System.out.println("X:"+x+",    Y:"+y+"   State:"+Table[x][y].getCellStateAsString());
        if(!option_test.isMode())
            Table[y][x].setNextState();
        else
            {
                if(Table[y][x].getCellStateAsint()==1)
                    Table[y][x].kill();
                else
                    Table[y][x].revive();

            }

        switch (Table[y][x].getCellStateAsint()){
            case 1:
                numberOfAliveCells++;
                numberOfDeadCells--;
                break;
            case 2:
                numberOfInfectedCells++;
                numberOfAliveCells--;
                break;
            case 3:
                numberOfImmuneCells++;
                numberOfInfectedCells--;
                break;
            case 4:
                numberOfDeadCells++;
                numberOfImmuneCells--;
                break;
        }
        repaint();
    }



    private void clickCell(MouseEvent me) {
        int x = me.getPoint().x/(int)cellSIZE.getWidth();
        int y = me.getPoint().y/(int)cellSIZE.getHeight();
        if ((x+1 >= 0) && (x+1 < frameSIZE.width) && (y >= 0) && (y < frameSIZE.height) && x<Table.length-1 && y<Table.length-1) {
            changeColor(x+1,y+1);
        }
    }

    //Ajoute les cases cliqué a liste toChange, si il est déjà présent, la fonction return false, sinon elle est change et ajoute a toChange.
    private boolean testIfAlreadyClicked(MouseEvent me) {
        int x = me.getPoint().x / (int) cellSIZE.getWidth();
        int y = me.getPoint().y / (int) cellSIZE.getHeight();
        if ((x + 1 >= 0) && (x + 1 < frameSIZE.width) && (y >= 0) && (y < frameSIZE.height) && x < Table.length - 1 && y < Table.length - 1) {
            if(!toChange.contains(Table[y+1][x+1])) {
                toChange.add(Table[y + 1][x + 1]);
                return true;
            }
        }
        return false;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        // Mouse is being dragged, user wants multiple selections
        if(testIfAlreadyClicked(e)){
            clickCell(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(testIfAlreadyClicked(e)){
            clickCell(e);
        }
        toChange.clear();
    }




    @Override
    public void mouseMoved(MouseEvent e) { }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}


    /*
    public void dump(Cell[][] array, String title) {
        System.out.println("========================== " + title);
        for (int i = 0; i < getTableSize()-1; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < getTableSize() - 1; j++) {
                System.out.print(array[i][j].alive ? "x" : " ");
            }
            System.out.println();
        }
    }
    */
}





