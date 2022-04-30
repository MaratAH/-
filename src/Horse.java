public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        //если все указанные координаты существуют, то выполняется код ниже, иначе ложь
        if (isOnBoard (line,column,toLine,toColumn)) {
            // если стартовая клетка не является конечной клеткой
            if (line != toLine && column != toColumn &&
            // и конечная клетка пустая или цвет фигуры в конечной клетке не равен текущему
                    (chessBoard.board[toLine][toColumn] == null || !chessBoard.board[toLine][toColumn].getColor().equals(this.color)) &&
                // и стартовая клетка не пуста
            chessBoard.board[line][column]!=null){
                // если стартовая клетка не соответствует коню, то не ходим
               if (!chessBoard.board[line][column].equals(this)){
                    return false;
                }
               // перечислим все возможные варианты хода конем
                int[][] positions = new int[][]{
                        {line - 2, column - 1}, {line - 2, column + 1},
                        {line + 2, column - 1}, {line + 2, column + 1},
                        {line - 1, column - 2}, {line - 1, column + 2},
                        {line + 1, column - 2}, {line + 1, column + 2}};

               // проверяем, конечная клетка совпадает с возможностями хода конем, если да то правда, иначе ложь
                // (можно ли сходить в указанную клетку конем)
                for (int[] position : positions) {
                    if (position[0] == toLine && position[1] == toColumn )
                        return true;
                }
            }

        } else return false;
        return false;
    }


    @Override
    public String getSymbol() {
        return "H";
    }


}
