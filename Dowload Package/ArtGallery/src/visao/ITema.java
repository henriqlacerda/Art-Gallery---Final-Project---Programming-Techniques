package visao;

import java.awt.Color;
import java.awt.Font;

public interface ITema {
    // Paleta de cores
    Color COR_FUNDO = new Color(18, 18, 18);        
    Color COR_MENU = new Color(25, 25, 25);         
    Color COR_TEXTO = new Color(240, 240, 240);     
    Color COR_BOTAO = new Color(255, 120, 0);       
    Color COR_BOTAO_TXT = new Color(15, 15, 15);    
    Color COR_BORDA = new Color(255, 120, 0);       
    
    // Fontes
    Font FONTE_DADOS = new Font("Consolas", Font.PLAIN, 14); 
    Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);
    Font FONTE_DESTAQUE = new Font("Lucida Handwriting", Font.BOLD, 64); 
}