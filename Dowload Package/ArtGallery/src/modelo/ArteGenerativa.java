package modelo;

public class ArteGenerativa extends Obra {
  private String algoritmo;
  private long seed;

  public ArteGenerativa(String titulo, String autor, String algoritmo, long seed) {
    super(titulo,autor);
    this.algoritmo = algoritmo;
    this.seed = seed;
  }

  @Override
  public String exibirDetalhes() {
    return "TÍTULO: " + getTitulo() + "\n" +
           "AUTOR: " + getAutor() + "\n" +
           "TIPO: Arte Generativa\n" +
           "ALGORITMO: " + this.algoritmo + "\n" +
           "SEED: " + this.seed; 
  }
}
