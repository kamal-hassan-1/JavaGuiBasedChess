import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessGUI implements Serializable {
    private ChessBoard board;
    private char currentPlayerColor;
    private transient JButton[][] boardButtons;
    private transient JLabel statusLabel;
    private transient JLabel capturedWhite;
    private transient JLabel capturedBlack;
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
        JFrame frame = new JFrame("Chess Game");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setSize(100, 100);
                boardButtons[i][j].setFocusPainted(false);
                boardButtons[i][j].setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);

                int row = i;
                int col = j;
                boardButtons[i][j].addActionListener(e -> handlePieceClick(row, col));

                boardPanel.add(boardButtons[i][j]);
            }
        }
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

        // game save panel on my right hand
        JPanel gameSavePlan = new JPanel();
        gameSavePlan.setLayout(new BoxLayout(gameSavePlan, BoxLayout.Y_AXIS));

        JLabel saveLabel = new JLabel("Save Game:");
        saveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameSavePlan.add(saveLabel);

        JButton saveButton = new JButton("Save And Exit");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> {
            ChessGame.writeDataToFile(this);
            JOptionPane.showMessageDialog(frame, "game is gonna exit");
            System.exit(1);
        });
        gameSavePlan.add(saveButton);

        frame.add(gameSavePlan, BorderLayout.EAST);

        renderBoard();


        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected static void rerunGUI(ChessGUI obj) {
        obj.boardButtons = new JButton[8][8];
        obj.statusLabel = new JLabel((obj.currentPlayerColor == 'w' ? "White's" : "Black's") + " move", JLabel.CENTER);
        obj.statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        obj.capturedWhite = new JLabel();
        obj.capturedBlack = new JLabel();

        JFrame frame = new JFrame("Saved Chess Game");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                obj.boardButtons[i][j] = new JButton();
                obj.boardButtons[i][j].setSize(100, 100);
                obj.boardButtons[i][j].setFocusPainted(false);
                obj.boardButtons[i][j]
                        .setBackground((i + j) % 2 == 0 ? new Color(210, 165, 120) : new Color(175, 115, 70));

                int row = i;
                int col = j;
                obj.boardButtons[i][j].addActionListener(e -> obj.handlePieceClick(row, col));

                boardPanel.add(obj.boardButtons[i][j]);
            }
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(obj.statusLabel);

        JPanel capturedPanel = new JPanel(new GridLayout(1, 2));
        capturedPanel.add(obj.capturedWhite);
        capturedPanel.add(obj.capturedBlack);
        infoPanel.add(capturedPanel);

        frame.add(infoPanel, BorderLayout.SOUTH);

        JPanel gameSavePlan = new JPanel();
        gameSavePlan.setLayout(new BoxLayout(gameSavePlan, BoxLayout.Y_AXIS));

        JLabel saveLabel = new JLabel("Save Game:");
        saveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameSavePlan.add(saveLabel);

        JButton saveButton = new JButton("Save And Exit");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> {
            ChessGame.writeDataToFile(obj);
            JOptionPane.showMessageDialog(frame, "game is gonna exit");
            System.exit(1);
        });
        gameSavePlan.add(saveButton);

        frame.add(gameSavePlan, BorderLayout.EAST);

        obj.renderBoard();

        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
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

                if (selectedPosition != null && selectedPosition.getRank() == (8 - i)
                        && selectedPosition.getFile() == (j + 1)) {
                    boardButtons[i][j].setBackground(Color.WHITE);
                } else {
                    boardButtons[i][j].setBackground((i + j) % 2 == 0 ? new Color(210, 165, 120) : new Color(175, 115, 70));
                }
            }
        }
        capturedWhite.setText("Captured by white: " + ChessBoard.capturedByWhite.toString());
        capturedBlack.setText("Captured by black: " + ChessBoard.capturedByBlack.toString());
        statusLabel.setText((currentPlayerColor == 'w' ? "White's" : "Black's") + " move");

    }

    private void handlePieceClick(int row, int col) {
        ChessPiece piece = board.pieces[row][col];
        if (selectedPosition == null) {
            if (piece != null && piece.getColor() == currentPlayerColor) {
                selectedPosition = new ChessPosition(col + 1, 8 - row);
                renderBoard();
            }
        } else {
            ChessPosition destination = new ChessPosition(col + 1, 8 - row);
            if (isValidMove(selectedPosition, destination)) {
                movePiece(selectedPosition, destination);
                selectedPosition = null;
                renderBoard();
                currentPlayerColor = (currentPlayerColor == 'w') ? 'b' : 'w';
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move!");
                selectedPosition = null;
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

        if (board.isThereAPiece(end.getRank(), end.getFile())) {
            ChessPiece capturedPiece = board.pieces[8 - end.getRank()][end.getFile() - 1];
            if (capturedPiece.color == 'w') {
                ChessBoard.capturedByBlack.add(capturedPiece);
            } else {
                ChessBoard.capturedByWhite.add(capturedPiece);
            }
        }

        board.pieces[8 - start.getRank()][start.getFile() - 1] = null;
        board.pieces[8 - end.getRank()][end.getFile() - 1] = piece;
        piece.position = end;
    }

    public String givePath(String name, char color) {
        String[] blackIconsPath = { "images\\b-king", "images\\b-queen", "images\\b-rook", "images\\b-bishop", "images\\b-knight", "images\\b-pawn" };
        String[] whiteIconsPath = { "images\\w-king", "images\\w-queen", "images\\w-rook", "images\\w-bishop", "images\\w-knight", "images\\w-pawn" };
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

    public BufferedImage getImage(String pathString) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(pathString + ".png"));
        } catch (Exception e) {
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