package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    
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
    
    int velocidadeX;
    int velocidadeY;
    
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snakeHead = new Quadrado(5, 5);
        
        comida = new Quadrado(10, 10);
        
        random = new Random();
        posicionarComida();
        
        velocidadeX = 0;
        velocidadeY = 0;
        
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
        comida.y = random.nextInt(boardHeight/tamanhoQuadrado);
    }
    
    public void move() {
        snakeHead.x += velocidadeX;
        snakeHead.y += velocidadeY;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // vai ficar repetindo a função draw para que o jogo rode
    }
    
    @Override
    public void keyPressed (KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                velocidadeX = 0;
                velocidadeY = -1;
                break;
            case KeyEvent.VK_DOWN:
                velocidadeX = 0;
                velocidadeY = 1;
                break;
            case KeyEvent.VK_LEFT:
                velocidadeX = -1;
                velocidadeY = 0;
                break;
            case KeyEvent.VK_RIGHT:
                velocidadeX = 1;
                velocidadeY = 0;
                break;
            default:
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
