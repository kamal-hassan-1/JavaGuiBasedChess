import java.io.Serializable;
import java.util.ArrayList;

public class ChessBoard implements Serializable {
    ChessPiece[][] pieces = new ChessPiece[8][8];
    static ArrayList<ChessPiece> capturedByWhite = new ArrayList<>();
    static ArrayList<ChessPiece> capturedByBlack = new ArrayList<>();

    ChessBoard() {
        fillBoardDefault();
    }

    boolean isAnyPieceOfSpecificColorAtPosition(ChessPosition position, char color) {
        if (pieces[Helpers.rankToRow(position.getRank())][Helpers.fileToColumn(position.getFile())] != null
                && pieces[Helpers.rankToRow(position.getRank())][Helpers
                        .fileToColumn(position.getFile())].color == color) {
            return true;
        }
        return false;
    }

    boolean isThereAPiece(int rank, int file) {
        return pieces[Helpers.rankToRow(rank)][Helpers.fileToColumn(file)] != null;
    }

    public void fillBoardDefault() {

        // Pawn pieces
        for (int i = 1; i <= 8; i++) {
            pieces[Helpers.rankToRow(2)][Helpers.fileToColumn(i)] = new PawnPiece('w', new ChessPosition(i, 2));
        }
        for (int i = 1; i <= 8; i++) {
            pieces[Helpers.rankToRow(7)][Helpers.fileToColumn(i)] = new PawnPiece('b', new ChessPosition(i, 7));
        }

        int rowForWhiteExecutivePieces = Helpers.rankToRow(1);
        int rowForBlackExecutivePieces = Helpers.rankToRow(8);

        // Rook Pieces
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(1)] = new RookPiece('w', new ChessPosition(1, 1));
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(8)] = new RookPiece('w', new ChessPosition(8, 1));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(1)] = new RookPiece('b', new ChessPosition(1, 8));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(8)] = new RookPiece('b', new ChessPosition(8, 8));

        // Knight Pieces
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(2)] = new KnightPiece('w', new ChessPosition(2, 1));
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(7)] = new KnightPiece('w', new ChessPosition(7, 1));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(2)] = new KnightPiece('b', new ChessPosition(2, 8));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(7)] = new KnightPiece('b', new ChessPosition(7, 8));

        // Bishop Pieces
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(3)] = new BishopPiece('w', new ChessPosition(3, 1));
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(6)] = new BishopPiece('w', new ChessPosition(6, 1));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(3)] = new BishopPiece('b', new ChessPosition(3, 8));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(6)] = new BishopPiece('b', new ChessPosition(6, 8));

        // King Pieces
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(5)] = new KingPiece('w', new ChessPosition(5, 1));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(5)] = new KingPiece('b', new ChessPosition(5, 8));

        // Queen Pieces
        pieces[rowForWhiteExecutivePieces][Helpers.fileToColumn(4)] = new QueenPiece('w', new ChessPosition(4, 1));
        pieces[rowForBlackExecutivePieces][Helpers.fileToColumn(4)] = new QueenPiece('b', new ChessPosition(4, 8));
    }

}
