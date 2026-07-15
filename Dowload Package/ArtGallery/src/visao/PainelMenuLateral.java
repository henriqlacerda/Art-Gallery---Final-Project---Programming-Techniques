package visao;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelMenuLateral extends JPanel implements ITema {
  private static final long serialVersionUID = 1L;

  // Aq é o menu lateral, onde ficam os botões e o usuário navega

  public PainelMenuLateral(Janela janela) {
    setLayout(new GridLayout(10, 1, 5, 10)); 
    setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
    setBackground(COR_MENU); 

    adicionarBotao("Início", e -> janela.acaoMostrarBoasVindas());
    adicionarBotao("Listar Todas as Obras", e -> janela.acaoListarObras());
    adicionarBotao("Cadastrar Obra", e -> janela.cadastrarNovaObra());
        
    JPanel painelStatus = new JPanel(new GridLayout(1, 2, 5, 0));
    painelStatus.setBackground(COR_MENU);
    painelStatus.add(criarBotaoEstilizado("Desativar Obra", e -> janela.desativarObra()));
    painelStatus.add(criarBotaoEstilizado("Reativar Obra", e -> janela.reativarObra()));
    add(painelStatus);

    adicionarBotao("Avaliar Obra", e -> janela.avaliarObra());
    adicionarBotao("Top Obras (Populares)", e -> janela.acaoTopObras());
    adicionarBotao("Buscar por Artista", e -> janela.buscarPorAutor());
    adicionarBotao("Organizar Nova Exposição", e -> janela.organizarExposicao());
    adicionarBotao("Visualizar Exposições", e -> janela.visualizarExposicoesAtivas());
    adicionarBotao("Editar Exposição", e -> janela.editarExposicao());
  }

  // Tudo daq p baixo é só p deixar a janela no tema
  private void adicionarBotao(String titulo, ActionListener acao) {
    add(criarBotaoEstilizado(titulo, acao));
  }

  private JButton criarBotaoEstilizado(String titulo, ActionListener acao) {
    JButton btn = new JButton(titulo);
    btn.setBackground(COR_BOTAO);
    btn.setForeground(COR_BOTAO_TXT);
    btn.setFont(FONTE_BOTAO);
    btn.setFocusPainted(false); 
    btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); 
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.addActionListener(acao);
    return btn;
  }
}