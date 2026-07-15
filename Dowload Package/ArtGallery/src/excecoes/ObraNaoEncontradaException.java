package excecoes;

public class ObraNaoEncontradaException extends Exception {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  public ObraNaoEncontradaException() {
    super("Não existe uma obra com esse nome em nosso sistema!");
  }
}
