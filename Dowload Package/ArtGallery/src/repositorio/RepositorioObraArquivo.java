package repositorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import excecoes.ErroPersistenciaException;
import excecoes.ObraJaCadastradaException;
import excecoes.ObraNaoEncontradaException;
import modelo.Obra;

public class RepositorioObraArquivo implements IRepositorioObra {
  private Vector<Obra> obras;
  private final String NOME_ARQUIVO = "galeria.dat";

  public RepositorioObraArquivo() {
    this.obras = new Vector<Obra>();
    carregarArquivo();
  }

  // Métodos p arquivo
  private void salvarArquivo() {
    try {
      FileOutputStream fos = new FileOutputStream(NOME_ARQUIVO);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this.obras);
      oos.close();
      fos.close();
    } catch (IOException e) {
      throw new ErroPersistenciaException("Falha crítica de corrupção: Os dados do catálogo de obras estão ilegíveis.");
    }
  }

  @SuppressWarnings("unchecked")
  private void carregarArquivo() {
    File arquivo = new File(NOME_ARQUIVO);
    if (arquivo.exists()) {
      try {
        FileInputStream fis = new FileInputStream(arquivo);
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.obras = (Vector<Obra>) ois.readObject();
        ois.close();
        fis.close();
      } catch (Exception e) {
        throw new ErroPersistenciaException("Falha crítica de corrupção: Os dados do catálogo de obras estão ilegíveis.");
      }
    }
  }

  // Métodos da Interface
  @Override
  public void cadastrar(Obra obra) throws ObraJaCadastradaException {
    for (Obra o: this.obras ) {
      if ( o.getTitulo().equalsIgnoreCase(obra.getTitulo()) ) {
        throw new ObraJaCadastradaException("Já existe uma obra cadastrada com o título " + obra.getTitulo() + "!");
      }
    }
    this.obras.add(obra);
    salvarArquivo();
  }

  @Override
  public Obra buscar(String titulo) {
    for( Obra o : this.obras ) {
      if(o.getTitulo().equalsIgnoreCase(titulo)) { return o; }
    }
    return null;
  }

  @Override
  public void atualizar(Obra obra) throws ObraNaoEncontradaException {
    Obra obraExistente = buscar(obra.getTitulo());
    if( obraExistente == null) { throw new ObraNaoEncontradaException(); }
    int i = this.obras.indexOf(obraExistente);
    this.obras.set(i,obra);
    salvarArquivo();
  }

  @Override
  public void remover(String titulo) throws ObraNaoEncontradaException {
    Obra obraRemover = buscar(titulo);
    if( obraRemover == null ) { throw new ObraNaoEncontradaException(); }
    obraRemover.setAtiva(false);
    salvarArquivo();
  }

  @Override
  public Vector<Obra> listar() {
    return this.obras;
  }
}
