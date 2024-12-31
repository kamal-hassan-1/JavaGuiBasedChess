import java.util.ArrayList;
public class BishopPiece extends ChessPiece {

    BishopPiece(char color, ChessPosition position) {
        super(color, position);
        this.pieceType = "bishop";
    }

    ArrayList<ChessPosition> getAllPossibleMoves(ChessBoard board){
        ArrayList<ChessPosition> moves = new ArrayList<>();

        int curFile = position.getFile();
        int curRank = position.getRank();

        // North-East Moves
        for (int i = 1; i <= 8; i++) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile + i, curRank + i)) break;
        }

        // South-East Moves
        for (int i = 1; i <= 8; i++) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile + i, curRank - i)) break;
        }

        // South-West Moves
        for (int i = 1; i <= 8; i++) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile - i, curRank - i)) break;
        }

        // North-West Moves
        for (int i = 1; i <= 8; i++) {
            if (!addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile - i, curRank + i)) break;
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == 'w') ? "♗" : "♝";
    }
}