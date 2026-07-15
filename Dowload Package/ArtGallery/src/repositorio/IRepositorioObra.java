package repositorio;

import java.util.Vector;

import excecoes.ObraJaCadastradaException;
import excecoes. ObraNaoEncontradaException;
import modelo.Obra;

public interface IRepositorioObra {
  public void cadastrar(Obra obra) throws ObraJaCadastradaException;
  public Obra buscar(String titulo);
  public void atualizar(Obra obra) throws ObraNaoEncontradaException;
  public void remover(String titulo) throws ObraNaoEncontradaException;
  public Vector<Obra> listar();
}
