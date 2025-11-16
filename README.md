# Proyecto Sudoku en Java

## 📌 Descripción
Este proyecto implementa el juego **Sudoku** en Java utilizando **Swing** para la interfaz gráfica.  
Incluye generación automática de tableros con diferentes niveles de dificultad, validación de soluciones y resolución automática.

## 🎯 Funcionalidades
- Generación de tableros con dificultad: fácil, medio y difícil.
- Interfaz gráfica con casillas interactivas.
- Validación de la solución ingresada por el usuario.
- Opción de limpiar casillas o reiniciar el tablero.
- Resolución automática del Sudoku.
- Documentación clara en el código fuente.

## 🛠️ Tecnologías utilizadas
- Lenguaje: **Java**
- Librerías: **Swing (JTextField, JPanel, JFrame, etc.)**
- IDE recomendado: **NetBeans / IntelliJ / Eclipse**
- Control de versiones: **Git + GitHub**

## 🚀 Ejecución
1. Clona el repositorio:
   ```bash
   git clone https://github.com/ByeolSBA/SudokuJava.git

# 📖 Manual de Usuario

### 🎮 Inicio del juego
1. Al ejecutar el programa se abrirá la ventana principal.
2. Selecciona el nivel de dificultad (fácil, medio o difícil).
3. El tablero se generará automáticamente con algunos números ya colocados.

### ✏️ Interacción con el tablero
- Haz clic en cualquier casilla vacía para escribir un número del 1 al 9.
- Usa el panel lateral de números para insertar valores rápidamente.
- Puedes borrar el contenido de una casilla seleccionándola y presionando la tecla **Suprimir/Backspace**.

### ✅ Validación
- Una vez completado el tablero, presiona el botón **Comprobar**.  
- El programa verificará si la solución es correcta.  
- Si hay errores, se mostrará un mensaje indicando que la solución no es válida.

### 🧹 Funciones adicionales
- **Limpiar:** borra todas las casillas libres y deja el tablero con el sudoku original.  
- **Resolver:** el programa completará el Sudoku con la solución correcta.  


### 📌 Recomendaciones
- Ingresa solo números del 1 al 9.  
- Evita repetir números en filas, columnas o subcuadrículas de 3x3.  
- Usa la opción de validación para comprobar tu progreso antes de resolver automáticamente.
