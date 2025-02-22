
public class Game {

   /**
 * Le plateau du jeu, stock� sous forme de tableau 
 */
 private BoxSymbol[] board;


   /**
 * round enregistre le nombre de tours qui ont �t�
 * jou� jusqu'� pr�sent. Commence � 0.
 */
 private int round;

   /**
 * gameState enregistre l'�tat actuel du jeu.
 */
 private GameState gameState;


   /**
 * rows est le nombre de lignes dans la grille
 */
 private final int rows;

   /**
 * columns est le nombre de colonnes dans la grille
 */
 private final int columns;


   /**
 * numberWin est le nombre de cellules du m�me type
 * qu'il faut aligner pour gagner la partie
 */
 private final int numberWin;


   /**
 * constructeur par d�faut, pour un jeu de 3x3, qui doit
 * aligner 3 cellules
 */
 public Game(){
  this(3,3,3);
 }

  
   /**
  * constructeur permettant de pr�ciser le nombre de lignes
  * et le nombre de colonnes pour le jeu, ainsi que
  * le nombre de cellules qu'il faut aligner pour gagner.
    * @param rows
    *  the number of rows in the game
    * @param columns
    *  the number of columns in the game
    * @param numberWin
    *  the number of cells that must be aligned to win.
    */
 public Game(int rows, int columns, int numberWin){
  //VOTRE CODE ICI
  this.rows = rows;
  this.columns = columns;
  this.numberWin = numberWin;
  this.round = 0;
  this.board = new BoxSymbol[rows * columns];
    for (int i = 0; i < board.length; i++) {
        board[i] = BoxSymbol.EMPTY;  
    }
    this.gameState = GameState.PLAYING; 
 }



   /**
 * getter pour la variable rows
   * @return
   *  the value of rows
   */
 public int getRows(){
  return rows;
 }

   /**
 * getter pour la variable columns
   * @return
   *  the value of columns
   */
 public int getColumns(){
  return columns;
 }

   /**
 * getter pour la variable round
   * @return
   *  the value of roud
   */
 public int getRound(){
  return round;
 }


   /**
 * getter pour la variable gameState
   * @return
   *  the value of gameState
   */
 public GameState getGameState(){
  return gameState;
 }

   /**
 * getter pour la variable numberWin
   * @return
   *  the value of numberWin
   */
 public int getNumberWin(){
  return numberWin;
 }

  /**
 *renvoie le prochain BoxSymbol prevu,
 * Cette m�thode ne modifie pas l'�tat du jeu.
   * @return 
   *  the value of the enum BoxSymbol corresponding
   * to the next expected symbol.
   */
 public BoxSymbol nextBoxSymbol(){
   if (round%2 == 0) return BoxSymbol.X;
   return BoxSymbol.O;
 }

 
   /**
 * renvoie la valeur de la case a l'index i.
 * Si l'index n'est pas valide, un message d'erreur est
 * imprim�. Le comportement est alors ind�termin�
    * @param i
    *  the index of the Box in the array board
    * @return 
    *  the value at index i in the variable board.
    */
 public BoxSymbol boxSymbolAt(int i) {
  if (i>0 && i<(columns*rows)){
    return board[i];
  } else {
    System.out.println("Index est out of range!");
    return BoxSymbol.EMPTY;
  }
 }

  /**
  * Cette m�thode est appel�e par le prochain joueur � jouer
  * � la case � l'index i.
  * Si l'index n'est pas valide, un message d'erreur est
  * imprim�. Le comportement est alors ind�termin�
  * Si la case choisie n'est pas vide, un message d'erreur s'affiche.
  * Le comportement est alors ind�termin�
  * Si le coup est valide, le plateau (board) est �galement mis � jour
  * ainsi que l'�tat du jeu. Doit appeler la m�thode update.
    * @param i
    *  the index of the box in the array board that has been 
    * selected by the next player
    */


  public void play(int i) {
    if ((i-1) < 0 || (i-1) >= rows * columns) {
        System.out.println("Illegal position: " + i);
        return;
      } 
    if (board[i-1] != BoxSymbol.EMPTY) { 
        System.out.println("Position already taken! Choose another.");
        return;
    } 
    board[(i-1)] = nextBoxSymbol();
    update((i-1));
    round++;
    }


   /**
 * Une m�thode d'assistance qui met � jour la variable gameState
 * correctement apr�s que la case � l'index i vient d'etre d�fini.
 * La m�thode suppose qu'avant de param�trer la case
 * � l'index i, la variable gameState a �t� correctement d�finie.
 * cela suppose aussi qu'elle n'est appel�e que si le jeu n'a pas encore �t�
 * �t� termin� lorsque la case � l'index i a �t� jou�e
 * (le jeu en cours). Il suffit donc de
 * V�rifiez si jouer � l'index i a termin� la partie.
    * @param i
    *  the index of the box in the array board that has just 
    * been set
   */

private void update(int index){
    int row = index / columns; 
    int col = index % columns; 
    if (checkWin(row, col)) {
        if (board[index] == BoxSymbol.X) {
            gameState = GameState.XWIN;
        } else {
            gameState = GameState.OWIN;
        }
    } else {
        boolean draw = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == BoxSymbol.EMPTY) {
                draw = false;
                break;
            }
        }
        if (draw) {
            gameState = GameState.DRAW;
        }
    }
}

private boolean checkWin(int row, int col) {
  BoxSymbol symbol = board[row * columns + col];

  // Check horizontal (row-wise)
  int count = 0;
  for (int i = 0; i < columns; i++) {
      if (board[row * columns + i] == symbol) {
          count++;
          if (count >= numberWin) return true;
      } else {
          count = 0;
      }
  }

  // Check vertical (column-wise)
  count = 0;
  for (int i = 0; i < rows; i++) {
      if (board[i * columns + col] == symbol) {
          count++;
          if (count >= numberWin) return true;
      } else {
          count = 0;
      }
  }

  // Check diagonal (top-left to bottom-right)
  count = 0;
  for (int i = -Math.min(row, col); row + i < rows && col + i < columns && row + i >= 0 && col + i >= 0; i++) {
      if (board[(row + i) * columns + (col + i)] == symbol) {
          count++;
          if (count >= numberWin) return true;
      } else {
          count = 0;
      }
  }

  // Check diagonal (top-right to bottom-left)
  count = 0;
  for (int i = -Math.min(row, columns - 1 - col); row + i < rows && col - i >= 0 && row + i >= 0 && col - i < columns; i++) {
      if (board[(row + i) * columns + (col - i)] == symbol) {
          count++;
          if (count >= numberWin) return true;
      } else {
          count = 0;
      }
  }

  return false;
}



 

   /**
 * Renvoie une repr�sentation sous forme de cha�ne du jeu correspondant
 * � l'exemple fourni dans la description du devoir
    * @return
    *  String representation of the game
   */

   public String toString() {
    String res = "";
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        int index = i * columns + j;
        BoxSymbol symbol = board[index];
  
        // Add box number if empty, otherwise add X or O
        if (symbol == BoxSymbol.EMPTY) {
          res += "  ";
        } else {
          res += (symbol == BoxSymbol.X ? "X " : "O ");
        }
      }
      res += "\n"; // Newline after each row
    }
    return res;
  }
  
   

}