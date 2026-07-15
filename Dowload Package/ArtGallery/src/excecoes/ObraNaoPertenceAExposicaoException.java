package excecoes;

public class ObraNaoPertenceAExposicaoException extends Exception {
  private static final long serialVersionUID = 1L;

  public ObraNaoPertenceAExposicaoException(String mensagem) {
    super(mensagem);
  }
}