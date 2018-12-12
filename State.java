import java.util.LinkedList;

//A class to represent each state in the game space

public class State{

  public static final int MAXIMIZING_PLAYER = 1;
  public static final int MINIMIZING_PLAYER = -MAXIMIZING_PLAYER;
  public static final int MAX_DEPTH = 5;
  private Game g;
  private int currentPlayer;
  private int minimaxVal;
  private int color;
  private int depth;

  public State(Game g, int currentPlayer, int color, int depth){
    this.g = g;
    this.currentPlayer = currentPlayer;
    this.minimaxVal = 0;
    this.depth = depth;
  }

  /**
    @author David Brackin
    Recursively calculate the minimax value of this state until MAX_DEPTH.
  */
  public int getMinimaxVal() throws Exception{
    // if(min) get min( minimax of next states )
    // if(max) get max( minimax of next states )
    // if green max RED
    // if red max GREEN

    LinkedList<Move> moves = g.getNextValidMoves();

    // utility function
    if(g.isGameOver() || this.depth >= State.MAX_DEPTH){
      if(this.color == Game.GREEN){
        return g.countRedTiles();
      }else{
        return g.countGreenTiles();
      }
    }

      // return highest value of MAXIMIZING_PLAYER
    if(this.currentPlayer == State.MAXIMIZING_PLAYER){
      // TODO
      // do all the moves then get max
      // don't forget to alternate

      int maxVal = 0;

      if(g.isHexxed()){
        Game newGame  = this.g.clone();
        newGame.move(null);
        State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
        int currVal = newState.getMinimaxVal();
        if(currVal > maxVal){
          maxVal = currVal;
        }
      }

      for(Move m : moves){

        Game newGame  = this.g.clone();
        newGame.move(m);
        State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
        int currVal = newState.getMinimaxVal();
        if(currVal > maxVal){
          maxVal = currVal;
        }
      }

      return maxVal;
    }

    // return lowest value of MINIMIZING_PLAYER
    if(this.currentPlayer == State.MINIMIZING_PLAYER){
      // TODO
      // do all the moves then get min
      // don't forget to alternate
      int minVal = 99999999;

      if(g.isHexxed()){
        Game newGame  = this.g.clone();
        newGame.move(null);
        State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
        int currVal = newState.getMinimaxVal();
        if(currVal < minVal){
          minVal = currVal;
        }
      }

      for(Move m : moves){

        Game newGame  = this.g.clone();
        newGame.move(m);
        State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
        int currVal = newState.getMinimaxVal();
        if(currVal < minVal){
          minVal = currVal;
        }
      }

      return minVal;
    }
    return 0;
  }

  /**
    @author Neil Bucsit
    This is a method for getting the best move for this state.
    It works by getting the maximum of all the minimax values of this state.
  */
  public Move getBestMove() throws Exception{

    LinkedList<Move> moves = this.g.getNextValidMoves();

    if(this.g.isGameOver()){
      throw new Exception("Game Over");
    }

    int maxVal = 0;
    Move bestMove = null;

    for(Move m : moves){
      Game newGame  = this.g.clone();
      newGame.move(m);
      State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
      int currVal = newState.getMinimaxVal();
      if(currVal > maxVal){
        bestMove = m;
        maxVal = currVal;
      }
    }

    return bestMove;

  }



}
