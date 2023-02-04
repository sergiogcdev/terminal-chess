public class Table {
    private int[][] tableMatrix = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
    };
    public int[][] getMatrix() {
        return this.tableMatrix;
    }
    public void setMatrixValue(int position_x, int position_y, int value){
        for (int i = 0; i < this.tableMatrix.length; i++)
        {
            if(position_y == i)
            {
                for (int j = 0; j < this.tableMatrix[i].length; j++)
                {
                    if(position_x == j)
                    {
                        this.tableMatrix[i][j] = value;
                    }
                }
        
            }
        }
    }
}
