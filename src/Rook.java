public class Rook extends ChessPiece {
    public Rook(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Если все заданные координаты есть на доске, выполняется следующий код:
        if (isOnBoard (line,column,toLine,toColumn)) {
            // если ходим по вертикали (по колонке)
            if (column == toColumn) {
                // проходим линии сверху вниз (хотя фигура может двигаться снизу вверх)
                for (int i = getMin(line, toLine); i < getMax(line, toLine); i++) {
                    // если следующая клетка не занята
                    if (chessBoard.board[i][column] != null) {
                        // нельзя двигаться туда где стоишь
                        if (chessBoard.board[i][column] == this && i == getMax(line, toLine)) return false;
                            // нельзя двигаться на клетку, где стоит фигура твоего цвета
                        else if (chessBoard.board[i][column].getColor().equals(this.color) && i == toLine)
                            return false;
                        else if (!chessBoard.board[i][column].getColor().equals(this.color) && i == toLine)
                            return true;
                        else if (i != toLine && i != line) return false;
                    }
                }
                // если на конечной клетке стоит какая-то фигура
                if (chessBoard.board[toLine][column] != null) {
                    // нельзя двигаться на клетку, где стоит фигура твоего цвета
                    //если          цвет фигуры в этой клетке       равен   цвету нашей и  координаты клетки куда мы ходим не равны этим где мы стоим
                    if (chessBoard.board[toLine][column].getColor().equals(this.color) && chessBoard.board[toLine][column] != this) {
                        return false;
                        // иначе вернет срубить
                    } else
                        return !chessBoard.board[toLine][column].getColor().equals(this.color) && chessBoard.board[toLine][column] != this;
                } else return true;
                // теперь пойдем по линии
            } else if (line == toLine) {
                // проходим по колонке справа налево (хотя фигура может двигаться слева на право)
                for (int i = getMin(toColumn, column); i < getMax(column, toColumn); i++) {
                    // если следующая клетка не занята
                    if (chessBoard.board[line][i] != null) {
                        // нельзя двигаться туда где стоишь
                        if (chessBoard.board[line][i] == this && i == getMax(column, toColumn)) return false;
                            // нельзя двигаться на клетку, где стоит фигура твоего цвета
                        else if (chessBoard.board[line][i].getColor().equals(this.color) && i == toColumn)
                            return false;
                        else if (!chessBoard.board[line][i].getColor().equals(this.color) && i == toColumn)
                            return true;
                        else if (i != toLine && i != column) return false;
                    }
                }
                // если в клетке куда мы ходим, стоит фигура
                if (chessBoard.board[toLine][column] != null) {
                    // нельзя двигаться на клетку, где стоит фигура твоего цвета
                    //если          цвет фигуры в этой клетке         равен цвету нашей  и  координаты клетки куда мы ходим не равны этим где мы стоим
                    if (chessBoard.board[toLine][toColumn].getColor().equals(this.color) && chessBoard.board[toLine][toColumn] != this) {
                        return false;
                        // иначе вернет срубить
                    } else
                        return !chessBoard.board[toLine][toColumn].getColor().equals(this.color) && chessBoard.board[toLine][toColumn] != this;
                } else return true;
            } else return false;
        } else return false;
    }

    @Override
    public String getSymbol() {
        return "R";
    }
}
