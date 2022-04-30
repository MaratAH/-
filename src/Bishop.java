public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Если начальная точка не равна конечной и
        if (line != toLine && column != toColumn &&
                // разница между координатами по линии и координатами по колонке должны быть равны (ходит по диагонали) и
                getMax(line, toLine) - getMin(line, toLine) == getMax(column, toColumn) - getMin(column, toColumn) &&
                // все координаты должны быть на доске и
                isOnBoard(line, column, toLine, toColumn) &&
                // конечная точка пустая или на ней стоит фигура другого цвета и
                (chessBoard.board[toLine][toColumn] == null || !chessBoard.board[toLine][toColumn].color.equals(this.color)) &&
                // стартовая клетка не пустая то выполняется следующий код:
                chessBoard.board[line][column] != null) {
            // при движении сверху слева направо вниз (или в обратном направлении)
            if (column == getMin(column, toColumn) && line == getMax(line, toLine) || //если первый случай
                    toColumn == getMin(column, toColumn) && toLine == getMax(line, toLine)) {// или обратный выполняется =>
                // макс и мин нужны чтобы можно было ходить сверху справа вниз влево
                int fromL = getMax(line, toLine);
                int fromC = getMin(column, toColumn);
                int toL = getMin(line, toLine);
                int toC = getMax(column, toColumn);
                // позиции которые слон проходит по пути
                int[][] positions = new int[toC - fromC][1];
                // взяли колонки потому что количество колонок = количеству линий (можно было и линии, без разницы)
                // цикл проверки каждой точки которую проходит слон
                for (int i = 1; i < toC - fromC; i++) {
                    // если позиция куда хотим ходить пуста
                    if (chessBoard.board[fromL - i][fromC + i] == null) {
                        // записываем эти координаты (переходим в эту позицию)
                        positions[i - 1] = new int[]{fromL - i, fromC + i};
                        // иначе если цвет фигуры не соответствует цвету фигуры на этой клетке и эта клетка куда нам нужно сходить
                    } else if (!chessBoard.board[fromL - i][fromC + i].color.equals(this.color) && fromL - i == toLine) {
                        // переходим в эту клетку (т.е слон кого то срубит)
                        positions[i - 1] = new int[]{fromL - i, fromC + i};
                    } else {  // иначе ложь
                        return false;
                    }
                }
                return true;
            } else { //при движении сверху справа влево вниз (или в обратном направлении)
                // макс и мин нужны чтобы можно было ходить сверху слева вниз направо
                int fromL = getMin(line, toLine);
                int fromC = getMin(column, toColumn);
                int toL = getMax(line, toLine);
                int toC = getMax(column, toColumn);
                // позиции которые слон проходит по пути
                int[][] positions = new int[toC - fromC][1];
                // взяли колонки потому что количество колонок = количеству линий (можно было и линии, без разницы)
                // цикл проверки каждой точки которую проходит слон
                for (int i = 1; i < toC - fromC; i++) {
                    // если позиция куда хотим ходить пуста
                    if (chessBoard.board[fromL + i][fromC + i] == null) {
                        // записываем эти координаты (переходим в эту позицию)
                        positions[i - 1] = new int[]{fromL + i, fromC + i};
                        // иначе если цвет фигуры не соответствует цвету фигуры на этой клетке и эта клетка куда нам нужно сходить
                    } else if (!chessBoard.board[fromL - i][fromC + i].color.equals(this.color) && fromL + i == toLine) {
                        // переходим в эту клетку (т.е слон кого то срубит)
                        positions[i - 1] = new int[]{fromL + i, fromC + i};
                    } else {  // иначе ложь
                        return false;
                    }
                }
                return true;
            }
        } else return false;
    }

    @Override
    public String getSymbol() {
        return "B";
    }


}
