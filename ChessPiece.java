import java.util.ArrayList;

abstract class ChessPiece {
    protected char color;
    protected String pieceType;
    public ChessPosition position;

    ChessPiece(char color, ChessPosition position) {
        if (color != 'w' && color != 'b') {
            throw new IllegalArgumentException("ChessPiece: Invalid color specified.");
        }

        this.color = color;
        this.position = position;
    }

    char getColor() { return this.color; }
    String getPieceType() { return this.pieceType; }

    boolean addMoveIfValidAndIndicateWhetherToContinue(ArrayList<ChessPosition> moves, ChessBoard board, int file, int rank) {
        // Make sure proposed move is not out-of-bounds.
        if (!ChessPosition.isValidPosition(file, rank)) {
            return true; // Possibly okay to continue.
        }

        ChessPosition newPos = new ChessPosition(file, rank);

        // Make sure any friendly piece is not at proposed position.
        if (board.isAnyPieceOfSpecificColorAtPosition(newPos, this.color)) {
            return false; // DO NOT CONTINUE.
        }

        // newPos is a valid move.
        moves.add(newPos);
        
        // Return DO NOT CONTINUE (false) if any piece (of the other color) is in the way.
        return !board.isAnyPieceOfSpecificColorAtPosition(newPos, Helpers.invertColor(this.color));
    }

    abstract ArrayList<ChessPosition> getAllPossibleMoves(ChessBoard board);
}
