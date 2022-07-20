public final class Singleton {
    private static Singleton INSTANCE;
    private static GameLogic gameLogic;

    private Singleton(){};

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    public static GameLogic getGameLogic() {
        if (gameLogic == null) {
            gameLogic = new GameLogic();
        }
        return gameLogic;
    }
}
