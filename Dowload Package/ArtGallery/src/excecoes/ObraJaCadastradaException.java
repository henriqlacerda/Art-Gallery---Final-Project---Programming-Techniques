package excecoes;

public class ObraJaCadastradaException extends Exception {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  public ObraJaCadastradaException() {
    super("Já existe uma obra com esse nome em nosso sistema!");
  }

  public ObraJaCadastradaException(String mensagem) {
    super(mensagem);
  }
}
