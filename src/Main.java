import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class Main{
    /** 
     * Funcion especifica que iniciara el juego de fichas iguales
     * @param args
     */    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int games = 0;
        List<String> salida = new ArrayList<String>();
        //Introducimos el número de juegos a jugar.
        do{
            // Utilizamos 'print' y no 'println' para que el numero introducido se muestre en la misma linea que el texto.
            System.out.print("Introduzca el número de juegos a jugar: ");
            games = sc.nextInt();
            //Comprueba que exista al menos un juego, si no finaliza.
            if(games < 1){
                System.out.println("El número de juegos debe ser mayor o igual que 1.");
            }
        }while(games < 1);
        //Vaciamos el buffer
        sc.nextLine();
        //Explicamos al usuario como debe introducir los tableros
        System.out.println("\nA partir de ahora, debes de introducir las letras de los colores Azul 'A', Rojo 'R' y Verde 'V'.");
        System.out.println("Debes utilizar el mismo número de columnas para cada fila. No se aplica entre distintos juegos.");
        System.out.println("Aquí tienes un ejemplo de tablero válido:");
        System.out.println("\nARV");
        System.out.println("VAV");
        System.out.println("VRA\n");
        System.out.println("En caso de que hayas elegido más de un juego, introduce una línea en blanco entre cada uno de ellos.");
        System.out.println("¡Que te diviertas! [Pulsa Enter para continuar]");
        //Introducimos la primera linea en blanco y en caso de no serlo, finaliza.
        String text = sc.nextLine();
        if(!text.isEmpty()){
            sc.close();
            return;
        }
        //Se ejecutaran todos los juegos hasta que se acaben o uno se cancele
        for(int i = 0; i < games; i++){
            //Inicializamos un tablero nuevo para cada juego.
            Tablero board = new Tablero(sc);
            //Inicializamos una lista de movimientos para cada juego.
            List<Jugadas> movimientos = new ArrayList<Jugadas>();
            StringBuilder cadena = new StringBuilder();
            //En caso de que el tablero introducido no sea valido se muestran los juegos anteriores
            if(board.getTablero() == null){
                i = games;
            }else{
                //Llamamos a la funcion algoritmo, introducimos el tablero, la lista de movimientos, puntuacion inicial ultima fila y primera columna y una jugada vacia.
                algoritmo(board, movimientos, 0, board.getFilas()-1, 0, new Jugadas());
                //Localizamos cual es la mejor jugada
                int mejor = mejorJugada(movimientos);
                int padre = mejor;
                List<Integer> posiciones = new ArrayList<Integer>();
                cadena.append("Juego "+(i+1)+":\n");
                //Si existe al menos un movimiento localiza los movimientos que preceden al movimiento final.
                if(movimientos.size() > 0){
                    while(mejor >= 0 && (movimientos.get(mejor).getPrecursor() != null)){
                        posiciones.add(mejor);
                        mejor = movimientos.indexOf(movimientos.get(mejor).getPrecursor());
                    }
                    
                    //Almacenamos los strings en cadena
                    for(int j = posiciones.size()-1; j >=0; j--){
                        int posicion = posiciones.get(j);
                        if(movimientos.get(posiciones.get(j)).getFichas() == 1){
                            if(movimientos.get(posiciones.get(posiciones.get(j))).getPuntuacion() == 1){
                                cadena.append("Movimiento "+(posiciones.size()-j)+" en ("+movimientos.get(posicion).getFila()+", "+movimientos.get(posicion).getColumna()+"): eliminó "+movimientos.get(posicion).getFichas()+" ficha de color "+movimientos.get(posicion).getColor()+" y obtuvo "+movimientos.get(posicion).getPuntuacion()+" punto.\n");
                            }else{
                                cadena.append("Movimiento "+(posiciones.size()-j)+" en ("+movimientos.get(posicion).getFila()+", "+movimientos.get(posicion).getColumna()+"): eliminó "+movimientos.get(posicion).getFichas()+" ficha de color "+movimientos.get(posicion).getColor()+" y obtuvo "+movimientos.get(posicion).getPuntuacion()+" puntos.\n");
                            }
                        }else{
                            if(movimientos.get(posiciones.get(j)).getPuntuacion() == 1){
                                cadena.append("Movimiento "+(posiciones.size()-j)+" en ("+movimientos.get(posicion).getFila()+", "+movimientos.get(posicion).getColumna()+"): eliminó "+movimientos.get(posicion).getFichas()+" fichas de color "+movimientos.get(posicion).getColor()+" y obtuvo "+movimientos.get(posicion).getPuntuacion()+" punto.\n");
                            }else{
                                cadena.append("Movimiento "+(posiciones.size()-j)+" en ("+movimientos.get(posicion).getFila()+", "+movimientos.get(posicion).getColumna()+"): eliminó "+movimientos.get(posicion).getFichas()+" fichas de color "+movimientos.get(posicion).getColor()+" y obtuvo "+movimientos.get(posicion).getPuntuacion()+" puntos.\n");
                            }
                        }
                    }
                }
                //En caso de que exista un movimiento almacena el final.
                if(movimientos.size()>0){
                    if(movimientos.get(padre).getTablero().cuentaFichas() == 1){
                        cadena.append("Puntuación final: "+ movimientos.get(padre).getPuntuacionFinal()+", quedando "+ movimientos.get(padre).getTablero().cuentaFichas()+" ficha.\n");
                    }else{
                        cadena.append("Puntuación final: "+ movimientos.get(padre).getPuntuacionFinal()+", quedando "+ movimientos.get(padre).getTablero().cuentaFichas()+" fichas.\n");
                    }
                }else{
                        cadena.append("Puntuación final: "+ 0 +", quedando "+ 1 +" ficha.");
                }
            }
            salida.add(cadena.toString());
        }
        for(int i = 0; i<salida.size(); i++){
            System.out.print(salida.get(i));
            if(i != salida.size()-1){
                System.out.println();
            }
        }     
    }
    /**
     * Funcion encargada de recorrer la matriz en todas sus combinaciones eliminando los distintos grupos de colores y almacenando cada jugada en una lista 
     * 
     * @param tablero el cual en su primera iteracion sera el tablero inicial y posteriormente se pasaran como parametro
     * sus modificaciones.
     * @param movimientos Lista que almacena cada jugada
     * @param puntuacion Suma de puntos adquiridos los movimientos sobre el mismo tablero
     * @param fila 
     * @param columna
     * @param precursor jugada anterior
     */
    private static void algoritmo(Tablero tablero, List<Jugadas> movimientos, int puntuacion, int fila, int columna, Jugadas precursor){
        //Creamos nuevas filas y columnas para no modificar su fila y columna raiz
        int row = fila;
        int column = columna;
        //Creamos una nueva puntuacion que poder modificar
        int puntos = puntuacion;
        char color = tablero.getColor(row, column);
        //En caso de que el color de la fila y columna sea distinto de blanco y que no se haya visitado antes y que el juego no este acabado
        if((color != 'B' && tablero.getFicha(row, column).getVisitada() == false) && !tablero.isEnded()){
            //Creamos un tablero temporal el cual se modificara en las nuevas iteraciones
            Tablero temporal = new Tablero(tablero);
            int fichas = temporal(temporal, row, column, color, tablero);
            //Si hay mas de una ficha en el grupo de la ficha(fila, columna)
            if(fichas > 1){
                //Comprimimos el tablero y generamos una nueva puntuacion la cual se suma a la puntuacion total
                comprimir(temporal);
                int tempPuntuacion = (int)Math.pow(fichas - 2, 2);
                puntos += tempPuntuacion;
                //En caso de haber acabado el tablero tras la compresion, creamos la jugada final y retrocedemos un nivel para seguir buscando
                if(temporal.isEnded()){
                    if(temporal.isEmpty()){
                        Jugadas juego = new Jugadas(tablero.getFilas()-row, column+1, color, tempPuntuacion, fichas, true, true, temporal, puntos+1000, precursor);
                        movimientos.add(juego);
                    }else{
                        Jugadas juego = new Jugadas(tablero.getFilas()-row, column+1, color, tempPuntuacion, fichas, true, false, temporal, puntos, precursor);
                        movimientos.add(juego);
                    }
                }else{
                    /*En caso de no haber acabado creamos la jugada y seguimos buscando con el nuevo tablero
                    * Al cual le pasamos el tablero ya modificado, los movimientos que se han hecho,
                    * los puntos generados durante todos los movimientos anteriores, fila ultima y columna 0 y la ultima jugada.
                    */
                    Jugadas juego = new Jugadas(tablero.getFilas()-row, column+1, color, tempPuntuacion, fichas, false, false, temporal, puntos, precursor);
                    movimientos.add(juego);
                    algoritmo(temporal, movimientos, juego.getPuntuacionFinal(), tablero.getFilas()-1, 0, juego);
                    /*
                     * Al haber completado todo el camino avanzamos una columna por lo que debemos heredar el tablero temporal
                     * la lista de movimientos, los puntos con los que se comenzo la iteracion, la fila en la que estamos, una columna mas y la jugada en la que estamos
                     */
                    if(column < tablero.getColumnas()-1){
                        algoritmo(tablero, movimientos, puntuacion, row, column+1, precursor);
                    }else if(column == tablero.getColumnas()-1 && row > 0){
                        algoritmo(tablero, movimientos, puntuacion, row-1, 0, precursor);
                    }
                }
            }else{
                //En caso de solo tener una ficha en el grupo seleccionado, se revierten los cambios ocasionados en temporal();
                temporal.setColor(row, column, color);
                temporal.getFicha(row, column).setVisitada(false);
                tablero.getFicha(row, column).setVisitada(false);
                if(column < tablero.getColumnas()-1){
                    algoritmo(tablero, movimientos, puntos, row, column+1, precursor);
                }else if(column == tablero.getColumnas()-1 && row > 0){
                    algoritmo(tablero, movimientos, puntos, row-1, 0, precursor);
                }
            }
        }else{
            if(column < tablero.getColumnas()-1){
                algoritmo(tablero, movimientos, puntos, row, column+1, precursor);
            }else if(column == tablero.getColumnas()-1 && row > 0){
                algoritmo(tablero, movimientos, puntos, row-1, 0, precursor);
            }
        }
    }
    /**
     * Funcion encargada de relocalizar las fichas blancas en la parte superior y en la parte izquierda del tablero
     * @param tablero
     * @return un nuevo tablero comprimido
     */
    private static Tablero comprimir(Tablero tablero){
        //Bucle que busca fichas 'T' en el tablero, una vez encontrada, la coloca en la fila = 0 y cada ficha desde la localizada hasta fila = 0 se mueve una posicion abajo
        for(int i = 0; i<tablero.getFilas(); i++){
            for(int j =0; j<tablero.getColumnas(); j++){
                if(tablero.getColor(i, j) == 'T'){
                    Ficha temp = tablero.getFicha(i, j);
                    for(int k=i; k>0; k--){
                        tablero.setFicha(k, j, tablero.getFicha(k-1, j));
                    }
                    tablero.setFicha(0, j, temp);
                    tablero.getFicha(0, j).setColor('B');
                }
            }
        }
        //Buscamos una columna completamente vacia
        for(int i = 0; i<tablero.getColumnas(); i++){
            if(tablero.isEmptyColumn(i)){
                Ficha[] temp = new Ficha[tablero.getFilas()];
                //Guardamos la columna vacia en un array
                for(int j = 0; j<tablero.getFilas(); j++){
                    temp[j] = tablero.getFicha(j, i);
                }
                //movemos todas las columnas desde la vacia hasta la ultima-1 a la derecha un paso a la derecha
                for(int j = i; j<tablero.getColumnas()-1; j++){
                    for(int k = 0; k<tablero.getFilas(); k++){
                        tablero.setFicha(k, j, tablero.getFicha(k, j+1));
                    }
                }
                //Convertimos la ultima fila en la fila vacia almacenada en el primer bucle.
                for(int j = 0; j<tablero.getFilas(); j++){
                    tablero.setFicha(j, tablero.getColumnas()-1, temp[j]);
                    tablero.getFicha(j, tablero.getColumnas()-1).setColor('B');
                }
            }
        }
        return tablero;
    }
    
    /**
     * Funcion que comprueba si el color pasado por parametro es igual al que se busca y que las filas y las columnas no se salen del tablero.
     * @param tablero
     * @param fila
     * @param columna
     * @param color
     * @return false en caso de no cumplir las normas.
     *         true en caso de si cumplir las normas.
     */
    private static boolean testColor(Tablero tablero, int fila, int columna, char color){
        int rows = tablero.getFilas();
        int columns = tablero.getColumnas();
        //If que comprueba que estamos dentro de los limites de la matriz y que el color es igual al buscado
        if(fila < 0 || fila > rows-1 || columna > columns-1 || columna < 0 || (tablero.getColor(fila, columna) != color)){
            return false;
        }else{
            return true;
        }
    }
    /**
     * Funcion recursiva que comprueba si una pieza de un color tiene piezas del mismo color a sus lados
     * @param tablero
     * @param fila
     * @param columna
     * @param color
     * @param padre
     * @return
     */
    private static int temporal(Tablero tablero, int fila, int columna, char color, Tablero padre){
        int i = fila;
        int j = columna;
        if(!testColor(tablero, i, j, color)){
            return 0;
        }else{
            int fichas = 1;

            tablero.setColor(i, j, 'T');
            tablero.getFicha(i, j).setVisitada(true);
            padre.getFicha(i, j).setVisitada(true);
            fichas += temporal(tablero, i+1, j, color, padre);
            fichas += temporal(tablero, i-1, j, color, padre);
            fichas += temporal(tablero, i, j+1, color, padre);
            fichas += temporal(tablero, i, j-1, color, padre);
            return fichas;
        }
    }
    /**
     * localiza cual es la primera jugada con mayor puntuacion.
     * @param movimientos
     * @return la posicion en la lista de movimientos en la que se encuentra la jugada con mayor puntuacion.
     *
     */
    private static int mejorJugada(List<Jugadas> movimientos){
        int mejor = 0;
        for(int i = 0; i<movimientos.size(); i++){
            if((movimientos.get(i).getPuntuacionFinal() > movimientos.get(mejor).getPuntuacionFinal()) && movimientos.get(i).getEnded()){
                mejor = i;
            }
        }
        return mejor;
    }
}
