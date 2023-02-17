public class Board {
    private String status;
    private PieceList whitePieces;
    private PieceList blackPieces;
    private int[][] matrix = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
    };
    public void setWhitePieces(PieceList list) {
        this.whitePieces = list;
    }
    public void setBlackPieces(PieceList list) {
        this.blackPieces = list;
    }
    public String getStatus() {
        return this.status;
    }
    public int[][] getMatrix() {
        return this.matrix;
    }
    public void setMatrixValue(int position_x, int position_y, int value){
        for (int i = 0; i < this.matrix.length; i++) {
            if(position_y == i) {
                for (int j = 0; j < this.matrix[i].length; j++) {
                    if(position_x == j) this.matrix[i][j] = value;
                }
            }
        }
    }
    public boolean checkPosition(Piece p, int newx, int newy){
        boolean flag = false;
        int nx = Math.abs(p.getPositionX() - newx);
        int ny = Math.abs(p.getPositionY() - newy);
        switch(p.getName()) {
            case "Pawn":
                flag = moveLikePawn(p, newx, newy);
                break;
            case "Rook":
                flag = moveLikeRook(p, newx, newy);
                break;
            case "Knight":
                if((nx == 2) && (ny == 1)) flag = true;
                if((ny == 2) && (nx == 1)) flag = true;
                break;
            case "King":
                if((nx == 1) && (ny == 0)) flag = true;
                if((nx == 0) && (ny == 1)) flag = true;
                if (nx == ny) flag = moveLikePawn(p, newx, newy);
                break;
            case "Queen":
                if((nx > 0 && nx <=8) && (ny == 0)) flag = moveLikeRook(p, newx, newy);
                else if((ny > 0 && ny <= 8 ) && (nx == 0)) flag = moveLikeRook(p, newx, newy);
                else if((nx > 0 && nx <=8) && (ny == nx)) flag = moveLikeBishop(p, newx, newy);
                else if((ny > 0 && ny <= 8) && (nx == ny)) flag = moveLikeBishop(p, newx, newy);
                break;
            case "Bishop":
                if((nx > 0 && nx <=8) && (ny == nx)) flag = moveLikeBishop(p, newx, newy);
                else if((ny > 0 && ny <= 8) && (nx == ny)) flag = moveLikeBishop(p, newx, newy);
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }
    //Check OR Checkmate
    public void kingCheck(Piece p) {
        Piece pKing = ( p.getColor().equals("White") ) ? this.blackPieces.getFirstPieceBySymbol("K") : this.whitePieces.getFirstPieceBySymbol("K");
        int nx = Math.abs(p.getPositionX() - pKing.getPositionX());
        int ny = Math.abs(p.getPositionY() - pKing.getPositionY());
        boolean flag = false;
        switch(p.getName()) {
            case "Pawn":
                flag = moveLikePawn(p, pKing.getPositionX(), pKing.getPositionY());
                break;
            case "Rook":
                flag = moveLikeRook(p, pKing.getPositionX(), pKing.getPositionY());
                break;
            case "Knight":
                if((nx == 2) && (ny == 1)) flag = true;
                if((ny == 2) && (nx == 1)) flag = true;
                break;
            case "King":
                if((nx == 1) && (ny == 0)) flag = true;
                if((nx == 0) && (ny == 1)) flag = true;
                if (nx == ny) flag = moveLikePawn(p, pKing.getPositionX(), pKing.getPositionY());
                break;
            case "Queen":
                if((nx > 0 && nx <=8) && (ny == 0)) flag = moveLikeRook(p, pKing.getPositionX(), pKing.getPositionY());
                else if((ny > 0 && ny <= 8 ) && (nx == 0)) flag = moveLikeRook(p, pKing.getPositionX(), pKing.getPositionY());
                else if((nx > 0 && nx <=8) && (ny == nx)) flag = moveLikeBishop(p, pKing.getPositionX(), pKing.getPositionY());
                else if((ny > 0 && ny <= 8) && (nx == ny)) flag = moveLikeBishop(p, pKing.getPositionX(), pKing.getPositionY());
                break;
            case "Bishop":
                if((nx > 0 && nx <=8) && (ny == nx)) flag = moveLikeBishop(p, pKing.getPositionX(), pKing.getPositionY());
                else if((ny > 0 && ny <= 8) && (nx == ny)) flag = moveLikeBishop(p, pKing.getPositionX(), pKing.getPositionY());
                break;
            default:
                flag = false;
                break;
        }
        if(flag) this.status = "CHECK_OR_CHECKMATE_STATUS";
        else this.status = "PLAYING_STATUS";
    }

    //Movement like pawn
    public boolean moveLikePawn(Piece p, int newx, int newy)
    {
        boolean flag = false;
        int n = Math.abs(p.getPositionY() - newy);
        if(p.getColor().equals("White") && p.getPositionY() == 1) {
            if(((n > 0 && n <=2) && (p.getPositionX() == newx)) && (this.blackPieces.getPiece(newx, newy) == null)) {
                flag = true;
                if(this.blackPieces.getPiece(newx, newy - 1) != null && !this.blackPieces.getPiece(newx, newy - 1).equals(p)) flag = false;
            }
        }
        else if(p.getColor().equals("Black") && p.getPositionY() == 6) {
            if(((n > 0 && n <=2) && (p.getPositionX() == newx)) && (this.whitePieces.getPiece(newx, newy) == null)) {
                flag = true;
                if(this.whitePieces.getPiece(newx, this.matrix.length - (newy - 1)) != null && !this.whitePieces.getPiece(newx, this.matrix.length - (newy - 1)).equals(p)) flag = false;
            }
        }
        if(p.getColor().equals("White") && (p.getPositionX() - 1 == newx || p.getPositionX() + 1 == newx ) && p.getPositionY() + 1 == newy) {
            if( this.blackPieces.getPiece(newx, newy) != null ) flag = true;
        }
        else if(p.getColor().equals("Black") && (p.getPositionX() - 1 == newx || p.getPositionX() + 1 == newx) && p.getPositionY() - 1 == newy) {
            if( this.whitePieces.getPiece(newx, newy) != null ) flag = true;
        }
        return flag;
    }
    //Diagonal movement like bishop
    public boolean moveLikeBishop(Piece p, int newx, int newy)
    {
        boolean flag = true;
        Piece p1 = null;
        Piece p2 = null;
        for(int k = 0; k < this.matrix.length; k++)
        {
            //Eje Y hacia abajo
            if(newy > p.getPositionY())
            {
                //Eje X
                if (newx > p.getPositionX()) {
                    p1 = this.whitePieces.getPiece(newx - k, newy - k);
                    p2 = this.blackPieces.getPiece(newx - k, newy - k);
                } else {
                    p1 = this.whitePieces.getPiece(newx + k, newy - k);
                    p2 = this.blackPieces.getPiece(newx + k, newy - k);
                }
                if(p1 != null && !p1.equals(p)) {
                    if(p.getPositionY() < p1.getPositionY() && p1.getPositionY() <= newy) {
                        if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor())) flag = true;
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
                else if(p2 != null && !p2.equals(p)) {
                    if(p.getPositionY() < p2.getPositionY() && p2.getPositionY() <= newy) {
                        if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor())) flag = true;
                        else {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            //Eje Y hacia arriba
            else if(newy < p.getPositionY()) {
                // Eje X
                if (newx > p.getPositionX()) {
                    p1 = this.whitePieces.getPiece(p.getPositionX() + k, p.getPositionY() - k);
                    p2 = this.blackPieces.getPiece(p.getPositionX() + k, p.getPositionY() - k);
                }
                else {
                    p1 = this.whitePieces.getPiece(p.getPositionX() - k, p.getPositionY() - k);
                    p2 = this.blackPieces.getPiece(p.getPositionX() - k, p.getPositionY() - k);
                }
                if(p1 != null && !p1.equals(p)) {
                    if(p.getPositionY() > p1.getPositionY() && p1.getPositionY() >= newy) {
                        if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor())) flag = true;
                        else {
                            flag = false;
                            break;
                        }
                        
                    }
                }
                else if(p2 != null && !p2.equals(p)) {
                    if(p.getPositionY() > p2.getPositionY() && p2.getPositionY() >= newy) {
                        if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor())) flag = true;
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
            for(int i = 0; i < this.matrix.length; i+=1) {
                Piece p1 = this.whitePieces.getPiece(i, p.getPositionY());
                Piece p2 = this.blackPieces.getPiece(i, p.getPositionY());
                //MOVIMIENTO PARA LA DERECHA
                if(newx > p.getPositionX()) {
                    if((p1 != null && !p1.equals(p)) || (p2 != null && !p2.equals(p))) {
                        if((p1 != null && !p1.equals(p))) {
                            if (newx >= p1.getPositionX() && p1.getPositionX() > p.getPositionX()) {
                                if(newx == p1.getPositionX() && !p1.getColor().equals(p.getColor())) {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        else if(p2 != null && !p2.equals(p)) {
                            if(newx >= p2.getPositionX() && p2.getPositionX() > p.getPositionX()) {
                                if(newx == p2.getPositionX() && !p2.getColor().equals(p.getColor())) {
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
                if(newx < p.getPositionX()) {
                    if((p1 != null && !p1.equals(p)) || (p2 != null && !p2.equals(p))) {
                        if((p1 != null && !p1.equals(p))) {
                            if (newx <= p1.getPositionX() && p1.getPositionX() < p.getPositionX()) {
                                if(newx == p1.getPositionX() && !p1.getColor().equals(p.getColor())) {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        else if(p2 != null && !p2.equals(p)) {
                            if(newx <= p2.getPositionX() && p2.getPositionX() < p.getPositionX()) {
                                if(newx == p2.getPositionX() && !p2.getColor().equals(p.getColor())) {
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
            }
        }
        //Eje de las Y
        else if(p.getPositionX() == newx && ny != 0) {
            for(int i = 0; i < this.matrix.length; i+=1) {
                Piece p1 = this.whitePieces.getPiece(p.getPositionX(), i);
                Piece p2 = this.blackPieces.getPiece(p.getPositionX(), i);
                //MOVIMIENTO PARA ABAJO
                if(newy > p.getPositionY()) {
                    if((p1 != null && !p1.equals(p))) {
                        if (newy >= p1.getPositionY() && p1.getPositionY() > p.getPositionY()) {
                            if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor())) {
                                flag = true;
                                break;
                            }
                            else {
                                flag = false;
                                break;
                            }
                        }
                    }
                    else if(p2 != null && !p2.equals(p)) {
                        if(newy >= p2.getPositionY() && p2.getPositionY() > p.getPositionY()) {
                            if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor())) {
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
                if(newy <= p.getPositionY()) {
                    if((p1 != null && !p1.equals(p)) || (p2 != null && !p2.equals(p))) {
                        if((p1 != null && !p1.equals(p))) {
                            if (newy <= p1.getPositionY() && p1.getPositionY() < p.getPositionY()) {
                                if(newy == p1.getPositionY() && !p1.getColor().equals(p.getColor())) {
                                    flag = true;
                                    break;
                                }
                                else {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        else if(p2 != null && !p2.equals(p)) {
                            if(newy <= p2.getPositionY() && p2.getPositionY() < p.getPositionY()) {
                                if(newy == p2.getPositionY() && !p2.getColor().equals(p.getColor())) {
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
            }
        }
        //Movimiento en ambos ejes
        else if(nx != 0 && ny != 0)
        {
            flag = false;
        }
        return flag;
    }
}
