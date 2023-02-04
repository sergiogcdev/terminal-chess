import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PieceList {
    
    private List<Piece> allPieces;

    public PieceList(List<Piece> list) {
        this.allPieces = list;
    }

    public Piece getPiece(int pos_x, int pos_y)
    {
        List<Piece> filteredList = new ArrayList<>();
        filteredList = this.allPieces.stream().filter(piece -> piece.getPositionX() == pos_x && piece.getPositionY() == pos_y).collect(Collectors.toList());
        Piece p;
        if(filteredList.size() > 0) p = filteredList.get(0);
        else p = null;
        return p;
    }

    public void setPiece(int oldx, int oldy, int newx, int newy)
    {
        List<Piece> auxList = new ArrayList<>();
        Piece oldPiece = this.getPiece(oldx, oldy);
        auxList.add(new Piece(oldPiece.getName(), oldPiece.getSymbol(), newx, newy, oldPiece.getColor()));
        for(Piece piece : allPieces)
        {
            Piece p = new Piece(piece.getName(), piece.getSymbol(), piece.getPositionX(), piece.getPositionY(), piece.getColor());
            auxList.add(p);
            if(piece.equals(oldPiece)) auxList.remove(p);
        }
        this.setAllPieces(auxList);
    }

    public void removeItem(Piece p)
    {
        this.allPieces.remove(p);
    }

    public List<Piece> getAllPieces() {
        return this.allPieces;
    }
    public void setAllPieces(List<Piece> list){
        this.allPieces = list;
    }

}
