import java.io.*;
public class ChessGame {
    private final static String filePath = "C:\\Users\\Kamal Hassan\\Desktop\\project\\GameToBeStored.txt";

    public static void main(String[] args) {
//         ChessBoard gameBoard = new ChessBoard();
//         gameBoard.fillBoardDefault();
//         ChessGUI gui = new ChessGUI(gameBoard);
//         gui.createAndShowGUI();

        ChessGUI obj = readDataFromFile();
        if (obj != null) {
            ChessGUI.rerunGUI(obj);
        }
    }

    public static void writeDataToFile(ChessGUI obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ChessGUI readDataFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            return (ChessGUI) ois.readObject();
        }
        catch (Exception e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}