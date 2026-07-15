package excecoes;

public class ErroPersistenciaException extends RuntimeException {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  public ErroPersistenciaException(String mensagem) {
    super(mensagem);
  }
}
