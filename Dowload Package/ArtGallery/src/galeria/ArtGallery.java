package galeria;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

import excecoes.ExposicaoJaCadastradaException;
import excecoes.ExposicaoNaoEncontradaException;
import excecoes.ObraJaCadastradaException;
import excecoes.ObraNaoEncontradaException;
import modelo.Avaliacao;
import modelo.Exposicao;
import modelo.Obra;
import repositorio.IRepositorioExposicao;
import repositorio.IRepositorioObra;

public class ArtGallery implements IArtGallery {
  private IRepositorioObra repositorioObras;
  private IRepositorioExposicao repositorioExposicoes;

  public ArtGallery(IRepositorioObra repositorioObras, IRepositorioExposicao repositorioExposicoes ) {
    this.repositorioObras = repositorioObras;
    this.repositorioExposicoes = repositorioExposicoes;
  }

  @Override
  public void publicarObra(Obra obra) throws ObraJaCadastradaException {
    this.repositorioObras.cadastrar(obra);
  }

  @Override
  public void removerObra(String titulo) throws ObraNaoEncontradaException, IllegalStateException {
    Obra obra = this.repositorioObras.buscar(titulo);
    if( obra == null ) { throw new ObraNaoEncontradaException(); }

    // Desativa a obra sem apagar do disco
    if(!obra.isAtiva()) { throw new IllegalStateException("A obra " + titulo + " já se encontra desativada."); }
    obra.setAtiva(false);
    this.repositorioObras.atualizar(obra);

    // Oculta ela das exposições em que estava antes
    try {
      for ( Exposicao exp : this.repositorioExposicoes.listar() ) {
        boolean alterou = false;
        for ( Obra o : exp.listarObras() ) {
          if ( o.getTitulo().equalsIgnoreCase(titulo) ) {
            o.setAtiva(false);
            alterou = true;
          }
        }
        if(alterou) { this.repositorioExposicoes.atualizar(exp); }
      }
    } catch (Exception e) {
      System.out.println("Erro ao ocultar nas exposições: " + e.getMessage());
    }
  }

  @Override
  public void reativarObra(String titulo) throws ObraNaoEncontradaException, IllegalStateException {
    Obra obra = this.repositorioObras.buscar(titulo);
    if( obra == null ) { throw new ObraNaoEncontradaException(); }

    // Olha se a obra já n está ativa
    if(obra.isAtiva()) { throw new IllegalStateException("A obra " + titulo + " já se encontra ativa."); }
    // reativa no repositório principal
    obra.setAtiva(true);
    this.repositorioObras.atualizar(obra);

    // Reativa nas exposições que antes estava também
    try {
      for( Exposicao exp : this.repositorioExposicoes.listar() ) {
        boolean alterou = false;
        for( Obra o : exp.listarObras() ) {
          if( o.getTitulo().equalsIgnoreCase(titulo) ) {
            o.setAtiva(true);
            alterou = true;
          }
        }
        if(alterou) { this.repositorioExposicoes.atualizar(exp); }
      }
    } catch (Exception e) {
      System.out.println("Erro ao reativar nas esposições: " + e.getMessage());
    }
  }

  @Override
  public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException {
    Obra obra = this.repositorioObras.buscar(titulo);
    if( obra == null || !obra.isAtiva() ) { throw new ObraNaoEncontradaException(); }
    obra.adicionarAvaliacao(avaliacao);
    this.repositorioObras.atualizar(obra);
  }

  @Override
  public Vector<Obra> listarObras() {
    Vector<Obra> ativas = new Vector<Obra>();
    for( Obra o : this.repositorioObras.listar() ) {
      if(o.isAtiva()) { ativas.add(o); }
    }
    return ativas;
  }

  @Override
  public Vector<Obra> buscarPorAutor(String autor) {
    Vector<Obra> filtro = new Vector<Obra>();
    for( Obra o : listarObras() ) {
      if( o.getAutor().equalsIgnoreCase(autor) ) { filtro.add(o); }
    }
    return filtro;
  }

  @Override
  public Vector<Obra> topObras() {
    Vector<Obra> todas = this.listarObras();
    int k = 10;

    PriorityQueue<Obra> minHeap = new PriorityQueue<>(new Comparator<Obra>() {
      @Override
      public int compare(Obra a, Obra b) {
        return Double.compare(a.mediaAvaliacoes(), b.mediaAvaliacoes());
      }
    });
    
    for( Obra obra : todas ) {
      minHeap.offer(obra);
      if(minHeap.size() > k) { 
        minHeap.poll(); 
      }
    }
    
    Vector<Obra> top10 = new Vector<>();
    while(!minHeap.isEmpty()) { 
        top10.add(0, minHeap.poll()); 
    }
    return top10;
  }

  @Override 
  public Vector<Obra> obrasExpostas(String nomeExposicao) {
    Vector<Obra> visiveis = new Vector<Obra>();

    for( Exposicao exp : this.repositorioExposicoes.listar() ) {
      if(exp.getNome().equalsIgnoreCase(nomeExposicao) ) {
        for( Obra o : exp.listarObras() ) {
          if(o.isAtiva()) { visiveis.add(o); }
        }
        return visiveis;
      }
    }
    return visiveis;
  }

  @Override
  public void criarExposicao(Exposicao exposicao) throws ExposicaoJaCadastradaException {
      this.repositorioExposicoes.cadastrar(exposicao);
  }

  @Override
  public Vector<Exposicao> listarExposicoes() {
    return this.repositorioExposicoes.listar();
  }

  @Override
  public Exposicao buscarExposicao(String nome) {
    for(Exposicao exp : this.repositorioExposicoes.listar()) {
      if( exp.getNome().equalsIgnoreCase(nome) ) { return exp; }
    }
    return null;
  }

  @Override
  public void atualizarExposicao(Exposicao exposicao) throws ExposicaoNaoEncontradaException {
    try { this.repositorioExposicoes.atualizar(exposicao); }
    catch (Exception e) { throw new ExposicaoNaoEncontradaException("Não foi possível atualizar. A exposicão não existe no sistema! "); }
  }

}
