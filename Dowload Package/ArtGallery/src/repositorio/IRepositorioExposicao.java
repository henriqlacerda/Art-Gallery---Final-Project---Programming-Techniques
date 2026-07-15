package repositorio;

import java.util.Vector;

import excecoes.ExposicaoJaCadastradaException;
import excecoes.ExposicaoNaoEncontradaException;
import modelo.Exposicao;

public interface IRepositorioExposicao {
  public void cadastrar(Exposicao exposicao) throws ExposicaoJaCadastradaException;
  public Vector<Exposicao> listar();
  public void atualizar(Exposicao exposicao) throws ExposicaoNaoEncontradaException;
}
