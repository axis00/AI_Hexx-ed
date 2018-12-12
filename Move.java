/**
@author Jhaylord Viray
This is a class for encapsulating the moves.
*/

public class Move{

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
    return "Column : " + this.col + " Row : " + this.row;
  }

}
