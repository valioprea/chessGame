import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//THIS SHOULD PROBABLY BECOME SOME SORT OF CLASS THAT CONTAINS ALL METHODS WHO GENERATE MOVEMENT LOGIC IN THE FUTURE.


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

    public void grabPiece(Piece selectedPiece) { //TODO: this needs rethinking -> only in sequence 1 I can grab the piece, I got lost in the code trying to find out when do I get the targetSquarePositions
        this.selectedPiece = selectedPiece;
        this.setSequence(2);
        System.out.println("...and my piece is "+this.selectedPiece.getName());
        System.out.println("ROW: " + selectedPiece.getRowPosition());
        System.out.println("COL: " + selectedPiece.getColumnPosition());
    }

    public void ungrabPiece() {
        this.selectedPiece = null;
        this.setSequence(1);
    }

    //TODO: I should probably implement some sort of board reader before each move, to use it to see each king in relationship with all oppponents pieces, if it is in check or not

    public void placePiece(int rowPosition, int columnPosition) {

        //TODO: I will need to think about how to properly do this below:
        if(this.selectedPiece.getName() == "rook"){
            ((Rook)this.selectedPiece).setRookHasMovedBeforeCastling(true);
        }

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

    public void whyAmIPressing(int targetRowPosition, int targetColumnPosition) {
        //I got a piece in hand, just pressed a square, I want to move my piece there.
        //What kind of piece do I have in my hand ?

        String pieceType = selectedPiece.getName();

        boolean isValid = false;
        if(pieceType == "pawn"){  //TODO: this block of code should become uncommented as I implement more and more piece types.
            System.out.println("I should implement logic for pawn move validation");
            isValid = false;
        } else if (pieceType == "rook") {
            isValid = validateRookMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "knight") {
            isValid = validateKnightMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "bishop") {
            System.out.println("I should implement logic for bishop move validation");
            isValid = false;
        } else if (pieceType == "queen") {
            System.out.println("I should implement logic for queen move validation");
            isValid = false;
        } else if (pieceType == "king") {
            System.out.println("I should implement logic for king move validation");
            isValid = false;
        }

//        isValid = validateRookMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition); //TODO: this will need deletion

        //Is the move valid ?
        if(isValid == false){
            ungrabPiece();
        } else {

            //Is the target square empty?
            if (board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == false){
                placePiece(targetRowPosition, targetColumnPosition); //if empty, I will place my piece here
                System.out.println("Sequence will be: " + sequence); //should be 1 now
            } else {
                //Is that an enemy I am targeting or friendly piece ?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                    System.out.println("I can't place a piece of my own over another piece of my own");
                    System.out.println("I need to reselect my piece");
                    ungrabPiece();
                    System.out.println("Sequence will be: " + sequence);
                } else {
                    System.out.println("Not so sure about capturing logic but here we go ...");
                    slay(targetRowPosition, targetColumnPosition);
                    System.out.println("Sequence will be: " + sequence);
                }
            }
        }
    }

    public void slay(int rowPosition, int columnPosition){
        board.getAllSquares()[rowPosition][columnPosition].remove(1);
        placePiece(rowPosition, columnPosition);
    }


    //Possible Moves of rook relative to current position
    public boolean validateRookMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){

        ArrayList<Integer> eastPossibility = new ArrayList<>(); //these are only values for column positions !!
        ArrayList<Integer> westPossibility = new ArrayList<>(); //these are only values for column positions !!
        ArrayList<Integer> northPossibility = new ArrayList<>(); //these are only values for row positions !!
        ArrayList<Integer> southPossibility = new ArrayList<>(); //these are only values for row positions !!

        //How does a square look like: -> board.getAllSquares()[rowPosition][columnPosition]
        //How does a piece look like: -> ((Piece) board.getAllSquares()[i][j].getComponents()[1])

        //LOOK TO THE EAST -> so a possible EASTERN POSITION should be any combination of: currentRowPosition, eastPossibility[index]
        int j = currentColumnPosition+1;
        while ( j <= 8 ){
            //IS THERE A PIECE?
            if (board.getAllSquares()[currentRowPosition][j].getContainsPiece() == true){
                //IS THE EXISTING PIECE SAME COLOR AS MINE?
                if( ((Piece) board.getAllSquares()[currentRowPosition][j].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    eastPossibility.add(j);
                    break;
                }
            } else {
                eastPossibility.add(j);
            }
        j++;
        }

        //LOOK TO THE WEST -> so a possible WESTERN POSITION should be any combination of: currentRowPosition, westPossibility[index]
        int w = currentColumnPosition-1;
        while ( w >= 1 ){
            //IS THERE A PIECE?
            if (board.getAllSquares()[currentRowPosition][w].getContainsPiece() == true){
                //IS THE EXISTING PIECE SAME COLOR AS MINE?
                if( ((Piece) board.getAllSquares()[currentRowPosition][w].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    westPossibility.add(w);
                    break;
                }
            } else {
                westPossibility.add(w);
            }
            w--;
        }

        //LOOK TO THE NORTH -> so a possible NORTHERN POSITION should be any combination of: northPossibility[index], currentColumnPosition
        int n = currentRowPosition-1;
        while ( n >= 1 ){
            //IS THERE A PIECE?
            if (board.getAllSquares()[n][currentColumnPosition].getContainsPiece() == true){
                //IS THE EXISTING PIECE SAME COLOR AS MINE?
                if( ((Piece) board.getAllSquares()[n][currentColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    northPossibility.add(n);
                    break;
                }
            } else {
                northPossibility.add(n);
            }
            n--;
        }

        //LOOK TO THE SOUTH -> so a possible SOUTHERN POSITION should be any combination of: southPossibility[index], currentColumnPosition
        int s = currentRowPosition+1;
        while ( s <= 8 ){
            //IS THERE A PIECE?
            if (board.getAllSquares()[s][currentColumnPosition].getContainsPiece() == true){
                //IS THE EXISTING PIECE SAME COLOR AS MINE?
                if( ((Piece) board.getAllSquares()[s][currentColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    southPossibility.add(s);
                    break;
                }
            } else {
                southPossibility.add(s);
            }
            s++;
        }
        System.out.println("WHERE CAN I MOVE TO EAST: ");
        System.out.println(eastPossibility);
        System.out.println("WHERE CAN I MOVE TO WEST: ");
        System.out.println(westPossibility);
        System.out.println("WHERE CAN I MOVE TO NORTH: ");
        System.out.println(northPossibility);
        System.out.println("WHERE CAN I MOVE TO SOUTH: ");
        System.out.println(southPossibility);
        int validate = 0;
        for(int index =0; index<eastPossibility.size(); index++){
            if ( eastPossibility.get(index) == targetColumnPosition && currentRowPosition == targetRowPosition) {
                validate++;
            }
        }
        for(int index =0; index<westPossibility.size(); index++){
            if ( westPossibility.get(index) == targetColumnPosition && currentRowPosition == targetRowPosition) {
                validate++;
            }
        }
        for(int index =0; index<northPossibility.size(); index++){
            if ( northPossibility.get(index) == targetRowPosition && currentColumnPosition == targetColumnPosition) {
                validate++;
            }
        }
        for(int index =0; index<southPossibility.size(); index++){
            if ( southPossibility.get(index) == targetRowPosition && currentColumnPosition == targetColumnPosition) {
                validate++;
            }
        }
        System.out.println(" MUTAREA ARE VALOAREA DE VALIDATE: "+validate); //if validate = 0 then move should not be valid!!

        if (validate == 0 ){
            return false;
        } else {
            return true;
        }
    }



    //Possible Moves of rook relative to current position
    public boolean validateKnightMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){


        ArrayList<Integer, Integer> a = new ArrayList<>();


//        ArrayList<Integer> canHopHereRow = new ArrayList<>();
//        ArrayList<Integer> canHopHereCol = new ArrayList<>();
//
//        canHopHereRow.add(currentRowPosition-2);
//        canHopHereRow.add(currentRowPosition-1);
//        canHopHereRow.add(currentRowPosition+1);
//        canHopHereRow.add(currentRowPosition+2);
//
//        canHopHereCol.add(currentColumnPosition-2);
//        canHopHereCol.add(currentColumnPosition-1);
//        canHopHereCol.add(currentColumnPosition+1);
//        canHopHereCol.add(currentColumnPosition+2);
//
//        int validate = 0;
//        int rowPosition = -1;
//        int colPosition = -1;
//        for( int i=0; i<canHopHereRow.size(); i++){
////            System.out.println("Hai sa vedem ce valori comparam");
////            System.out.println(canHopHereRow.get(i)+" cu "+targetRowPosition);
//            if(canHopHereRow.get(i) == targetRowPosition) {
//                rowPosition = i;
//            }
//        }
//        for( int i=0; i<canHopHereCol.size(); i++){
//            if(canHopHereCol.get(i) == targetColumnPosition) {
//                colPosition = i;
//            }
//        }
//
//        if(canHopHereRow.get(rowPosition) == targetRowPosition && canHopHereCol.get(colPosition) == targetColumnPosition){
//            validate++;
//        }

        if (validate < 1 ){
            return false;
        } else {
            return true;
        }
    };
}

