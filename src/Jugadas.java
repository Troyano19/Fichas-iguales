public class Jugadas {
    private int fila;
    private int columna;
    private char color;
    private int tempPuntuacion;
    private int puntuacion;
    private int fichas;
    private boolean ended;
    private boolean empty;
    private Tablero tablero;
    private Jugadas precursor;

    Jugadas(int fila, int columna, char color, int puntuacion, int fichas, boolean ended, boolean empty, Tablero tablero, int tempPuntuacion, Jugadas precursor){
        this.fila = fila;
        this.columna = columna;
        this.color = color;
        this.fichas = fichas;
        this.ended = ended;
        this.empty = empty;
        this.tablero = tablero;
        this.puntuacion = tempPuntuacion;
        this.tempPuntuacion = puntuacion;
        this.precursor = precursor;
    }

    Jugadas(){
        this.fila = 0;
        this.columna = 0;
        this.color = ' ';
        this.fichas = 0;
        this.ended = false;
        this.empty = false;
        this.tablero = null;
        this.puntuacion = 0;
        this.tempPuntuacion = 0;
        this.precursor = null;
    }
    public Jugadas getPrecursor(){
        return this.precursor;
    }

    public int getFila(){
        return this.fila;
    }
    
    public int getColumna(){
        return this.columna;
    }
    
    public char getColor(){
        return this.color;
    }
    
    public int getPuntuacion(){
        return this.tempPuntuacion;
    }

    public int getPuntuacionFinal(){
        return this.puntuacion;
    }

    public int getFichas(){
        return this.fichas;
    }

    public boolean getEnded(){
        return this.ended;
    }

    public boolean getEmpty(){
        return this.empty;
    }

    public Tablero getTablero(){
        return this.tablero;
    }

    public String toString(){
        return "Fila: "+this.fila+" Columna: "+this.columna+" Color: "+this.color+" Puntuacion: "+this.puntuacion+" Fichas: "+this.fichas+" Ended: "+this.ended+" Empty: "+this.empty;
    }

}
