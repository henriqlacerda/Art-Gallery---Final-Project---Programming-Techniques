package modelo;

import java.io.Serializable;
import java.util.Vector;

public abstract class Obra implements Serializable {
  private String titulo;
  private String autor;
  private boolean ativa;
  private Vector<Avaliacao> avaliacoes;

  public Obra(String titulo, String autor) {
    this.titulo = titulo;
    this.autor = autor;
    this.ativa = true;
    this.avaliacoes = new Vector<Avaliacao>();
  }

  // Getters n setters
  public String getTitulo() { return titulo; }
  public String getAutor() { return autor; }
  public boolean isAtiva() { return ativa; }
  public void setAtiva(boolean ativa) { this.ativa = ativa; }

  public void adicionarAvaliacao(Avaliacao avaliacao) {
    if (avaliacao != null) {
      this.avaliacoes.add(avaliacao);
    }
  }

  public double mediaAvaliacoes() {
    int n = this.avaliacoes.size();
    if (n == 0) { return 0.0; }
    double soma = 0;
    for(Avaliacao aval : this.avaliacoes ) { soma += aval.getNota(); }
    return soma / n;
  }

  // Método abstrato
  public abstract String exibirDetalhes();
}
