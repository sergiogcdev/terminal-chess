import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;;

public class Main {

    //Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        
        //Load Data
        DataLoader data = new DataLoader();
        data.load();

        Board board = data.getMatrix();
        int[][] matrix = board.getMatrix();
        PieceList wp = data.getWhitePieces();
        PieceList bp = data.getBlackPieces();
        
        //Scanner
        Scanner scan = new Scanner(System.in);
        String opt = "Continue";

        //Print table
        do {

            board.setWhitePieces(wp);
            board.setBlackPieces(bp);

            try {
                //StringBuilder
                StringBuilder stringBuilder = new StringBuilder();
                matrix = board.getMatrix();
                
                System.out.println("\n\nTURN : " + data.getTurn());
                System.out.print("\n\n");
                for(int i = 0; i < matrix.length; i++)
                {

                    System.out.println("---------------------------------------------------------");
                    for(int j = 0; j < matrix[i].length; j++)
                    {
                        if(j == 0) System.out.print("--" + i + "--");
                        System.out.print("|");
                        Piece p = wp.getPiece(j, i);
                        Piece p2 = bp.getPiece(j, i);
                        if(matrix[i][j] == 1) 
                        {
                            if(p != null && p2 != null)
                            {
                                if((p.getSymbol() != null && p2.getSymbol() != null) && (p.getPositionX() == p2.getPositionX() && p.getPositionX() == p2.getPositionX()))
                                {
                                    if(data.getTurn().equals("Red"))
                                    {
                                        bp.removeItem(p2);
                                        System.out.print(" X ");
                                    }
                                    else if (data.getTurn().equals("White"))
                                    {
                                        wp.removeItem(p);
                                        System.out.print(ANSI_RED + " X " + ANSI_RESET);
                                    }
                                    
                                }
                            } 
                            else 
                            {
                                if(p != null)
                                {
                                    if(p.getSymbol() != null) System.out.print(" " + p.getSymbol() + " "); 
                                }
                                else System.out.print("  ");
                                if(p2 != null)
                                {
                                    if(p2.getSymbol() != null) System.out.print(ANSI_RED + " " + p2.getSymbol() + " " + ANSI_RESET);
                                }
                                else System.out.print("  ");
                            }

                            
                        }
                        else if(matrix[i][j] == 0) {
                            System.out.print("     ");
                        }
                    
                        if(i == 7) stringBuilder.append("  (" + j + ") ");
                    }

                    System.out.print("\n");
                    if(i==7) System.out.println(stringBuilder.toString());

                }

                System.out.print("\n\n");

                System.out.println("Type \"exit\" to close the game. Otherwise, introduce a move [ Example: \"1-1>1-2\" ]");
                opt = scan.nextLine();
                data.play(opt);
                wp = data.getWhitePieces();
                bp = data.getBlackPieces();

            } catch(Exception e) {
                System.out.println("Error.");
            } finally {
                continue;
            }

        } while (!opt.equals("exit"));

    }
}
