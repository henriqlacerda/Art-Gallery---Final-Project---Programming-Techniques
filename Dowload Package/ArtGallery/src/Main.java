import galeria.ArtGallery;
import galeria.IArtGallery;
import repositorio.IRepositorioExposicao;
import repositorio.IRepositorioObra;
import repositorio.RepositorioExposicaoArquivo;
import repositorio.RepositorioObraArquivo;
import visao.Janela;

public class Main {
    
  public static void main(String[] args) {
    try {
      IRepositorioObra repoObras = new RepositorioObraArquivo();
      IRepositorioExposicao repoExposicoes = new RepositorioExposicaoArquivo();
      IArtGallery galeria = new ArtGallery(repoObras, repoExposicoes);

      Janela janela = new Janela(galeria);
            
      janela.setVisible(true);

    } catch (Exception e) {
      // Interceção do erro primordial
      System.out.println("Falha crítica durante o Bootstrapping do sistema: " + e.getMessage());
    }
  }
}