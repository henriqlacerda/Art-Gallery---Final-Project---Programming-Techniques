package modelo;

import java.io.Serializable;

import excecoes.NotaInvalidaException;

public class Avaliacao implements Serializable {
  private String usuario;
  private int nota;
  private String comentario;

  public Avaliacao(String usuario, int nota, String comentario) throws NotaInvalidaException {
    this.setNota(nota);
    this.usuario = usuario;
    this.comentario = comentario;
  }

  // Getters n setters
  public String getUsuario() { return usuario; }
  public int getNota() { return nota; }
  public String getComentario() { return comentario; }
  public void setNota(int nota) throws NotaInvalidaException {
    if(nota < 0 || nota > 10) { throw new NotaInvalidaException(); }
    this.nota = nota;
  }

}
