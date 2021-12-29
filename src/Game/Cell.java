package Game;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Cell implements Serializable {

   /*
    private static final int ALIVE = 1;
    private static final int INFECTED = 2;
    private static final int IMMUNE = 3;
    private static final int DEAD = 4;
    private static final int VOID = 5;
    */

    private final Color LIGHT_GREEN = new Color(89, 225, 82); //saine
    private final Color LIGHT_GRAY = new Color(142, 144, 140); //morte
    private final Color LIGHT_BLUE = new Color(85, 225, 215); // immunise
    private final Color LIGHT_RED = new Color(225, 37, 37); //infecte
    private final Color DARK_GRAY = new Color(44, 40, 37); //void LE MUR

    int placex,placey;
    Color cellColor;
    int lifetime;
    private int state;
    Dimension cellSIZE;

    Cell(int posx,int posy,Dimension size){
        cellSIZE= size;

        placex= posx*cellSIZE.width; //System.out.print(placex);
        placey= posy*cellSIZE.height; //System.out.println(","+placey);

        revive();
    }

    public void revive() {
        state = 1;
        cellColor = LIGHT_GREEN;
    }

    public void setInfected() {
        state = 2;

        //set infection lifetime
        Random rand = new Random();
        if(Option.isRandHeal())
            this.setLifetime(rand.nextInt(Option.getTimeToHeal()));
        else
            this.setLifetime(Option.getTimeToHeal());


        cellColor = LIGHT_RED;
    }

    public void setImmune() {
        state = 3;
        cellColor = LIGHT_BLUE;
    }

    public void kill() {
        state = 4;
        cellColor = LIGHT_GRAY;
    }
    public void nullify() {
        state = 5;
        cellColor = DARK_GRAY;
    }


    public boolean isAlive(){
        return state == 1;
    }
    public boolean isInfected(){
        return state == 2;
    }
    public boolean isImmune(){
        return state == 3;
    }
    public boolean isDead(){
        return state == 4;
    }
    public int getLifetime() {
        return lifetime;
    }


    //Fait une rotation entre les etats de la cellule, de vivant a null puis a vivante
    public void setNextState() {
        if(state<5) state=state+1;
        else state=1;

        if(state==1) revive();
        if(state==2) setInfected();
        if(state==3) setImmune();
        if(state==4) kill();
        if(state==5) nullify();
    }

    //récupere l'etat de la cellule en int
    public int getCellStateAsint()
    {
        return state;
    }

    public String getCellStateAsString()
    {
        if(state==1) return "Alive";
        if(state==2) return "Infected";
        if(state==3) return "Immune";
        if(state==4) return "Dead";
        return "Void";
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }
}
