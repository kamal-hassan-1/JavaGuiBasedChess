import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessGUI {
    private ChessBoard board;
    private char currentPlayerColor;
    private JButton[][] boardButtons;
    private JLabel statusLabel;
    private JLabel capturedWhite;
    private JLabel capturedBlack;
    private ChessPosition selectedPosition;

    public ChessGUI(ChessBoard board) {
        this.board = board;
        this.currentPlayerColor = 'w';
        this.boardButtons = new JButton[8][8];
        this.statusLabel = new JLabel("White's move", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.selectedPosition = null;
    }

    protected void createAndShowGUI() {
        // Create main window
        JFrame frame = new JFrame("Chess Game");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel for chessboard
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);

        // Initialize board buttons
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setSize(100, 100);
                boardButtons[i][j].setFocusPainted(false);
                boardButtons[i][j].setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);

                // Add action listener for each button
                int row = i;
                int col = j;
                boardButtons[i][j].addActionListener(e -> handlePieceClick(row, col));

                boardPanel.add(boardButtons[i][j]);
            }
        }

        // Panel for capturing pieces and current player's turn
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(statusLabel);

        JPanel capturedPanel = new JPanel(new GridLayout(1, 2));
        capturedWhite = new JLabel();
        capturedBlack = new JLabel();
        capturedPanel.add(capturedWhite);
        capturedPanel.add(capturedBlack);
        infoPanel.add(capturedPanel);

        frame.add(infoPanel, BorderLayout.SOUTH);

        // Set initial board display
        renderBoard();

        // Finalize frame
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }




    protected void rerunGUI() {
        JFrame frame = new JFrame("Chess Game");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);
        for(int i = 0; i<8; i++){
            for(int j=0; j<8; j++){
                boardPanel.add(boardButtons[i][j]);
                int row = i;
                int col = j;
                boardButtons[i][j].addActionListener(e -> handlePieceClick(row, col));
            }
        }
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(statusLabel);

        JPanel capturedPanel = new JPanel(new GridLayout(1, 2));
        capturedPanel.add(capturedWhite);
        capturedPanel.add(capturedBlack);
        infoPanel.add(capturedPanel);

        frame.add(infoPanel, BorderLayout.SOUTH);

        // Set initial board display
        renderBoard();

        // Finalize frame
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private void renderBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.pieces[i][j];
                if (piece != null) {
                    boardButtons[i][j].setIcon(new ImageIcon(getImage(givePath(piece.pieceType, piece.color))));
                } else {
                    boardButtons[i][j].setIcon(null);
                }

                if (selectedPosition != null && selectedPosition.getRank() == (8 - i) && selectedPosition.getFile() == (j + 1)) {
                    boardButtons[i][j].setBackground(Color.WHITE);
                } else {
                    boardButtons[i][j].setBackground((i + j) % 2 == 0 ? new Color(210,165,120) : new Color(175,115,70));
                }
            }
        }

        // Update the status label with the captured pieces
        capturedWhite.setText("Captured by white: " + ChessBoard.capturedByWhite.toString());
        capturedBlack.setText("Captured by black: " + ChessBoard.capturedByBlack.toString());
        statusLabel.setText((currentPlayerColor == 'w' ? "White's" : "Black's") + " move");
        
    }

    private void handlePieceClick(int row, int col) {
        // Convert the button click to chess coordinates
        ChessPiece piece = board.pieces[row][col];
        if (selectedPosition == null) {
            // First click: select a piece
            if (piece != null && piece.getColor() == currentPlayerColor) {
                selectedPosition = new ChessPosition(col + 1, 8 - row);
                renderBoard();
            }
        } else {
            // Second click: move the selected piece
            ChessPosition destination = new ChessPosition(col + 1, 8 - row);
            if (isValidMove(selectedPosition, destination)) {
                movePiece(selectedPosition, destination);
                selectedPosition = null; // Clear selection after moving
                renderBoard();
                // Change turn
                currentPlayerColor = (currentPlayerColor == 'w') ? 'b' : 'w';
            } else {
                // Invalid move
                JOptionPane.showMessageDialog(null, "Invalid move!");
                selectedPosition = null;  // Clear selection on invalid move
                renderBoard();
            }
        }
    }

    private boolean isValidMove(ChessPosition start, ChessPosition end) {
        ChessPiece piece = board.pieces[8 - start.getRank()][start.getFile() - 1];
        ArrayList<ChessPosition> validMoves = piece.getAllPossibleMoves(board);
        return ifContains(validMoves, end);
    }

    private void movePiece(ChessPosition start, ChessPosition end) {
        ChessPiece piece = board.pieces[8 - start.getRank()][start.getFile() - 1];

        // Capture any piece at destination
        if (board.isThereAPiece(end.getRank(), end.getFile())) {
            ChessPiece capturedPiece = board.pieces[8 - end.getRank()][end.getFile() - 1];
            if (capturedPiece.color == 'w') {
                ChessBoard.capturedByBlack.add(capturedPiece);
            } else {
                ChessBoard.capturedByWhite.add(capturedPiece);
            }
        }

        // Move the piece on the board
        board.pieces[8 - start.getRank()][start.getFile() - 1] = null;
        board.pieces[8 - end.getRank()][end.getFile() - 1] = piece;
        piece.position = end;
    }

    public static String givePath(String name, char color) {
        String[] blackIconsPath = {"images\\b-king", "images\\b-queen", "images\\b-rook", "images\\b-bishop", "images\\b-knight", "images\\b-pawn"};
        String[] whiteIconsPath = {"images\\w-king", "images\\w-queen", "images\\w-rook", "images\\w-bishop", "images\\w-knight", "images\\w-pawn"};
        HashMap<String, Integer> pieceToIndex = new HashMap<>();
        pieceToIndex.put("king", 0);
        pieceToIndex.put("queen", 1);
        pieceToIndex.put("rook", 2);
        pieceToIndex.put("bishop", 3);
        pieceToIndex.put("knight", 4);
        pieceToIndex.put("pawn", 5);

        int index = pieceToIndex.get(name);
        return (color == 'w') ? whiteIconsPath[index] : blackIconsPath[index];
    }
    public BufferedImage getImage(String pathString){
        BufferedImage img = null;
        try{
            img = ImageIO.read(getClass().getResourceAsStream(pathString + ".png"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static boolean ifContains(ArrayList<ChessPosition> moves, ChessPosition nxtPosition) {
        for (ChessPosition move : moves) {
            if (move.getRank() == nxtPosition.getRank() && move.getFile() == nxtPosition.getFile()) {
                return true;
            }
        }
        return false;
    }
}