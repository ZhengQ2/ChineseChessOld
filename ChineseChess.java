package chinesechess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class ChineseChess {

    int[][] chessBoard = new int[9][10];
    Chess[] redChess = new Chess[17];
    Chess[] blackChess = new Chess[17];
    static int limit = 60;
    int redSteps = 0;
    int blackSteps = 0;

    public static void main(String[] args) {
        // TODO code application logic here
    }

    public void initialSetting() {
        redChess[1] = new Rook(1, 1, true);
        redChess[2] = new Rook(9, 1, true);
        redChess[3] = new Horse(2, 1, true);
        redChess[4] = new Horse(8, 1, true);
        redChess[5] = new Elephant(3, 1, true);
        redChess[6] = new Elephant(7, 1, true);
        redChess[7] = new Guard(4, 1, true);
        redChess[8] = new Guard(6, 1, true);
        redChess[9] = new General(5, 1, true);
        redChess[10] = new Cannon(2, 3, true);
        redChess[11] = new Cannon(8, 3, true);
        redChess[12] = new Pawn(1, 4, true);
        redChess[13] = new Pawn(3, 4, true);
        redChess[14] = new Pawn(5, 4, true);
        redChess[15] = new Pawn(7, 4, true);
        redChess[16] = new Pawn(9, 4, true);

        blackChess[1] = new Rook(1, 10, true);
        blackChess[2] = new Rook(9, 10, true);
        blackChess[3] = new Horse(2, 10, true);
        blackChess[4] = new Horse(8, 10, true);
        blackChess[5] = new Elephant(3, 10, true);
        blackChess[6] = new Elephant(7, 10, true);
        blackChess[7] = new Guard(4, 10, true);
        blackChess[8] = new Guard(6, 10, true);
        blackChess[9] = new General(5, 10, true);
        blackChess[10] = new Cannon(2, 8, true);
        blackChess[11] = new Cannon(8, 8, true);
        blackChess[12] = new Pawn(1, 7, true);
        blackChess[13] = new Pawn(3, 7, true);
        blackChess[14] = new Pawn(5, 7, true);
        blackChess[15] = new Pawn(7, 7, true);
        blackChess[16] = new Pawn(9, 7, true);

        for (int i = 0; i <= 8; i++) {
            for (int j = 0; i <= 9; i++) {
                chessBoard[i][j] = 0;
            }
        }

        chessBoard[0][0] = 1;
        chessBoard[8][0] = 2;
        chessBoard[1][0] = 3;
        chessBoard[7][0] = 4;
        chessBoard[2][0] = 5;
        chessBoard[6][0] = 6;
        chessBoard[3][0] = 7;
        chessBoard[5][0] = 8;
        chessBoard[4][0] = 9;
        chessBoard[1][2] = 10;
        chessBoard[7][2] = 11;
        chessBoard[0][3] = 12;
        chessBoard[2][3] = 13;
        chessBoard[4][3] = 14;
        chessBoard[6][3] = 15;
        chessBoard[8][3] = 16;

        chessBoard[0][9] = -1;
        chessBoard[8][9] = -2;
        chessBoard[1][9] = -3;
        chessBoard[7][9] = -4;
        chessBoard[2][9] = -5;
        chessBoard[6][9] = -6;
        chessBoard[3][9] = -7;
        chessBoard[5][9] = -8;
        chessBoard[4][9] = -9;
        chessBoard[1][8] = -10;
        chessBoard[7][8] = -11;
        chessBoard[0][7] = -12;
        chessBoard[2][7] = -13;
        chessBoard[4][7] = -14;
        chessBoard[6][7] = -15;
        chessBoard[8][7] = -16;
    }

    public void pushButtons(int chessRule, boolean red, int chessNum, int xPosMove, int yPosMove) {
        //Chess Rule:
        //1 - Pawns; 2 - Elephants; 3 - Horses; 4 - Rooks; 5 - Cannons; 6 - guards; 7 - kings
        boolean move;
        int oriXPos, oriYPos;
        int newXPos, newYPos;

        if (red) {
            oriXPos = redChess[chessNum].getXPos();
            oriYPos = redChess[chessNum].getYPos();
        } else {
            oriXPos = blackChess[chessNum].getXPos();
            oriYPos = blackChess[chessNum].getYPos();
        }

        newXPos = oriXPos + xPosMove;
        newYPos = oriYPos + yPosMove;

        if (red) {
            if (chessBoard[newXPos + 1][newYPos + 1] > 0) {
                return;
            } else {
                chessBoard[newXPos + 1][newYPos + 1] = chessNum;
            }
        } else {
            if (chessBoard[newXPos + 1][newYPos + 1] < 0) {
                return;
            } else {
                chessBoard[newXPos + 1][newYPos + 1] = -chessNum;
            }
        }

        move = move(red, chessRule, xPosMove, yPosMove, chessNum);

        if (move) {
            if (red) {
                newXPos = redChess[chessNum].getXPos();
                newYPos = redChess[chessNum].getYPos();
                redSteps++;
            } else {
                newXPos = blackChess[chessNum].getXPos();
                newYPos = blackChess[chessNum].getYPos();
                blackSteps++;
            }

            chessBoard[oriXPos + 1][oriYPos + 1] = 0;

            int winner = checkWinner(red);
            if (winner == 1) {
                JOptionPane.showMessageDialog(null, "Red Wins!");
            } else if (winner == -1) {
                JOptionPane.showMessageDialog(null, "Black Wins!");
            }

        }

    }

    public boolean move(boolean red, int chessRule, int xPosMove, int yPosMove, int chessNum) {
        boolean returnVal;
        if (chessRule == 1) {
            if (red) {
                returnVal = ((Pawn) redChess[chessNum]).move(xPosMove, yPosMove);
            } else {
                returnVal = ((Pawn) blackChess[chessNum]).move(xPosMove, yPosMove);
            }
        } else if (chessRule == 2) {
            if (red) {
                returnVal = ((Elephant) redChess[chessNum]).move(xPosMove, yPosMove);
            } else {
                returnVal = ((Elephant) blackChess[chessNum]).move(xPosMove, yPosMove);
            }
        } else if (chessRule == 3) {
            boolean canMove = checkHorseMove(xPosMove, yPosMove, chessNum, red);

            if (red) {
                returnVal = ((Horse) redChess[chessNum]).move(xPosMove, yPosMove, canMove);
            } else {
                returnVal = ((Horse) blackChess[chessNum]).move(xPosMove, yPosMove, canMove);
            }

        } else if (chessRule == 4) {
            if (red) {
                returnVal = ((Rook) redChess[chessNum]).move(xPosMove, yPosMove);
            } else {
                returnVal = ((Rook) blackChess[chessNum]).move(xPosMove, yPosMove);
            }
        } else if (chessRule == 5) {
            boolean canEat = checkCannonEat(xPosMove, yPosMove, chessNum, red);

            if (red) {
                returnVal = ((Cannon) redChess[chessNum]).move(xPosMove, yPosMove, canEat);
            } else {
                returnVal = ((Cannon) blackChess[chessNum]).move(xPosMove, yPosMove, canEat);
            }
        } else if (chessRule == 6) {
            if (red) {
                returnVal = ((Guard) redChess[chessNum]).move(xPosMove, yPosMove);
            } else {
                returnVal = ((Guard) blackChess[chessNum]).move(xPosMove, yPosMove);
            }

        } else {
            if (red) {
                returnVal = ((General) redChess[chessNum]).move(xPosMove, yPosMove);
            } else {
                returnVal = ((General) blackChess[chessNum]).move(xPosMove, yPosMove);
            }
        }
        return returnVal;
    }

    public boolean checkHorseMove(int xMove, int yMove, int chessNum, boolean red) {

        int xPos, yPos;
        if (red) {
            xPos = redChess[chessNum].getXPos();
            yPos = redChess[chessNum].getYPos();
        } else {
            xPos = blackChess[chessNum].getXPos();
            yPos = blackChess[chessNum].getYPos();
        }
        if (xMove == 2) {
            if (chessBoard[xPos + 1][yPos] != 0) {
                return false;
            }
        } else if (xMove == -2) {
            if (chessBoard[xPos - 1][yPos] != 0) {
                return false;
            }
        } else if (yMove == 2) {
            if (chessBoard[xPos][yPos + 1] != 0) {
                return false;
            }
        } else if (yMove == -2) {
            if (chessBoard[xPos][yPos - 2] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCannonEat(int xMove, int yMove, int chessNum, boolean red) {
        int xPos, yPos;
        if (red) {
            xPos = redChess[chessNum].getXPos();
            yPos = redChess[chessNum].getYPos();
        } else {
            xPos = blackChess[chessNum].getXPos();
            yPos = blackChess[chessNum].getYPos();
        }
        int middle = 0;
        if (xMove == 0) {
            if (yMove > 0) {
                for (int i = 1; i < yMove; i++) {
                    if (chessBoard[xPos - 1][yPos + i - 1] != 0) {
                        middle++;
                    }
                    if (middle == 1) {
                        return true;
                    }
                }
            } else if (yMove < 0) {
                for (int i = -1; i > yMove; i--) {
                    if (chessBoard[xPos - 1][yPos + i - 1] != 0) {
                        middle++;
                    }
                    if (middle == 1) {
                        return true;
                    }
                }
            }
        } else {
            if (xMove > 0) {
                for (int i = 1; i < xMove; i++) {
                    if (chessBoard[xPos - 1 + i][yPos - 1] != 0) {
                        middle++;
                    }
                    if (middle == 1) {
                        return true;
                    }
                }
            } else if (xMove < 0) {
                for (int i = -1; i > xMove; i--) {
                    if (chessBoard[xPos - 1 + i][yPos - 1] != 0) {
                        middle++;
                    }
                    if (middle == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int countDown(boolean red) {
        if (red) {
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    limit--;
                    System.out.println(limit);
                    if (limit == 0) {
                        timer.cancel();
                    }
                }

            }, 0, 1000);

        } else {
            Timer timer2 = new Timer();

            timer2.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    limit--;
                    System.out.println(limit);
                    if (limit == 0) {
                        timer2.cancel();
                    }
                }

            }, 0, 1000);

        }

        if (limit == 0 && red) {
            return 1;
        } else if (limit == 0 && !red) {
            return -1;
        } else {
            return 0;
        }
    }

    public int getSteps(boolean red, int redSteps, int blackSteps) {
        if (red) {
            return redSteps;
        } else {
            return blackSteps;
        }
    }

    public void save(int saveNum) {
        String gameChess = "";
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 10; column++) {
                gameChess += chessBoard[row][column] + "\n";
            }
        }
        File file;
        try {
            if (saveNum == 1) {
                file = new File("src//chinesechess//save1.txt");
            } else if (saveNum == 2) {
                file = new File("src//chinesechess//save2.txt");
            } else if (saveNum == 3) {
                file = new File("src//chinesechess//save3.txt");
            } else if (saveNum == 4) {
                file = new File("src//chinesechess//save4.txt");
            } else {
                file = new File("src//chinesechess//save5.txt");
            }
            PrintWriter fw = new PrintWriter(file);
            fw.write(gameChess);
            fw.flush();
            fw.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error" + ex);
        }
    }

    public void load(int loadNum) {
        String sChess;
        boolean eof = false;

        try {
            FileReader fr;
            if (loadNum == 1) {
                fr = new FileReader("src//load//save1.txt");
            } else if (loadNum == 2) {
                fr = new FileReader("src//load//save2.txt");
            } else if (loadNum == 3) {
                fr = new FileReader("src//load//save3.txt");
            } else if (loadNum == 4) {
                fr = new FileReader("src//load//save4.txt");
            } else {
                fr = new FileReader("src//load//save5.txt");
            }
            BufferedReader br = new BufferedReader(fr);

            while (!eof) {
                for (int row = 0; row < 9; row++) {
                    for (int column = 0; column < 10; column++) {
                        sChess = br.readLine();
                        chessBoard[row][column] = Integer.parseInt(sChess);
                        if (chessBoard[row][column] < 0) {
                            if (false) {//black
                                if (chessBoard[row][column] == -1) {
                                    blackChess[1] = new Rook(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -2)) {
                                    blackChess[2] = new Rook(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -3)) {
                                    blackChess[3] = new Horse(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -4)) {
                                    blackChess[4] = new Horse(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -5)) {
                                    blackChess[5] = new Elephant(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -6)) {
                                    blackChess[6] = new Elephant(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -7)) {
                                    blackChess[7] = new Guard(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -8)) {
                                    blackChess[8] = new Guard(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -9)) {
                                    blackChess[9] = new General(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -10)) {
                                    blackChess[10] = new Cannon(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -11)) {
                                    blackChess[11] = new Cannon(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -12)) {
                                    blackChess[12] = new Pawn(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -13)) {
                                    blackChess[13] = new Pawn(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -14)) {
                                    blackChess[14] = new Pawn(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -15)) {
                                    blackChess[15] = new Pawn(row + 1, column + 1, false);
                                } else if ((chessBoard[row][column] == -16)) {
                                    blackChess[16] = new Pawn(row + 1, column + 1, false);
                                }
                            }
                        } else if (chessBoard[row][column] > 0) {
                            if (true) {//red
                                if (chessBoard[row][column] == 1) {
                                    redChess[1] = new Rook(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 2)) {
                                    redChess[2] = new Rook(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 3)) {
                                    redChess[3] = new Horse(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 4)) {
                                    redChess[4] = new Horse(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 5)) {
                                    redChess[5] = new Elephant(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 6)) {
                                    redChess[6] = new Elephant(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 7)) {
                                    redChess[7] = new Guard(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 8)) {
                                    redChess[8] = new Guard(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 9)) {
                                    redChess[9] = new General(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 10)) {
                                    redChess[10] = new Cannon(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 11)) {
                                    redChess[11] = new Cannon(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 12)) {
                                    redChess[12] = new Pawn(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 13)) {
                                    redChess[13] = new Pawn(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 14)) {
                                    redChess[14] = new Pawn(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 15)) {
                                    redChess[15] = new Pawn(row + 1, column + 1, true);
                                } else if ((chessBoard[row][column] == 16)) {
                                    redChess[16] = new Pawn(row + 1, column + 1, true);
                                }
                            }
                        }
                    }
                }
            }
            fr.close();
            br.close();
        } catch (IOException ex) {
            System.out.println("Error" + ex);
        }
    }

    public int checkWinner(boolean roundRed) {
        boolean redKing, blackKing;
        redKing = false;
        blackKing = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (chessBoard[i][j] == 1) {
                    redKing = true;
                } else if (chessBoard[i][j] == -1) {
                    blackKing = true;
                }
            }
        }

        if (!redKing) {
            return -1;//black wins
        } else if (!blackKing) {
            return 1;//red wins
        }

        boolean kingMeets = false;

        int redKingPos = redChess[0].getYPos();
        int blackKingPos = blackChess[0].getYPos();

        if (redKingPos == blackKingPos) {
            for (int i = 0; i < 10; i++) {
                if (chessBoard[redKingPos - 1][i] != 0) {
                    return 0;
                }
                if (roundRed) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        if (countDown(roundRed) == 1) {
            return 1;
        } else if (countDown(roundRed) == -1) {
            return -1;
        }
        return 0;
    }

}
