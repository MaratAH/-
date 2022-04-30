public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    //метод позволяющий определять может ли фигура двигаться в какую либо позицию
    //в него передается шахматная доска, линия и колонка с которых начинается ход, линия и колонка куда будет сделан ход
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // проверяем все ли начальные и переданные координаты существуют(все ли они есть на нашей доске)
        // и есть ли по указанным координатам на доске шахматная фигура
        if (isOnBoard (line,column,toLine,toColumn) && chessBoard.board[line][column] != null) {
            if (column == toColumn) { // реализовываем часть метода когда пешка не ходит наискосок (не рубит)
                int dir; // переменная говорит на сколько клеток пешка движется вперед (длина хода, для белых со знаком плюс)
                int start; // переменная которая говорит на какой line стоит пешка
                if (color.equals("White")) { // если пешка белая
                    dir = 1; // положительная - значит вверх по доске
                    start = 1; // стартовая линия на которой находится пешка
                } else {
                    dir = -1; // отрицательная - значит вниз по доске
                    start = 6;
                }
                // проверяем можем ли сходить в конечную клетку
                if (line + dir == toLine) {
                    // если указанная клетка (chessBoard.board[toLine][toColumn]) пуста то вернется правда, иначе ложь
                    return chessBoard.board[toLine][toColumn] == null;//
                }
                // если линия, на которой стоит пешка, является стартовой (1 или 6) и ход выполняется на 2 клетки
                if (line == start && line + 2 * dir == toLine) {
                    // если конечная клетка свободна и на ее пути нет фигур, то функция вернет правду
                    return (chessBoard.board[toLine][toColumn] == null && chessBoard.board[line + dir][column] == null);
                }
            }
        } else { // теперь пешка должна рубить
            // если по колонке и линии сдвигаемся на один
            if (column - toColumn == 1 || column - toColumn == -1 && line - toLine == 1 || line - toLine == -1 &&
            // и на этой клетке есть фигура
            chessBoard.board[toLine][toColumn] != null){
                //возвращаем правду если цвет этой фигуры не совпадает с цветом нашей фигуры
                return (!chessBoard.board[toLine][toColumn].getColor().equals(color));
            } else return false;

        } return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }



}
