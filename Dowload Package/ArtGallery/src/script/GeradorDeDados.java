package script;

import java.util.Random;

import galeria.ArtGallery;
import galeria.IArtGallery;
import modelo.ArteGenerativa;
import modelo.Avaliacao;
import modelo.Modelagem3D;
import modelo.Obra;
import modelo.PinturaDigital;
import repositorio.RepositorioExposicaoArquivo;
import repositorio.RepositorioObraArquivo;


// Isso aq eu criei só p mu ajudar a fazer testes e saber se tudo tava funcionando direito
public class GeradorDeDados {
  public static void main(String[] args) {
    RepositorioObraArquivo repoObras = new RepositorioObraArquivo();
    RepositorioExposicaoArquivo repoExposicoes = new RepositorioExposicaoArquivo();

    IArtGallery galeria = new ArtGallery(repoObras, repoExposicoes);
    Random rand = new Random();

    String[] softwares = {"Photoshop", "Corel Draw", "ProCreate", "Clip Studio Paint"};
    String[] engines = {"Blender", "Maya", "ZBrush", "Cinema 4D"};
    String[] algoritmos = {"Perlin Noise", "Fractal de Mandelbrot", "Cellular Automata", "L-System"};
    String[] autores = {"Leonador DaVinci", "Van Gogh", "Artista Desconhecido", "IA Generativa", "Estudante de CC"};

    for(int i = 1; i <= 50; i++) {
      Obra novaObra = null;
      String titulo = "Arte" + i;
      String autor = autores[rand.nextInt(autores.length)];

      int tipo = i % 3;
      if(tipo == 0) { novaObra = new PinturaDigital(titulo, autor, "4K", softwares[rand.nextInt(softwares.length)]); }
      else if(tipo == 1) { novaObra = new Modelagem3D(titulo, autor, rand.nextInt(50000) + 1000, engines[rand.nextInt(engines.length)]); }
      else { novaObra = new ArteGenerativa(titulo, autor, algoritmos[rand.nextInt(algoritmos.length)], rand.nextLong()); }

      int numAvaliacoes = rand.nextInt(7) + 1; 
        for (int j = 1; j <= numAvaliacoes; j++) {
          int nota = rand.nextInt(11); 
          try {
            novaObra.adicionarAvaliacao(new Avaliacao("Avaliador" + j, nota, "Feedback gerado automaticamente."));
          } catch (Exception e) {
            // Ignora caso a regra de negócio da nota barre algo
          }
        }

      try {
        galeria.publicarObra(novaObra);
        System.out.println("Sucesso: " + titulo + " | Média: " + String.format("%.2f", novaObra.mediaAvaliacoes()));
      } catch (Exception e) {
        System.out.println("Erro ao salvar " + titulo + ": " + e.getMessage());
      }
    }

    System.out.println("GERAÇÃO DE DADOS CONCLUÍDA");
  }
}
