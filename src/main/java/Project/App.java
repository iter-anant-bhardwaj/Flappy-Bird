package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class App extends JPanel implements ActionListener, KeyListener {

    // Window
    int boardWidth = 400;
    int boardHeight = 600;

    // Bird
    int birdX = 100;
    int birdY = 300;
    int birdWidth = 35;
    int birdHeight = 35;

    int velocityY = 0;
    int gravity = 1;

    // Pipes
    int pipeWidth = 70;
    int pipeGap = 150;
    ArrayList<Rectangle> pipes;

    Timer gameLoop;
    Timer pipeSpawner;

    boolean gameOver = false;

    int score = 0;

    Random random = new Random();

    public App() {

        setPreferredSize(new Dimension(boardWidth, boardHeight));

        setBackground(Color.cyan);

        setFocusable(true);

        addKeyListener(this);

        pipes = new ArrayList<>();

        // Game loop
        gameLoop = new Timer(20, this);
        gameLoop.start();

        // Pipe generator
        pipeSpawner = new Timer(2000, e -> addPipe());
        pipeSpawner.start();
    }

    public void addPipe() {

        int pipeHeight = random.nextInt(300) + 50;

        // Top Pipe
        Rectangle topPipe = new Rectangle(
                boardWidth,
                0,
                pipeWidth,
                pipeHeight
        );

        // Bottom Pipe
        Rectangle bottomPipe = new Rectangle(
                boardWidth,
                pipeHeight + pipeGap,
                pipeWidth,
                boardHeight - pipeHeight - pipeGap
        );

        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g) {

        // Bird
        g.setColor(Color.yellow);
        g.fillOval(birdX, birdY, birdWidth, birdHeight);

        // Pipes
        g.setColor(Color.green);

        for (Rectangle pipe : pipes) {

            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        // Score
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        g.drawString("Score: " + score, 20, 40);

        // Game Over
        if (gameOver) {

            g.setFont(new Font("Arial", Font.BOLD, 40));

            g.drawString("Game Over", 80, 300);

            g.setFont(new Font("Arial", Font.BOLD, 20));

            g.drawString("Press SPACE to Restart", 90, 350);
        }
    }

    public void move() {

        // Bird movement
        velocityY += gravity;

        birdY += velocityY;

        // Pipes movement
        for (Rectangle pipe : pipes) {

            pipe.x -= 5;
        }

        // Collision
        Rectangle bird = new Rectangle(birdX, birdY, birdWidth, birdHeight);

        for (Rectangle pipe : pipes) {

            if (bird.intersects(pipe)) {

                gameOver = true;
            }
        }

        // Ground / sky collision
        if (birdY > boardHeight || birdY < 0) {

            gameOver = true;
        }

        // Score
        for (Rectangle pipe : pipes) {

            if (pipe.x + pipe.width == birdX) {

                score++;
            }
        }

        if (gameOver) {

            gameLoop.stop();

            pipeSpawner.stop();
        }
    }

    public void restartGame() {

        birdY = 300;

        velocityY = 0;

        pipes.clear();

        score = 0;

        gameOver = false;

        gameLoop.start();

        pipeSpawner.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        move();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (gameOver) {

                restartGame();
            } else {

                velocityY = -12;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {

        JFrame frame = new JFrame("Flappy Bird");

        App game = new App();

        frame.add(game);

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}