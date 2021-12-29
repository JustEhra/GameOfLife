package Game;

public class Option {
    private static int nbOfCells ;
    private static int windowSizeW ;
    private static int windowSizeH ;
    private static double chanceToInfect;
    private static double chanceToKill;
    private static int timeToHeal;
    private static int randomize; //Valeur de combien le bouton Randomize place d'infecté

    private static boolean mode; // Game of life / simulation
    private static boolean ongoing; //Start / pause
    private static boolean randHeal; //Randomize time of healing of a person

    Option(){
        nbOfCells = 50;
        windowSizeW = 800;
        windowSizeH = 800;
        chanceToInfect = 2.8;
        chanceToKill = 0.26;
        randomize = 150;
        timeToHeal= 14;
        ongoing = false;
        mode = false;
        randHeal= true;
    }

    public int getNbOfCells() {
        return nbOfCells;
    }
    public int getWindowSizeW() {
        return windowSizeW ;
    }
    public int getWindowSizeH() {
        return windowSizeH;
    }
    public static int getRandomize() {
        return randomize;
    }
    public static int getTimeToHeal() {
        return timeToHeal;
    }
    public static double getChanceToInfect() {
        return chanceToInfect;
    }
    public static double getChanceToKill() {
        return chanceToKill;
    }

    public static boolean isOngoing() {
        return ongoing;
    }
    public static boolean isMode() {
        return mode;
    }
    public static boolean isRandHeal() {
        return randHeal;
    }
    
    public static void setNbOfCells(int nbOfCells) {
        Option.nbOfCells = nbOfCells;
    }
    public static void setChanceToInfect(double chanceToInfect) {
        Option.chanceToInfect = chanceToInfect;
    }
    public static void setChanceToKill(double chanceToKill) {
        Option.chanceToKill = chanceToKill;
    }
    public static void setRandomize(int randomize) {
        Option.randomize = randomize;
    }
    public static void setTimeToHeal(int timeToHeal) {
        Option.timeToHeal = timeToHeal;
    }

    public static void changeOngoing() {
        ongoing= !ongoing;
    }
    public static void changeMode() { mode = !mode;}
    public static void changeRand(){
        randHeal = !randHeal;
    }

}