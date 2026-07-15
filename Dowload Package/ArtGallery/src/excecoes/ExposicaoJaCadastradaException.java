package excecoes;

public class ExposicaoJaCadastradaException extends Exception {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  public ExposicaoJaCadastradaException() {
    super("Já existe uma exposição com esse nome cadastrada no sistema!");
  }

  public ExposicaoJaCadastradaException(String mensagem) {
    super(mensagem);
  }
}
