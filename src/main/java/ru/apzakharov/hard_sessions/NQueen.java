package ru.apzakharov.hard_sessions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NQueen {


  public static void main(String[] args) {
    NQueen nQueen = new NQueen();
    List<List<String>> board = nQueen.solve(4);

    board.forEach(System.out::println);
  }

  public List<List<String>> solve(int n) {
    String[][] board = new String[n][n];
    List<List<String>> result = new ArrayList<>(n);
    solveRecursive(board, result, 0, n);

    return result;
  }

  private void solveRecursive(String[][] board, List<List<String>> result, int col, int n) {
    if (col >= n) {
      List<String> boardAsList = Arrays.stream(board)
          .map(row ->
              Arrays.stream(row)
                  .map(cell -> cell == null ? "." : cell)
                  .collect(Collectors.joining()))
          .collect(Collectors.toList());
      result.add(boardAsList);
      return; // Конец рекурсии, переходим к другим веткам дерева решений
    }

    for (int row = 0; row < n; row++) {
      if (canBePlaced(board, row, col, n)) {
        board[row][col] = "Q";
        solveRecursive(board, result, col + 1, n);

        board[row][col] = null;
      }

    }

  }

  private boolean canBePlaced(String[][] board, int row, int col, int n) {
    int i, j;

    for (i = 0; i < col; i++) {
      if (Objects.equals(board[row][i], "Q")) {
        return false;
      }
    }

    for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
      if (Objects.equals(board[i][j], "Q")) {
        return false;
      }
    }

    for (i = row, j = col; j >= 0 && i < n; i++, j--) {
      if (Objects.equals(board[i][j], "Q")) {
        return false;
      }
    }

    return true;
  }

}
