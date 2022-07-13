import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ListOfPieces {

    private BufferedImage mainImage;
    private static Image listOfPieceImages[];
    private int index;

    public Image[] getListOfPieceImages() throws IOException {
        this.mainImage = ImageIO.read(new File("C:\\Users\\FerendiaIT\\Desktop\\Work\\01_Develop\\myChess\\myChess\\chess.png"));
        this.listOfPieceImages = new Image[12];
        this.index = 0;

        for(int y=0; y<400; y+=200){
            for(int x=0; x<1200;x+=200){
                listOfPieceImages[this.index] =mainImage
                        .getSubimage(x,y,200,200)
                        .getScaledInstance(60,60,BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }
        return this.listOfPieceImages;
    };
}
