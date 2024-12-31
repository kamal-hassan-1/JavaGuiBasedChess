import java.util.ArrayList;

public class QueenPiece extends ChessPiece {

    QueenPiece(char color, ChessPosition position)  {
        super(color, position);
        this.pieceType = "queen";
    }

    ArrayList<ChessPosition> getAllPossibleMoves(ChessBoard board) {
        RookPiece rook = new RookPiece(this.color, this.position);
        BishopPiece bishop = new BishopPiece(this.color, this.position);

        ArrayList<ChessPosition> moves = new ArrayList<>(rook.getAllPossibleMoves(board));
        moves.addAll(bishop.getAllPossibleMoves(board));

        return moves;
    }

    @Override
    public String toString() {
        return (color == 'w') ? "♕" : "♛";
    }
}