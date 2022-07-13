import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PieceInitializer {

    private Square[][] squareList;
    ListOfPieces listPics;

    GameLogic gameLogic;

    public PieceInitializer(Square[][] squareList,ListOfPieces listPics, GameLogic gameLogic) {
        this.squareList = squareList;
        this.listPics = listPics;
        this.gameLogic = gameLogic;
    }


    public void injectImages() throws IOException {

        //Attaching white pieces
//        this.squareList[0][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[4])), BorderLayout.CENTER);
        this.squareList[0][7].setContainsPiece(true);
        this.squareList[0][7].add(new Rook("rook", new ImageIcon(listPics.getListOfPieceImages()[4]), this.gameLogic));

        this.squareList[1][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[3])), BorderLayout.CENTER);
        this.squareList[2][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[2])), BorderLayout.CENTER);
        this.squareList[3][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[1])), BorderLayout.CENTER);
        this.squareList[4][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[0])), BorderLayout.CENTER);
        this.squareList[5][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[2])), BorderLayout.CENTER);
        this.squareList[6][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[3])), BorderLayout.CENTER);
        this.squareList[7][7].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[4])), BorderLayout.CENTER);
        for(int i=0;i<8;i++){
            this.squareList[i][6].add(new JLabel(new ImageIcon((listPics.getListOfPieceImages()[5]), BorderLayout.CENTER)));
        }

        //Attaching black pieces
        this.squareList[0][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[10])), BorderLayout.CENTER);
        this.squareList[1][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[9])), BorderLayout.CENTER);
        this.squareList[2][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[8])), BorderLayout.CENTER);
        this.squareList[3][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[7])), BorderLayout.CENTER);
        this.squareList[4][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[6])), BorderLayout.CENTER);
        this.squareList[5][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[8])), BorderLayout.CENTER);
        this.squareList[6][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[9])), BorderLayout.CENTER);
        this.squareList[7][0].add(new JLabel(new ImageIcon(listPics.getListOfPieceImages()[10])), BorderLayout.CENTER);
        for(int i=0;i<8;i++){
            this.squareList[i][1].add(new JLabel(new ImageIcon((listPics.getListOfPieceImages()[11]), BorderLayout.CENTER)));
        }
    };
}
