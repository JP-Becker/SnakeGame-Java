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
    
    // Cobra
    Quadrado snakeHead;
    ArrayList<Quadrado> snakeBody; // ArrayList pra guardar as partes do corpo da cobra
    
    // Maçã
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
        snakeBody = new ArrayList<Quadrado>();
        
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
        
        for (int i = 0;i < snakeBody.size(); i++){
            Quadrado snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tamanhoQuadrado, snakePart.y * tamanhoQuadrado, tamanhoQuadrado, tamanhoQuadrado);
        }
    }
    
    
    // funcao para posicionar a maçã aleatóriamente na tela
    public void posicionarComida () {
        comida.x = random.nextInt(boardWidth/tamanhoQuadrado);
        comida.y = random.nextInt(boardHeight/tamanhoQuadrado);
    }
    
    public boolean colisao (Quadrado q1, Quadrado q2) {
        return q1.x == q2.x && q1.y == q2.y;
    }
    
    public void move() {
        
        // if para a cobra "comer" a maçã
        if (colisao(snakeHead, comida)) {
            snakeBody.add(new Quadrado(comida.x, comida.y));
            posicionarComida();
        }
        
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
        if (e.getKeyCode() == KeyEvent.VK_UP && velocidadeY != 1) {
            velocidadeX = 0;
            velocidadeY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadeY != -1) {
            velocidadeX = 0;
            velocidadeY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadeX != 1) {
            velocidadeX = -1;
            velocidadeY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadeX != -1) {
            velocidadeX = 1;
            velocidadeY = 0;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
