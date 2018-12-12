import java.util.LinkedList;
import java.util.Scanner;

public class HexxedAI{

  /**
    @author Jhaylord Viray, Neil Bucsit, David Brackin (in order of contribution)
  */

  public static void main(String[] args) throws Exception{

    Scanner sc = new Scanner(System.in);
    System.out.print("What is my color? [g/r] : ");
    int color = sc.next().charAt(0) == 'g' ? Game.GREEN : Game.RED;
    System.out.print("Enter the starting config (col row color[g/r] first[g/r]) : ");
    int g_col = sc.nextInt();
    int g_row = sc.nextInt();
    int g_color = sc.next().charAt(0) == 'g' ? Game.GREEN : Game.RED;
    int g_first = sc.next().charAt(0) == 'g' ? Game.GREEN : Game.RED;

    Game g = new Game(g_col,g_row,g_color,g_first);
    System.out.println(g);


    State currState = new State(g.clone(),State.MAXIMIZING_PLAYER,color,0);

    while(!g.isGameOver()){

      LinkedList<Move> moves = g.getNextValidMoves();

      if(g.getCurrentPlayer() == color){
        System.out.println("\nAI is thinking...hmmm...");
        Move aiMove = currState.getBestMove();
        System.out.println("AI's move : " + (aiMove != null ? aiMove : "Pass") );
        g.move(aiMove);
      }else{

        if(g.isHexxed()){
          g.move(null);
          System.out.println("Enter opponent's move (col row) : Hexxed! I moved it for you. (Press Enter to Continue ...)");
          String nothing = sc.nextLine();
          currState = new State(g.clone(),State.MAXIMIZING_PLAYER,color,0);
          continue;
        }

        try {
          System.out.print("Enter opponent's move (col row) : ");

          int o_col = sc.nextInt();
          int o_row = sc.nextInt();
          Move o_move = new Move(o_row,o_col,g.getCurrentPlayer());
          g.move(o_move);
        }catch(Exception e){
          System.out.println("Invalid Move! Nice try guy.");
          continue;
        }
      }

      currState = new State(g.clone(),State.MAXIMIZING_PLAYER,color,0);

    }

    System.out.println("Game Over");
  }

}
