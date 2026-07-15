package repositorio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import excecoes.ErroPersistenciaException;
import excecoes.ExposicaoJaCadastradaException;
import excecoes.ExposicaoNaoEncontradaException;
import modelo.Exposicao;

public class RepositorioExposicaoArquivo implements IRepositorioExposicao {
  private Vector<Exposicao> exposicoes;
  private final String NOME_ARQUIVO = "exposicoes.dat";

  public RepositorioExposicaoArquivo(){
    this.exposicoes = new Vector<Exposicao>();
    carregarArquivo();
  }

  private void salvarArquivo() {
    try {
      FileOutputStream fos = new FileOutputStream(NOME_ARQUIVO);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this.exposicoes);
      oos.close();
      fos.close();
    } catch (IOException e) {
      throw new ErroPersistenciaException("Falha crítica de hardware: Não foi possível escrever no disco.");
    }
  }

  @SuppressWarnings("unchecked")
  private void carregarArquivo() {
    File arquivo = new File(NOME_ARQUIVO);
    if (arquivo.exists()) {
      try {
        FileInputStream fis = new FileInputStream(arquivo);
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.exposicoes = (Vector<Exposicao>) ois.readObject();
        ois.close();
        fis.close();
      } catch (Exception e) {
        throw new ErroPersistenciaException("Falha crítica de hardware: Não foi possível escrever no disco.");
      }
    }
  }

  @Override
  public void cadastrar(Exposicao exposicao) throws ExposicaoJaCadastradaException {
    for (Exposicao e : this.exposicoes) {
      if ( e.getNome().equalsIgnoreCase(exposicao.getNome()) ) {
        throw new ExposicaoJaCadastradaException("A exposição '" + exposicao.getNome() + "' já existe!");
      }
    }
    this.exposicoes.add(exposicao);
    salvarArquivo();
  }

  @Override
  public void atualizar(Exposicao exposicao) throws ExposicaoNaoEncontradaException {
    int n = this.exposicoes.size();
    for ( int i = 0; i < n; i++ ) {
      if( this.exposicoes.get(i).getNome().equalsIgnoreCase(exposicao.getNome()) ) {
        this.exposicoes.set(i, exposicao);
        salvarArquivo();
        return;
      }
    }
    throw new ExposicaoNaoEncontradaException();
  }

  @Override
  public Vector<Exposicao> listar() {
    return this.exposicoes;
  }
}
