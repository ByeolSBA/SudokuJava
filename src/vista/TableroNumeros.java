package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Clase TableroNumeros Panel lateral que contiene los números del 1 al 9.
 * Permite al usuario seleccionar un número y colocarlo en el tablero principal.
 */
public class TableroNumeros extends JPanel {

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

    private TableroSudoku tableroSudoku;

    /**
     * Constructor de la clase TableroNumeros. Inicializa los componentes
     * gráficos y obtiene la referencia al tablero principal.
     */
    public TableroNumeros() {
        iniciarComponentes();
        tableroSudoku = FormSudoku.tableroSudoku;
    }

    /**
     * Método iniciarComponentes Configura los valores iniciales de tamaño,
     * colores y estilos del panel.
     */
    public void iniciarComponentes() {
        txtAncho = 30;
        txtAltura = 30;
        txtMargen = 4;
        txtTamañoLetra = 27;
        panelBackground = Color.BLACK;
        txtBackground1 = Color.WHITE;
        txtForeground1 = Color.BLACK;
        txtBackground2 = Color.WHITE;
        txtForeground2 = Color.BLACK;
    }

    /**
     * Método crearTablero Genera el panel lateral con los números del 1 al 9.
     */
    public void crearTablero() {
        this.setLayout(null);
        this.setSize(txtAncho + (2 * txtMargen), txtAltura * 9 + (4 * txtMargen));
        this.setBackground(panelBackground);
        crearCamposTxt();
    }

    /**
     * Método crearCamposTxt Crea los campos de texto que representan los
     * números del 1 al 9. Cada número se coloca en una casilla vertical dentro
     * del panel.
     */
    public void crearCamposTxt() {
        int x = txtMargen;
        int y = txtMargen;

        for (int i = 0; i < 9; i++) {
            JTextField txt = new JTextField();
            this.add(txt);
            txt.setBounds(x, y, txtAncho, txtAltura);
            txt.setBackground(txtBackground1);
            txt.setForeground(txtForeground1);
            txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
            txt.setEditable(false);
            txt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
            txt.setFont(new Font("MontSerrat", Font.BOLD, txtTamañoLetra));
            txt.setText(String.valueOf(i + 1));

            y += txtAltura;
            if ((i + 1) % 3 == 0) {
                y += txtMargen;
            }
            generarEventosTxt(txt);
        }
    }

    /**
     * Método generarEventosTxt Asigna eventos de mouse a cada número del panel.
     * (txt) campo de texto que representa el número seleccionado.
     */
    public void generarEventosTxt(JTextField txt) {
        MouseListener evento = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                // Si la casilla seleccionada en el tablero es fija, no se modifica
                if (tableroSudoku.txtGenerado(tableroSudoku.txtSelected)) {
                    return;
                }
                // Coloca el número seleccionado en la casilla activa del tablero
                tableroSudoku.txtSelected.setText(txt.getText());
                tableroSudoku.registrarMovimiento(tableroSudoku.txtSelected);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                // Cambia colores al pasar el mouse sobre el número
                txt.setBackground(txtBackground2);
                txt.setForeground(txtForeground2);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                // Restaura colores al salir el mouse
                txt.setBackground(txtBackground1);
                txt.setForeground(txtForeground1);
            }
        };
        txt.addMouseListener(evento);
    }

    // Métodos getters y setters para personalizar atributos gráficos
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
}
