public class Game{

  private int[][] board = new int[7][9];

  public static final int BLANKTILE = 0;
  public static final int GREENTILE = 1;
  public static final int REDTILE = 2;
  public static final int NOTTILE = -1;

  public Game(int col, int row, int color){

    //init the board
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(i == board.length - 1 && j % 2 != 0 ){
          board[i][j] = Game.NOTTILE;
        }else{
          board[i][j] = Game.BLANKTILE;
        }
      }
    }

  }

}
