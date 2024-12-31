import java.util.ArrayList;

public class KingPiece extends ChessPiece {

    KingPiece(char color, ChessPosition position) {
        super(color, position);
        this.pieceType = "king";
    }

    ArrayList<ChessPosition> getAllPossibleMoves(ChessBoard board) {
        ArrayList<ChessPosition> moves = new ArrayList<>();

       int curFile = position.getFile();
       int curRank = position.getRank();

       for (int f = curFile - 1; f <= curFile + 1; f++) {
           for(int r = curRank - 1; r <= curRank + 1; r++) {

               // Exclude our own position
               if (position.equals(f, r)) continue;

               // Add move to list; ignore continue-indicator
               addMoveIfValidAndIndicateWhetherToContinue(moves, board, f, r);
           }
       }

        return moves;
    }

    @Override
    public String toString() {
        return (color == 'w') ? "♔" : "♚";
    }
}