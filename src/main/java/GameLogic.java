import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.util.ArrayList;

//THIS SHOULD PROBABLY BECOME SOME SORT OF CLASS THAT CONTAINS ALL METHODS WHO GENERATE MOVEMENT LOGIC IN THE FUTURE.
public class GameLogic {

    private String GAMETURN = "white";
    public JFrame frame;
    public Board board;
    public Board imaginaryBoard;
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
    public GameLogic(Board board, Board imaginaryBoard) {
        this.board = board;
        this.imaginaryBoard = imaginaryBoard;
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
            getAllPiecesOfThisColor(true, this.board).forEach(piece -> {
                piece.setCanBeMoved(false);
            });
            getAllPiecesOfThisColor(false, this.board).forEach(piece -> {
                piece.setCanBeMoved(true);
            });
            this.setGAMETURN("black"); //-> black is next
            System.out.println("Black moves now ->");
        } else {
            //black finished the turn
            //deactivate black pieces + activate white pieces
            getAllPiecesOfThisColor(false, this.board).forEach(piece -> {
                piece.setCanBeMoved(false);
            });
            getAllPiecesOfThisColor(true, this.board).forEach(piece -> {
                piece.setCanBeMoved(true);
            });
            this.setGAMETURN("white"); //-> white is next
            System.out.println("White moves now ->");
        }
    }

    public void placePiece(int rowPosition, int columnPosition) {
        //TODO: I will need to think about how to properly do this below:
//        if(this.selectedPiece.getName() == "rook"){
//            ((Rook)this.selectedPiece).setRookHasMovedBeforeCastling(true);
//        }

        //Set false to contains piece for previous square
//        this.board.getAllSquares()[this.selectedPiece.getPiecePosition().getRowPosition()][this.selectedPiece.getPiecePosition().getColPosition()].setContainsPiece(false);

        //Assign selected piece to new square
        this.board.getAllSquares()[rowPosition][columnPosition].add(this.selectedPiece);

        //Assign new coordinates for the recently placed piece
        this.selectedPiece.setPiecePosition(new Position(rowPosition, columnPosition));

        //Set true to contains piece for new square
        this.board.getAllSquares()[rowPosition][columnPosition].setContainsPiece(true);

        this.frame.repaint();
        finishTurn();
        ungrabPiece();
    }

    public void endGame(){
        ungrabPiece();
        getAllPiecesOfThisColor(true, this.board).forEach(piece -> {
            piece.setCanBeMoved(false);
        });
        getAllPiecesOfThisColor(false, this.board).forEach(piece -> {
            piece.setCanBeMoved(false);
        });
        System.out.println(" --- CHECKMATE --- ");
    }



    //TODO: this function is a mess
    public void whyAmIPressing(int targetRowPosition, int targetColumnPosition) {

        System.out.println("*** TEST ***");
        System.out.println("This is the square: "+this.board.getAllSquares()[8][8]);
        System.out.println("This is how many things a square contains: a label on 0 , a piece on 1 : "+this.board.getAllSquares()[8][8].getComponents().length );
        System.out.println("To access the piece on the square: "+ this.board.getAllSquares()[8][8].getComponents()[1] );
        System.out.println("*** END TEST ***");

        //I got a piece in hand, just pressed a square, I want to move my piece there.
        //I have a validator, how happy I am
        Position myTargetPosition = new Position(targetRowPosition, targetColumnPosition);
        ArrayList<Position> validPositions = evaluatePossibleMoves();

        //Am I checkmated?
        if(validPositions.size() == 0){
            endGame();
        }

        //Is the move valid ?
        for (Position validPosition : validPositions){

            if(myTargetPosition.equals(validPosition)){
                //Yes, the move is valid, I can find it here in all the valid positions.

                //Is the target square empty?
                if(!this.board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece()){
                    //Yes, it is empty
                    placePiece(targetRowPosition,targetColumnPosition);
                    System.out.println("Sequence will be " + sequence+": you need to grab a piece"); //should be 1 now
                } else {
                    //So the square is not empty
                    //Do I want to slay a king ? - this will not be allowed. No kingSlayers around here
                    if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).getName() == "king" ){
                        ungrabPiece();
                        System.out.println("You tried to kill a king. Your punishment is guillotine");
                    } else {
                        //So the square is still not empty & the targeted piece is not a king
                        //Is the piece in my hand same color as the one on the target square ?
                        if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                            System.out.println("I can't place a piece of my own over another piece of my own. I need to reselect my piece. Computer punishes!");
                            ungrabPiece();
                            System.out.println("Sequence will be " + sequence+": you need to grab a piece");
                        } else {
                            System.out.println("Not so sure about capturing logic but here we go ...you murderer!");
                            slay(targetRowPosition, targetColumnPosition);
                            System.out.println("Sequence will be " + sequence+": you need to grab a piece");
                        }
                    }
                }
            }
        }


//        //What kind of piece do I have in my hand ?
//        String pieceType = selectedPiece.getName();
//        boolean isValid = false;
//        if(pieceType == "pawn"){
//            System.out.println("I should implement logic for pawn move validation");
//            isValid = false;
//        } else if (pieceType == "rook") {
//            //Do i have any computed positions for my rook, relative to my current position ? ->I can get a list of all of them.
//            if( getRookPositions(this.selectedPiece, this.board).size() != 0) {
//                //Going through the list of positions
//                for (Position target : getRookPositions(this.selectedPiece, this.board) ){
//                    //Is my selected position found among the computed positions ?
//                    if (target.getRowPosition() == targetRowPosition && target.getColPosition() == targetColumnPosition) {
//                        isValid = true;                                                                 //TODO: HERE I NEED TO ASK MYSELF IF MY TARGET IS A KING !!!!
//                        break;
//                    }
//                }
//            }
//        } else if (pieceType == "knight") {
//            //Do i have any computed positions for my knight, relative to my current position ? ->I can get a list of all of them.
//            if( getKnightPositions(this.selectedPiece, this.board).size() != 0) {
//                //Going through the list of positions
//                for (Position target : getKnightPositions(this.selectedPiece, this.board) ){
//                    //Is my selected position found among the computed positions ?
//                    if (target.getRowPosition() == targetRowPosition && target.getColPosition() == targetColumnPosition) {
//                        isValid = true;                                                                 //TODO: HERE I NEED TO ASK MYSELF IF MY TARGET IS A KING !!!!
//                        break;
//                    }
//                }
//            }
//        } else if (pieceType == "bishop") {
//            //Do i have any computed positions for my bishop, relative to my current position ? ->I can get a list of all of them.
//            if( getBishopPositions(this.selectedPiece, this.board).size() != 0) {
//                //Going through the list of positions
//                for (Position target : getBishopPositions(this.selectedPiece, this.board) ){
//                    //Is my selected position found among the computed positions ?
//                    if (target.getRowPosition() == targetRowPosition && target.getColPosition() == targetColumnPosition) {
//                        isValid = true;                                                                 //TODO: HERE I NEED TO ASK MYSELF IF MY TARGET IS A KING !!!!
//                        break;
//                    }
//                }
//            }
//        } else if (pieceType == "queen") {
//            //Do i have any computed positions for my queen, relative to my current position ? ->I can get a list of all of them.
//            if( getQueenPositions(this.selectedPiece, this.board).size() != 0) {
//                //Going through the list of positions
//                for (Position target : getQueenPositions(this.selectedPiece, this.board) ){
//                    //Is my selected position found among the computed positions ?
//                    if (target.getRowPosition() == targetRowPosition && target.getColPosition() == targetColumnPosition) {
//                        isValid = true;                                                                 //TODO: HERE I NEED TO ASK MYSELF IF MY TARGET IS A KING !!!!
//                        break;
//                    }
//                }
//            }
//        } else if (pieceType == "king") {
//            System.out.println("I should implement logic for king move validation");
//            isValid = false;
//        }
//
//        //Is the move valid ?
//        if(isValid == false){
//            ungrabPiece();
//            System.out.println("That move is illegal. Please reselect a piece");
//        } else {
//            //Is the target square empty?
//            if (board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece() == false){
//                placePiece(targetRowPosition, targetColumnPosition); //if empty, I will place my piece here
//                System.out.println("Sequence will be " + sequence+": you need to grab a piece"); //should be 1 now
//            } else {
//                //Is that an enemy I am targeting or friendly piece ?
//                if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
//                    System.out.println("I can't place a piece of my own over another piece of my own. I need to reselect my piece");
//                    ungrabPiece();
//                    System.out.println("Sequence will be " + sequence+": you need to grab a piece");
//                } else {
//                    System.out.println("Not so sure about capturing logic but here we go ...");
//                    slay(targetRowPosition, targetColumnPosition);
//                    System.out.println("Sequence will be " + sequence+": you need to grab a piece");
//                }
//            }
//        }


    }




    //TODO: learn how to change color of TODO lol
    public void slay(int rowPosition, int columnPosition){
        board.getAllSquares()[rowPosition][columnPosition].remove(1);
        board.getAllSquares()[rowPosition][columnPosition].setContainsPiece(false);
//        ((Piece) board.getAllSquares()[rowPosition][rowPosition].getComponents()[1]) = null; //TODO: how do I remove a piece correctly ?
        placePiece(rowPosition, columnPosition);
    }









    //TODO: COMPUTATIONS #######################################################################################################################################################

    //UTILITY - get all pieces of the same color true=WHITE & false=BLACK
    public ArrayList<Piece> getAllPiecesOfThisColor(boolean isWhite, Board currentBoard){
        if(isWhite){
            ArrayList<Piece> allWhitePieces = new ArrayList<>();
            for(int i=1; i<=8;i++){
                for(int j=1;j<=8;j++){
                    if(currentBoard.getAllSquares()[i][j].getContainsPiece()){
                        if( ((Piece) currentBoard.getAllSquares()[i][j].getComponents()[1]).isWhite() == isWhite ) {
                            allWhitePieces.add(  ((Piece) currentBoard.getAllSquares()[i][j].getComponents()[1]) );
                        }
                    }
                }
            }
            return allWhitePieces;
        } else {
            ArrayList<Piece> allBlackPieces = new ArrayList<>();
            for(int i=1; i<=8;i++){
                for(int j=1;j<=8;j++){
                    if(currentBoard.getAllSquares()[i][j].getContainsPiece()) {
                        if( ((Piece) currentBoard.getAllSquares()[i][j].getComponents()[1]).isWhite() == isWhite ) {
                            allBlackPieces.add(  ((Piece) currentBoard.getAllSquares()[i][j].getComponents()[1]) );
                        }
                    }
                }
            }
            return allBlackPieces;
        }
    };

    //TODO: IMPORTANT: de verificat getAllPiecesOfThisColor, ca nu cred ca primeste isWhite-ul bun - ma refer de verificat usageurile si ce fel de culori se trimit pe fiecare usage :)
    //How do all squares from the imaginary board look like -> imaginaryBoard.getAllSquares()[rowPosition][columnPosition]
    //How does a square look like:                          -> board.getAllSquares()[rowPosition][columnPosition]
    //How does a piece look like:                           -> ((Piece) board.getAllSquares()[i][j].getComponents()[1])

    //GET Possible moves of rook relative to current position
    //TODO: IMPORTANT!!! -> this method will also return an attacked position in which the opponent king is
    public int utilizationOfthisfunction=0;
    public ArrayList<Position> getRookPositions(Piece currentRook, Board currentBoard){
        utilizationOfthisfunction++;
        System.out.println("I have used this function this times:  "+utilizationOfthisfunction+" with board "+currentBoard.getBoardName());
        Position currentPosition = currentRook.getPiecePosition();
        //targets - an array of positions that are attacked by the rook
        ArrayList<Position> targets = new ArrayList<>();

        //Compute horizontally NEGATIVE (LEFT)
        int horizontalNegative = currentPosition.getColPosition()-1;
        while (horizontalNegative >= 1 ) {
            //IS THERE A PIECE?
            if(currentBoard.getAllSquares()[currentPosition.getRowPosition()][horizontalNegative].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                //TODO: here i have the error ...
                if(((Piece) currentBoard.getAllSquares()[currentPosition.getRowPosition()][horizontalNegative].getComponents()[1]).isWhite() == currentRook.isWhite() ) {
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
            if(currentBoard.getAllSquares()[currentPosition.getRowPosition()][horizontalPositive].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) currentBoard.getAllSquares()[currentPosition.getRowPosition()][horizontalPositive].getComponents()[1]).isWhite() == currentRook.isWhite()) {
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
            if(currentBoard.getAllSquares()[verticalPositive][currentPosition.getColPosition()].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) currentBoard.getAllSquares()[verticalPositive][currentPosition.getColPosition()].getComponents()[1]).isWhite() == currentRook.isWhite()) {
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
            if(currentBoard.getAllSquares()[verticalNegative][currentPosition.getColPosition()].getContainsPiece()) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if(((Piece) currentBoard.getAllSquares()[verticalNegative][currentPosition.getColPosition()].getComponents()[1]).isWhite() == currentRook.isWhite()) {
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
    public ArrayList<Position> getKnightPositions(Piece currentKnight, Board currentBoard){
        int currentRowPosition = currentKnight.getPiecePosition().getRowPosition();
        int currentColumnPosition = currentKnight.getPiecePosition().getColPosition();
        ArrayList<Position> targets = new ArrayList<>();
        //IS THE TARGET SQUARE REACHABLE FOR THE KNIGHT ?
        if( currentRowPosition-2 >= 1 && currentColumnPosition-1 >=1 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition - 2][currentColumnPosition - 1].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition-2][currentColumnPosition-1].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition-2,currentColumnPosition-1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-2,currentColumnPosition-1));
            }
        }
        if( currentRowPosition-2 >= 1 && currentColumnPosition+1 <=8 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition - 2][currentColumnPosition + 1].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition-2][currentColumnPosition+1].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition-2,currentColumnPosition+1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-2,currentColumnPosition+1));
            }
        }
        if( currentRowPosition-1 >= 1 && currentColumnPosition+2 <=8 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition - 1][currentColumnPosition + 2].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition-1][currentColumnPosition+2].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition-1,currentColumnPosition+2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-1,currentColumnPosition+2));
            }
        }
        if( currentRowPosition+1 <= 8 && currentColumnPosition+2 <=8 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition + 1][currentColumnPosition + 2].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition+1][currentColumnPosition+2].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition+1,currentColumnPosition+2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+1,currentColumnPosition+2));
            }
        }
        if( currentRowPosition+2 <= 8 && currentColumnPosition+1 <=8 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition + 2][currentColumnPosition + 1].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition+2][currentColumnPosition+1].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition+2,currentColumnPosition+1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+2,currentColumnPosition+1));
            }
        }
        if( currentRowPosition+2 <= 8 && currentColumnPosition-1 >=1 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition + 2][currentColumnPosition - 1].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition+2][currentColumnPosition-1].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition+2,currentColumnPosition-1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+2,currentColumnPosition-1));
            }
        }
        if( currentRowPosition+1 <= 8 && currentColumnPosition-2 >=1 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition + 1][currentColumnPosition - 2].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition+1][currentColumnPosition-2].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition+1,currentColumnPosition-2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+1,currentColumnPosition-2));
            }
        }
        if( currentRowPosition-1 >= 1 && currentColumnPosition-2 >=1 ){
            //Is there a piece ?
            if (currentBoard.getAllSquares()[currentRowPosition - 1][currentColumnPosition - 2].getContainsPiece()) {
                //Is that piece a different color compared to the one in my hand ?
                if ( ((Piece) currentBoard.getAllSquares()[currentRowPosition-1][currentColumnPosition-2].getComponents()[1]).isWhite != currentKnight.isWhite() ){
                    targets.add(new Position(currentRowPosition-1,currentColumnPosition-2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-1,currentColumnPosition-2));
            }
        }
        return targets;
    };

    //Possible moves of bishop relative to current position
    public ArrayList<Position> getBishopPositions(Piece currentBishop, Board currentBoard){
        ArrayList<Position> targets = new ArrayList<>();
        int currentRowPosition = currentBishop.getPiecePosition().getRowPosition();
        int currentColumnPosition = currentBishop.getPiecePosition().getColPosition();

        //LOOK NORTH EAST
        int ner = currentRowPosition-1; //ner= north-east row
        int nec = currentColumnPosition+1; //nec= north-east column
        while( ner>=1 && nec <=8 ){
            //IS THERE A PIECE?
            if(currentBoard.getAllSquares()[ner][nec].getContainsPiece()){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) currentBoard.getAllSquares()[ner][nec].getComponents()[1]).isWhite == currentBishop.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    targets.add(new Position(ner, nec));
                    break;
                }
            } else {
                //there is no piece, then
                targets.add(new Position(ner, nec));
            }
            ner--;
            nec++;
        }

        //LOOK SOUTH EAST
        int ser = currentRowPosition+1;
        int sec = currentColumnPosition+1;
        while( ser<=8 && sec <=8 ){
            //IS THERE A PIECE?
            if(currentBoard.getAllSquares()[ser][sec].getContainsPiece()){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) currentBoard.getAllSquares()[ser][sec].getComponents()[1]).isWhite == currentBishop.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    targets.add(new Position(ser,sec));
                    break;
                }
            } else {
                //there is no piece, then
                targets.add(new Position(ser,sec));
            }
            ser++;
            sec++;
        }

        //LOOK SOUTH WEST
        int swr = currentRowPosition+1;
        int swc = currentColumnPosition-1;
        while( swr<=8 && swc >=1 ){
            //IS THERE A PIECE?
            if(currentBoard.getAllSquares()[swr][swc].getContainsPiece()){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) currentBoard.getAllSquares()[swr][swc].getComponents()[1]).isWhite == currentBishop.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    targets.add(new Position(swr,swc));
                    break;
                }
            } else {
                //there is no piece, then
                targets.add(new Position(swr,swc));
            }
            swr++;
            swc--;
        }

        //LOOK NORTH WEST
        int nwr = currentRowPosition-1;
        int nwc = currentColumnPosition-1;
        while( nwr>=1 && nwc >=1 ){
            //IS THERE A PIECE?
            if(currentBoard.getAllSquares()[nwr][nwc].getContainsPiece()){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( ((Piece) currentBoard.getAllSquares()[nwr][nwc].getComponents()[1]).isWhite == currentBishop.isWhite() ){
                    break;
                } else {
                    //i just encountered an enemy
                    targets.add(new Position(nwr,nwc));
                    break;
                }
            } else {
                //there is no piece, then
                targets.add(new Position(nwr,nwc));
            }
            nwr--;
            nwc--;
        }
        return targets;
    };

    //Possible moves of queen relative to current position
    public ArrayList<Position> getQueenPositions(Piece currentQueen, Board currentBoard){
        ArrayList<Position> targetsAsRook = getRookPositions(currentQueen, currentBoard);
        ArrayList<Position> targetsAsBishop = getBishopPositions(currentQueen, currentBoard);
        ArrayList<Position> queenTargets = new ArrayList<>();
        //I just wat to have the targets in the same vector
        for (Position target : targetsAsRook ){
            queenTargets.add(target);
        }
        for (Position target : targetsAsBishop ){
            queenTargets.add(target);
        }
        return queenTargets;
    };


    public ArrayList<Position> getAllAttackedSquaresByOpponent(Board currentBoard){

        //Aux transformation -> from string to boolean LMAO
        boolean color;
        if( getGAMETURN() == "white"){
            color = false;
        } else {
            color = true;
        }

        ArrayList<Piece> allOpponentPieces = getAllPiecesOfThisColor(color, currentBoard);
        ArrayList<Position> allOpponentPossibleAttacks = new ArrayList<>();

        int a = 0;
        for (Piece piece : allOpponentPieces){

            if( piece.getName() == "rook" ){

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getRookPositions(piece,currentBoard)){
                    allOpponentPossibleAttacks.add(position);
                }
                a++;
            } else if (piece.getName() == "knight") {

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getKnightPositions(piece,currentBoard)){
                    allOpponentPossibleAttacks.add(position);
                }

            } else if (piece.getName() == "bishop") {

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getBishopPositions(piece,currentBoard)){
                    allOpponentPossibleAttacks.add(position);
                }

            } else if (piece.getName() == "queen") {

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getQueenPositions(piece,currentBoard)){
                    allOpponentPossibleAttacks.add(position);
                }
            }
        }

        System.out.println("UITE DE ATATEA ORI:   "+a);
        //TODO: this whole array of possitions may contain duplicates
        return allOpponentPossibleAttacks;
    }

    public ArrayList<Position> evaluatePossibleMoves(){
        ArrayList<Position> validPositions = new ArrayList<>();
        //Aux transformation -> from string to boolean LMAO
        boolean color;
        boolean oppositeColor;
        if( getGAMETURN() == "white"){
            color = true;
            oppositeColor = false;
        } else {
            color = false;
            oppositeColor = true;
        }

        System.out.println(" LA  FIND 2");

        ArrayList<Piece> allMyPieces = getAllPiecesOfThisColor(color, this.board); //PRIMUL APEL CRONOLOGIC AL FUNCTIEI

        System.out.println(allMyPieces);

        System.out.println(" LA  FIND 3");

        ArrayList<Piece> allOpponentPieces = getAllPiecesOfThisColor(oppositeColor, this.board); //AL DOILEA APEL CRONOLOGIC AL FUNCTIEI

        //I have all my pieces. I want each position of each piece to compute.
        System.out.println("*** TEST ***");
        System.out.println("This is the square: "+this.board.getAllSquares()[8][8]);
        System.out.println("This is how many things a square contains: a label on 0 , a piece on 1 : "+this.board.getAllSquares()[8][8].getComponents().length );
        System.out.println("To access the piece on the square: "+ this.board.getAllSquares()[8][8].getComponents()[1] );
        System.out.println("*** END TEST ***");
        //For each piece...
        for (Piece piece : allMyPieces){ //GET PIECE i
            System.out.println(piece.getName());
            //What piece type do I have?
            if( piece.getName() == "rook" ){

                //Get all positions for the rook
                System.out.println("Print me. get rook positions with real will be next");
                ArrayList<Position> rookPositions = getRookPositions(piece, this.board); //-> i need to know the ''real'' moves
                //For each position...
                int indexDeVerificare = 0;
                for( Position position : rookPositions ){ //GET POSITION j
                    System.out.println(position.toString());
                    //CREATE imaginary board - refresh - clean
                    assignImaginaryBoardCurrentPieces(allMyPieces,allOpponentPieces); //TODO: THIS HAPPENS FOR EACH POSITION ! //aici nu se foloseste getRookPositions

//                    System.out.println("*** TEST ***");
//                    System.out.println("Oare dupa assignment, mi le fura practic din tabla reala si le aseaza pe cea imaginara si tabla reala ramane goala ?");
//                    System.out.println("This is the square: "+this.board.getAllSquares()[8][8]);
//                    System.out.println("This is how many things a square contains: a label on 0 , a piece on 1 : "+this.board.getAllSquares()[8][8].getComponents().length );
//                    System.out.println("To access the piece on the square: "+ this.board.getAllSquares()[8][8].getComponents()[1] );
//                    System.out.println("*** END TEST ***");
                    //PLACE piece i on position j on the imaginary board
                    placePieceIonPositionJonImaginaryBoard(piece,position); //Nici aici nu se foloseste getRookPositions

                    //GET all attacked squares by the opponent -> in the context of the imaginary move
                    System.out.println("Deci ... -> "+ indexDeVerificare);
                    ArrayList<Position> dangerZone = getAllAttackedSquaresByOpponent(this.imaginaryBoard); //Foloseste get rook positions de 3 ori pentru ca o utilizeaza si queen as rook gg
                    indexDeVerificare++;
                    //GET all my pieces from the imaginary board // My imaginary arrangement of pieces
                    ArrayList<Piece> myImaginaryArrangement = getAllPiecesOfThisColor(color, this.imaginaryBoard);

                    //FIND my King in the 'imaginary' board
                    Position kingImaginaryPosition = new Position(0,0);
                    for ( Piece thisShouldBeKing : myImaginaryArrangement ) {
                        if (thisShouldBeKing.getName() == "king"){
                            kingImaginaryPosition = thisShouldBeKing.getPiecePosition();
                        }
                    }

                    //Sanity check
                    if( kingImaginaryPosition.equals(new Position(0,0)) ){
                        System.out.println("Huston we have a problem ....");
                    }

                    //Go across all dangerZone
                    for(Position dangerPosition : dangerZone){
                        //Is the position of my KING from the ''imaginary'' board NOT in the danger zone ?
                        if( !kingImaginaryPosition.equals(dangerPosition) ){
                            validPositions.add(position);
                        }
                    }
                }

            } else if (piece.getName() == "knight") {

                //Get all positions for the knight
                ArrayList<Position> knightPositions = getKnightPositions(piece, this.board); //-> i need to know the ''real'' moves
                //For each position...
                for( Position position : knightPositions ){ //GET POSITION j

                    //CREATE imaginary board - refresh - clean
                    assignImaginaryBoardCurrentPieces(allMyPieces,allOpponentPieces); //TODO: THIS HAPPENS FOR EACH POSITION !

                    //PLACE piece i on position j on the imaginary board
                    placePieceIonPositionJonImaginaryBoard(piece,position);

                    //GET all attacked squares by the opponent -> in the context of the imaginary move
                    ArrayList<Position> dangerZone = getAllAttackedSquaresByOpponent(this.imaginaryBoard);

                    //GET all my pieces from the imaginary board // My imaginary arrangement of pieces
                    ArrayList<Piece> myImaginaryArrangement = getAllPiecesOfThisColor(color, this.imaginaryBoard);

                    //FIND my King in the 'imaginary' board
                    Position kingImaginaryPosition = new Position(0,0);
                    for ( Piece thisShouldBeKing : myImaginaryArrangement ) {
                        if (thisShouldBeKing.getName() == "king"){
                            kingImaginaryPosition = thisShouldBeKing.getPiecePosition();
                        }
                    }

                    //Sanity check
                    if( kingImaginaryPosition.equals(new Position(0,0)) ){
                        System.out.println("Huston we have a problem ....");
                    }

                    //Go across all dangerZone
                    for(Position dangerPosition : dangerZone){
                        //Is the position of my KING from the ''imaginary'' board NOT in the danger zone ?
                        if( !kingImaginaryPosition.equals(dangerPosition) ){
                            validPositions.add(position);
                        }
                    }
                }

            } else if (piece.getName() == "bishop") {

                //Get all positions for the bishop
                ArrayList<Position> bishopPositions = getBishopPositions(piece, this.board); //-> i need to know the ''real'' moves
                //For each position...
                for( Position position : bishopPositions ){ //GET POSITION j

                    //CREATE imaginary board - refresh - clean
                    assignImaginaryBoardCurrentPieces(allMyPieces,allOpponentPieces); //TODO: THIS HAPPENS FOR EACH POSITION !

                    //PLACE piece i on position j on the imaginary board
                    placePieceIonPositionJonImaginaryBoard(piece,position);

                    //GET all attacked squares by the opponent -> in the context of the imaginary move
                    ArrayList<Position> dangerZone = getAllAttackedSquaresByOpponent(this.imaginaryBoard);

                    //GET all my pieces from the imaginary board // My imaginary arrangement of pieces
                    ArrayList<Piece> myImaginaryArrangement = getAllPiecesOfThisColor(color, this.imaginaryBoard);

                    //FIND my King in the 'imaginary' board
                    Position kingImaginaryPosition = new Position(0,0);
                    for ( Piece thisShouldBeKing : myImaginaryArrangement ) {
                        if (thisShouldBeKing.getName() == "king"){
                            kingImaginaryPosition = thisShouldBeKing.getPiecePosition();
                        }
                    }

                    //Sanity check
                    if( kingImaginaryPosition.equals(new Position(0,0)) ){
                        System.out.println("Huston we have a problem ....");
                    }

                    //Go across all dangerZone
                    for(Position dangerPosition : dangerZone){
                        //Is the position of my KING from the ''imaginary'' board NOT in the danger zone ?
                        if( !kingImaginaryPosition.equals(dangerPosition) ){
                            validPositions.add(position);
                        }
                    }
                }

            } else if (piece.getName() == "queen") {

                //Get all positions for the queen

                System.out.println("ASTA E FIX INAINTE DE EROARE, URMEAZA SA O PRIND");
                System.out.println("*** TEST ***");
                System.out.println("This is the square: "+this.board.getAllSquares()[8][8]);
                System.out.println("This is how many things a square contains: a label on 0 , a piece on 1 : "+this.board.getAllSquares()[8][8].getComponents().length );
                System.out.println("To access the piece on the square: "+ this.board.getAllSquares()[8][8].getComponents()[1] );
                System.out.println("*** END TEST ***");

                ArrayList<Position> queenPositions = getQueenPositions(piece, this.board); //-> i need to know the ''real'' moves
                //For each position...
                for( Position position : queenPositions ){ //GET POSITION j

                    //CREATE imaginary board - refresh - clean
                    assignImaginaryBoardCurrentPieces(allMyPieces,allOpponentPieces); //TODO: THIS HAPPENS FOR EACH POSITION !

                    //PLACE piece i on position j on the imaginary board
                    placePieceIonPositionJonImaginaryBoard(piece,position);

                    //GET all attacked squares by the opponent -> in the context of the imaginary move
                    ArrayList<Position> dangerZone = getAllAttackedSquaresByOpponent(this.imaginaryBoard);

                    //GET all my pieces from the imaginary board // My imaginary arrangement of pieces
                    ArrayList<Piece> myImaginaryArrangement = getAllPiecesOfThisColor(color, this.imaginaryBoard);

                    //FIND my King in the 'imaginary' board
                    Position kingImaginaryPosition = new Position(0,0);
                    for ( Piece thisShouldBeKing : myImaginaryArrangement ) {
                        if (thisShouldBeKing.getName() == "king"){
                            kingImaginaryPosition = thisShouldBeKing.getPiecePosition();
                        }
                    }

                    //Sanity check
                    if( kingImaginaryPosition.equals(new Position(0,0)) ){
                        System.out.println("Huston we have a problem ....");
                    }

                    //Go across all dangerZone
                    for(Position dangerPosition : dangerZone){
                        //Is the position of my KING from the ''imaginary'' board NOT in the danger zone ?
                        if( !kingImaginaryPosition.equals(dangerPosition) ){
                            validPositions.add(position);
                        }
                    }
                }

            } else if (piece.getName() == "king") {
                System.out.println("I need to implement logic for king");
            } else if (piece.getName() == "pawn") {
                System.out.println("I need to implement logic for pawn");
            }


        }



        //this position list is basically all the squares in which i can move the piece in my hand
        return validPositions;
    };


    //How do all squares from the imaginary board look like -> imaginaryBoard.getAllSquares()[rowPosition][columnPosition]
    //How does a square look like: -> imaginaryBoard.getAllSquares()[rowPosition][columnPosition]
    //How does a piece look like: -> ((Piece) imaginaryBoard.getAllSquares()[i][j].getComponents()[1])

    public void assignImaginaryBoardCurrentPieces(ArrayList<Piece> myPieces, ArrayList<Piece> opponentPieces){
        //TODO: -> CRED CA AM GASIT BUG ? Mai trebuie studiat cum functioneaza lambda functions
        //first I need to clean the board
        for( int i=1; i<=8; i++){
            for ( int j=1; j<=8; j++) {
                //if the imaginary square contains a piece, remove it
                if(((Square)imaginaryBoard.getAllSquares()[i][j]).getContainsPiece()){
                    imaginaryBoard.getAllSquares()[i][j].remove(1);
                    imaginaryBoard.getAllSquares()[i][j].setContainsPiece(false);
                }
            }
        };

        //assigning myPieces and opponentPieces //TODO: EXTREMELY BAD CHOICE TO USE LAMBDA PLEASE ASK OTHER COLLEAGUES WHY THIS DOES NOT WORK
//        myPieces.forEach(piece -> {
//            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
//            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//        });
//        opponentPieces.forEach(piece -> {
//            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
//            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//        });

        for(Piece piece : myPieces) {
            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
        }
        for(Piece piece : opponentPieces) {
            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if ( this.imaginaryBoard.getAllSquares()[i][j].getContainsPiece() ){
                    System.out.println(this.imaginaryBoard.getAllSquares()[i][j]);
                    System.out.println( (this.imaginaryBoard.getAllSquares()[i][j]).getComponents().length );
                }
            }
        }
        System.out.println("Le mai contine realul ?");
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if ( this.board.getAllSquares()[i][j].getContainsPiece() ){
                    System.out.println(this.board.getAllSquares()[i][j]);
                    System.out.println( (this.board.getAllSquares()[i][j]).getComponents().length );
                }
            }
        }

//        System.out.println(((Piece) imaginaryBoard.getAllSquares()[1][1].getComponents()[1]).isWhite());
    }
    public void placePieceIonPositionJonImaginaryBoard(Piece currentPiece, Position currentTargetPosition){
        System.out.println("*** TEST *** -> place Piece I on position J");
        System.out.println("This is the square: "+this.board.getAllSquares()[8][8]);
        System.out.println("This is how many things a square contains: a label on 0 , a piece on 1 : "+this.board.getAllSquares()[8][8].getComponents().length );
        System.out.println("To access the piece on the square: "+ this.board.getAllSquares()[8][8].getComponents()[1] );
        System.out.println("*** END TEST ***");
        //I need to ask myself if a piece was on that imaginary square -> it needs to be slain
        if(((Square) imaginaryBoard.getAllSquares()[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()]).getContainsPiece()){
            //TODO: IMPORTANT! -> the index is 1 because in imaginary squares you contain dummy labels and pieces
            imaginaryBoard.getAllSquares()[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].remove(1);
            imaginaryBoard.getAllSquares()[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].setContainsPiece(false);
        }
        //Set false to contains piece for previous square of current piece
        this.imaginaryBoard.getAllSquares()[currentPiece.getPiecePosition().getRowPosition()][currentPiece.getPiecePosition().getColPosition()].setContainsPiece(false);
        //Assign currentPiece to new square
        this.imaginaryBoard.getAllSquares()[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].add(currentPiece);
        //Assign new coordinates for the recently placed currentPiece
        currentPiece.setPiecePosition(new Position(currentTargetPosition.getRowPosition(), currentTargetPosition.getColPosition()));
        //Set true to contains piece for new square
        this.imaginaryBoard.getAllSquares()[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].setContainsPiece(true);
    };

//    public int isMyKingInCheck(Piece currentKing){
//        //As long as my king's position is within the attacked squares, I am in check
//        ArrayList<Position> attackedSquares =  getAllAttackedSquaresByOpponent(); //TODO: Do I need a method to give me this answer for imaginary positions ?
//        int checkIndex = 0;
//        for (Position attackedsquare : attackedSquares) {
//            if(currentKing.getPiecePosition().equals(attackedsquare)){
//                checkIndex++;
//            }
//        }
//        return checkIndex;
//    };






    //below is the class' brackets
}

