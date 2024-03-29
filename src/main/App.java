package main;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        // Declarando tamanho do painel
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");// criando uma instÂncia do Jframe para mostrar o painel na tela
        frame.setVisible(true);// seta como true para aparecer a janela
	frame.setSize(boardWidth, boardHeight);// seta o tamanho da janela
        frame.setLocationRelativeTo(null); // posiciona no centro da tela
        frame.setResizable(false); // nao deixa o usuário muda o tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // faz o programa parar de rodar quando a janela é fechada

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight); // criando instância da classe snakeGame
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
