package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import modelo.Obra;

public class PainelExibicao extends JPanel implements ITema {
  private static final long serialVersionUID = 1L;
  private JTextPane areaTexto;
  private TitledBorder bordaPainel;
  private JScrollPane scrollPane;

  // Aq é a parte onde aparecem as informações.
  // É o meio da janela

  // Geral
  public PainelExibicao() {
    setLayout(new BorderLayout());
    setBackground(COR_FUNDO);
    setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 15));

    areaTexto = new JTextPane(); 
    areaTexto.setEditable(false);
    areaTexto.setBackground(COR_FUNDO);
    areaTexto.setForeground(COR_TEXTO);
    areaTexto.setCaretColor(COR_TEXTO); 
  
    bordaPainel = BorderFactory.createTitledBorder(
      BorderFactory.createLineBorder(COR_BORDA, 2), 
      " PÁGINA INICIAL ", 0, 0, FONTE_BOTAO, COR_BORDA
    );

    scrollPane = new JScrollPane(areaTexto);
    scrollPane.setBorder(bordaPainel); 
    scrollPane.setBackground(COR_FUNDO); 
    scrollPane.getViewport().setBackground(COR_FUNDO);
        
    injetarOperadorDeRolagem(scrollPane);
    add(scrollPane, BorderLayout.CENTER);
  }

  // Scroll da tela
  private void injetarOperadorDeRolagem(JScrollPane scroll) {
    scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      protected void configureScrollBarColors() { this.thumbColor = COR_BOTAO; this.trackColor = COR_FUNDO; }
      protected JButton createDecreaseButton(int orientation) { return criarBotaoInvisivel(); }
      protected JButton createIncreaseButton(int orientation) { return criarBotaoInvisivel(); }
    });
    scroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
      protected void configureScrollBarColors() { this.thumbColor = COR_BOTAO; this.trackColor = COR_FUNDO; }
      protected JButton createDecreaseButton(int orientation) { return criarBotaoInvisivel(); }
      protected JButton createIncreaseButton(int orientation) { return criarBotaoInvisivel(); }
    });
  }

  private JButton criarBotaoInvisivel() {
    JButton btn = new JButton();
    btn.setPreferredSize(new Dimension(0, 0));
    return btn;
  }

  // Boas vindas do início
  public void mostrarBoasVindas() {
    bordaPainel.setTitle(" PÁGINA INICIAL ");
    scrollPane.repaint();
    areaTexto.setText(""); 
        
    StyledDocument doc = areaTexto.getStyledDocument();
    SimpleAttributeSet estiloTitulo = new SimpleAttributeSet();
    StyleConstants.setFontFamily(estiloTitulo, FONTE_DESTAQUE.getFamily()); 
    StyleConstants.setFontSize(estiloTitulo, FONTE_DESTAQUE.getSize());                     
    StyleConstants.setBold(estiloTitulo, true);
    StyleConstants.setForeground(estiloTitulo, COR_BOTAO);           

    SimpleAttributeSet estiloInstrucoes = new SimpleAttributeSet();
    StyleConstants.setFontFamily(estiloInstrucoes, FONTE_DADOS.getFamily());       
    StyleConstants.setFontSize(estiloInstrucoes, FONTE_DADOS.getSize());                 
    StyleConstants.setForeground(estiloInstrucoes, COR_TEXTO);       

    try {
      doc.insertString(doc.getLength(), "\n\n\n\n\n\n", estiloInstrucoes); 
      doc.insertString(doc.getLength(), "Art Gallery\n", estiloTitulo);
      doc.insertString(doc.getLength(), "\n", estiloInstrucoes); 
      doc.insertString(doc.getLength(), "Utilize o menu lateral esquerdo para navegar pelo sistema.\n", estiloInstrucoes);
      doc.insertString(doc.getLength(), "Todos os dados são salvos automaticamente no disco.\n", estiloInstrucoes);
      SimpleAttributeSet centro = new SimpleAttributeSet();
      StyleConstants.setAlignment(centro, StyleConstants.ALIGN_CENTER);
      doc.setParagraphAttributes(0, doc.getLength(), centro, false);
    } catch (BadLocationException e) { e.printStackTrace(); }
  }

  // O que aparecem na listagem de todas as obras
  public void exibirObras(Vector<Obra> obras, String tituloSessao) {
    bordaPainel.setTitle(" " + tituloSessao.toUpperCase() + " ");
    scrollPane.repaint();
    areaTexto.setText(""); 
        
    StyledDocument doc = areaTexto.getStyledDocument();
    SimpleAttributeSet estiloCatalogo = new SimpleAttributeSet();
    StyleConstants.setFontFamily(estiloCatalogo, FONTE_DADOS.getFamily());
    StyleConstants.setFontSize(estiloCatalogo, FONTE_DADOS.getSize());
    StyleConstants.setForeground(estiloCatalogo, COR_TEXTO);

    StringBuilder sb = new StringBuilder();
    if (obras.isEmpty()) {
        sb.append("\nNenhuma obra encontrada para esta consulta.\n");
    } else {
      sb.append("\n.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.\n\n"); 
      for (Obra o : obras) {
        sb.append(o.exibirDetalhes()).append("\n");
        sb.append(String.format("Média de Avaliações: %.2f\n", o.mediaAvaliacoes()));
        sb.append("\n.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.\n\n");
      }
    }
        
    try {
      doc.insertString(0, sb.toString(), estiloCatalogo);
      SimpleAttributeSet esquerda = new SimpleAttributeSet();
      StyleConstants.setAlignment(esquerda, StyleConstants.ALIGN_LEFT);
      doc.setParagraphAttributes(0, doc.getLength(), esquerda, false);
    } catch (BadLocationException e) { areaTexto.setText(sb.toString()); }
      areaTexto.setCaretPosition(0); 
    }
}