import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class DataLoader {

    private List<Piece> whitePiecesList;
    private List<Piece> blackPiecesList;
    private PieceList whitePieces;
    private PieceList blackPieces;
    private Table matrix;
    private String turn;


    //Getter
    public PieceList getWhitePieces() {
        return new PieceList(whitePieces.getAllPieces());
    }
    public PieceList getBlackPieces() {
        return new PieceList(blackPieces.getAllPieces());
    }
    public Table getMatrix() {
        return matrix;
    } 
    public String getTurn(){
        return this.turn;
    }
    //Setter
    public void setWhitePieces(PieceList whitePieces) {
        this.whitePieces = whitePieces;
    }
    public void setBlackPieces(PieceList blackPieces) {
        this.blackPieces = blackPieces;
    }
    public void setMatrix(Table matrix) {
        this.matrix = matrix;
    }


    public void load()
    {

        this.whitePiecesList = new ArrayList<>();
        this.blackPiecesList = new ArrayList<>();

        this.turn = "White";

        //Pieces creation

        //White pieces
        this.loadWhitePawns();
        this.loadWhiteMinorPieces();
        this.loadWhiteMajorPieces();
        this.loadWhiteKing();
        
        //Black pieces
        this.loadBlackPawns();
        this.loadBlackMinorPieces();
        this.loadBlackMajorPieces();
        this.loadBlackKing();

        //Add pieces to list
        this.whitePieces = new PieceList(whitePiecesList);
        this.blackPieces = new PieceList(blackPiecesList);

        //Create table matrix
        this.matrix = new Table();
    }
    


    public void play(String option)
    {
        if(option.matches("[0-8]{1}[-]{1}[0-8]{1}[>]{1}[0-8]{1}[-]{1}[0-8]{1}"))
        {
            
            String oldPos = option.split(">")[0];
            int old_pos_x = Integer.valueOf(oldPos.split("-")[0]);
            int old_pos_y = Integer.valueOf(oldPos.split("-")[1]);

            String newPos = option.split(">")[1];
            int new_pos_x = Integer.valueOf(newPos.split("-")[0]);
            int new_pos_y = Integer.valueOf(newPos.split("-")[1]);

            if(this.turn.equals("White")){
                if(this.checkPosition(this.whitePieces.getPiece(old_pos_x, old_pos_y), new_pos_x, new_pos_y)){
                    System.out.println("Valid move.");
                    this.matrix.setMatrixValue(old_pos_x, old_pos_y, 0);
                    this.matrix.setMatrixValue(new_pos_x, new_pos_y, 1);
                    this.whitePieces.setPiece(old_pos_x, old_pos_y, new_pos_x, new_pos_y);
                    this.turn = "Red";
                }
                else {
                    System.out.println("Invalid move.");
                }
            }
            else {
                if(this.checkPosition(this.blackPieces.getPiece(old_pos_x, old_pos_y), new_pos_x, new_pos_y))
                {
                    System.out.println("Valid move.");
                    this.matrix.setMatrixValue(old_pos_x, old_pos_y, 0);
                    this.matrix.setMatrixValue(new_pos_x, new_pos_y, 1);
                    this.blackPieces.setPiece(old_pos_x, old_pos_y, new_pos_x, new_pos_y);
                    this.turn = "White";
                }
                else {
                    System.out.println("Invalid move.");
                }
            }
        }
        else
        {
            System.out.println("Invalid move.");
        }
    }


    public boolean checkPosition(Piece p, int newx, int newy){
        
        boolean flag = false;
        int nx = Math.abs(p.getPositionX() - newx);
        int ny = Math.abs(p.getPositionY() - newy);
        
        switch(p.getName())
        {
            case "Pawn":
                flag = moveLikePawn(p, newx, newy);
                break;
            case "Rook":
                flag = moveLikeRook(p, newx, newy);
                break;
            case "Knight":
                if((nx == 2) && (ny == 1))
                {
                    flag = true;
                }
                if((ny == 2) && (nx == 1)){
                    flag = true;
                }
                break;
            case "King":
                if((nx == 1) && (ny == 0))
                {
                    flag = true;
                }
                if((nx == 0) && (ny == 1))
                {
                    flag = true;
                }
                break;
            case "Queen":
                if((nx > 0 && nx <=8) && (ny == 0))
                {
                    flag = moveLikeRook(p, newx, newy);
                }
                else if((ny > 0 && ny <= 8 ) && (nx == 0))
                {
                    flag = moveLikeRook(p, newx, newy);
                }
                else if((nx > 0 && nx <=8) && (ny == nx))
                {
                    flag = moveLikeBishop(p, newx, newy);
                }
                else if((ny > 0 && ny <= 8) && (nx == ny))
                {
                    flag = moveLikeBishop(p, newx, newy);
                }
                break;
            case "Bishop":
                if((nx > 0 && nx <=8) && (ny == nx))
                {
                    flag = moveLikeBishop(p, newx, newy);
                }
                else if((ny > 0 && ny <= 8) && (nx == ny))
                {
                    flag = moveLikeBishop(p, newx, newy);
                }
                break;
            default:
                flag = false;
                break;
        }

        return flag;

    }

    //Movement like pawn
    public boolean moveLikePawn(Piece p, int newx, int newy)
    {
        boolean flag = false;
        int n = Math.abs(p.getPositionY() - newy);
        if(p.getColor().equals("White") && p.getPositionY() == 1)
        {
            
            if((n > 0 && n <=2) && (p.getPositionX() == newx))
            {
                flag = true;
            }
        }
        else if(p.getColor().equals("Black") && p.getPositionY() == 6)
        {
            
            if((n > 0 && n <=2) && (p.getPositionX() == newx))
            {
                flag = true;
            }
        }
        else if(p.getColor().equals("White") && (p.getPositionX() - 1 == newx || p.getPositionX() + 1 == newx ) && p.getPositionY() + 1 == newy)
        {
            if( this.blackPieces.getPiece(newx, newy) != null ) flag = true;
        }
        else if(p.getColor().equals("Black") && (p.getPositionX() - 1 == newx || p.getPositionX() + 1 == newx) && p.getPositionY() - 1 == newy)
        {
            if( this.whitePieces.getPiece(newx, newy) != null ) flag = true;
        }
        else 
        {
            if((n == 1) && (p.getPositionX() == newx))
            {
                flag = true;
            }
        }
        return flag;
    }

    //Diagonal movement like bishop
    public boolean moveLikeBishop(Piece p, int newx, int newy)
    {
        boolean flag = true;
        Piece p1 = null;
        Piece p2 = null;
        for(int k = 0; k < this.matrix.getMatrix().length; k++)
        {
            if(newy > p.getPositionY())
            {
                if (k < newy) 
                {
                    p1 = this.whitePieces.getPiece(newx, newy - k);
                    p2 = this.blackPieces.getPiece(newx, newy - k);
                }
                if(p1 != null)
                {
                    if(p.getPositionY() < p1.getPositionY() && p1.getPositionY() <= newy)
                    {
                        if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor()))
                        {
                            flag = true;
                            break;
                        }
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
                else if(p2 != null)
                {
                    
                    if(p.getPositionY() < p2.getPositionY() && p2.getPositionY() <= newy)
                    {
                        if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor()))
                        {
                            flag = true;
                            break;
                        }
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            else if(newy < p.getPositionY()) {
                if (k < newx) 
                {
                    p1 = this.whitePieces.getPiece(newx - k, newy);
                    p2 = this.blackPieces.getPiece(newx - k, newy);
                }
                if(p1 != null)
                {
                    if(p.getPositionX() > p1.getPositionX() && p1.getPositionX() >= newx)
                    {
                        if(newx == p1.getPositionX() && !p1.getColor().equals(p.getColor()))
                        {
                            flag = true;
                            break;
                        }
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
                else if(p2 != null)
                {
                    if(p.getPositionX() > p2.getPositionX() && p2.getPositionX() >= newx)
                    {
                        if(newx == p2.getPositionX() && !p2.getColor().equals(p.getColor()))
                        {
                            flag = true;
                            break;
                        }
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
            }
        }
        
        return flag;
    }
    
    
    //Linear movement like rook
    public boolean moveLikeRook(Piece p, int newx, int newy)
    {
        int nx = Math.abs(p.getPositionX() - newx);
        int ny = Math.abs(p.getPositionY() - newy);
        boolean flag = true;
        //Eje de las X
        if(p.getPositionY() == newy && nx != 0)
        {
            
            for(int i = 0; i < this.matrix.getMatrix().length; i+=1)
            {
                //if( k % 8 == 0 ) k=0;
                Piece p1 = this.whitePieces.getPiece(i, p.getPositionY());
                Piece p2 = this.blackPieces.getPiece(i, p.getPositionY());
                //MOVIMIENTO PARA LA DERECHA
                if(newx > p.getPositionX())
                {
                    if((p1 != null && !p1.equals(p)) || (p2 != null && !p2.equals(p)))
                    {
                        if((p1 != null && !p1.equals(p)))
                        {
                            if (newx >= p1.getPositionX() && p1.getPositionX() > p.getPositionX())
                            {
                                if(newx == p1.getPositionX() && !p1.getColor().equals(p.getColor()))
                                {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        else if(p2 != null && !p2.equals(p))
                        {
                            if(newx >= p2.getPositionX() && p2.getPositionX() > p.getPositionX())
                            {
                                if(newx == p2.getPositionX() && !p2.getColor().equals(p.getColor()))
                                {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                            
                        }
                    }
                }
                //MOVIMIENTO PARA LA IZQUIERDA
                if(newx < p.getPositionX())
                {
                    if((p1 != null && !p1.equals(p)) || (p2 != null && !p2.equals(p)))
                    {
                        if((p1 != null && !p1.equals(p)))
                        {
                            if (newx <= p1.getPositionX() && p1.getPositionX() < p.getPositionX())
                            {
                                if(newx == p1.getPositionX() && !p1.getColor().equals(p.getColor()))
                                {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        else if(p2 != null && !p2.equals(p))
                        {
                            if(newx <= p2.getPositionX() && p2.getPositionX() < p.getPositionX())
                            {
                                if(newx == p2.getPositionX() && !p2.getColor().equals(p.getColor()))
                                {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                            
                        }
                    }
                }
                //k += 1;
            }

        }
        //Eje de las Y
        else if(p.getPositionX() == newx && ny != 0)
        {
            
            for(int i = 0; i < this.matrix.getMatrix().length; i+=1)
            {
                //if ( k % 8 == 0 ) k = 0;
                Piece p1 = this.whitePieces.getPiece(p.getPositionX(), i);
                Piece p2 = this.blackPieces.getPiece(p.getPositionX(), i);
                //MOVIMIENTO PARA ABAJO
                if(newy > p.getPositionY())
                {
                    if((p1 != null && !p1.equals(p)))
                    {
                        if (newy >= p1.getPositionY() && p1.getPositionY() > p.getPositionY())
                        {
                            if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor()))
                            {
                                flag = true;
                                break;
                            }
                            else {
                                flag = false;
                                break;
                            }
                        }
                    }
                    else if(p2 != null && !p2.equals(p))
                    {
                        if(newy >= p2.getPositionY() && p2.getPositionY() > p.getPositionY())
                        {
                            if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor()))
                            {
                                flag = true;
                                break;
                            }
                            else {
                                flag = false;
                                break;
                            }
                        }
                        
                    }
                    
                }
                //MOVIMIENTO PARA ARRIBA
                if(newy <= p.getPositionY())
                {
                    if((p1 != null && !p1.equals(p)) || (p2 != null && !p2.equals(p)))
                    {
                        if((p1 != null && !p1.equals(p)))
                        {
                            if (newy <= p1.getPositionY() && p1.getPositionY() < p.getPositionY())
                            {
                                if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor()))
                                {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        else if(p2 != null && !p2.equals(p))
                        {
                            if(newy <= p2.getPositionY() && p2.getPositionY() < p.getPositionY())
                            {
                                if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor()))
                                {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                            
                        }
                    }
                }
                //k += 1;
            }
        }
        //Movimiento en ambos ejes
        else if(nx != 0 && ny != 0)
        {
            flag = false;
        }

        return flag;

    }

    //White pieces methods


    public void loadWhitePawns(){
        //Create pawns
        Piece whitePawn1 = new Piece("Pawn", "P", 0, 1, "White");
        Piece whitePawn2 = new Piece("Pawn", "P", 1, 1, "White");
        Piece whitePawn3 = new Piece("Pawn", "P", 2, 1, "White");
        Piece whitePawn4 = new Piece("Pawn", "P", 3, 1, "White");
        Piece whitePawn5 = new Piece("Pawn", "P", 4, 1, "White");
        Piece whitePawn6 = new Piece("Pawn", "P", 5, 1, "White");
        Piece whitePawn7 = new Piece("Pawn", "P", 6, 1, "White");
        Piece whitePawn8 = new Piece("Pawn", "P", 7, 1, "White");
        //Add to list
        whitePiecesList.add(whitePawn1);
        whitePiecesList.add(whitePawn2);
        whitePiecesList.add(whitePawn3);
        whitePiecesList.add(whitePawn4);
        whitePiecesList.add(whitePawn5);
        whitePiecesList.add(whitePawn6);
        whitePiecesList.add(whitePawn7);
        whitePiecesList.add(whitePawn8);
    }
    public void loadWhiteMinorPieces()
    {
        //Create Minor pieces
        //Knights
        Piece whiteKnight1 = new Piece("Knight", "N", 1, 0, "White");
        Piece whiteKnight2 = new Piece("Knight", "N", 6, 0, "White");
        //Bishops
        Piece whiteBishop1 = new Piece("Bishop", "B", 2, 0, "White");
        Piece whiteBishop2 = new Piece("Bishop", "B", 5, 0, "White");
        //Add to list
        whitePiecesList.add(whiteKnight1);
        whitePiecesList.add(whiteKnight2);
        whitePiecesList.add(whiteBishop1);
        whitePiecesList.add(whiteBishop2);
    }
    public void loadWhiteMajorPieces()
    {
        //Rooks
        Piece whiteRook1 = new Piece("Rook", "R", 0, 0, "White");
        Piece whiteRook2 = new Piece("Rook", "R", 7, 0, "White");
        //Queen
        Piece whiteQueen = new Piece("Queen", "Q", 4, 0, "White");
        //Add to list
        whitePiecesList.add(whiteRook1);
        whitePiecesList.add(whiteRook2);
        whitePiecesList.add(whiteQueen);
    }
    public void loadWhiteKing()
    {
        Piece whiteKing = new Piece("King", "K", 3, 0, "White");
        whitePiecesList.add(whiteKing);
    }


    //Black pieces methods


    public void loadBlackPawns(){
        //Pawns
        Piece blackPawn1 = new Piece("Pawn", "P", 0, 6, "Black");
        Piece blackPawn2 = new Piece("Pawn", "P", 1, 6, "Black");
        Piece blackPawn3 = new Piece("Pawn", "P", 2, 6, "Black");
        Piece blackPawn4 = new Piece("Pawn", "P", 3, 6, "Black");
        Piece blackPawn5 = new Piece("Pawn", "P", 4, 6, "Black");
        Piece blackPawn6 = new Piece("Pawn", "P", 5, 6, "Black");
        Piece blackPawn7 = new Piece("Pawn", "P", 6, 6, "Black");
        Piece blackPawn8 = new Piece("Pawn", "P", 7, 6, "Black");
        //Add to list
        blackPiecesList.add(blackPawn1);
        blackPiecesList.add(blackPawn2);
        blackPiecesList.add(blackPawn3);
        blackPiecesList.add(blackPawn4);
        blackPiecesList.add(blackPawn5);
        blackPiecesList.add(blackPawn6);
        blackPiecesList.add(blackPawn7);
        blackPiecesList.add(blackPawn8);
    }
    public void loadBlackMinorPieces()
    {
        
        //Knights
        Piece blackKnight1 = new Piece("Knight", "N", 1, 7, "Black");
        Piece blackKnight2 = new Piece("Knight", "N", 6, 7, "Black");
        //Bishops
        Piece blackBishop1 = new Piece("Bishop", "B", 2, 7, "Black");
        Piece blackBishop2 = new Piece("Bishop", "B", 5, 7, "Black");
        //Add to list
        blackPiecesList.add(blackKnight1);
        blackPiecesList.add(blackKnight2);
        blackPiecesList.add(blackBishop1);
        blackPiecesList.add(blackBishop2);
    }
    public void loadBlackMajorPieces()
    {
        //Rooks
        Piece blackRook1 = new Piece("Rook", "R", 0, 7, "Black");
        Piece blackRook2 = new Piece("Rook", "R", 7, 7, "Black");
        //Queen
        Piece blackQueen = new Piece("Queen", "Q", 4, 7, "Black");
        //Add to list
        blackPiecesList.add(blackRook1);
        blackPiecesList.add(blackRook2);
        blackPiecesList.add(blackQueen);
    }
    public void loadBlackKing()
    {
        //King
        Piece blackKing = new Piece("King", "K", 3, 7, "Black");
        blackPiecesList.add(blackKing);
    }

}
