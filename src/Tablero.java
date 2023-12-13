import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tablero {
    private int filas;
    private int columnas;
    private Ficha[][] tablero;

    Tablero(Tablero board){
        this.filas = board.getFilas();
        this.columnas = board.getColumnas();
        this.tablero = fill(board.getTablero());
    }

    Tablero(Scanner sc){
        this.tablero = fill(sc);
        if(this.tablero == null){
            this.filas = 0;
            this.columnas = 0;
        }else{
            this.filas = tablero.length;
            this.columnas = tablero[0].length;
        }
    }

    public Ficha[][] getTablero(){
        return this.tablero;
    }
    
    public int getFilas(){
        return this.filas;
    }
    public int getColumnas(){
        return this.columnas;
    }

    public char getColor(int fila, int columna){
        if(fila < 0 || fila > this.filas-1 || columna > this.columnas-1 || columna < 0){
            return ' ';
        }
        return this.tablero[fila][columna].getColor();
    }

    public Ficha getFicha(int fila, int columna){
        if(fila < 0 || fila > this.filas-1 || columna > this.columnas-1 || columna < 0){
            return null;
        }
        return this.tablero[fila][columna];
    }

    public void setColor(int fila, int columna, char color){
        this.tablero[fila][columna].setColor(color);
    }

    public void setFicha(int fila, int columna, Ficha ficha){
        this.tablero[fila][columna] = ficha;
    }
    /**
     * Funcion que comprueba si el tablero tiene como mucho una ficha de cada color (Las celdas estan compuestas por el caracter 'B')
     * @param Tablero
     * @return true en caso de estar vacio y false en caso contrario
     */
    public boolean isEnded(){
        int verde = 0;
        int azul = 0;
        int rojo = 0;
        for(int i = 0; i<this.filas; i++){
            for(int j = 0; j<this.columnas; j++){
                if(this.tablero[i][j].getColor() == 'R'){
                    rojo++;
                }else if(this.tablero[i][j].getColor() == 'V'){
                    verde++;
                }else if(this.tablero[i][j].getColor() == 'A'){
                    azul++;
                }
            }
        }
        if(azul > 1 || verde > 1 || rojo > 1){
            return false;
        }
        return true;
    }
    /**
     * Comprueba si el tablero esta completamente vacio
     * @return true en caso de estar vacio y false en caso de no estarlo
     */
    public boolean isEmpty(){
        int verde = 0;
        int azul = 0;
        int rojo = 0;
        for(int i = 0; i<this.filas; i++){
            for(int j = 0; j<this.columnas; j++){
                if(tablero[i][j].getColor() == 'R'){
                    rojo++;
                }else if(tablero[i][j].getColor() == 'V'){
                    verde++;
                }else if(tablero[i][j].getColor() == 'A'){
                    azul++;
                }
            }
        }
        if(azul > 0 || verde > 0 || rojo > 0){
            return false;
        }
        return true;
    }
    /**
     * 
     * Comprueba que una columna pasada por parametro esta vacia
     * @param columna
     * @return true en caso de tener la columna vacia false en caso contrario
     */
    public boolean isEmptyColumn(int columna){
        for(int i = 0; i < this.filas; i++){
            if(this.tablero[i][columna].getColor() != 'T' && this.tablero[i][columna].getColor() != 'B'){
                return false;
            }
        }
        return true;
    }

    /**
     * Funcion encargada de rellenar el tablero de juego con todas las fichas necesarias de otro tablero.
     * 
     * @param sc
     * @param cadenas
     * @param comprobado
     * @return Ficha[][] con todas las fichas del tablero
     * o null si no se cumplen las normas.
     */
    private Ficha[][] fill(Ficha[][] tablero){
        Ficha[][] tableroAux = new Ficha[tablero.length][tablero[0].length];
        for(int i = 0; i<tablero.length; i++){
            for(int j = 0; j<tablero[0].length; j++){
                tableroAux[i][j] = new Ficha(i, j, tablero[i][j].getColor());
            }
        }
        return tableroAux;
    }
    /**
     * Rellena un tablero a traves de lineas pasadas por parametro.
     * 
     * @param sc
     * @return Ficha[][] con todos los caracteres introducidos por texto o null en caso de romper una norma
     */
    public Ficha[][] fill(Scanner sc){
        int contador = 0;
        List<String> cadenas = new ArrayList<>();
        //Si text no es una linea en blanco retornar null
        //Introducimos la primera linea de texto
        
        /*comprobara que el texto no es un salto de linea
        y añade, exclusivamente texto escrito a la cadena.
        */
        while(sc.hasNextLine()){
            String text = sc.nextLine();
            if(text.isEmpty()){
                break;
            }
            if(rulesTablero(cadenas, text)){
                cadenas.add(text);
                contador++;
            }else{
                return null;
            }
        }
        
        //Comprueba que se ha añadido al menos una fila y que no se ha roto ninguna norma
        //Devuelve el tablero con los caracteres o, en caso negativo retorna null
        if(contador > 0){
            char[][] tablero = new char[cadenas.size()][cadenas.get(0).length()];
            for(int i = 0; i < cadenas.size(); i++){
                tablero[i] = cadenas.get(i).toCharArray();
            }
            Ficha[][] tableroFichas = new Ficha[tablero.length][tablero[0].length];
            for(int i = 0; i<tablero.length; i++){
                for(int j = 0; j<tablero[0].length; j++){
                    tableroFichas[i][j] = new Ficha(i, j, tablero[i][j]);
                }
            }
            return tableroFichas;
        }else{
            return null;
        }
    }

    /**
     * Se comprobaran las reglas definidas del juego
     * @param tablero
     * @return true si las reglas se cumplen y false en caso contrario.
     */
    private boolean rulesTablero(List<String> tablero, String text){
        
        //Comprobar que la linea a introducir solo contiene los caracteres aceptados "R", "V" o "A"
        for(int i = 0; i< text.length(); i++){
            if((text.charAt(i) != 'R') && (text.charAt(i) != 'V') && (text.charAt(i) != 'A')){
                return false;
            }
        }
        //Comprobar que la longitud de la cadena es menor o igual a 20
        if(text.length() > 20){
            return false;
        }

        //Comprobar que en la lista, todas las cadenas miden lo mismo
        //Siempre y cuando una linea no sea '\n'
        if(tablero.size() > 1){
            for(int i = 0; i < tablero.size(); i++){
                if((tablero.get(1).length() != tablero.get(i).length()) && (tablero.get(i) != "\n")){
                    return false;
                }
            }
        }
        
        return true;
    }
    /**
     * Cuenta cuantas fichas coloreadas (No blancas) se encuentran en el tablero.
     * @return
     */
    public int cuentaFichas(){
        int fichas = 0;
        for(int i = 0; i<this.filas; i++){
            for(int j = 0; j<this.columnas; j++){
                if(this.tablero[i][j].getColor() != 'T' && this.tablero[i][j].getColor() != 'B'){
                    fichas++;
                }
            }
        }
        return fichas;
    }

    
}
