import java.util.LinkedList;
import java.util.Scanner;

public class HexxedAI{

  public static void main(String[] args) {
    Game g = new Game(4,4,Game.GREEN,Game.GREEN);
    System.out.println(g);

    Scanner sc = new Scanner(System.in);

    while(!g.isGameOver()){

      sc.nextLine();

      LinkedList<Move> moves = g.getNextValidMoves();

      for(Move m : moves){
        System.out.println(m);
      }

      try{
        if(g.isHexxed()){
          //pass
          g.move(null);
          System.out.println("passed");
          System.out.println(g);
          continue;

        }
        State initState = new State(g,State.MAXIMIZING_PLAYER,Game.GREEN,0);
        int n = initState.getMinimaxVal();
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }

}
