import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//THIS SHOULD PROBABLY BECOME SOME SORT OF CLASS THAT CONTAINS ALL METHODS WHO GENERATE MOVEMENT LOGIC IN THE FUTURE.
//THIS IS GOING TO BE SINGLETON!
public class GameLogic {

    public JFrame gameFrame = new JFrame();
    private String gameTurn = "white";
    private int sequence = 1;
    public Piece selectedPiece;

    //FUNCTIONS FOR ABOVE ATTRIBUTES

    public String getGameTurn() {
        return gameTurn;
    }
    public void setGameTurn(String gameTurn) {
        this.gameTurn = gameTurn;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
    public int getSequence() {
        return sequence;
    }

    public Piece getPiece() {
        return selectedPiece;
    }


    //GAME CONSTRUCTION FUNCTIONS
    ListOfPieces pieceImageList = new ListOfPieces();
    public Square[][] allSquares = new Square[9][9];
    public String printedPosition;
    public int squareDimension = 60;
    public boolean alternativeColor = true;
    public void generateSquares(){
        int generatedSquare = 1; //will be a number from 1 to 64
        for( int i=1; i<=8; i++){
            alternativeColor = !alternativeColor;
            for ( int j=1; j<=8; j++) {
                this.allSquares[i][j] = new Square();    // INITIALIZING A NEW SQUARE OBJECT !!!
                this.allSquares[i][j].setLayout(new BorderLayout());
                if(alternativeColor == true) {
                    this.allSquares[i][j].setBackground(new Color(120,60,24));
                    alternativeColor = false;
                } else {
                    this.allSquares[i][j].setBackground(new Color(210,147,88));
                    alternativeColor = true;
                }
                this.allSquares[i][j].setBounds(j*60,i*60,squareDimension,squareDimension); //set dims and position for each square
                this.allSquares[i][j].setRowPosition(i);
                this.allSquares[i][j].setColumnPosition(j);
                this.printedPosition = i+""+j;

                String generatedSquareString = String.valueOf(generatedSquare);
                JLabel positionLabel = new JLabel(generatedSquareString+"  "+this.printedPosition);
                positionLabel.setHorizontalAlignment(JLabel.CENTER);
                this.allSquares[i][j].add(positionLabel, BorderLayout.SOUTH); //Finalization of generation of squares. Square[][] allSquares;
                gameFrame.add(allSquares[i][j]); //add squares to game frame
                generatedSquare++;
            }
        };
        gameFrame.setTitle("VALI's CHESS GAME");
        gameFrame.setResizable(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(null);
        gameFrame.setSize(600,600);
        gameFrame.setVisible(true);
    }
    public void initializePieces() throws IOException {
        //WHITE PIECES
        this.allSquares[8][1].setPiece(new Rook("rook", new Position(8,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white", true));
        this.allSquares[8][8].setPiece(new Rook("rook", new Position(8,8), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white", true));
        this.allSquares[8][2].setPiece(new Knight("knight", new Position(8,2), new ImageIcon(this.pieceImageList.getListOfPieceImages()[3]), "white",true));
        this.allSquares[8][7].setPiece(new Knight("knight", new Position(8,7), new ImageIcon(this.pieceImageList.getListOfPieceImages()[3]), "white",true));
        this.allSquares[8][3].setPiece(new Bishop("bishop", new Position(8,3), new ImageIcon(this.pieceImageList.getListOfPieceImages()[2]), "white",true));
        this.allSquares[8][6].setPiece(new Bishop("bishop", new Position(8,6), new ImageIcon(this.pieceImageList.getListOfPieceImages()[2]), "white",true));
        this.allSquares[8][4].setPiece(new Queen("queen", new Position(8,4), new ImageIcon(this.pieceImageList.getListOfPieceImages()[1]), "white", true));
        this.allSquares[8][5].setPiece(new King("king", new Position(8,5), new ImageIcon(this.pieceImageList.getListOfPieceImages()[0]), "white", true));

        //BLACK PIECES
        this.allSquares[1][8].setPiece(new Rook("rook", new Position(1,8), new ImageIcon(this.pieceImageList.getListOfPieceImages()[10]), "black", false));
        this.allSquares[1][1].setPiece(new Rook("rook", new Position(1,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[10]), "black", false));
        this.allSquares[1][2].setPiece(new Knight("knight", new Position(1,2), new ImageIcon(this.pieceImageList.getListOfPieceImages()[9]), "black", false));
        this.allSquares[1][7].setPiece(new Knight("knight", new Position(1,7), new ImageIcon(this.pieceImageList.getListOfPieceImages()[9]), "black", false));
        this.allSquares[1][3].setPiece(new Bishop("bishop", new Position(1,3), new ImageIcon(this.pieceImageList.getListOfPieceImages()[8]), "black", false));
        this.allSquares[1][6].setPiece(new Bishop("bishop", new Position(1,6), new ImageIcon(this.pieceImageList.getListOfPieceImages()[8]), "black", false));
        this.allSquares[1][4].setPiece(new Queen("queen", new Position(1,4), new ImageIcon(this.pieceImageList.getListOfPieceImages()[7]), "black",false));
        this.allSquares[1][5].setPiece(new King("king", new Position(1,5), new ImageIcon(this.pieceImageList.getListOfPieceImages()[6]), "black", false));

        this.gameFrame.setVisible(true);
    }

    public Square[][] getAllSquares() {
        return this.allSquares;
    }

    public void grabPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
        this.setSequence(2);
        System.out.println("I selected the "+this.selectedPiece.getColor()+" "+this.selectedPiece.getName() + " from position: on ROW "+selectedPiece.getPiecePosition().getRowPosition()+" on column: "+selectedPiece.getPiecePosition().getColPosition());
        System.out.println("Sequence will be "+sequence+": you need to place the piece somewhere");
    }
    public void ungrabPiece() {
        this.selectedPiece = null;
        this.setSequence(1);
    }

    public void finishTurn(){
        //Who did finish the turn ?
        if( this.selectedPiece.getColor() == "white" ){
            //white finished the turn
            //deactivate white pieces + activate black pieces
            for(Piece piece : getAllPiecesOfThisColor("white", this.getAllSquares()) ){
                piece.setCanBeMoved(false);
            }
            for(Piece piece : getAllPiecesOfThisColor("black", this.getAllSquares()) ){
                piece.setCanBeMoved(true);
            }
            this.setGameTurn("black"); //-> black is next
            System.out.println("Black moves now ->");
        } else {
            //black finished the turn
            //deactivate black pieces + activate white pieces
            for(Piece piece : getAllPiecesOfThisColor("black", this.getAllSquares()) ){
                piece.setCanBeMoved(false);
            }
            for(Piece piece : getAllPiecesOfThisColor("white", this.getAllSquares()) ){
                piece.setCanBeMoved(true);
            }
            this.setGameTurn("white"); //-> white is next
            System.out.println("White moves now ->");
        }
    }

    public void placePiece(int rowPosition, int columnPosition) {

        //Eliminate selected piece from previous square
        this.getAllSquares()[this.selectedPiece.getPiecePosition().getRowPosition()][this.selectedPiece.getPiecePosition().getColPosition()].eliminatePiece(this.selectedPiece);

        //Assign selected piece to new square
        this.getAllSquares()[rowPosition][columnPosition].setPiece(this.selectedPiece);

        //Assign new coordinates for the recently placed piece
        this.selectedPiece.setPiecePosition(new Position(rowPosition, columnPosition));

        this.gameFrame.repaint();
        finishTurn();
        ungrabPiece();
    }

//    public void endGame(){
//        ungrabPiece();
//        getAllPiecesOfThisColor(true, this.board).forEach(piece -> {
//            piece.setCanBeMoved(false);
//        });
//        getAllPiecesOfThisColor(false, this.board).forEach(piece -> {
//            piece.setCanBeMoved(false);
//        });
//        System.out.println(" --- CHECKMATE --- ");
//    }



    //TODO: this function is a mess
    public void whyAmIPressing(int targetRowPosition, int targetColumnPosition) {

        System.out.println(this.allSquares[1][1]);
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                System.out.println(this.getAllSquares()[i][j].getPiece());
//            }
//        }
//        System.out.println(getAllPiecesOfThisColor("black",this.board));
//        ArrayList<Position> testRook = getRookPositions(this.selectedPiece, this.getAllSquares());
//        ArrayList<Position> testKnight = getKnightPositions(this.selectedPiece, this.getAllSquares());
//        ArrayList<Position> testBishop = getBishopPositions(this.selectedPiece, this.getAllSquares());
//        ArrayList<Position> testQueen = getQueenPositions(this.selectedPiece, this.getAllSquares());
//        ArrayList<Position> getAllAttacks = getAllAttackedSquaresByOpponent(this.getAllSquares());
//        for (Position position : getAllAttacks){
//            System.out.println("Attacked square is: "+position);
//        }
//        ArrayList<Position> validPositions = evaluatePossibleMoves();
//        System.out.println(evaluatePossibleMoves());
//        placePiece(targetRowPosition,targetColumnPosition);
        System.out.println("i don't know why i am pressing");
        //I got a piece in hand, just pressed a square, I want to move my piece there.
        //I have a validator, how happy I am
//        Position myTargetPosition = new Position(targetRowPosition, targetColumnPosition);
//        ArrayList<Position> validPositions = evaluatePossibleMoves();

//        //Am I checkmated?
//        if(validPositions.size() == 0){
//            endGame();
//        }
//
//        //Is the move valid ?
//        for (Position validPosition : validPositions){
//
//            if(myTargetPosition.equals(validPosition)){
//                //Yes, the move is valid, I can find it here in all the valid positions.
//
//                //Is the target square empty?
//                if(!this.board.getAllSquares()[targetRowPosition][targetColumnPosition].getContainsPiece()){
//                    //Yes, it is empty
//                    placePiece(targetRowPosition,targetColumnPosition);
//                    System.out.println("Sequence will be " + sequence+": you need to grab a piece"); //should be 1 now
//                } else {
//                    //So the square is not empty
//                    //Do I want to slay a king ? - this will not be allowed. No kingSlayers around here
//                    if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).getName() == "king" ){
//                        ungrabPiece();
//                        System.out.println("You tried to kill a king. Your punishment is guillotine");
//                    } else {
//                        //So the square is still not empty & the targeted piece is not a king
//                        //Is the piece in my hand same color as the one on the target square ?
//                        if( ((Piece) board.getAllSquares()[targetRowPosition][targetColumnPosition].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
//                            System.out.println("I can't place a piece of my own over another piece of my own. I need to reselect my piece. Computer punishes!");
//                            ungrabPiece();
//                            System.out.println("Sequence will be " + sequence+": you need to grab a piece");
//                        } else {
//                            System.out.println("Not so sure about capturing logic but here we go ...you murderer!");
//                            slay(targetRowPosition, targetColumnPosition);
//                            System.out.println("Sequence will be " + sequence+": you need to grab a piece");
//                        }
//                    }
//                }
//            }
//        }





    }




    //TODO: learn how to change color of TODO lol
//    public void slay(int rowPosition, int columnPosition){
//        this.getAllSquares().getAllSquares()[rowPosition][columnPosition].remove(1);
//        this.getAllSquares().getAllSquares()[rowPosition][columnPosition].validate();
//        this.getAllSquares().getAllSquares()[rowPosition][columnPosition].repaint();
//        this.getAllSquares().getAllSquares()[rowPosition][columnPosition].setContainsPiece(false);
////        ((Piece) board.getAllSquares()[rowPosition][rowPosition].getComponents()[1]) = null; //TODO: how do I remove a piece correctly ?
//        placePiece(rowPosition, columnPosition);
//    }









    //TODO: COMPUTATIONS #######################################################################################################################################################


    //UTILITY - get all pieces of the same color WHITE OR BLACK
    public ArrayList<Piece> getAllPiecesOfThisColor(String color, Square[][] currentConfiguration){
        ArrayList<Piece> allWhitePieces = new ArrayList<>();
        ArrayList<Piece> allBlackPieces = new ArrayList<>();
        for(int i=1; i<=8;i++){
            for(int j=1;j<=8;j++){
                //Does this square contain a piece ?
                if(currentConfiguration[i][j].getPiece() != null){
                    //Is the color white ?
                    if( currentConfiguration[i][j].getPiece().getColor() == "white" ) {
                        //Attach that piece to this list
                        allWhitePieces.add( currentConfiguration[i][j].getPiece() );
                    } else {
                        //The color of the piece must be black then,
                        allBlackPieces.add( currentConfiguration[i][j].getPiece() );
                    }
                }
            }
        }
        if(color == "white"){
            return allWhitePieces;
        } else {
            return allBlackPieces;
        }
    };


    //How do all squares from the imaginary board look like -> imaginaryBoard.getAllSquares()[rowPosition][columnPosition]
    //How does a square look like:                          -> board.getAllSquares()[rowPosition][columnPosition]
    //How does a piece look like:                           -> board.getAllSquares()[i][j].getPiece()

    //GET Possible moves of rook relative to current position
    //IMPORTANT!!! -> this method will also return an attacked position in which the opponent king is
    public ArrayList<Position> getRookPositions(Piece currentRook, Square[][] currentConfiguration){

        Position currentPosition = currentRook.getPiecePosition();
        //targets - an array of positions that are attacked by the rook
        ArrayList<Position> targets = new ArrayList<>();

        //Compute horizontally NEGATIVE (LEFT)
        int horizontalNegative = currentPosition.getColPosition()-1;
        while (horizontalNegative >= 1 ) {
            //IS THERE A PIECE?
            if( currentConfiguration[currentPosition.getRowPosition()][horizontalNegative].getPiece() != null) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if( currentConfiguration[currentPosition.getRowPosition()][horizontalNegative].getPiece().getColor() == currentRook.getColor() ) {
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
            if( currentConfiguration[currentPosition.getRowPosition()][horizontalPositive].getPiece() != null ) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if( currentConfiguration[currentPosition.getRowPosition()][horizontalPositive].getPiece().getColor() == currentRook.getColor() ) {
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
            if( currentConfiguration[verticalPositive][currentPosition.getColPosition()].getPiece() != null ) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if( currentConfiguration[verticalPositive][currentPosition.getColPosition()].getPiece().getColor() == currentRook.getColor() ) {
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
            if( currentConfiguration[verticalNegative][currentPosition.getColPosition()].getPiece() != null ) {
                //IS THE EXISTING PIECE SAME COLOR AS MINE ?
                if( currentConfiguration[verticalNegative][currentPosition.getColPosition()].getPiece().getColor() == currentRook.getColor() ) {
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

    //Possible moves of knight relative to current position
    public ArrayList<Position> getKnightPositions(Piece currentKnight, Square[][] currentConfiguration){

        int currentRowPosition = currentKnight.getPiecePosition().getRowPosition();
        int currentColumnPosition = currentKnight.getPiecePosition().getColPosition();
        ArrayList<Position> targets = new ArrayList<>();

        //IS THE TARGET SQUARE REACHABLE FOR THE KNIGHT ?
        if( currentRowPosition-2 >= 1 && currentColumnPosition-1 >=1 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition - 2][currentColumnPosition - 1].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if (  currentConfiguration[currentRowPosition-2][currentColumnPosition-1].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition-2,currentColumnPosition-1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-2,currentColumnPosition-1));
            }
        }
        if( currentRowPosition-2 >= 1 && currentColumnPosition+1 <=8 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition - 2][currentColumnPosition + 1].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if (  currentConfiguration[currentRowPosition-2][currentColumnPosition+1].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition-2,currentColumnPosition+1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-2,currentColumnPosition+1));
            }
        }
        if( currentRowPosition-1 >= 1 && currentColumnPosition+2 <=8 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition - 1][currentColumnPosition + 2].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if ( currentConfiguration[currentRowPosition-1][currentColumnPosition+2].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition-1,currentColumnPosition+2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition-1,currentColumnPosition+2));
            }
        }
        if( currentRowPosition+1 <= 8 && currentColumnPosition+2 <=8 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition + 1][currentColumnPosition + 2].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if ( currentConfiguration[currentRowPosition+1][currentColumnPosition+2].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition+1,currentColumnPosition+2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+1,currentColumnPosition+2));
            }
        }
        if( currentRowPosition+2 <= 8 && currentColumnPosition+1 <=8 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition + 2][currentColumnPosition + 1].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if ( currentConfiguration[currentRowPosition+2][currentColumnPosition+1].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition+2,currentColumnPosition+1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+2,currentColumnPosition+1));
            }
        }
        if( currentRowPosition+2 <= 8 && currentColumnPosition-1 >=1 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition + 2][currentColumnPosition - 1].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if ( currentConfiguration[currentRowPosition+2][currentColumnPosition-1].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition+2,currentColumnPosition-1));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+2,currentColumnPosition-1));
            }
        }
        if( currentRowPosition+1 <= 8 && currentColumnPosition-2 >=1 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition + 1][currentColumnPosition - 2].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if ( currentConfiguration[currentRowPosition+1][currentColumnPosition-2].getPiece().getColor() != currentKnight.getColor() ){
                    targets.add(new Position(currentRowPosition+1,currentColumnPosition-2));
                }
            } else {
                //the square was empty
                targets.add(new Position(currentRowPosition+1,currentColumnPosition-2));
            }
        }
        if( currentRowPosition-1 >= 1 && currentColumnPosition-2 >=1 ){
            //Is there a piece ?
            if (currentConfiguration[currentRowPosition - 1][currentColumnPosition - 2].getPiece() != null ) {
                //Is that piece a different color compared to the one in my hand ?
                if ( currentConfiguration[currentRowPosition-1][currentColumnPosition-2].getPiece().getColor() != currentKnight.getColor() ){
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
    public ArrayList<Position> getBishopPositions(Piece currentBishop, Square[][] currentConfiguration){
        ArrayList<Position> targets = new ArrayList<>();
        int currentRowPosition = currentBishop.getPiecePosition().getRowPosition();
        int currentColumnPosition = currentBishop.getPiecePosition().getColPosition();

        //LOOK NORTH EAST
        int ner = currentRowPosition-1; //ner= north-east row
        int nec = currentColumnPosition+1; //nec= north-east column
        while( ner>=1 && nec <=8 ){
            //IS THERE A PIECE?
            if(currentConfiguration[ner][nec].getPiece() != null ){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( currentConfiguration[ner][nec].getPiece().getColor() == currentBishop.getColor() ){
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
            if(currentConfiguration[ser][sec].getPiece() != null){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( currentConfiguration[ser][sec].getPiece().getColor() == currentBishop.getColor() ){
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
            if(currentConfiguration[swr][swc].getPiece() != null){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( currentConfiguration[swr][swc].getPiece().getColor() == currentBishop.getColor() ){
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
            if(currentConfiguration[nwr][nwc].getPiece() != null){
                //IS THAT PIECE SAME COLOR AS THE ONE IN MY HAND ?
                if( currentConfiguration[nwr][nwc].getPiece().getColor() == currentBishop.getColor() ){
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
    public ArrayList<Position> getQueenPositions(Piece currentQueen, Square[][] currentConfiguration){
        ArrayList<Position> targetsAsRook = getRookPositions(currentQueen, currentConfiguration);
        ArrayList<Position> targetsAsBishop = getBishopPositions(currentQueen, currentConfiguration);
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


    public ArrayList<Position> getAllAttackedSquaresByOpponent(Square[][] currentConfiguration){

        //Aux transformation
        String opponentColor;
        if( this.getGameTurn() == "white"){
            opponentColor = "black";
        } else {
            opponentColor = "white";
        }

        ArrayList<Piece> allOpponentPieces = getAllPiecesOfThisColor(opponentColor, currentConfiguration);
        ArrayList<Position> allOpponentPossibleAttacks = new ArrayList<>();

        for (Piece piece : allOpponentPieces){

            if( piece.getName() == "rook" ){

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getRookPositions(piece,currentConfiguration)){
                    allOpponentPossibleAttacks.add(position);
                }

            } else if (piece.getName() == "knight") {

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getKnightPositions(piece,currentConfiguration)){
                    allOpponentPossibleAttacks.add(position);
                }

            } else if (piece.getName() == "bishop") {

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getBishopPositions(piece,currentConfiguration)){
                    allOpponentPossibleAttacks.add(position);
                }

            } else if (piece.getName() == "queen") {

                //I am grabbing the attacked positions of this piece, at its own position!
                for(Position position : getQueenPositions(piece,currentConfiguration)){
                    allOpponentPossibleAttacks.add(position);
                }
            }
        }

        //This list could contain duplicates
        return allOpponentPossibleAttacks;
    }

    public ArrayList<Position> evaluatePossibleMoves(){

        ArrayList<Position> validPositions = new ArrayList<>();

        //Aux transformation
        String color;
        String oppositeColor;
        if( getGameTurn() == "white"){
            color = "white";
            oppositeColor = "black";
        } else {
            color = "black";
            oppositeColor = "white";
        }

        ArrayList<Piece> allMyPieces = getAllPiecesOfThisColor(color, this.getAllSquares()); //PRIMUL APEL CRONOLOGIC AL FUNCTIEI
        ArrayList<Piece> allOpponentPieces = getAllPiecesOfThisColor(oppositeColor, this.getAllSquares()); //AL DOILEA APEL CRONOLOGIC AL FUNCTIEI

        //I have all my pieces. I want each position of each piece to compute.
        //For each piece...
        for (Piece piece : allMyPieces){ //GET PIECE i

            //What piece type do I have?
            if( piece.getName() == "rook" ){

                //Get all positions for my rook
                ArrayList<Position> rookPositions = getRookPositions(piece, this.getAllSquares()); //-> i need to know the ''real'' moves

                //For each position...
                for( Position position : rookPositions ){ //GET POSITION j

                    //CREATE imaginary board - ''copy'' the context of the real board, need to work only on the copy
                    Gson gson = new Gson();
                    Square[][] imaginaryBoard = gson.fromJson( gson.toJson(this.getAllSquares()), Square[][].class );

                    Piece imaginaryPiece = imaginaryBoard[position.getRowPosition()][position.getColPosition()].getPiece();
//                    System.out.println("Ia de citeste asta: "+imaginaryBoard.getAllSquares()[position.getRowPosition()][position.getColPosition()]);

                    //CREATE imaginary board - refresh - clean
//                    assignImaginaryBoardCurrentPieces(allMyPieces,allOpponentPieces); //TODO: THIS HAPPENS FOR EACH POSITION ! //aici nu se foloseste getRookPositions

                    //PLACE piece i on position j on the imaginary board
                    placePieceIonPositionJonImaginaryBoard(imaginaryPiece,position, imaginaryBoard); //Nici aici nu se foloseste getRookPositions

                    //GET all attacked squares by the opponent -> in the context of the imaginary move
                    ArrayList<Position> dangerZone = getAllAttackedSquaresByOpponent(imaginaryBoard); //Foloseste get rook positions de 3 ori pentru ca o utilizeaza si queen as rook gg

                    //GET all my pieces from the imaginary board // My imaginary arrangement of pieces
                    ArrayList<Piece> myImaginaryArrangement = getAllPiecesOfThisColor(color, imaginaryBoard);

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
                System.out.println("I need to implement logic for knight");
            } else if (piece.getName() == "bishop") {
                System.out.println("I need to implement logic for bishop");
            } else if (piece.getName() == "queen") {
                System.out.println("I need to implement logic for quenn");
            } else if (piece.getName() == "king") {
                System.out.println("I need to implement logic for king");
            } else if (piece.getName() == "pawn") {
                System.out.println("I need to implement logic for pawn");
            }


        }

        //this position list is basically all the squares in which i can move regardless of piece in my hand (somehow legal moves available)
        return validPositions;
    };


    //How do all squares from the imaginary board look like -> imaginaryBoard.getAllSquares()[rowPosition][columnPosition]
    //How does a square look like: -> imaginaryBoard.getAllSquares()[rowPosition][columnPosition]
    //How does a piece look like: -> ((Piece) imaginaryBoard.getAllSquares()[i][j].getComponents()[1])

//    public void assignImaginaryBoardCurrentPieces(ArrayList<Piece> myPieces, ArrayList<Piece> opponentPieces){
//        //TODO: -> CRED CA AM GASIT BUG ? Mai trebuie studiat cum functioneaza lambda functions
//        //first I need to clean the board
//        for( int i=1; i<=8; i++){
//            for ( int j=1; j<=8; j++) {
//                //if the imaginary square contains a piece, remove it
//                if(((Square)imaginaryBoard.getAllSquares()[i][j]).getContainsPiece()){
//                    System.out.println(" ANDREI DA SA AFISEZE SI NUMELE PIESEI: "+  ((Piece)imaginaryBoard.getAllSquares()[i][j].getComponents()[1]).getName()+" " );
//                    imaginaryBoard.getAllSquares()[i][j].remove(1);
//                    System.out.println("DANA RAND: "+i+"DANA COL"+ j);
//                    System.out.println( "ASTA ESTE CE MI_A ZIS ANDREI CA STERGE DOAR VIZUAL, ASTA INS CA AR TREBUI SA AM INCA PIESA AICI (lungimea tre sa fie >1: "+imaginaryBoard.getAllSquares()[i][j].getComponents().length );
//                    imaginaryBoard.getAllSquares()[i][j].setContainsPiece(false);
//                }
//            }
//        };
//
//
//        //sterge dreq astea
////        Board abstractBoard = this.board.clone();
//
//        System.out.println(" TEST DANA ANDREI");
//        for (int i = 1; i <= 8; i++) {
//            for (int j = 1; j <= 8; j++) {
//                if ( this.imaginaryBoard.getAllSquares()[i][j].getComponents().length > 1 ){
//                    System.out.println(this.imaginaryBoard.getAllSquares()[i][j]);
//                    System.out.println( (this.imaginaryBoard.getAllSquares()[i][j]).getComponents().length );
//                }
//            }
//        }
//        System.out.println(" TEST DANA ANDREI");
//
//        //assigning myPieces and opponentPieces //TODO: EXTREMELY BAD CHOICE TO USE LAMBDA PLEASE ASK OTHER COLLEAGUES WHY THIS DOES NOT WORK
////        myPieces.forEach(piece -> {
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
////        });
////        opponentPieces.forEach(piece -> {
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
////        });
//
//        //TODO: this was the initial way of placing pieces from real to imaginary which apparently steals them
////        for(Piece piece : myPieces) {
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
////        }
////        for(Piece piece : opponentPieces) {
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].add(piece);
////            this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
////        }
//
//        for(Piece piece: myPieces){
//            if (piece.getName() == "rook"){
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Rook("rook", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "knight") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Knight("knight", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "bishop") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Bishop("bishop", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "queen") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Queen("queen", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "king") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new King("king", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            }
//        }
//        for(Piece piece: opponentPieces){
//            if (piece.getName() == "rook"){
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Rook("rook", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "knight") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Knight("knight", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "bishop") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Bishop("bishop", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "queen") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new Queen("queen", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            } else if (piece.getName() == "king") {
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()].setContainsPiece(true);
//                this.imaginaryBoard.getAllSquares()[piece.getPiecePosition().getRowPosition()][piece.getPiecePosition().getColPosition()]
//                        .add(new King("king", new Position(piece.getPiecePosition().getRowPosition(),piece.getPiecePosition().getColPosition()), new ImageIcon(), piece.isWhite(), piece.isCanBeMoved()));
//            }
//        }
//
//        for (int i = 1; i <= 8; i++) {
//            for (int j = 1; j <= 8; j++) {
//                if ( this.imaginaryBoard.getAllSquares()[i][j].getContainsPiece() ){
//                    System.out.println(this.imaginaryBoard.getAllSquares()[i][j]);
//                    System.out.println( (this.imaginaryBoard.getAllSquares()[i][j]).getComponents().length );
//                }
//            }
//        }
//        System.out.println("Le mai contine realul ?");
//        for (int i = 1; i <= 8; i++) {
//            for (int j = 1; j <= 8; j++) {
//                if ( this.board.getAllSquares()[i][j].getContainsPiece() ){
//                    System.out.println(this.board.getAllSquares()[i][j]);
//                    System.out.println( (this.board.getAllSquares()[i][j]).getComponents().length );
//                }
//            }
//        }
//
////        System.out.println(((Piece) imaginaryBoard.getAllSquares()[1][1].getComponents()[1]).isWhite());
//    }


    public void placePieceIonPositionJonImaginaryBoard(Piece currentPiece, Position currentTargetPosition, Square[][] imaginaryBoard){
        //I need to ask myself if a piece was on that imaginary square -> it needs to be slain
        if( imaginaryBoard[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].getPiece() != null ){
            imaginaryBoard[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()]
                    .eliminatePiece( imaginaryBoard[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].getPiece() );
        }

        //Eliminate piece from previous square of current piece
        imaginaryBoard[currentPiece.getPiecePosition().getRowPosition()][currentPiece.getPiecePosition().getColPosition()].eliminatePiece(currentPiece);

        //Assign currentPiece to new square
        imaginaryBoard[currentTargetPosition.getRowPosition()][currentTargetPosition.getColPosition()].setPiece(currentPiece);

        //Assign new coordinates for the recently placed currentPiece
        currentPiece.setPiecePosition(new Position(currentTargetPosition.getRowPosition(), currentTargetPosition.getColPosition()));
        //TODO: daca nici acum nu o sa imi mearga inseamna ca fura piesa din boardul initial (real) si o muta pe clona lui
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

