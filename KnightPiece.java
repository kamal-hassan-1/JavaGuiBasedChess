import java.util.ArrayList;

public class KnightPiece extends ChessPiece {

    KnightPiece(char color, ChessPosition position) {
        super(color, position);
        this.pieceType = "knight";
    }

    ArrayList<ChessPosition> getAllPossibleMoves(ChessBoard board) {
        ArrayList<ChessPosition> moves = new ArrayList<>();

        int curFile = position.getFile();
        int curRank = position.getRank();

        int tempRank = curRank + 2;
        for(int i = 1; i <= 2; i++) {
            addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile + i, tempRank);  // Top Right Move
            addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile - i, tempRank);  // Top Left Move
            tempRank -= 1;
        }

        tempRank = curRank - 2;
        for(int i = 1; i <= 2; i++) {
            addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile + i, tempRank);  // Bottom Right Move
            addMoveIfValidAndIndicateWhetherToContinue(moves, board, curFile - i, tempRank);  // Bottom Left Move
            tempRank += 1;
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == 'w') ? "♘" : "♞";
    }
}