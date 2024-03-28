package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener {
    
    private class Quadrado {
        int x;
        int y;
        
        Quadrado(int x, int y) {
            this.x = x;
            this.y = y;
            
        }
    }
    
    int boardWidth;
    int boardHeight;
    int tamanhoQuadrado = 25;
    
    Quadrado snakeHead;
    Quadrado comida;
    Random random;
    
    Timer gameLoop;
    
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        
        snakeHead = new Quadrado(5, 5);
        
        comida = new Quadrado(10, 10);
        
        random = new Random();
        posicionarComida();
        
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw (Graphics g) {
        // desenhando um grid no painel pra ficar mais fácil de visualizar
        for (int i = 0; i < boardWidth/tamanhoQuadrado; i++) {
            g.drawLine(i*tamanhoQuadrado, 0, i*tamanhoQuadrado, boardHeight);
            g.drawLine(0, i*tamanhoQuadrado, boardWidth, i*tamanhoQuadrado);
        }
        
        // Maçã
        g.setColor(Color.red);
        g.fillRect(comida.x * tamanhoQuadrado, comida.y * tamanhoQuadrado, tamanhoQuadrado, tamanhoQuadrado);

        // Cobra
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tamanhoQuadrado, snakeHead.y * tamanhoQuadrado, tamanhoQuadrado, tamanhoQuadrado);
    }
    
    
    // funcao para posicionar a maçã aleatóriamente na tela
    public void posicionarComida () {
        comida.x = random.nextInt(boardWidth/tamanhoQuadrado);
        comida.x = random.nextInt(boardHeight/tamanhoQuadrado);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // vai ficar repetindo a função draw para que o jogo rode
    }
    
}
