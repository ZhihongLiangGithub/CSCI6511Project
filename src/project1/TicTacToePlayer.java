package project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToePlayer {
    private int chess;
    private boolean random;
    private int[][] currBoard;

    public TicTacToePlayer(boolean playFirst, boolean random) {
        this.chess = playFirst ? 1 : -1;
        this.random = random;
    }

    public void play(GameAgent agent) {
        currBoard = agent.getBoard();
        if (random) {
            randomPlay(agent);
        } else {
            if (currBoard[1][1] == 0) {
                agent.setBoard(chess, 1, 1);
            } else if (!canAttackOrDefense(agent)) {
                if (!canPlaceCorner(agent)) {
                    randomPlay(agent);
                }
            }
        }
    }

    private boolean canPlaceCorner(GameAgent agent) {
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        if (currBoard[0][2] == 0) {
            x.add(0);
            y.add(2);
        }
        if (currBoard[2][2] == 0) {
            x.add(2);
            y.add(2);
        }
        if (currBoard[2][0] == 0) {
            x.add(2);
            y.add(0);
        }
        if (currBoard[0][0] == 0) {
            x.add(0);
            y.add(0);
        }
        if (x.size() != 0) {
            Random rand = new Random();
            int num = rand.nextInt(x.size());
            agent.setBoard(chess, x.get(num), y.get(num));
            return true;
        }
        return false;
    }

    private boolean canAttackOrDefense(GameAgent agent) {
        int selfDiag = 0;
        int rivalDiag = 0;
        int[] zeroDiag = new int[]{-1, -1};
        int selfAntiDiag = 0;
        int rivalAntiDiag = 0;
        int[] zeroAntiDiag = new int[]{-1, -1};
        int[] defensePos = new int[]{-1, -1};
        for (int i = 0; i < 3; i++) {
            if (currBoard[i][i] == chess) {
                selfDiag++;
            } else if (currBoard[i][i] == -chess) {
                rivalDiag++;
            } else if (currBoard[i][i] == 0) {
                zeroDiag[0] = i;
                zeroDiag[1] = i;
            }
            if (currBoard[2 - i][i] == chess) {
                selfAntiDiag++;
            } else if (currBoard[2 - i][i] == -chess) {
                rivalAntiDiag++;
            } else if (currBoard[2 - i][i] == 0) {
                zeroAntiDiag[0] = 2 - i;
                zeroAntiDiag[1] = i;
            }
            int selfRow = 0;
            int rivalRow = 0;
            int[] zeroRow = new int[]{-1, -1};
            int selfCol = 0;
            int rivalCol = 0;
            int[] zeroCol = new int[]{-1, -1};
            for (int j = 0; j < 3; j++) {
                if (currBoard[i][j] == chess) {
                    selfRow++;
                } else if (currBoard[i][j] == -chess) {
                    rivalRow++;
                } else if (currBoard[i][j] == 0) {
                    zeroRow[0] = i;
                    zeroRow[1] = j;
                }
                if (currBoard[j][i] == chess) {
                    selfCol++;
                } else if (currBoard[j][i] == -chess) {
                    rivalCol++;
                } else if (currBoard[j][i] == 0) {
                    zeroCol[0] = j;
                    zeroCol[1] = i;
                }
            }
            if (selfRow == 2 && zeroRow[0] > -1) {
                agent.setBoard(chess, zeroRow[0], zeroRow[1]);
                return true;
            }
            if (selfCol == 2 && zeroCol[0] > -1) {
                agent.setBoard(chess, zeroCol[0], zeroCol[1]);
                return true;
            }
            if (rivalRow == 2 && zeroRow[0] > -1) {
                defensePos[0] = zeroRow[0];
                defensePos[1] = zeroRow[1];
            }
            if (rivalCol == 2 && zeroCol[0] > -1) {
                defensePos[0] = zeroCol[0];
                defensePos[1] = zeroCol[1];
            }
        }
        if (selfDiag == 2 && zeroDiag[0] > -1) {
            agent.setBoard(chess, zeroDiag[0], zeroDiag[1]);
            return true;
        }
        if (selfAntiDiag == 2 && zeroAntiDiag[0] > -1) {
            agent.setBoard(chess, zeroAntiDiag[0], zeroAntiDiag[1]);
            return true;
        }
        if (rivalDiag == 2 && zeroDiag[0] > -1) {
            agent.setBoard(chess, zeroDiag[0], zeroDiag[1]);
            return true;
        }
        if (rivalAntiDiag == 2 && zeroAntiDiag[0] > -1) {
            agent.setBoard(chess, zeroAntiDiag[0], zeroAntiDiag[1]);
            return true;
        }
        if (defensePos[0] > -1) {
            agent.setBoard(chess, defensePos[0], defensePos[1]);
            return true;
        }
        return false;
    }

    private void randomPlay(GameAgent agent) {
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currBoard[i][j] == 0) {
                    x.add(i);
                    y.add(j);
                }
            }
        }
        Random rand = new Random();
        int num = rand.nextInt(x.size());
        agent.setBoard(chess, x.get(num), y.get(num));
    }
}
