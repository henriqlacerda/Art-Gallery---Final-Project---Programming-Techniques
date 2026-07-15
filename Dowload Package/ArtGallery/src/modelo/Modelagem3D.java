package modelo;

public class Modelagem3D extends Obra {
  private int numeroPoligonos;
  private String engine;

  public Modelagem3D(String titulo, String autor, int numeroPoligonos, String engine) {
    super(titulo,autor);
    this.numeroPoligonos = numeroPoligonos;
    this.engine = engine;
  }

  @Override
  public String exibirDetalhes() {
    return "TÍTULO: " + getTitulo() + "\n" +
           "AUTOR: " + getAutor() + "\n" +
           "TIPO: Modelagem 3D\n" +
           "NÚMERO DE POLÍGONOS: " + this.numeroPoligonos + "\n" +
           "ENGINE: " + this.engine;
  }
}
