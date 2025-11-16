package modelo;

import java.util.Random;

/**
 * Clase Sudoku Implementa la lógica principal del juego Sudoku: - Generación de
 * tableros con distintos niveles de dificultad. - Validación de reglas (filas,
 * columnas y subcuadrantes). - Resolución automática mediante algoritmo de
 * backtracking.
 */
public class Sudoku {

    // Matriz que representa el tablero de Sudoku (9x9)
    private int sudoku[][];

    /**
     * Constructor: inicializa el tablero vacío.
     */
    public Sudoku() {
        sudoku = new int[9][9];
        limpiarSudoku();
    }

    /**
     * Resuelve el Sudoku usando backtracking. Busca casillas vacías y prueba
     * valores del 1 al 9. regresa true si el tablero se resuelve correctamente,
     * false si no hay solución.
     */
    public boolean resolverSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                if (sudoku[i][j] == 0) { // Casilla vacía
                    for (int valor = 1; valor <= 9; valor++) {
                        if (validarFila(i, valor) && validarColumna(j, valor) && validarCuadrante(i, j, valor)) {
                            sudoku[i][j] = valor;
                            if (resolverSudoku()) {
                                return true;
                            }
                            sudoku[i][j] = 0; // Retrocede si no funciona
                        }
                    }
                    return false; // No hay número válido en esta casilla
                }
            }
        }
        return true; // Tablero completo
    }

    /**
     * Comprueba si el tablero actual cumple las reglas del Sudoku. regresa true
     * si es válido, false si hay errores.
     */
    public boolean comprobarSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = sudoku[i][j];
                sudoku[i][j] = 0; // Se quita temporalmente el valor
                if (!validarFila(i, aux) || !validarColumna(j, aux) || !validarCuadrante(i, j, aux)) {
                    sudoku[i][j] = aux;
                    return false;
                }
                sudoku[i][j] = aux;
            }
        }
        return true;
    }

    /**
     * Valida si un número puede colocarse en el subcuadrante correspondiente.
     * (i) fila (j) columna (valor) número a validar true si no hay repetición,
     * false si ya existe.
     */
    public boolean validarCuadrante(int i, int j, int valor) {
        int posI = subCuadranteActual(i);
        int posJ = subCuadranteActual(j);
        for (int k = posI - 3; k < posI; k++) {
            for (int l = posJ - 3; l < posJ; l++) {
                if (sudoku[k][l] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Limpia el tablero, colocando ceros en todas las posiciones.
     */
    public void limpiarSudoku() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                sudoku[i][j] = 0;
            }
        }
    }

    /**
     * Determina el límite superior del subcuadrante según la posición. (pos)
     * índice de fila o columna regresa al límite superior del subcuadrante (3,
     * 6 o 9).
     */
    public int subCuadranteActual(int pos) {
        if (pos <= 2) {
            return 3;
        } else if (pos <= 5) {
            return 6;
        } else {
            return 9;
        }
    }

    /**
     * Valida si un número puede colocarse en una fila. (i) fila (valor) número
     * a validar true si no hay repetición, false si ya existe.
     */
    public boolean validarFila(int i, int valor) {
        for (int j = 0; j < sudoku[i].length; j++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    /**
     * Valida si un número puede colocarse en una columna. (j) columna (valor)
     * número a validar true si no hay repetición, false si ya existe.
     */
    public boolean validarColumna(int j, int valor) {
        for (int i = 0; i < sudoku.length; i++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    /**
     * Muestra el tablero en consola, resolviéndolo previamente.
     */
    public void mostrarSudoku() {
        resolverSudoku();
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                System.out.print(sudoku[i][j]);
                if (!(j == sudoku[0].length - 1)) {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Genera un tablero de Sudoku con un nivel de dificultad dado. (nivel)
     * nivel de dificultad (dependiendo del nivel seleccionado afecta las
     * casillas vacias).
     */
    public void generarSudoku(int nivel) {
        limpiarSudoku();
        Random random = new Random();

        // Primer cuadrante
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        // Cuadrante central
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        // Cuadrante final
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        // Resolver tablero completo
        resolverSudoku();

        // Vaciar casillas según nivel de dificultad
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = j; // posición de columna actual
                int ran = random.nextInt(nivel + 1);
                j += ran;
                for (int k = aux; k < j && k < sudoku.length; k++) {
                    sudoku[i][k] = 0;
                }
            }
        }
    }

    // Getters y setters
    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
}
