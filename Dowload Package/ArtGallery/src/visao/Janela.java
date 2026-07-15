package visao;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import excecoes.ExposicaoJaCadastradaException;
import excecoes.ObraJaCadastradaException;
import excecoes.ObraJaNaExposicaoException;
import excecoes.ObraNaoEncontradaException;
import excecoes.ObraNaoPertenceAExposicaoException;
import galeria.IArtGallery;
import modelo.ArteGenerativa;
import modelo.Avaliacao;
import modelo.Exposicao;
import modelo.Modelagem3D;
import modelo.Obra;
import modelo.PinturaDigital;

public class Janela extends JFrame implements ITema {
  private static final long serialVersionUID = 1L;
  
  private IArtGallery galeria; 
  private PainelExibicao painelExibicao;

  // Aq é a janela num geral
  public Janela(IArtGallery galeria) {
    this.galeria = galeria;
    this.painelExibicao = new PainelExibicao();
        
    setTitle("ArtGallery - Curadoria e Exposição");
    setSize(850, 550); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); 
    setUndecorated(true);
    getContentPane().setBackground(COR_FUNDO);
    setLayout(new BorderLayout(10, 0)); 
        
    aplicarTemaGlobalNosPopups();
        
    // Montagem dos sub-espaços da janela
    add(new PainelBarraTitulo(this), BorderLayout.NORTH);
    add(new PainelMenuLateral(this), BorderLayout.WEST);
    add(painelExibicao, BorderLayout.CENTER);
        
    acaoMostrarBoasVindas();
  }

  // Subtítulos
  public void acaoMostrarBoasVindas() { painelExibicao.mostrarBoasVindas(); }
  public void acaoListarObras() { painelExibicao.exibirObras(galeria.listarObras(), "Catálogo Geral"); }
  public void acaoTopObras() { painelExibicao.exibirObras(galeria.topObras(), "Top 10 Obras Mais Bem Avaliadas"); }

  // Lógica dos formulário e regras de negócio
  public void cadastrarNovaObra() {
    try {
      String[] opcoes = {"Pintura Digital", "Modelagem 3D", "Arte Generativa"};
      String tipoEscolhido = (String) JOptionPane.showInputDialog(this, "Qual o tipo de obra que deseja cadastrar?", "Selecione o Tipo", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
      if (tipoEscolhido == null) return; 

      String titulo = JOptionPane.showInputDialog(this, "Título da obra:");
      if (titulo == null || titulo.trim().isEmpty()) return;

      for (Obra o : galeria.listarObras()) {
        if (o.getTitulo().equalsIgnoreCase(titulo)) { 
          JOptionPane.showMessageDialog(this, "A obra '" + titulo + "' já existe no catálogo!", "Aviso", JOptionPane.WARNING_MESSAGE); return; 
        }
      }

      String autor = JOptionPane.showInputDialog(this, "Autor:");
      if (autor == null || autor.trim().isEmpty()) return;

      Obra novaObra = null;
      if (tipoEscolhido.equals("Pintura Digital")) {
        String resolucao = JOptionPane.showInputDialog(this, "Resolução (ex: 4K):"); if (resolucao == null) return;
        String software = JOptionPane.showInputDialog(this, "Software utilizado:"); if (software == null) return;
        novaObra = new PinturaDigital(titulo, autor, resolucao, software);
      } else if (tipoEscolhido.equals("Modelagem 3D")) {
        String poligonosStr = JOptionPane.showInputDialog(this, "Número de Polígonos:"); if (poligonosStr == null) return;
        int poligonos = Integer.parseInt(poligonosStr); 
        String engine = JOptionPane.showInputDialog(this, "Engine (ex: Blender):"); if (engine == null) return;
        novaObra = new Modelagem3D(titulo, autor, poligonos, engine);
      } else if (tipoEscolhido.equals("Arte Generativa")) {
        String algoritmo = JOptionPane.showInputDialog(this, "Algoritmo utilizado:"); if (algoritmo == null) return;
        String seedStr = JOptionPane.showInputDialog(this, "Seed (número inteiro):"); if (seedStr == null) return;
        long seed = Long.parseLong(seedStr);
        novaObra = new ArteGenerativa(titulo, autor, algoritmo, seed);
      }

      if (novaObra != null) {
        galeria.publicarObra(novaObra); 
        JOptionPane.showMessageDialog(this, tipoEscolhido + " cadastrada com sucesso!");
        acaoListarObras();
      }
    } catch (ObraJaCadastradaException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Erro numérico!", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
  }

  public void desativarObra() {
    try {
      String titulo = JOptionPane.showInputDialog(this, "Título da obra a desativar:");
      if (titulo == null || titulo.trim().isEmpty()) return;
      galeria.removerObra(titulo);
      JOptionPane.showMessageDialog(this, "Obra desativada com sucesso.");
      acaoListarObras();
    } catch (ObraNaoEncontradaException | IllegalStateException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
  }

  public void reativarObra() {
    try {
      String titulo = JOptionPane.showInputDialog(this, "Título da obra a reativar:");
      if (titulo == null || titulo.trim().isEmpty()) return;
      galeria.reativarObra(titulo);
      JOptionPane.showMessageDialog(this, "Obra reativada com sucesso.");
      acaoListarObras();
    } catch (ObraNaoEncontradaException | IllegalStateException ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
  }

  public void avaliarObra() {
    try {
      String titulo = JOptionPane.showInputDialog(this, "Título da obra:"); if (titulo == null || titulo.trim().isEmpty()) return;
      String usuario = JOptionPane.showInputDialog(this, "Seu nome:"); if (usuario == null) return;
      String notaStr = JOptionPane.showInputDialog(this, "Nota (0 a 10):"); if (notaStr == null) return;
      int nota = Integer.parseInt(notaStr);
      String comentario = JOptionPane.showInputDialog(this, "Comentário:");
      galeria.avaliarObra(titulo, new Avaliacao(usuario, nota, comentario));
      JOptionPane.showMessageDialog(this, "Feedback guardado!"); acaoListarObras();
    } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
  }

  public void buscarPorAutor() {
    String autor = JOptionPane.showInputDialog(this, "Nome do artista:");
    if (autor != null && !autor.trim().isEmpty()) painelExibicao.exibirObras(galeria.buscarPorAutor(autor), "Obras de: " + autor);
  }

  public void organizarExposicao() {
    String nomeExp = JOptionPane.showInputDialog(this, "Nome da Nova Exposição:"); if (nomeExp == null || nomeExp.trim().isEmpty()) return;
    if (galeria.buscarExposicao(nomeExp) != null) { JOptionPane.showMessageDialog(this, "Nome de exposição já em uso!", "Aviso", JOptionPane.WARNING_MESSAGE); return; }

    Exposicao exposicao = new Exposicao(nomeExp);
    boolean adicionando = true;
    while (adicionando) {
      String tituloObra = JOptionPane.showInputDialog(this, "Obra para adicionar (ou cancele):");
      if (tituloObra == null || tituloObra.trim().isEmpty()) { 
        adicionando = false; 
      } else {
        Obra obraEncontrada = null;
        for (Obra o : galeria.listarObras()) { if (o.getTitulo().equalsIgnoreCase(tituloObra)) { obraEncontrada = o; break; } }
        if (obraEncontrada != null) {
          try {
            exposicao.adicionarObra(obraEncontrada);
            JOptionPane.showMessageDialog(this, "Adicionada!");
          } catch (ObraJaNaExposicaoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(this, "Obra inativa/inexistente.", "Erro", JOptionPane.WARNING_MESSAGE);
        }
      }
    }
    try { 
      galeria.criarExposicao(exposicao); 
      JOptionPane.showMessageDialog(this, "Exposição criada!"); 
      painelExibicao.exibirObras(galeria.obrasExpostas(nomeExp), "Exposição: " + nomeExp);
    } catch (ExposicaoJaCadastradaException ex) { 
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); 
    }
  }

  public void visualizarExposicoesAtivas() {
    Vector<Exposicao> exposicoes = galeria.listarExposicoes();
    if (exposicoes.isEmpty()) { JOptionPane.showMessageDialog(this, "Sem exposições ativas.", "Aviso", JOptionPane.INFORMATION_MESSAGE); return; }
    String[] nomes = new String[exposicoes.size()]; for (int i = 0; i < exposicoes.size(); i++) nomes[i] = exposicoes.get(i).getNome();
    String exp = (String) JOptionPane.showInputDialog(this, "Visitar exposição:", "Exposições", JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
    if (exp != null) painelExibicao.exibirObras(galeria.obrasExpostas(exp), "Exposição: " + exp);
  }

  public void editarExposicao() {
    Vector<Exposicao> exposicoes = galeria.listarExposicoes();
    if (exposicoes.isEmpty()) { JOptionPane.showMessageDialog(this, "Nenhuma exposição ativa.", "Aviso", JOptionPane.INFORMATION_MESSAGE); return; }
    String[] nomes = new String[exposicoes.size()]; for (int i = 0; i < exposicoes.size(); i++) nomes[i] = exposicoes.get(i).getNome();
    String nomeExp = (String) JOptionPane.showInputDialog(this, "Editar exposição:", "Editar", JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
    if (nomeExp == null) return;
    Exposicao expAlvo = galeria.buscarExposicao(nomeExp); if (expAlvo == null) return;

    String[] acoes = {"Adicionar Nova Obra", "Remover Obra Existente"};
    String acao = (String) JOptionPane.showInputDialog(this, "O que fazer?", "Ação", JOptionPane.QUESTION_MESSAGE, null, acoes, acoes[0]); if (acao == null) return;

    try {
      if (acao.equals("Adicionar Nova Obra")) {
        String titulo = JOptionPane.showInputDialog(this, "Obra a adicionar:"); if (titulo == null || titulo.trim().isEmpty()) return;
        Obra obra = null; for (Obra o : galeria.listarObras()) { if (o.getTitulo().equalsIgnoreCase(titulo)) { obra = o; break; } }
        if (obra != null) { 
          expAlvo.adicionarObra(obra); 
          galeria.atualizarExposicao(expAlvo); 
          JOptionPane.showMessageDialog(this, "Adicionada!"); 
        } 
      } else if (acao.equals("Remover Obra Existente")) {
        if (expAlvo.listarObras().isEmpty()) return;
        String[] obras = new String[expAlvo.listarObras().size()]; for (int i = 0; i < obras.length; i++) obras[i] = expAlvo.listarObras().get(i).getTitulo();
        String remover = (String) JOptionPane.showInputDialog(this, "Obra a retirar:", "Remover", JOptionPane.QUESTION_MESSAGE, null, obras, obras[0]);
        if (remover != null) {
          expAlvo.removerObra(remover);
          galeria.atualizarExposicao(expAlvo); 
          JOptionPane.showMessageDialog(this, "Removida!");
        }
      }
      painelExibicao.exibirObras(galeria.obrasExpostas(expAlvo.getNome()), "Exposição: " + expAlvo.getNome());
    } catch (ObraJaNaExposicaoException | ObraNaoPertenceAExposicaoException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
    } catch (Exception ex) { 
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); 
    }
  }

  // Estilização
  private void aplicarTemaGlobalNosPopups() {
    UIManager.put("OptionPane.background", COR_MENU); UIManager.put("Panel.background", COR_MENU);
    UIManager.put("OptionPane.messageForeground", COR_TEXTO); UIManager.put("Button.background", COR_BOTAO);
    UIManager.put("Button.foreground", COR_BOTAO_TXT); UIManager.put("Button.font", FONTE_BOTAO);
    UIManager.put("TextField.background", COR_FUNDO); UIManager.put("TextField.foreground", COR_TEXTO);
    UIManager.put("ComboBox.background", COR_FUNDO); UIManager.put("ComboBox.foreground", COR_TEXTO);
    UIManager.put("OptionPane.questionIcon", criarIconePersonalizado("?")); UIManager.put("OptionPane.informationIcon", criarIconePersonalizado("i"));
    UIManager.put("OptionPane.warningIcon", criarIconePersonalizado("!")); UIManager.put("OptionPane.errorIcon", criarIconePersonalizado("X"));
  }

  private ImageIcon criarIconePersonalizado(String simbolo) {
    BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = img.createGraphics(); g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(COR_BOTAO); g2d.fillOval(0, 0, 32, 32); g2d.setColor(COR_BOTAO_TXT); g2d.setFont(new Font("Consolas", Font.BOLD, 22));
    FontMetrics fm = g2d.getFontMetrics(); g2d.drawString(simbolo, (32 - fm.stringWidth(simbolo)) / 2, (32 / 2) + ((fm.getAscent() - fm.getDescent()) / 2)); g2d.dispose();
    return new ImageIcon(img); 
  }
}