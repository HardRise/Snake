import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MainBoard extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int PIXEL_SIZE = 16;
    private final int PIXEL_ALL = 400;

    private int[] xSnake = new int[PIXEL_ALL];
    private int[] ySnake = new int[PIXEL_ALL];

    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean left = false;

    private Image pixel;
    private Image fruit;
    private Timer timer;

    private int snake;
    private int xFruit;
    private int yFruit;
    /**
     * Initializing of details of the game to make our snake moving
     * and add module of processing keyboard signals
     */
    public MainBoard() {
        setBackground(Color.white);
        loadImages();
        initGame();
        addKeyListener(new KeyAdapt());
        setFocusable(true);
    }
    /**
     * Images loading fruit and part of snake
     */
    private void loadImages() {
        ImageIcon snakeIm = new ImageIcon("snake.png");
        pixel = snakeIm.getImage();
        ImageIcon fruitIm = new ImageIcon("fruit.png");
        fruit = fruitIm.getImage();

    }
    /**
     * Init of first start of game
     */
    private void initGame() {
        snake = 3;
        for (int i = 0; i < snake; i++) {
            xSnake[i] = 48 - i * PIXEL_SIZE;
            ySnake[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        generateFruit();
    }
    /**
     * Initializing of fruits
     */
    private void generateFruit() {
        xFruit = new Random().nextInt(20) * PIXEL_SIZE;
        yFruit = new Random().nextInt(20) * PIXEL_SIZE;
    }
    /**
     * Images painting and the end of the game
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(fruit, xFruit, yFruit, this);
            for (int i = 0; i < snake; i++) {
                g.drawImage(pixel, xSnake[i], ySnake[i], this);
            }
        } else {
            g.setColor(Color.black);
            g.setFont(new Font("Helvetica", Font.BOLD, 24));
            g.drawString("Game over", 96, SIZE/2/2);
        }
    }
    /**
     * Left, right, up and down movements
     */
    private void move() {
        for (int i = snake; i > 0; i--) {
            xSnake[i] = xSnake[i - 1];
            ySnake[i] = ySnake[i - 1];
        }
        if (left) {
            xSnake[0] -= PIXEL_SIZE;
        }
        if (right) {
            xSnake[0] += PIXEL_SIZE;
        }
        if (down) {
            ySnake[0] += PIXEL_SIZE;
        }
        if (up) {
            ySnake[0] -= PIXEL_SIZE;
        }
    }
    /**
     * Actions of the snake
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            eatFruit();
            checkEdge();
            move();
        }
        repaint();
    }
    /**
     * Check the contact with border
     */
    private void checkEdge() {
        for (int i = snake; i > 0; i--) {
            if (i > 4 && xSnake[0] == xSnake[i] && ySnake[0] == ySnake[i]) {
                inGame = false;
                break;
            }
        }
        moveCrossBorder(xSnake);
        moveCrossBorder(ySnake);
    }
    /**
     * Border crossed movement
     */
    private void moveCrossBorder(int[] snakeP) {
        if (snakeP[0] > SIZE) {
            for (int i = 0; i < snake; i++) {
                snakeP[i] -= SIZE;
            }
        }
        if (snakeP[0] < 0) {
            for (int i = 0; i < snake; i++) {
                snakeP[i] += SIZE;
            }
        }
    }
    /**
     * Snake extension by eating fruit
     */
    private void eatFruit() {
        if (xSnake[0] == xFruit && ySnake[0] == yFruit) {
            snake++;
            generateFruit();
        }
    }
    /**
     * Processing keyboard signals
     */
    public class KeyAdapt extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
