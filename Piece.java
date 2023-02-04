public class Piece {
    
    private String name;
    private String symbol;
    private int position_x;
    private int position_y;
    private String color;
    
    public Piece(String name, String symbol, int position_x, int position_y, String color) {
        this.name = name;
        this.symbol = symbol;
        this.position_x = position_x;
        this.position_y = position_y;
        this.color = color;
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getPositionX() {
        return position_x;
    }
    public int getPositionY() {
        return position_y;
    }
    public String getColor() {
        return color;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public void setPositionX(int position) {
        this.position_x = position;
    }
    public void setPositionY(int position) {
        this.position_y = position;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Piece " + this.name + " in x: " + Integer.toString(position_x) + " and y: " + Integer.toString(position_y);
    }

}
