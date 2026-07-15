package excecoes;

public class ObraJaNaExposicaoException extends Exception {
  private static final long serialVersionUID = 1L;

  public ObraJaNaExposicaoException(String mensagem) {
    super(mensagem);
  }
}