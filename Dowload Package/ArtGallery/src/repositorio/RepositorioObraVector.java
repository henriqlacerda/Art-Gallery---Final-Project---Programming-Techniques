package repositorio;

import java.util.Vector;

import excecoes.ObraJaCadastradaException;
import excecoes.ObraNaoEncontradaException;
import modelo.Obra;

public class RepositorioObraVector implements IRepositorioObra {
  private Vector<Obra> obras;

  public RepositorioObraVector() {
    this.obras = new Vector<Obra>();
  }

  @Override
  public void cadastrar(Obra obra) throws ObraJaCadastradaException {
    for (Obra o : this.obras ) {
      if( o.getTitulo().equalsIgnoreCase(obra.getTitulo()) && o.getAutor().equalsIgnoreCase(obra.getAutor()) ) {
        throw new ObraJaCadastradaException();
      }
    }
    this.obras.add(obra);
  }

  @Override
  public Obra buscar(String titulo) {
    for( Obra o : this.obras ) {
      if ( o.getTitulo().equalsIgnoreCase(titulo) ) { return o; }
    }
    return null;
  }

  @Override
  public void atualizar(Obra obra) throws ObraNaoEncontradaException {
    Obra obraExistente = buscar(obra.getTitulo());
    if( obraExistente == null ) { throw new ObraNaoEncontradaException(); }
    int i = this.obras.indexOf(obraExistente);
    this.obras.set(i,obra);
  }

  @Override
  public void remover(String titulo) throws ObraNaoEncontradaException {
    Obra obraRemover = buscar(titulo);
    if( obraRemover == null ) { throw new ObraNaoEncontradaException(); }
    obraRemover.setAtiva(false);
  }

  @Override
  public Vector<Obra> listar() {
    return this.obras;
  }
}
