import javax.swing.*;

public class GameLogic {
    public JFrame frame;
    public Board board;
    public Piece selectedPiece;
    public int sequence = 1;

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public GameLogic(Board board) {
        this.board = board;
    }

    public Piece getPiece() {
        return selectedPiece;
    }

    public void grabPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
        this.setSequence(2);
        System.out.println("Na de aici coordonatele piesei cand o grabuiesc");
        System.out.println("ROW: " + selectedPiece.getRowPosition());
        System.out.println("COL: " + selectedPiece.getColumnPosition());
    }

    public void ungrabPiece() {
        this.selectedPiece = null;
        this.setSequence(1);
    }

    public void placePiece(int rowPosition, int columnPosition) {
        //Set false to contains piece for previous square
        this.board.getAllSquares()[this.selectedPiece.getRowPosition()][this.selectedPiece.getColumnPosition()].setContainsPiece(false);

        //Assign selected piece to new square
        this.board.getAllSquares()[rowPosition][columnPosition].add(this.selectedPiece);

        //Assign new coordinates for the recently placed piece
        this.selectedPiece.setRowPosition(rowPosition);
        this.selectedPiece.setColumnPosition(columnPosition);

        //Set true to contains piece for new square
        this.board.getAllSquares()[rowPosition][columnPosition].setContainsPiece(true);

        this.frame.repaint();

        ungrabPiece();
    }

    public void setFrame(JFrame gameFrame) {
        this.frame = gameFrame;
    }

    public void whyAmIPressing(int rowPosition, int columnPosition) {
        //IS THE SQUARE THAT I HAVE CHOSEN EMPTY?
        if (board.getAllSquares()[rowPosition][columnPosition].getContainsPiece() == false) {
            placePiece(rowPosition, columnPosition); //if empty, I will place my piece here
            System.out.println("Sequence will be: " + sequence); //should be 1 now
        } else {
            //SO THE SQUARE IS NOT EMPTY. DOES THE COLOR OF THE PIECE ON THIS SQUARE MATCH THE ONE THAT I HAVE IN MY HAND ?
            if (((Piece) board.getAllSquares()[rowPosition][columnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite()) {
                System.out.println("I can't place a piece of my own over another piece of my own");
                System.out.println("I need to reselect my piece");
                ungrabPiece();
                System.out.println("Sequence will be: " + sequence);
            } else {
                System.out.println("I will capture here -> I need to write the logic for that");
                slay(rowPosition, columnPosition);
                System.out.println("Sequence will be: " + sequence);
            }
        }
    }

    public void slay(int rowPosition, int columnPosition){
        board.getAllSquares()[rowPosition][columnPosition].remove(1);
        placePiece(rowPosition, columnPosition);
    }


    public void getPossibleMovesOfRook(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){

        int rowNorth;
        int colNorth;
        int[] posNorth = new int[2];
        int rowSouth;
        int colSouth;
        int[] posSouth = new int[2];
        int rowWest;
        int colWest;
        int[] posWest = new int[2];
        int rowEast;
        int colEast;
        int[] posEast = new int[2];

        //Cum arata un square ? uite asa -> board.getAllSquares()[rowPosition][columnPosition]

        //INCERC SPRE EST
        int j = currentColumnPosition+1;
        while ( j <= 8 ){

            //IS THERE A PIECE?
            if (board.getAllSquares()[currentRowPosition][j].getContainsPiece() == true){

                //IS THE EXISTING PIECE SAME COLOR AS MINE?
                if(((Piece) board.getAllSquares()[currentRowPosition][j].getComponents()[1]).isWhite() == this.selectedPiece.isWhite())){
    
                }

            }


        }
    }


}

