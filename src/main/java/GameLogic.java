import javax.security.auth.callback.Callback;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//THIS SHOULD PROBABLY BECOME SOME SORT OF CLASS THAT CONTAINS ALL METHODS WHO GENERATE MOVEMENT LOGIC IN THE FUTURE.


public class GameLogic {

    private String GAMETURN = "white";
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

    public String getGAMETURN() {
        return GAMETURN;
    }

    public void setGAMETURN(String GAMETURN) {
        this.GAMETURN = GAMETURN;
    }

    public Piece getPiece() {
        return selectedPiece;
    }

    public void grabPiece(Piece selectedPiece) {
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
    //TODO: setCheckMate, Draw (move repetition/ noCheckmate in 50 moves / insufficient material), StaleMate

    public void placePiece(int rowPosition, int columnPosition) {

        //TODO: I will need to think about how to properly do this below:
//        if(this.selectedPiece.getName() == "rook"){
//            ((Rook)this.selectedPiece).setRookHasMovedBeforeCastling(true);
//        }

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
        finishTurn(); //TODO: maybe here ? -> enable/disable white/black pieces
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
        if(pieceType == "pawn"){
            System.out.println("I should implement logic for pawn move validation");
            isValid = false;
        } else if (pieceType == "rook") {
            isValid = validateRookMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "knight") {
            isValid = validateKnightMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "bishop") {
            isValid = validateBishopMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "queen") {
            isValid = validateQueenMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "king") {
            System.out.println("I should implement logic for king move validation");
            isValid = false;
        }

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
//        ((Piece) board.getAllSquares()[rowPosition][rowPosition].getComponents()[1]) = null; //TODO: how do I remove a piece correctly ?
        placePiece(rowPosition, columnPosition);
    }


    //TODO: check these two functions below
    //UTILITY - get all pieces of the same color
    public ArrayList<Piece> getAllPiecesOfThisColor(boolean isWhite){

        if(isWhite == true){
            ArrayList<Piece> allWhitePieces = new ArrayList<>();
            for(int i=1; i<=8;i++){
                for(int j=1;j<=8;j++){
                    if( board.getAllSquares()[i][j].getContainsPiece() ==true && ((Piece) board.getAllSquares()[i][j].getComponents()[1]).isWhite() == isWhite ){
                        allWhitePieces.add(  ((Piece) board.getAllSquares()[i][j].getComponents()[1]) );
                    }
                }
            }
            System.out.println(allWhitePieces);
            return allWhitePieces;
        } else {
            ArrayList<Piece> allBlackPieces = new ArrayList<>();
            for(int i=1; i<=8;i++){
                for(int j=1;j<=8;j++){
                    if( board.getAllSquares()[i][j].getContainsPiece() ==true && ((Piece) board.getAllSquares()[i][j].getComponents()[1]).isWhite() == isWhite ){
                        allBlackPieces.add(  ((Piece) board.getAllSquares()[i][j].getComponents()[1]) );
                    }
                }
            }
            System.out.println(allBlackPieces);
            return allBlackPieces;
        }

    };

    public void finishTurn(){
    //Who did finish the turn ?
        if( this.selectedPiece.isWhite() == true ){
            //white finished the turn
            //deactivate white pieces + activate black pieces
            getAllPiecesOfThisColor(true).forEach(piece -> {
                piece.setCanBeMoved(false);
            });
            getAllPiecesOfThisColor(false).forEach(piece -> {
                piece.setCanBeMoved(true);
            });
            this.setGAMETURN("black"); //-> black is next

        } else {
            //black finished the turn
            //deactivate black pieces + activate white pieces
            getAllPiecesOfThisColor(false).forEach(piece -> {
                piece.setCanBeMoved(false);
            });
            getAllPiecesOfThisColor(true).forEach(piece -> {
                piece.setCanBeMoved(true);
            });
            this.setGAMETURN("white"); //-> white is next
        }
    }



    //TODO: VALIDATIONS VALIDATIONS VALIDATIONS READ BELOW THIS LINE FOR THOUGHTS
        //TODO: i need to check for all pieces that a move which will capture the opponent king is not valid

    //How does a square look like: -> board.getAllSquares()[rowPosition][columnPosition]
    //How does a piece look like: -> ((Piece) board.getAllSquares()[i][j].getComponents()[1])

    //Possible moves of rook relative to current position
    public boolean validateRookMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){

        ArrayList<Integer> eastPossibility = new ArrayList<>(); //these are only values for column positions !!
        ArrayList<Integer> westPossibility = new ArrayList<>(); //these are only values for column positions !!
        ArrayList<Integer> northPossibility = new ArrayList<>(); //these are only values for row positions !!
        ArrayList<Integer> southPossibility = new ArrayList<>(); //these are only values for row positions !!

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

    //Possible moves of rook relative to current position
    public boolean validateKnightMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){

        Position[] possiblePositions = new Position[8];

        int validate=0;
        //IS THE TARGET SQUARE REACHABLE FOR THE KNIGHT ?
        if( currentRowPosition-2 >= 1 && currentColumnPosition-1 >=1 && currentRowPosition-2 == targetRowPosition && currentColumnPosition-1 == targetColumnPosition){ //physical validation for the 'L move'
//            possiblePositions[0].setRowPosition(currentRowPosition-2);
//            possiblePositions[0].setColPosition(currentColumnPosition-1);

            //DOES THE SQUARE CONTAIN A PIECE ?
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition-2 >= 1 && currentColumnPosition+1 <=8 && currentRowPosition-2 == targetRowPosition && currentColumnPosition+1 == targetColumnPosition ){
//            possiblePositions[1].setRowPosition(currentRowPosition-2);
//            possiblePositions[1].setColPosition(currentColumnPosition+1);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition-1 >= 1 && currentColumnPosition+2 <=8 && currentRowPosition-1 == targetRowPosition && currentColumnPosition+2 == targetColumnPosition ){
//            possiblePositions[2].setRowPosition(currentRowPosition-1);
//            possiblePositions[2].setColPosition(currentColumnPosition+2);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition+1 <= 8 && currentColumnPosition+2 <=8 && currentRowPosition+1 == targetRowPosition && currentColumnPosition+2 == targetColumnPosition ){
//            possiblePositions[3].setRowPosition(currentRowPosition+1);
//            possiblePositions[3].setColPosition(currentColumnPosition+2);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition+2 <= 8 && currentColumnPosition+1 <=8 && currentRowPosition+2 == targetRowPosition && currentColumnPosition+1 == targetColumnPosition ){
//            possiblePositions[4].setRowPosition(currentRowPosition+2);
//            possiblePositions[4].setColPosition(currentColumnPosition+1);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition+2 <= 8 && currentColumnPosition-1 >=1 && currentRowPosition+2 == targetRowPosition && currentColumnPosition-1 == targetColumnPosition ){
//            possiblePositions[5].setRowPosition(currentRowPosition+2);
//            possiblePositions[5].setColPosition(currentColumnPosition-1);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition+1 <= 8 && currentColumnPosition-2 >=1 && currentRowPosition+1 == targetRowPosition && currentColumnPosition-2 == targetColumnPosition ){
//            possiblePositions[6].setRowPosition(currentRowPosition+1);
//            possiblePositions[6].setColPosition(currentColumnPosition-2);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }
        if( currentRowPosition-1 >= 1 && currentColumnPosition-2 >=1 && currentRowPosition-1 == targetRowPosition && currentColumnPosition-2 == targetColumnPosition ){
//            possiblePositions[7].setRowPosition(currentRowPosition-1);
//            possiblePositions[7].setColPosition(currentColumnPosition-2);
            if(board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == true){
                //IS THE COLOR OF THAT PIECE AS THE ONE I HAVE IN HAND?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() != this.selectedPiece.isWhite()  ){
                    validate++;
                }
            } else {
                validate++;
            }
        }

        if(validate != 0){
            return true;
        } else {
            return false;
        }
    };

    //Possible moves of bishop relative to current position
    public boolean validateBishopMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){

        ArrayList<Position> northEastPossibilities = new ArrayList<>();
        ArrayList<Position> southEastPossibilities = new ArrayList<>();
        ArrayList<Position> southWestPossibilities = new ArrayList<>();
        ArrayList<Position> northWestPossibilities = new ArrayList<>();

        //LOOK NORTH EAST
        int ner = currentRowPosition-1; //ner= north-east row
        int nec = currentColumnPosition+1; //nec= north-east column
        while( ner>=1 && nec <=8 ){
            //IS THERE A PIECE?
            if( board.getAllSquares()[ner][nec].getContainsPiece()==true ){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) board.getAllSquares()[ner][nec].getComponents()[1]).isWhite == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    northEastPossibilities.add(new Position(ner, nec));
                    break;
                }
            } else {
                //there is no piece, then
                northEastPossibilities.add(new Position(ner, nec));
            }
            ner--;
            nec++;
        }

        //LOOK SOUTH EAST
        int ser = currentRowPosition+1;
        int sec = currentColumnPosition+1;
        while( ser<=8 && sec <=8 ){
            //IS THERE A PIECE?
            if( board.getAllSquares()[ser][sec].getContainsPiece()==true ){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) board.getAllSquares()[ser][sec].getComponents()[1]).isWhite == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    southEastPossibilities.add(new Position(ser,sec));
                    break;
                }
            } else {
                //there is no piece, then
                southEastPossibilities.add(new Position(ser,sec));
            }
            ser++;
            sec++;
        }

        //LOOK SOUTH WEST
        int swr = currentRowPosition+1;
        int swc = currentColumnPosition-1;
        while( swr<=8 && swc >=1 ){
            //IS THERE A PIECE?
            if( board.getAllSquares()[swr][swc].getContainsPiece()==true ){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) board.getAllSquares()[swr][swc].getComponents()[1]).isWhite == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    southWestPossibilities.add(new Position(swr,swc));
                    break;
                }
            } else {
                //there is no piece, then
                southWestPossibilities.add(new Position(swr,swc));
            }
            swr++;
            swc--;
        }

        //LOOK NORTH WEST
        int nwr = currentRowPosition-1;
        int nwc = currentColumnPosition-1;
        while( nwr>=1 && nwc >=1 ){
            //IS THERE A PIECE?
            if( board.getAllSquares()[nwr][nwc].getContainsPiece()==true ){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) board.getAllSquares()[nwr][nwc].getComponents()[1]).isWhite == this.selectedPiece.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    northWestPossibilities.add(new Position(nwr,nwc));
                    break;
                }
            } else {
                //there is no piece, then
                northWestPossibilities.add(new Position(nwr,nwc));
            }
            nwr--;
            nwc--;
        }

        System.out.println("WHERE CAN I MOVE TO NORTH EAST: ");
        System.out.println(northEastPossibilities);
        System.out.println("WHERE CAN I MOVE TO SOUTH EAST: ");
        System.out.println(southEastPossibilities);
        System.out.println("WHERE CAN I MOVE TO SOUTH WEST: ");
        System.out.println(southWestPossibilities);
        System.out.println("WHERE CAN I MOVE TO NORTH WEST: ");
        System.out.println(northWestPossibilities);


        Position targetPosition = new Position(targetRowPosition, targetColumnPosition);

        int validate = 0;

        for (int index =0; index < northEastPossibilities.size(); index++){
            if ( northEastPossibilities.get(index).getRowPosition() == targetRowPosition && northEastPossibilities.get(index).getColPosition() == targetColumnPosition){
                validate++;
            }
        }
        for (int index =0; index < southEastPossibilities.size(); index++){
            if ( southEastPossibilities.get(index).getRowPosition() == targetRowPosition && southEastPossibilities.get(index).getColPosition() == targetColumnPosition){
                validate++;
            }
        }
        for (int index =0; index < southWestPossibilities.size(); index++){
            if ( southWestPossibilities.get(index).getRowPosition() == targetRowPosition && southWestPossibilities.get(index).getColPosition() == targetColumnPosition){
                validate++;
            }
        }
        for (int index =0; index < northWestPossibilities.size(); index++){
            if ( northWestPossibilities.get(index).getRowPosition() == targetRowPosition && northWestPossibilities.get(index).getColPosition() == targetColumnPosition){
                validate++;
            }
        }

        if( validate==0){
            return false;
        } else {
            return true;
        }
    };

    public boolean validateQueenMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){
        boolean asRook = validateRookMove(currentRowPosition, currentColumnPosition, targetRowPosition, targetColumnPosition);
        boolean asBishop = validateBishopMove(currentRowPosition, currentColumnPosition, targetRowPosition, targetColumnPosition);
        if( asRook == true || asBishop == true){
            return true;
        } else {
            return false;
        }
    };





    //below is the class' brackets
}

