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
    
    boolean gameOver = false;
    
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
            g.fill3DRect(snakePart.x * tamanhoQuadrado, snakePart.y * tamanhoQuadrado, tamanhoQuadrado, tamanhoQuadrado, true);
        }
        
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("O jogo acabou, você obteve " + String.valueOf(snakeBody.size()) + " pontos", tamanhoQuadrado - 16, tamanhoQuadrado);
        } else {
            g.drawString("Pontuação: " + String.valueOf(snakeBody.size()), tamanhoQuadrado - 16, tamanhoQuadrado);
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
        
        //Loop pra fazer as partes da cobra seguirem a cabeça
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Quadrado snakePart = snakeBody.get(i);
            if (i == 0) {
               snakePart.x = snakeHead.x;
               snakePart.y = snakeHead.y;
            } else {
                Quadrado snakePartAnterior = snakeBody.get(i - 1);
                snakePart.x = snakePartAnterior.x;
                snakePart.y = snakePartAnterior.y;
            }
        }
        
        snakeHead.x += velocidadeX;
        snakeHead.y += velocidadeY;
        
        for (int i = 0; i < snakeBody.size(); i++) {
            Quadrado snakePart = snakeBody.get(i);
            if (colisao(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        
        if (snakeHead.x* tamanhoQuadrado < 0 || snakeHead.x* tamanhoQuadrado >= boardWidth ||
                snakeHead.y* tamanhoQuadrado < 0 || snakeHead.y* tamanhoQuadrado >= boardHeight) {
            gameOver = true;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); // vai ficar repetindo a função draw para que o jogo rode
        
        if (gameOver) {
            gameLoop.stop();
        }
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            snakeHead = new Quadrado(5, 5);
            snakeBody = new ArrayList<Quadrado>();
            velocidadeX = 0;
            velocidadeY = 0;
            gameOver = false;
            gameLoop.start();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
