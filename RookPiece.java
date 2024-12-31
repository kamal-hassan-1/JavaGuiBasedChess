import java.util.ArrayList;
public class RookPiece extends ChessPiece {
    RookPiece(char color, ChessPosition position)  {
        super(color, position);
        this.pieceType = "rook";
    }
    ArrayList<ChessPosition> getAllPossibleMoves(ChessBoard board) {
        ArrayList<ChessPosition> moves = new ArrayList<>();

        int curFile = position.getFile();
        int curRank = position.getRank();

        // North Moves
        for(int r = curRank + 1; r <= 8; r++) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile, r)) break;
        }

        // South Moves
        for(int r = curRank - 1; r >= 1; r--) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile, r)) break;
        }

        // East Moves
        for(int f = curFile + 1; f <= 8; f++) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, f, curRank)) break;
        }

        // West Moves
        for(int f = curFile - 1; f >= 1; f--) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, f, curRank)) break;
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == 'w') ? "♖" : "♜";
    }
}