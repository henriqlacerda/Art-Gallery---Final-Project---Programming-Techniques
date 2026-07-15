package modelo;

import java.io.Serializable;
import java.util.Vector;

import excecoes.ObraJaNaExposicaoException;
import excecoes.ObraNaoPertenceAExposicaoException;

public class Exposicao implements Serializable {
  // P n dar erro na serialização
  private static final long serialVersionUID = 1L;

  private String nome;
  private Vector<Obra> obras;

  public Exposicao(String nome) {
    this.nome = nome;
    this.obras = new Vector<Obra>();
  }

  // Getters
  public String getNome() { return this.nome; }

  public void adicionarObra(Obra obra) throws ObraJaNaExposicaoException {
    if (obra == null) return;

    for( Obra o : this.obras ) {
      if( o.getTitulo().equalsIgnoreCase(obra.getTitulo()) ) {
          throw new ObraJaNaExposicaoException("A obra '" + obra.getTitulo() + "' já está cadastrada nesta exposição!");
      }
    }

    this.obras.add(obra);
  }

  public void removerObra(String titulo) throws ObraNaoPertenceAExposicaoException {
    int n = this.obras.size();
    for( int i = 0; i < n; i++ ) {
      if( this.obras.get(i).getTitulo().equalsIgnoreCase(titulo) ) {
        this.obras.remove(i);
        return; 
      }
    }
    throw new ObraNaoPertenceAExposicaoException("A obra '" + titulo + "' não foi encontrada nesta exposição.");
  }

  public Vector<Obra> listarObras() {
    return this.obras;
  }
}
