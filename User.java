public class User extends Player {
 

 public  void play(Game game) {
   if(game.getRound() == game.getRows()*game.getColumns()){
     System.out.println("Game is finished already!");
   } else{
     String input = GameMain.console.readLine("Index: ");
     int index = Integer.parseInt(input);
     game.play(index);
     System.out.println(game.toString());
    }
  }
}