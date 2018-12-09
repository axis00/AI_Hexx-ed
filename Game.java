import java.util.LinkedList;

public class Game{

  class Move{

    private int col, row, player;

    public Move(int row, int col, int player){
      this.col = col;
      this.row = row;
      this.player = player;
    }

    public void setCol(int col){
      this.col = col;
    }

    public void setRow(int row){
      this.row = row;
    }

    public void setPlayer(int player){
      this.player = player;
    }

    public int getCol(){
      return col;
    }

    public int getRow(){
      return row;
    }

    public int getPlayer(){
      return player;
    }

    @Override
    public String toString(){
      return "Column : " + this.col + " Row : " + this.row +
        " Player : " + (player == Game.GREEN ? "GREEN" : "RED");

    }

  }

  private int[][] board = new int[7][9];
  private int currentPlayer = Game.GREEN;

  public static final int BLANKTILE = 0;
  public static final int GREEN = 1;
  public static final int RED = 2;
  public static final int NOTTILE = 3;

  //traversal directions
  public static final int DIRECTION_UP = 0;
  public static final int DIRECTION_DOWN = 1;
  public static final int DIRECTION_UPLEFT = 2;
  public static final int DIRECTION_UPRIGHT = 3;
  public static final int DIRECTION_DOWNLEFT = 4;
  public static final int DIRECTION_DOWNRIGHT = 5;

  public Game(int col, int row, int color, int firstMove){

    this.currentPlayer = firstMove;

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
    int rowOffset = 0;
    if(col % 2 == 0){
      rowOffset = 1;
    }
    board[row][col] = color;
    board[row - (rowOffset + 1)][col-1] = color;
    board[row - (rowOffset + 1)][col+1] = color;

    //opponent color
    board[row - rowOffset][col-1] = color == Game.GREEN ? Game.RED : Game.GREEN;
    board[row - rowOffset][col+1] = color == Game.GREEN ? Game.RED : Game.GREEN;
    board[row - 2][col] = color == Game.GREEN ? Game.RED : Game.GREEN;

  }

  /**
  * returns a list of valid moves
  */
  public LinkedList<Move> getNextValidMoves(){

    LinkedList<Move> moves = new LinkedList<>();
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(isMoveValid(i,j,false)){
          moves.add(new Move(i,j,currentPlayer));
        }
      }
    }

    return moves;
  }

  public void move(Move m) throws Exception{

    int row = m.getRow();
    int col = m.getCol();
    int player = m.getPlayer();
    if(player != currentPlayer){
      throw new Exception("Wrong Player");
    }
    if(isMoveValid(row,col,true)){
      board[row][col] = currentPlayer;
      currentPlayer = currentPlayer == Game.GREEN ? Game.RED : Game.GREEN;
      return;
    }else{
      throw new Exception("Invalid Move");
    }

  }

  private boolean isSandwiched(int row,int col, int direction, int opponent, boolean capture){

    int nextRow = row;
    int nextCol = col;

    // different directions
    //up
    if(direction == Game.DIRECTION_UP){
      nextRow = row + 1;
      nextCol = col;
    }

    //down
    if(direction == Game.DIRECTION_DOWN){
      nextRow = row - 1;
      nextCol = col;
    }

    if(col % 2 == 0){
      //col is even
      switch(direction){
        case Game.DIRECTION_UPLEFT:
          nextRow = row;
          nextCol = col - 1;
          break;
        case Game.DIRECTION_UPRIGHT:
          nextRow = row;
          nextCol = col + 1;
          break;
        case Game.DIRECTION_DOWNLEFT:
          nextRow = row - 1;
          nextCol = col - 1;
          break;
        case Game.DIRECTION_DOWNRIGHT:
          nextRow = row - 1;
          nextCol = col + 1;
          break;
      }
    } else {
      switch(direction){
        case Game.DIRECTION_UPLEFT:
          nextRow = row + 1;
          nextCol = col - 1;
          break;
        case Game.DIRECTION_UPRIGHT:
          nextRow = row + 1;
          nextCol = col + 1;
          break;
        case Game.DIRECTION_DOWNLEFT:
          nextRow = row;
          nextCol = col - 1;
          break;
        case Game.DIRECTION_DOWNRIGHT:
          nextRow = row;
          nextCol = col + 1;
          break;
        }
    }

    if(nextRow >= board.length || nextRow < 0
      || nextCol >= board[0].length || nextCol < 0){
      return false;
    }


    int nextTile = board[nextRow][nextCol];
    if(nextTile == currentPlayer){
      if(capture){
        board[row][col] = currentPlayer;
      }
      return true;
    }

    if(nextTile == opponent){
      if(capture){
        if(isSandwiched(nextRow, nextCol, direction, opponent,capture)){

          board[row][col] = currentPlayer;
          return true;
        }else{
          return false;
        }
      }else{
        return isSandwiched(nextRow, nextCol, direction, opponent,capture);
      }

    }

    return false;
  }

  public boolean isMoveValid(int row, int col, boolean capture){
    if(board[row][col] == Game.BLANKTILE){
      int opponent = currentPlayer == Game.GREEN ? Game.RED : Game.GREEN;

      //array of ints for the coords of the neighbors of the move
      //6 moves, each has 3 attrs (row, col) and direction
      int[][] neighbors = new int[6][3];
      //up
      neighbors[0][0] = row + 1;
      neighbors[0][1] = col;
      neighbors[0][2] = Game.DIRECTION_UP;
      //down
      neighbors[1][0] = row - 1;
      neighbors[1][1] = col;
      neighbors[1][2] = Game.DIRECTION_DOWN;

      if(col % 2 == 0){
        //col is even
        //up left
        neighbors[2][0] = row;
        neighbors[2][1] = col - 1;
        neighbors[2][2] = Game.DIRECTION_UPLEFT;

        //up right
        neighbors[3][0] = row;
        neighbors[3][1] = col + 1;
        neighbors[3][2] = Game.DIRECTION_UPRIGHT;

        //down left
        neighbors[4][0] = row - 1;
        neighbors[4][1] = col - 1;
        neighbors[4][2] = Game.DIRECTION_DOWNLEFT;

        neighbors[5][0] = row - 1;
        neighbors[5][1] = col + 1;
        neighbors[5][2] = Game.DIRECTION_DOWNRIGHT;

      }else{
        //col is odd
        //up left
        neighbors[2][0] = row + 1;
        neighbors[2][1] = col - 1;
        neighbors[2][2] = Game.DIRECTION_UPLEFT;

        //up right
        neighbors[3][0] = row + 1;
        neighbors[3][1] = col + 1;
        neighbors[3][2] = Game.DIRECTION_UPRIGHT;

        //down left
        neighbors[4][0] = row;
        neighbors[4][1] = col - 1;
        neighbors[4][2] = Game.DIRECTION_DOWNLEFT;

        neighbors[5][0] = row;
        neighbors[5][1] = col + 1;
        neighbors[5][2] = Game.DIRECTION_DOWNRIGHT;

      }

      //for all neighbors
      for(int i = 0; i < neighbors.length; i++){
        try{
          int currentNeighbor = board[ neighbors[i][0] ][ neighbors[i][1] ];

          if(currentNeighbor == opponent){
            if(isSandwiched(neighbors[i][0], neighbors[i][1], neighbors[i][2], opponent, capture)){
              return true;
            }
          }
        } catch (ArrayIndexOutOfBoundsException e){
          continue;
        }
      }
      return false;
    }
    return false;
  }

  public Game clone(){
    Game copy = new Game(2,2,Game.GREEN,Game.RED);
    copy.board = this.board;
    copy.currentPlayer = this.currentPlayer;
    return copy;
  }

  public int countRedTiles(){
    int num = 0;
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j < this.board[i].length; j++){
        if(this.board[i][j] == Game.RED)
          num++;
      }
    }
    return num;
  }

  public int countGreenTiles(){
    int num = 0;
    for(int i = 0; i < this.board.length; i++){
      for(int j = 0; j < this.board[i].length; j++){
        if(this.board[i][j] == Game.GREEN)
          num++;
      }
    }
    return num;
  }

  public boolean isHexxed(){
    if(this.getNextValidMoves().size() == 0){
      return true;
    } else {
      return false;
    }
  }

  @Override
  public String toString(){
    String res = "";
    for(int i = board.length - 1; i >= 0 ; i--){
      for(int j = 0; j < board[i].length; j++){
        res += board[i][j] + " ";
      }
      res += "\n";
    }

    return res;
  }

  public static void main(String[] args) {
    Game g = new Game(1,4,Game.RED,Game.RED);
    System.out.println(g);

    for(int i = 0 ; i < 3; i++){
      LinkedList<Move> moves = g.getNextValidMoves();

      for(Move m : moves){
        System.out.println(m);
      }

      try{
        g.move(moves.get(0));
        System.out.println(g);
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }

}
