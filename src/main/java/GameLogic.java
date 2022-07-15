import javax.swing.*;
import java.util.ArrayList;

//THIS SHOULD PROBABLY BECOME SOME SORT OF CLASS THAT CONTAINS ALL METHODS WHO GENERATE MOVEMENT LOGIC IN THE FUTURE.


public class GameLogic {

    private String GAMETURN = "white";
    public JFrame frame;
    public Board board;
    public Piece selectedPiece;
    public int sequence = 1;

    public void setFrame(JFrame gameFrame) {
        this.frame = gameFrame;
    }
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
        System.out.println("I selected a "+this.selectedPiece.getName() + " from position: on ROW "+selectedPiece.getPiecePosition().getRowPosition()+" on column: "+selectedPiece.getPiecePosition().getColPosition());
        System.out.println("Sequence will be "+sequence+": you need to place the piece somewhere");
    }
    public void ungrabPiece() {
        this.selectedPiece = null;
        this.setSequence(1);
    }
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
            System.out.println("Black moves now ->");
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
            System.out.println("White moves now ->");
        }
    }


    //TODO: I should probably implement some sort of board reader before each move, to use it to see each king in relationship with all oppponents pieces, if it is in check or not
    //TODO: setCheckMate, Draw (move repetition/ noCheckmate in 50 moves / insufficient material), StaleMate



    public void placePiece(int rowPosition, int columnPosition) {
        //TODO: I will need to think about how to properly do this below:
//        if(this.selectedPiece.getName() == "rook"){
//            ((Rook)this.selectedPiece).setRookHasMovedBeforeCastling(true);
//        }

        //Set false to contains piece for previous square
        this.board.getAllSquares()[this.selectedPiece.getPiecePosition().getRowPosition()][this.selectedPiece.getPiecePosition().getColPosition()].setContainsPiece(false);

        //Assign selected piece to new square
        this.board.getAllSquares()[rowPosition][columnPosition].add(this.selectedPiece);

        //Assign new coordinates for the recently placed piece
        this.selectedPiece.setPiecePosition(new Position(rowPosition, columnPosition));

        //Set true to contains piece for new square
        this.board.getAllSquares()[rowPosition][columnPosition].setContainsPiece(true);

        this.frame.repaint();
        finishTurn(); //TODO: maybe here ? -> enable/disable white/black pieces
        ungrabPiece();
    }



    //TODO: this function is a mess
    public void whyAmIPressing(int targetRowPosition, int targetColumnPosition) {

        //I got a piece in hand, just pressed a square, I want to move my piece there.
        getRookPositions(this.selectedPiece.getPiecePosition() ,targetRowPosition,targetColumnPosition);
        //What kind of piece do I have in my hand ?
        String pieceType = selectedPiece.getName();
        boolean isValid = false;
        if(pieceType == "pawn"){
            System.out.println("I should implement logic for pawn move validation");
            isValid = false;
        } else if (pieceType == "rook") {

            //Do i have any computed positions for my rook ? ->I have a list of all of them.
            if( getRookPositions(this.selectedPiece.getPiecePosition() ,targetRowPosition,targetColumnPosition).size() != 0) {
                //Going through the list of positions
                for (Position target : getRookPositions(this.selectedPiece.getPiecePosition() ,targetRowPosition,targetColumnPosition) ){
                    //Is my selected position found among the computed positions ?
                    if( target.getRowPosition() == targetRowPosition && target.getColPosition() == targetColumnPosition ) {
                        isValid = true;                                                                                     //TODO: HERE I NEED TO ASK MYSELF IF MY TARGET IS A KING !!!!
                    }
                }
            }

        } else if (pieceType == "knight") {
//            isValid = validateKnightMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "bishop") {
//            isValid = validateBishopMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "queen") {
//            isValid = validateQueenMove(this.selectedPiece.rowPosition, this.selectedPiece.columnPosition,targetRowPosition,targetColumnPosition);
        } else if (pieceType == "king") {
            System.out.println("I should implement logic for king move validation");
            isValid = false;
        }

        //Is the move valid ?
        if(isValid == false){
            ungrabPiece();
            System.out.println("That move is illegal. Please reselect a piece");
        } else {
            //Is the target square empty?
            if (board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == false){
                placePiece(targetRowPosition, targetColumnPosition); //if empty, I will place my piece here
                System.out.println("Sequence will be " + sequence+": you need to grab a piece"); //should be 1 now
            } else {
                //Is that an enemy I am targeting or friendly piece ?
                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                    System.out.println("I can't place a piece of my own over another piece of my own. I need to reselect my piece");
                    ungrabPiece();
                    System.out.println("Sequence will be " + sequence+": you need to grab a piece");
                } else {
                    System.out.println("Not so sure about capturing logic but here we go ...");
                    slay(targetRowPosition, targetColumnPosition);
                    System.out.println("Sequence will be " + sequence+": you need to grab a piece");
                }
            }
        }
    }


    //TODO: learn how to change color of TODO lol
    public void slay(int rowPosition, int columnPosition){
        board.getAllSquares()[rowPosition][columnPosition].remove(1);
//        ((Piece) board.getAllSquares()[rowPosition][rowPosition].getComponents()[1]) = null; //TODO: how do I remove a piece correctly ?
        placePiece(rowPosition, columnPosition);
    }


    //TODO: What do i do with this ? below:
    //UTILITY - get all pieces of the same color true=WHITE & false=BLACK
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
            return allBlackPieces;
        }

    };

        //TODO: COMPUTATIONS #######################################################################################################################################################


    //How does a square look like: -> board.getAllSquares()[rowPosition][columnPosition]
    //How does a piece look like: -> ((Piece) board.getAllSquares()[i][j].getComponents()[1])

    //GET Possible moves of rook relative to current position
    //TODO: IMPORTANT!!! -> this method will also return an attacked position in which the opponent king is
    public ArrayList<Position> getRookPositions(Position currentPosition, int targetRowPosition, int targetColumnPosition){
        //targets - an array of positions that are attacked by the rook
        ArrayList<Position> targets = new ArrayList<>();

        //Compute horizontally NEGATIVE (LEFT)
        int horizontalNegative = currentPosition.getColPosition()-1;
        while (horizontalNegative >= 1 ) {
            //IS THERE A PIECE?
            if(board.getAllSquares()[currentPosition.getRowPosition()][horizontalNegative].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) board.getAllSquares()[currentPosition.getRowPosition()][horizontalNegative].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ) {
                    break;
                } else {
                    //I just encountered an enemy
                    //TODO: this enemy might be the opponent's king
                    targets.add(new Position(currentPosition.getRowPosition(), horizontalNegative));
                    break;
                }
            } else {
                //The square is empty
                targets.add(new Position(currentPosition.getRowPosition(), horizontalNegative));
            }
            horizontalNegative--;
        }

        //Compute horizontally POSITIVE (RIGHT)
        int horizontalPositive = currentPosition.getColPosition()+1;
        while (horizontalPositive <= 8 ) {
            //IS THERE A PIECE?
            if(board.getAllSquares()[currentPosition.getRowPosition()][horizontalPositive].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) board.getAllSquares()[currentPosition.getRowPosition()][horizontalPositive].getComponents()[1]).isWhite() == this.selectedPiece.isWhite()) {
                    break;
                } else {
                    //I just encountered an enemy
                    //TODO: this enemy might be the opponent's king
                    targets.add(new Position(currentPosition.getRowPosition(), horizontalPositive));
                    break;
                }
            } else {
                //The square is empty
                targets.add(new Position(currentPosition.getRowPosition(), horizontalPositive));
            }
            horizontalPositive++;
        }

        //Compute vertically POSITIVE (DOWN)
        int verticalPositive = currentPosition.getRowPosition()+1;
        while (verticalPositive <= 8 ) {
            //IS THERE A PIECE?
            if(board.getAllSquares()[verticalPositive][currentPosition.getColPosition()].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) board.getAllSquares()[verticalPositive][currentPosition.getColPosition()].getComponents()[1]).isWhite() == this.selectedPiece.isWhite()) {
                    break;
                } else {
                    //I just encountered an enemy
                    //TODO: this enemy might be the opponent's king
                    targets.add(new Position(verticalPositive, currentPosition.getColPosition()));
                    break;
                }
            } else {
                //The square is empty
                targets.add(new Position(verticalPositive, currentPosition.getColPosition()));
            }
            verticalPositive++;
        }

        //Compute vertically NEGATIVE (UP)
        int verticalNegative = currentPosition.getRowPosition()-1;
        while (verticalNegative >= 1 ) {
            //IS THERE A PIECE?
            if(board.getAllSquares()[verticalNegative][currentPosition.getColPosition()].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) board.getAllSquares()[verticalNegative][currentPosition.getColPosition()].getComponents()[1]).isWhite() == this.selectedPiece.isWhite()) {
                    break;
                } else {
                    //I just encountered an enemy
                    //TODO: this enemy might be the opponent's king
                    targets.add(new Position(verticalNegative, currentPosition.getColPosition()));
                    break;
                }
            } else {
                //The square is empty
                targets.add(new Position(verticalNegative, currentPosition.getColPosition()));
            }
            verticalNegative--;
        }
        return targets;
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

    //Possible moves of queen relative to current position
//    public boolean validateQueenMove(int currentRowPosition, int currentColumnPosition, int targetRowPosition, int targetColumnPosition){
//        boolean asRook = validateRookMove(currentRowPosition, currentColumnPosition, targetRowPosition, targetColumnPosition);
//        boolean asBishop = validateBishopMove(currentRowPosition, currentColumnPosition, targetRowPosition, targetColumnPosition);
//        if( asRook == true || asBishop == true){
//            return true;
//        } else {
//            return false;
//        }
//    };





    //below is the class' brackets
}

