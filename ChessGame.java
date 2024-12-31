import java.io.*;

public class ChessGame implements Serializable {
    static String filePath = "C:\\Users\\Kamal Hassan\\Desktop\\project\\GameToBeStored.txt";
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    static {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        ChessBoard gameBoard = new ChessBoard();
        gameBoard.fillBoardDefault();
        ChessGUI gui = new ChessGUI(gameBoard);
        gui.createAndShowGUI();

    }
    public static void writeDataToFile(ChessGUI obj){
        try{
            oos.writeObject(obj);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static ChessGUI readDataFromFile(){
        ChessGUI obj = null;
        try{
            obj = (ChessGUI) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }
}