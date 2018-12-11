public class State{

  public static final MAXIMIZING_PLAYER = 1;
  public static final MINIMIZING_PLAYER = -MAXIMIZING_PLAYER;
  public static final MAX_DEPTH = 4;
  private Game g;
  private int currentPlayer;
  private int minimaxVal;
  private int depth;
  private LinkedList<State> children;

  public State(Game g, int currentPlayer, int color, int depth){
    this.g = g;
    this.currentPlayer = currentPlayer;
    this.minimaxVal = 0;
    this.depth = depth;
  }

  public void expand(){
    if(g.isGameOver()){
      return ;
    }

    if(this.depth >= State.MAX_DEPTH){
      return ;
    }

    LinkedList<Move> moves = g.getNextValidMoves();

    if(g.isHexxed){
      //pass
      Game newGame = this.g.clone();
      newGame.move(null);
      State newState = new State(newGame,-this.currentPlayer,this.depth++);
      newState.expand();
      this.children.add(newState);
    }

    for(Move m : moves){
      Game newGame = this.g.clone();
      newGame.move(m);

      State newState = new State(newGame,-this.currentPlayer,this.depth++);
      newState.expand();
      this.children.add(newState);
    }
  }

  public int getMinimaxVal(){
    // if(min) get min( minimax of next states )
    // if(max) get max( minimax of next states )
    // if green max RED
    // if red max GREEN

  }

  public Move getBestMove(){

  }



}
