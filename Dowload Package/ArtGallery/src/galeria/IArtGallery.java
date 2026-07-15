package galeria;

import java.util.Vector;

import excecoes.ExposicaoJaCadastradaException;
import excecoes.ExposicaoNaoEncontradaException;
import excecoes.ObraJaCadastradaException;
import excecoes.ObraNaoEncontradaException;
import modelo.Avaliacao;
import modelo.Exposicao;
import modelo.Obra;

public interface IArtGallery {
  // Interface da galeria
  public void publicarObra(Obra obra) throws ObraJaCadastradaException;
  public void removerObra(String titulo) throws ObraNaoEncontradaException, IllegalStateException;
  public void reativarObra(String titulo) throws ObraNaoEncontradaException, IllegalStateException;
  public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException;
  public Vector<Obra> listarObras();
  public Vector<Obra> buscarPorAutor(String autor);
  public Vector<Obra> topObras();
  public Vector<Obra> obrasExpostas(String nomeExposicao);
  public void criarExposicao(Exposicao exposicao) throws ExposicaoJaCadastradaException;
  public Vector<Exposicao> listarExposicoes();
  public Exposicao buscarExposicao(String nome);
  public void atualizarExposicao(Exposicao exposicao) throws ExposicaoNaoEncontradaException;
}
