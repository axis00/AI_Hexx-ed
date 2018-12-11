import java.util.LinkedList;
import java.util.Scanner;

public class HexxedAI{

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

      if(g.getCurrentPlayer() == color){
        Move aiMove = currState.getBestMove();
        System.out.println(aiMove);
        g.move(aiMove);
      }else{

        System.out.print("Enter opponent's move (col row) : ");
        int o_col = sc.nextInt();
        int o_row = sc.nextInt();

        Move o_move = new Move(o_row,o_col,g.getCurrentPlayer());
        g.move(o_move);

      }

      currState = new State(g.clone(),State.MAXIMIZING_PLAYER,color,0);

    }

    System.out.println("Game Over");
  }

}
