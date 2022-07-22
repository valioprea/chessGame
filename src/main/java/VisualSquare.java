import javax.swing.*;

public class VisualSquare extends JPanel {

    private Position visualSquarePosition;

    private VisualPiece visualPiece;

    public Position getVisualSquarePosition() {
        return visualSquarePosition;
    }

    public void setVisualSquarePosition(Position visualSquarePosition) {
        this.visualSquarePosition = visualSquarePosition;
    }

    public VisualPiece getVisualPiece() {
        return visualPiece;
    }

    @Override
    public String toString() {
        return "VisualSquare{" +
                "visualSquarePosition=" + visualSquarePosition +
                ", piece=" + visualPiece +
                '}';
    }

    public void setVisualPiece(VisualPiece visualPiece) {
        add(visualPiece);
        this.visualPiece = visualPiece;
    }
}
