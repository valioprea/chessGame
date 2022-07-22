import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my chess game");
        System.out.println("White moves first");

//        Singleton gameLogic = new GameLogic();

//        Singleton.getGameLogic().generateSquares();
//        Singleton.getGameLogic().initializePieces();
//        gameLogic.generateSquares();
//        gameLogic.initializePieces();

        Visuals visuals = new Visuals();

        visuals.generateVisualSquares();
        visuals.drawInitial();
        visuals.eventVali(visuals);



    }
}
