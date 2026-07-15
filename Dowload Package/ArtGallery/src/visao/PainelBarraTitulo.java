package visao;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelBarraTitulo extends JPanel implements ITema {
  private static final long serialVersionUID = 1L;
  private int mousepX, mousepY;
  private BufferedImage imgQuadradoCache;
  private ImageIcon iconeQuadradoCache;

  public PainelBarraTitulo(JFrame janelaPai) {
    // Borda e Título
    setLayout(new BorderLayout());
    setBackground(COR_MENU); 
    setPreferredSize(new Dimension(850, 35));
    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDA)); 

    JLabel labelTitulo = new JLabel("   ArtGallery - Curadoria e Exposição");
    labelTitulo.setForeground(COR_BOTAO);
    labelTitulo.setFont(FONTE_BOTAO);
    add(labelTitulo, BorderLayout.WEST);

    // Botões de maximizar, minimizar, etc.
    JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    painelBotoes.setBackground(COR_MENU);

    JButton btnMinimizar = criarBotaoJanela("—");
    JButton btnMaximizar = criarBotaoJanela("");
    btnMaximizar.setIcon(criarIconeQuadradoVetorial());
    JButton btnFechar = criarBotaoJanela("X");

    // Interação do mouse com os botões
    btnMinimizar.addActionListener(e -> janelaPai.setExtendedState(JFrame.ICONIFIED));
    btnMaximizar.addActionListener(e -> janelaPai.setExtendedState(janelaPai.getExtendedState() == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH));
    btnFechar.addActionListener(e -> System.exit(0)); 

    btnFechar.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent evt) { btnFechar.setBackground(new Color(200, 40, 40)); btnFechar.setForeground(COR_TEXTO); }
      public void mouseExited(MouseEvent evt) { btnFechar.setBackground(COR_MENU); btnFechar.setForeground(COR_BOTAO); }
    });

    MouseAdapter hoverSuave = new MouseAdapter() {
      public void mouseEntered(MouseEvent evt) { ((JButton)evt.getSource()).setBackground(new Color(45, 45, 45)); }
      public void mouseExited(MouseEvent evt) { ((JButton)evt.getSource()).setBackground(COR_MENU); }
    };
    btnMinimizar.addMouseListener(hoverSuave);
    btnMaximizar.addMouseListener(hoverSuave);

    painelBotoes.add(btnMinimizar);
    painelBotoes.add(btnMaximizar); 
    painelBotoes.add(btnFechar);
    add(painelBotoes, BorderLayout.EAST);

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) { mousepX = e.getX(); mousepY = e.getY(); }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        janelaPai.setLocation(janelaPai.getLocation().x + e.getX() - mousepX, janelaPai.getLocation().y + e.getY() - mousepY);
      }
    });
  }

  private JButton criarBotaoJanela(String texto) {
    JButton btn = new JButton(texto);
    btn.setBackground(COR_MENU);
    btn.setForeground(COR_BOTAO); 
    btn.setFont(FONTE_BOTAO);
    btn.setFocusPainted(false); 
    btn.setPreferredSize(new Dimension(45, 35));
    btn.setBorder(BorderFactory.createEmptyBorder());
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return btn;
  }

  // Cria o quadrado p botão de maximizar ficar bonitin
  private ImageIcon criarIconeQuadradoVetorial() {
    if (iconeQuadradoCache == null) {
      imgQuadradoCache = new BufferedImage(12, 12, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = imgQuadradoCache.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(COR_BOTAO); 
      g2d.setStroke(new BasicStroke(2.0f)); 
      g2d.drawRect(1, 1, 10, 10); 
      g2d.dispose();
      iconeQuadradoCache = new ImageIcon(imgQuadradoCache);
    }
    return iconeQuadradoCache;
  }
}