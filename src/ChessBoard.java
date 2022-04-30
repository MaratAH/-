public class ChessBoard {
    ChessPiece[][] board = new ChessPiece[8][8]; // двумерный массив объектов шахматных фигур
    String nowPlayer; // переменная показывает чей сейчас ход

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        // проверяем с какой линии и колонки и на какую линию и колонку нужно переместить фигуру(есть ли такая клетка на доске)
        if (checkPos(startLine) && checkPos(startColumn)) {
            // если цвет текущего игрока не совпадает с цветом фигуры на данной клетке, то ход не возможен
            // (нельзя играть чужими фигурами)
            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;
            //  если данная фигура может быть сдвинута на эту позицию
            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                // проверяем позицию для рокировки
                // если позиция - это король (King) или - ладья (Rook)
                if (board[startLine][startColumn].getSymbol().equals("K") || board[startLine][startColumn].getSymbol().equals("R")) {
                    // устанавливаем, что данная фигура еще не двигалась
                    board[startLine][startColumn].check = false;
                }

                // если была возможность сдвинуть фигуру, то переместили фигуру на конечную клетку
                board[endLine][endColumn] = board[startLine][startColumn];
                // удалили фигуру со стартовой клетки (сделав ход, убрали фигуру со стартовой позиции)
                board[startLine][startColumn] = null;
                // если был ход белых, то следующий ход за черными
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                return true;
            } else return false;
        } else return false;
    }

    // этот метод выводит доску в консоль (пробегаясь по массиву и выставляя в пустых ячейках(где null) две точки)
// если нет, то в зависимости от цвета фигуры либо W, либо B
    public void printBoard() {
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        // если ход белых
        if (nowPlayer.equals("White")) {
            // длинная рокировка белых (через ферзевый фланг - рокировка слева,
            // клетка [0][0] или [0][4], где стоят ладья и король соответственно, пустые, рокировка не возможна)
            if (board[0][0] == null || board[0][4] == null) return false;
            // если на клетке [0][0] стоит ладья и на клетке [0][4] стоит король и
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") &&
                    // клетки [0][1], [0][2], [0][3] пусты
                    board[0][1] == null && board[0][2] == null && board[0][3] == null) {
                // проверяем условие: если ладья на клетке [0][0] белая и король на клетке [0][4] белый и
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        // они не двигались и
                        board[0][0].check && board[0][4].check &&
                        // эта позиция не находится под атакой
                        !new King("White").isUnderAttack(this, 0, 2)) {
                    // клетка [0][4] пуста
                    board[0][4] = null;
                    // клетка [0][2] занята белым королем
                    board[0][2] = new King("White");
                    // после перемещения короля на клетку [0][2] рокировка более не доступна
                    board[0][2].check = false;
                    // клетка [0][0] пуста
                    board[0][0] = null;
                    // клетка [0][3] занята ладьей
                    board[0][3] = new Rook("White");
                    //после перемещения ладьи на клетку [0][3] рокировка более не доступна
                    board[0][3].check = false;
                    // переход хода к черным
                    nowPlayer = "Black";
                    return true;
                } else return false;
            } else return false;
            // иначе длинная рокировка черных (рокировка черных через ферзевый фланг слева)
        } else {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") &&
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][0].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 2)) {
                    board[7][4] = null;
                    board[7][2] = new King("Black");
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");
                    board[7][3].check = false;
                    nowPlayer = "White";
                    return true;
                } else return false;
            } else return false;
        }
    }

    public boolean castling7() {
        // если ход белых
        if (nowPlayer.equals("White")) {
            // короткая рокировка белых (через королевский фланг - рокировка справа,
            // клетка [0][7] или [0][4], где стоят ладья и король соответственно, пустые, рокировка не возможна)
            if (board[0][7] == null || board[0][4] == null) return false;
            // если на клетке [0][7] стоит ладья и на клетке [0][4] стоит король и
            if (board[0][7].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") &&
                    // клетки [0][5], [0][6] пусты
                    board[0][5] == null && board[0][6] == null) {
                // проверяем условие: если ладья на клетке [0][0] белая и король на клетке [0][4] белый и
                if (board[0][7].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        // они не двигались и
                        board[0][7].check && board[0][4].check &&
                        // эта позиция не находится под атакой
                        !new King("White").isUnderAttack(this, 0, 6)) {
                    // клетка [0][4] пуста
                    board[0][4] = null;
                    // клетку [0][6] занимаем белым королем
                    board[0][6] = new King("White");
                    // после перемещения короля на клетку [0][6] рокировка более не доступна
                    board[0][6].check = false;
                    // клетка [0][7] пуста
                    board[0][7] = null;
                    // клетка [0][5] занята ладьей
                    board[0][5] = new Rook("White");
                    //после перемещения ладьи на клетку [0][5] рокировка более не доступна
                    board[0][5].check = false;
                    // переход хода к черным
                    nowPlayer = "Black";
                    return true;
                } else return false;
            } else return false;
            // иначе короткая рокировка черных (рокировка черных через королевский фланг справа)
        } else {
            if (board[7][7] == null || board[7][4] == null) return false;
            if (board[7][7].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") &&
                    board[7][5] == null && board[7][6] == null) {
                if (board[7][7].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][7].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 6)) {
                    board[7][4] = null;
                    board[7][6] = new King("Black");
                    board[7][6].check = false;
                    board[7][7] = null;
                    board[7][5] = new Rook("Black");
                    board[7][5].check = false;
                    nowPlayer = "White";
                    return true;
                } else return false;
            } else return false;
        }
    }
}
