package vista;

import java.util.Stack;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Sudoku;

/**
 * Clase TableroSudoku Representa el tablero principal del juego Sudoku.
 * Contiene la lógica de la interfaz gráfica, manejo de eventos y conexión con
 * la clase Sudoku (modelo).
 */
public class TableroSudoku extends JPanel {

    // Matriz de campos de texto que representan las casillas del tablero
    private JTextField[][] listaTxt;
    // Atributos de configuración gráfica
    private int txtAncho;
    private int txtAltura;
    private int txtMargen;
    private int txtTamañoLetra;
    private Color panelBackground;
    private Color txtBackground1;
    private Color txtForeground1;
    private Color txtBackground2;
    private Color txtForeground2;
    private Color txtBackground3;
    private Color txtForeground3;
    private Color txtBackground4;
    private Color txtForeground4;
    // Lógica del Sudoku (modelo)
    private Sudoku sudoku;
    // Listas auxiliares para manejar casillas resaltadas y casillas generadas
    private ArrayList<JTextField> listaTxtAux;
    private ArrayList<JTextField> listaTxtGenerados;
    // Casilla actualmente seleccionada por el usuario
    public JTextField txtSelected;
    // Cola pistas
    private Queue<JTextField> colaPistas = new LinkedList<>();

    /**
     * Constructor de la clase TableroSudoku. Inicializa los componentes
     * gráficos y la lógica del tablero.
     */
    public TableroSudoku() {
        iniciarComponentes();
    }

    /**
     * Método iniciarComponentes Configura los valores iniciales del tablero:
     * (listaTxt) matriz de 9x9 casillas de texto (txtAncho, txtAltura,
     * txtMargen, txtTamañoLetra) dimensiones y estilo de las casillas
     * (panelBackground, txtBackground1..4, txtForeground1..4) colores de fondo
     * y texto (sudoku) instancia del modelo Sudoku para la lógica del juego
     * (listaTxtAux) lista auxiliar para casillas resaltadas (listaTxtGenerados)
     * lista de casillas generadas automáticamente (txtSelected) casilla
     * actualmente seleccionada por el usuario
     */
    public void iniciarComponentes() {
        listaTxt = new JTextField[9][9];
        txtAncho = 35;
        txtAltura = 36;
        txtMargen = 4;
        txtTamañoLetra = 27;
        panelBackground = Color.BLACK;
        txtBackground1 = Color.WHITE;
        txtForeground1 = Color.BLACK;
        txtBackground2 = Color.WHITE;
        txtForeground2 = Color.BLACK;
        txtBackground3 = Color.WHITE;
        txtForeground3 = Color.BLACK;
        txtBackground4 = new Color(238, 227, 255);
        txtForeground4 = Color.WHITE;
        sudoku = new Sudoku();
        listaTxtAux = new ArrayList<>();
        listaTxtGenerados = new ArrayList<>();
        txtSelected = new JTextField();
    }

    /**
     * Método crearSudoku Configura el tablero principal de Sudoku: - Define el
     * layout y tamaño del panel. - Establece el color de fondo. - Llama a
     * crearCamposTxt() para generar las casillas.
     */
    public void crearSudoku() {
        this.setLayout(null);
        this.setSize(txtAncho * 9 + (txtMargen * 4), txtAltura * 9 + (txtMargen * 4));
        this.setBackground(panelBackground);
        crearCamposTxt();
    }

    /**
     * Método crearCamposTxt Genera las casillas del tablero (9x9) como
     * JTextField. Cada casilla se configura con tamaño, color, fuente y
     * eventos.
     */
    public void crearCamposTxt() {
        int x = txtMargen;
        int y = txtMargen;
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                JTextField txt = new JTextField();
                this.add(txt);
                txt.setBounds(x, y, txtAncho, txtAltura);
                txt.setBackground(txtBackground1);
                txt.setFont(new Font("Montserrat", Font.BOLD, txtTamañoLetra));
                txt.setEditable(false);
                txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
                txt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                txt.setVisible(true);
                x += txtAncho;
                if ((j + 1) % 3 == 0) {
                    x += txtMargen;
                }
                listaTxt[i][j] = txt;
                generarEventos(txt);
            }
            x = txtMargen;
            y += txtAltura;
            if ((i + 1) % 3 == 0) {
                y += txtMargen;
            }
        }
    }

    /**
     * Método txtGenerado Verifica si una casilla pertenece a las casillas
     * generadas automáticamente. (txt) casilla a verificar. regresa true si es
     * generada, false si no.
     */
    public boolean txtGenerado(JTextField txt) {
        for (JTextField jTxt : listaTxtGenerados) {
            if (txt == jTxt) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método generarEventos Asigna eventos de mouse y teclado a cada casilla
     * del tablero. (txt) casilla a la que se asignan los eventos.
     */
    public void generarEventos(JTextField txt) {
        MouseListener evento = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                pressed(txt);
                txtSelected = txt;
                //resalta casillas con el mismo numero con el cursor
                resaltarCoincidencias(txt);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };

        KeyListener eventoTecla = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                if (txtGenerado(txt)) {
                    ke.consume();
                    return;
                }
                char c = ke.getKeyChar();
                if (c == KeyEvent.VK_BACK_SPACE) {
                    txt.setText("");
                    ke.consume();
                } else if (c >= '1' && c <= '9') {
                    txt.setText(String.valueOf(c));
                    ke.consume();
                } else {
                    ke.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        };
        txt.addMouseListener(evento);
        txt.addKeyListener(eventoTecla);
    }

    /**
     * Método pressed Resalta la casilla seleccionada y sus filas, columnas y
     * subcuadrante. (txt) casilla seleccionada por el usuario.
     */
    public void pressed(JTextField txt) {
        for (JTextField jTxt : listaTxtAux) {
            jTxt.setBackground(txtBackground1);
            jTxt.setForeground(txtForeground1);
            jTxt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
        }
        listaTxtAux.clear();

        for (JTextField jTxt : listaTxtGenerados) {
            jTxt.setBackground(txtBackground4);
            jTxt.setForeground(txtForeground4);
        }
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                if (listaTxt[i][j] == txt) {
                    for (int k = 0; k < listaTxt.length; k++) {
                        listaTxt[k][j].setBackground(txtBackground2);
                        listaTxtAux.add(listaTxt[k][j]);
                    }
                    for (int k = 0; k < listaTxt[0].length; k++) {
                        listaTxt[i][k].setBackground(txtBackground2);
                        listaTxtAux.add(listaTxt[i][k]);
                    }
                    int posI = sudoku.subCuadranteActual(i);
                    int posJ = sudoku.subCuadranteActual(j);
                    for (int k = posI - 3; k < posI; k++) {
                        for (int l = posJ - 3; l < posJ; l++) {
                            listaTxt[k][l].setBackground(txtBackground2);
                            listaTxtAux.add(listaTxt[k][l]);
                        }
                    }
                    listaTxt[i][j].setBackground(txtBackground3);
                    listaTxt[i][j].setForeground(txtForeground3);
                    listaTxt[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                    return;
                }
            }
        }
    }

    /**
     * Método generarSudoku Genera un tablero de Sudoku con un nivel de
     * dificultad dado. (nivel) nivel de dificultad (1 fácil, 2 medio, 3
     * difícil).
     */
    public void generarSudoku(int nivel) {
        limpiarTxt();
        listaTxtGenerados.clear();
        sudoku.generarSudoku(nivel);
        int[][] sudokuGenerado = sudoku.getSudoku();

        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                if (sudokuGenerado[i][j] != 0) {
                    listaTxt[i][j].setText(String.valueOf(sudokuGenerado[i][j]));
                    listaTxt[i][j].setBackground(txtBackground4);
                    listaTxt[i][j].setForeground(txtForeground4);
                    listaTxt[i][j].setEditable(false);
                    listaTxtGenerados.add(listaTxt[i][j]);
                } else {
                    listaTxt[i][j].setText("");
                    listaTxt[i][j].setBackground(txtBackground1);
                    listaTxt[i][j].setForeground(txtForeground1);
                    listaTxt[i][j].setEditable(true);
                }
            }
        }

        //Reiniciar y llenar la cola de pistas con las casillas vacías
        colaPistas.clear();
        inicializarColaPistas();
    }

    /**
     * Método limpiarTxt Limpia todas las casillas del tablero, dejándolas
     * vacías y con colores iniciales.
     */
    public void limpiarTxt() {
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                listaTxt[i][j].setText("");
                listaTxt[i][j].setBackground(txtBackground1);
                listaTxt[i][j].setForeground(txtForeground1);
            }
        }
    }

    /**
     * Método limpiar Limpia únicamente las casillas editables (no las generadas
     * automáticamente).
     */
    public void limpiar() {
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                boolean b = false;
                for (JTextField txt : listaTxtGenerados) {
                    if (listaTxt[i][j] == txt) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    listaTxt[i][j].setText("");
                }
            }
        }
    }

    /**
     * Método comprobar Verifica si el Sudoku está completo y cumple las reglas.
     * Muestra un mensaje de éxito o error según el resultado.
     */
    public void comprobar() {
        int comp[][] = new int[9][9];
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                if (listaTxt[i][j].getText().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Sudoku Incompleto",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                } else {
                    comp[i][j] = Integer.parseInt(listaTxt[i][j].getText());
                }
            }
        }
        sudoku.setSudoku(comp);
        if (sudoku.comprobarSudoku()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sudoku Completado",
                    "Correcto",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No hay Solución",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Método resolver Resuelve automáticamente el Sudoku. Copia los valores
     * fijos al modelo. Usa backtracking para completar el tablero. Muestra
     * solución o un mensaje de error si no existe.
     */
    public void resolver() {
        sudoku.limpiarSudoku();
        for (int i = 0; i < listaTxt.length; i++) {
            for (int j = 0; j < listaTxt[0].length; j++) {
                for (JTextField txt : listaTxtGenerados) {
                    if (txt == listaTxt[i][j]) {
                        sudoku.getSudoku()[i][j] = Integer.parseInt(txt.getText());
                    }
                }
            }
        }
        if (sudoku.resolverSudoku()) {
            for (int i = 0; i < listaTxt.length; i++) {
                for (int j = 0; j < listaTxt[0].length; j++) {
                    listaTxt[i][j].setText(String.valueOf(sudoku.getSudoku()[i][j]));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay solucion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //deshacer con pila
    private Stack<JTextField> pilaMovimientos = new Stack<>();

    public void registrarMovimiento(JTextField casilla) {
        pilaMovimientos.push(casilla);
    }

    public void deshacerMovimiento() {
        if (!pilaMovimientos.isEmpty()) {
            JTextField ultimo = pilaMovimientos.pop();
            ultimo.setText(""); // borra el último número colocado
        }
    }

    //Resaltar Numeros Con Panel
    public void resaltarNumero(String numero) {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField casilla = listaTxt[fila][col];
                if (casilla.getText().equals(numero)) {
                    // Cambia el color de fondo para resaltar
                    casilla.setBackground(Color.YELLOW);
                } else {
                    // Restaura el color normal si no coincide
                    casilla.setBackground(Color.WHITE);
                }
            }
        }
    }

    //Resaltar Numeros tocando con mouse
    public void resaltarCoincidencias(JTextField casilla) {
        String numero = casilla.getText();

        if (numero == null || numero.isEmpty()) {
            return;
        }

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField actual = listaTxt[fila][col];
                if (actual.getText().equals(numero)) {
                    actual.setBackground(Color.YELLOW);
                } else {
                    actual.setBackground(Color.WHITE);
                }
            }
        }
    }

    //cola pistas
    public void inicializarColaPistas() {
        List<JTextField> lista = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (listaTxt[i][j].getText().isEmpty()) {
                    lista.add(listaTxt[i][j]);
                }
            }
        }
        Collections.shuffle(lista); // mezcla aleatoriamente
        colaPistas = new LinkedList<>(lista); // vuelve a ser una cola
    }

    public void mostrarPista() {
        while (!colaPistas.isEmpty()) {
            JTextField casilla = colaPistas.poll();

            // Si la casilla ya tiene número, la ignoramos y seguimos con la siguiente
            if (!casilla.getText().isEmpty()) {
                continue;
            }

            // Guardar estado actual
            String[][] estadoOriginal = new String[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    estadoOriginal[i][j] = listaTxt[i][j].getText();
                }
            }

            // Resolver el tablero
            resolver();

            // Buscar posición de la casilla
            int fila = -1, col = -1;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (listaTxt[i][j] == casilla) {
                        fila = i;
                        col = j;
                    }
                }
            }

            // Obtener valor correcto
            String valorCorrecto = listaTxt[fila][col].getText();

            // Restaurar estado original
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    listaTxt[i][j].setText(estadoOriginal[i][j]);
                }
            }

            // Mostrar la pista
            casilla.setText(valorCorrecto);
            casilla.setForeground(Color.BLACK);     
            casilla.setBackground(txtBackground1);  
            casilla.setEditable(false);             
            listaTxtGenerados.add(casilla);
            return; 
        }

        JOptionPane.showMessageDialog(null, "No hay más pistas disponibles.");
    }

    // Métodos getter y setter para personalizar atributos gráficos y acceder a las casillas
    public Color getTxtBackground4() {
        return txtBackground4;
    }

    public void setTxtBackground4(Color txtBackground4) {
        this.txtBackground4 = txtBackground4;
    }

    public Color getTxtForeground4() {
        return txtForeground4;
    }

    public void setTxtForeground4(Color txtForeground4) {
        this.txtForeground4 = txtForeground4;
    }

    public JTextField[][] getListaTxt() {
        return listaTxt;
    }

    public void setListaTxt(JTextField[][] listaTxt) {
        this.listaTxt = listaTxt;
    }

    public int getTxtAncho() {
        return txtAncho;
    }

    public void setTxtAncho(int txtAncho) {
        this.txtAncho = txtAncho;
    }

    public int getTxtAltura() {
        return txtAltura;
    }

    public void setTxtAltura(int txtAltura) {
        this.txtAltura = txtAltura;
    }

    public int getTxtMargen() {
        return txtMargen;
    }

    public void setTxtMargen(int txtMargen) {
        this.txtMargen = txtMargen;
    }

    public int getTxtTamañoLetra() {
        return txtTamañoLetra;
    }

    public void setTxtTamañoLetra(int txtTamañoLetra) {
        this.txtTamañoLetra = txtTamañoLetra;
    }

    public Color getPanelBackground() {
        return panelBackground;
    }

    public void setPanelBackground(Color panelBackground) {
        this.panelBackground = panelBackground;
    }

    public Color getTxtBackground1() {
        return txtBackground1;
    }

    public void setTxtBackground1(Color txtBackground1) {
        this.txtBackground1 = txtBackground1;
    }

    public Color getTxtForeground1() {
        return txtForeground1;
    }

    public void setTxtForeground1(Color txtForeground1) {
        this.txtForeground1 = txtForeground1;
    }

    public Color getTxtBackground2() {
        return txtBackground2;
    }

    public void setTxtBackground2(Color txtBackground2) {
        this.txtBackground2 = txtBackground2;
    }

    public Color getTxtForeground2() {
        return txtForeground2;
    }

    public void setTxtForeground2(Color txtForeground2) {
        this.txtForeground2 = txtForeground2;
    }

    public Color getTxtBackground3() {
        return txtBackground3;
    }

    public void setTxtBackground3(Color txtBackground3) {
        this.txtBackground3 = txtBackground3;
    }

    public Color getTxtForeground3() {
        return txtForeground3;
    }

    public void setTxtForeground3(Color txtForeground3) {
        this.txtForeground3 = txtForeground3;
    }

}
