package principal;

import modelo.Sudoku;
import vista.FormSudoku;

/**
 * Clase Principal
 * Punto de entrada del programa Sudoku.
 * Se encarga de inicializar la interfaz gráfica principal (FormSudoku).
 */
public class Principal {

    /**
     * Método main
     * Inicia la aplicación ejecutando la ventana principal del juego Sudoku.
     */
    public static void main (String [] args){
        FormSudoku sudoku = new FormSudoku();
        sudoku.setVisible(true); // Muestra la ventana principal
    }
}
