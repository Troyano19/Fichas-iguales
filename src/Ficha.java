public class Ficha {
    private int fila;
    private int columna;
    private char color;
    private boolean visitada;

    Ficha(int fila, int columna, char color){
        this.fila = fila;
        this.columna = columna;
        this.color = color;
        this.visitada = false;
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

    public boolean getVisitada(){
        return this.visitada;
    }

    public void setVisitada(boolean visitada){
        this.visitada = visitada;
    }

    public void setColor(char color){
        this.color = color;
    }
}
