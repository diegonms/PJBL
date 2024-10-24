import java.util.ArrayList;

abstract class Quarto {
    private String classe;
    private int numero;
    private boolean disponivel;
    private int valorDiaria;

    public Quarto(String classe, int numero, boolean disponivel, int valorDiaria){
        this.classe = classe;
        this.numero = numero;
        this.disponivel = disponivel;
        this.valorDiaria = valorDiaria;
    }
    //Completar
    public void reservar(){}

    public boolean isDisponivel(){
        return disponivel;
    }
}
class QuartoNormal extends Quarto{
    public QuartoNormal(String classe, int numero, boolean disponivel, int valorDiaria){
        super(classe, numero, disponivel, valorDiaria);
    }
}
class QuartoFamilia extends Quarto{
    public QuartoFamilia(String classe, int numero, boolean disponivel, int valorDiaria){
        super(classe, numero, disponivel, valorDiaria);
    }
}
class QuartoSuite extends Quarto{
    public QuartoSuite(String classe, int numero, boolean disponivel, int valorDiaria){
        super(classe, numero, disponivel, valorDiaria);
    }
}

class Unidades{
    private String local;
    private ArrayList<String> locais = new ArrayList<String>();
}
class Estadia{
    private String tempo;
    public Estadia(String tempo){
        this.tempo = tempo;
    }
}
