package project2;

public class PointScore {

    private int row;
    private int column;
    private double score;

    public PointScore(int row, int column, double score) {
        this.row = row;
        this.column = column;
        this.score = score;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double getScore() {
        return score;
    }

}
