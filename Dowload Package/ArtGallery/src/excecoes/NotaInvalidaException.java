package excecoes;

public class NotaInvalidaException extends Exception {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  public NotaInvalidaException() {
    super("A nota da sua avaliação deve estar entre 1 e 10!");
  }
}
