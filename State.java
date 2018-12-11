import java.util.LinkedList;

public class State{

  public static final int MAXIMIZING_PLAYER = 1;
  public static final int MINIMIZING_PLAYER = -MAXIMIZING_PLAYER;
  public static final int MAX_DEPTH = 1;
  private Game g;
  private int currentPlayer;
  private int minimaxVal;
  private int maxVal;
  private int color;
  private int depth;
  private LinkedList<State> children;

  public State(Game g, int currentPlayer, int color, int depth){
    this.g = g;
    this.currentPlayer = currentPlayer;
    this.minimaxVal = 0;
    this.depth = depth;
  }

  public int getMinimaxVal() throws Exception{
    // if(min) get min( minimax of next states )
    // if(max) get max( minimax of next states )
    // if green max RED
    // if red max GREEN

    LinkedList<Move> moves = g.getNextValidMoves();

    for(Move m : moves){
      System.out.println(m);
    }

    // utility function ; this should be right
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

      for(Move m : moves){
        System.out.println("Maximize---- " + depth + m);
        Game newGame  = this.g.clone();
        newGame.move(m);
        State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
        int currVal = newState.getMinimaxVal();
        System.out.println("can you go here max");
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

      for(Move m : moves){
        System.out.println("Minimize---- " + depth + m);
        Game newGame  = this.g.clone();
        newGame.move(m);
        State newState = new State(newGame,-this.currentPlayer,this.color,this.depth++);
        int currVal = newState.getMinimaxVal();
        System.out.println("can you go here min");
        if(currVal < maxVal){
          minVal = currVal;
        }
      }

      return minVal;
    }
    return 0;
  }

  public Move getBestMove() throws Exception{

    LinkedList<Move> moves = this.g.getNextValidMoves();

    if(this.g.isGameOver()){
      throw new Exception("Game Over");
    }

    for(Move m : moves){
      System.out.println(m);
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
