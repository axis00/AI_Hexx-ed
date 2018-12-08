public class Game{

  private int[][] board = new int[7][9];

  public static final int BLANKTILE = 0;
  public static final int GREENTILE = 1;
  public static final int REDTILE = 2;
  public static final int NOTTILE = 3;

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

    //start config
    board[row][col] = color;
    board[row-1][col-1] = color;
    board[row-1][col+1] = color;

    //opponent color
    board[row][col-1] = color == Game.GREENTILE ? Game.REDTILE : Game.GREENTILE;
    board[row][col+1] = color == Game.GREENTILE ? Game.REDTILE : Game.GREENTILE;
    board[row-2][col] = color == Game.GREENTILE ? Game.REDTILE : Game.GREENTILE;

  }

  @Override
  public String toString(){
    String res = "";
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        res += board[i][j] + " ";
      }
      res += "\n";
    }

    return res;
  }

  public static void main(String[] args) {
    Game g = new Game(7,4,Game.GREENTILE);
    System.out.println(g);
  }

}
