package modelo;

public class PinturaDigital extends Obra {
  private String resolucao;
  private String softwareUtilizado;

  public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado) {
    super(titulo,autor);
    this.resolucao = resolucao;
    this.softwareUtilizado = softwareUtilizado;
  }

  @Override
  public String exibirDetalhes() {
    return "TITULO: " + getTitulo() + "\n" +
           "AUTOR: " + getAutor() + "\n" +
           "TIPO: Pintura Digital\n" +
           "RESOLUÇÃO: " + this.resolucao + "\n" +
           "SOFTWARE UTILIZADO: " + this.softwareUtilizado;
  }
}
