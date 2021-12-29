package Game;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class Option_Controller extends JPanel {
    Label mort_label;
    Label envie_label;
    Label immunise_label;
    Label infecte_label;
    Label iteration_label;

    Option_Controller(Option option,CellTable cellTable){
        Box vertical = new Box(1);

        Box horizontal_1 = new Box(0);
        Box horizontal_2 = new Box(0);
        Box horizontal_3 = new Box(0);
        Box horizontal_4 = new Box(0);
        Box horizontal_5 = new Box(0);
        Box horizontal_6 = new Box(0);
        Box horizontal_7 = new Box(0);
        Box horizontal_8 = new Box(0);

        //textfield et texte du textfield nombre de cellule.
        Label nbcellule_label = new Label("Nombre de cellule:");
        horizontal_1.add(nbcellule_label);
        TextField nbcellule_TextField = new TextField(""+option.getNbOfCells());
        horizontal_1.add(nbcellule_TextField);
        nbcellule_TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(Integer.parseInt(nbcellule_TextField.getText())>0 && Integer.parseInt(nbcellule_TextField.getText())<=250)
                {
                    option.setNbOfCells(Integer.parseInt(nbcellule_TextField.getText()) + 1);
                    cellTable.initialition(option);
                }
                else JOptionPane.showMessageDialog(cellTable, "Entrer une valeur entre 1 et 250");

            }
        });

        //textfield et texte du textfield mortalite.
        Label mortalite_label = new Label("Mortalité:");
        horizontal_2.add(mortalite_label);
        TextField mortalite_TextField = new TextField(""+ option.getChanceToKill());
        horizontal_2.add(mortalite_TextField);
        mortalite_TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Double.parseDouble(mortalite_TextField.getText())>=0 && Double.parseDouble(mortalite_TextField.getText())<=100) {
                    option.setChanceToKill(Double.parseDouble(mortalite_TextField.getText()));
                }
                else JOptionPane.showMessageDialog(cellTable, "Entrer une valeur entre 0.00 et 100.00");

            }
        });

        //textfield et texte du textfield propagation.
        Label propagation_label = new Label("Propagation:");
        horizontal_3.add(propagation_label);
        TextField propagation_TextField = new TextField(""+ option.getChanceToInfect());
        horizontal_3.add(propagation_TextField);
        propagation_TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Double.parseDouble(propagation_TextField.getText())>=0 && Double.parseDouble(propagation_TextField.getText())<=100) {
                    option.setChanceToInfect(Double.parseDouble(propagation_TextField.getText()));
                }
                else JOptionPane.showMessageDialog(cellTable, "Entrer une valeur entre 0.00 et 100.00");
            }
        });

        //textfield et texte du textfield guerison.
        Label guerison_label = new Label("Temps de Guérison:");

        TextField guerison_TextField = new TextField(""+option.getTimeToHeal());
        guerison_TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(guerison_TextField.getText())>0 ) {
                    option.setTimeToHeal(Integer.parseInt(guerison_TextField.getText()));
                    guerison_TextField.setText(""+(option.getTimeToHeal()));
                }
                else JOptionPane.showMessageDialog(cellTable, "Entrer une valeur positive");
            }
        });

        this.setMinimumSize(new Dimension(200,200));
        this.setMaximumSize(new Dimension(200,1000));


        //Text Randomize
        Label randomize_label = new Label("Génération d'infecté:");
        TextField randomize_TextField = new TextField(""+(option.getRandomize()));
        randomize_TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(randomize_TextField.getText())>0 ) {
                    option.setRandomize(Integer.parseInt(randomize_TextField.getText()));
                }
                else JOptionPane.showMessageDialog(cellTable, "Entrer une valeur positive");
            }
        });

        //Bouton Randomize
        Button randomize = new Button("Randomiser");
        randomize.setMaximumSize(new Dimension(70,30));
        randomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                for(int i = 0; i < option.getRandomize(); i++) {
                    Cell temp_Cell = cellTable.getCell(
                            rand.nextInt(
                                    option.getNbOfCells()),
                            rand.nextInt(
                                    option.getNbOfCells()));

                    if(!temp_Cell.isAlive()) i--;
                    else temp_Cell.setInfected();

                    if(cellTable.getNumberOfCells(1)<=0) //si il n'y a plus de cellule a infecter /en vie
                        i=option.getRandomize()+1;

                }

                cellTable.repaint();
            }
        });
        horizontal_5.add(randomize);
        horizontal_5.add(randomize_TextField);

        //Bouton Mode

        Button mode = new Button("Simulation");
        horizontal_8.add(vertical.add(new Label("Mode:")));
        horizontal_8.add(mode);
        mode.setMaximumSize(new Dimension(70,30));
        mode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(option.isMode())
                    mode.setLabel("Simulation");
                else
                    mode.setLabel("Game of life");
                cellTable.initialition(option);
                option.changeMode();
            }
        });


        //Bouton Reset
        Button reset = new Button("Reset");
        horizontal_6.add(reset);
        reset.setMaximumSize(new Dimension(70,30));
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellTable.initialition(option);
            }
        });

        //Bouton Play
        Button start = new Button("Play");
        horizontal_6.add(start);
        start.setMaximumSize(new Dimension(70,30));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SystemJeu.iterationReset();
               if(!option.isOngoing())
                    start.setLabel("Stop");
               else
                   start.setLabel("Play");
                option.changeOngoing();
            }
        });


        //Bouton Aléatoire
        Button rand = new Button("Aléatoire");
        horizontal_4.add(rand);
        horizontal_4.add(guerison_TextField);
        rand.setMaximumSize(new Dimension(70,30));
        rand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!option.isRandHeal())
                    rand.setLabel("Aléatoire");
                else
                    rand.setLabel("Fixe");
                option.changeRand();
            }
        });

        //Bouton save
        Button save = new Button("Save");
        horizontal_7.add(save);
        save.setMaximumSize(new Dimension(70,30));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("FileChooser.saveButtonText","Save");
                JFileChooser fileChooser = new JFileChooser(".");
                int userSelection = fileChooser.showSaveDialog(cellTable.getParent());
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(
                                new FileOutputStream(fileToSave+".save")
                        );
                        out.writeObject(cellTable.getTable());
                        out.flush();
                        out.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //Bouton load
        Button load = new Button("Load");
        horizontal_7.add(load);
        load.setMaximumSize(new Dimension(70,30));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("FileChooser.saveButtonText","Load"); //change le text du bouton "save" en "load"
                JFileChooser fileChooser = new JFileChooser("."); //emplacement et creation du filechooser
                fileChooser.setDialogTitle("Specify a file to Load");
                fileChooser.setAcceptAllFileFilterUsed(false); //remove le filtre all
                fileChooser.setFileFilter(new FileFilter() { //creation du filtre ".save"
                    @Override
                    public boolean accept(File f) {
                        if (f.getName().endsWith(".save")) {
                            return true;
                        }
                        return false;
                    }
                    @Override
                    public String getDescription() {
                        return ".save";
                    }
                });

                int userSelection = fileChooser.showSaveDialog(cellTable.getParent());
                if ((userSelection == JFileChooser.APPROVE_OPTION)) {
                    File fileToSave = fileChooser.getSelectedFile();
                    //chargement du fichier et de sa table de cellule
                    try {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileToSave));
                        Cell[][] temp = (Cell[][]) in.readObject();
                        option.setNbOfCells(temp.length);
                        cellTable.initialition(option);
                        cellTable.setTable( temp);
                        in.close();
                        cellTable.repaint();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    System.out.println("Choisissez un fichier (.save).");

            }
        });


        //Stats
        mort_label = new Label("Mort: "+0);
        envie_label = new Label("En vie: "+option.getNbOfCells()*option.getNbOfCells());
        immunise_label = new Label("Immunisé: "+0);
        infecte_label = new Label("Infecté: "+0);
        iteration_label = new Label("Iteration: "+0);
        //


        //ajout de toutes les box et component a la fenetre
        vertical.add(horizontal_1);
        vertical.add(horizontal_2);
        vertical.add(horizontal_3);

        vertical.add(guerison_label);
        vertical.add(horizontal_4);

        vertical.add(randomize_label);
        vertical.add(horizontal_5);
        vertical.add(Box.createRigidArea(new Dimension(0, 75)));

        vertical.add(new Label("Statistique:"));
        vertical.add(new Label(""));
        vertical.add(envie_label);
        vertical.add(mort_label);
        vertical.add(immunise_label);
        vertical.add(infecte_label);
        vertical.add(iteration_label);


        vertical.add(Box.createRigidArea(new Dimension(0, 150)));

        Box justifybottom = new Box(1);
        justifybottom.add(leftJustify(horizontal_6));
        justifybottom.add(new Label(""));
        justifybottom.add(leftJustify(horizontal_7));
        justifybottom.add(horizontal_8);

        vertical.add(justifybottom);

        this.add(vertical);
    }

    public void stat_actualisation(CellTable cellTable,int iteration){
        envie_label.setText("En vie: "+ cellTable.getNumberOfCells(1) );
        mort_label.setText("Mort: "+ cellTable.getNumberOfCells(4));
        immunise_label.setText("Immunisé: "+ cellTable.getNumberOfCells(3));
        infecte_label.setText("Infecté: "+ cellTable.getNumberOfCells(2));
        if(Option.isOngoing())
            iteration_label.setText("Iteration: "+ iteration);

    }

    //fonction qui renvois un component Box qui est ajuster à gauche
    private Component leftJustify( Component panel )  {
        Box  b = Box.createHorizontalBox();
        b.add( panel );
        b.add( Box.createHorizontalGlue() );
        return b;
    }
}
