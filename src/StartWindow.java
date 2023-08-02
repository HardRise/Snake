import javax.swing.*;

public class StartWindow extends JFrame {
    /**
     * Initializing a window of our game
     */
    public StartWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(368, 392);
        setLocation(352, 376);
        add(new MainBoard());
        setVisible(true);
    }

    public static void main(String[] args) {
        new StartWindow();
    }
}