package prototipo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongGame2 extends JPanel implements ActionListener, KeyListener {
    private int ballX, ballY, ballVelX, ballVelY;
    private int paddle1Y, paddleVel; 
    private Timer timer;
    private int vida = 3;
    
    private void resetGame() {
        ballX = 400;
        ballY = 300;
        ballVelX = 5;
        ballVelY = 5;

        paddle1Y = 250;
        paddleVel = 0;

        // Reiniciar el temporizador
        timer.start();
    }

    public PongGame2() {
    	// Configuración del panel
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

     // Inicialización de variables
        ballX = 400;
        ballY = 300;
        ballVelX = 5;
        ballVelY = 5;
        paddleVel = 0;

     // Configuración del temporizador
        timer = new Timer(10, this);
        JOptionPane.showMessageDialog(this, "Tenes "+vida+" vidas");
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

     // Dibujamos la pelota y las paletas
        g.setColor(Color.WHITE);
        g.fillRect(ballX - 10, ballY - 10, 20, 20);
        g.fillRect(50, paddle1Y - 40, 20, 80);
    }

    public void actionPerformed(ActionEvent e) {
    	 // Actualizamos la posición de la pelota
        ballX += ballVelX;
        ballY += ballVelY;

        if (ballX <= 60 && ballY >= paddle1Y - 40 && ballY <= paddle1Y + 40) {
            ballVelX = -ballVelX;
        }

        // Verificar colisión con los bordes izquierdo y derecho
        if (ballX <= 0 || ballX >= getWidth()) {
            ballVelX = -ballVelX;
        }

        // Asegurar que la pelota no salga por arriba o abajo
        if (ballY <= 10) {
            ballY = 10;
            ballVelY = -ballVelY;
        }
        if (ballY >= getHeight() - 10) {
            ballY = getHeight() - 10;
            ballVelY = -ballVelY;
        }

        paddle1Y += paddleVel;
        if (paddle1Y < 40) {
            paddle1Y = 40;
        }
        if (paddle1Y > 560) {
            paddle1Y = 560;
        }


        // Verificar si el juego ha terminado
        if (ballX <= 0 ) {
        	
        	vida--;
            // La pelota ha salido del panel, terminar el juego
            timer.stop();
            if(vida == 0)
            {
            	JOptionPane.showMessageDialog(this, "PERDISTE");
            	System.exit(0);
            }
            JOptionPane.showMessageDialog(this, "Perdiste una vida. Te quedan "+vida);
            resetGame();
            
        }
        
        repaint();
    }

    //poder presionar las teclas 
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            paddleVel = -5;
        }
        if (key == KeyEvent.VK_S) {
            paddleVel = 5;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            paddleVel = 0;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame2 game = new PongGame2();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}