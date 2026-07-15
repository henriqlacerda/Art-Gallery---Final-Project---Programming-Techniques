package excecoes;

public class ExposicaoNaoEncontradaException extends Exception {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  public ExposicaoNaoEncontradaException() {
    super("A exposição não foi encontrada no repositório!");
  }

  public ExposicaoNaoEncontradaException(String mensagem) {
    super(mensagem);
  }
}
