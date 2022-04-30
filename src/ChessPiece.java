public abstract class ChessPiece {
    String color;
    boolean check = true;

    public ChessPiece(String color) {
        this.color = color;
    }

    public abstract String getColor();

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public abstract String getSymbol();

    public boolean checkPos(int pos) { // проверьте правильность нашей позиции
        return pos >= 0 && pos <= 7;
    }
    public boolean isOnBoard (int line,int column,int toLine, int toColumn){
        return checkPos(line) && checkPos(column) && checkPos(toLine) && checkPos(toColumn);
    }

    int getMax(int a, int b) {
        return Math.max(a, b);
    }

    int getMin(int a, int b) {
        return Math.min(a, b);
    }
}
