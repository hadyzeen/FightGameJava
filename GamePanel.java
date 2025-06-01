import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    final int WIDTH = 900;
    final int HEIGHT = 500;
    Timer timer;
    Player p1, p2;
    GameKeyHandler keyHandler;
    boolean gameOver = false;
    String winnerText = "";
    Image backgroundImage;
    JButton startGameButton;
    String p1Folder, p2Folder;
    String name1, name2;

    public GamePanel(String p1Folder, String p2Folder, String name1, String name2) {
        this.p1Folder = p1Folder;
        this.p2Folder = p2Folder;
        this.name1 = name1;
        this.name2 = name2;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.setLayout(null); // for absolute positioning

        backgroundImage = new ImageIcon("assets/background.png").getImage();

        p1 = new Player(p1Folder, 100, 300, name1, Color.RED, true);
        p2 = new Player(p2Folder, 700, 300, name2, Color.BLUE, false);

        keyHandler = new GameKeyHandler(p1, p2, this);
        this.addKeyListener(keyHandler);
        this.requestFocusInWindow();

        timer = new Timer(20, this);
        timer.start();

        startGameButton = new JButton("Play Again");
        startGameButton.setFocusable(false);
        startGameButton.setBounds(375, 300, 150, 40);
        startGameButton.setVisible(false);
        startGameButton.addActionListener(e -> restartGame());
        this.add(startGameButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, (ImageObserver) null);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString(winnerText, WIDTH / 2 - 150, HEIGHT / 2);
            startGameButton.setVisible(true);
        } else {
            startGameButton.setVisible(false);
            p1.draw(g);
            p2.draw(g);
            HealthBar.draw(g, p1, 50);
            HealthBar.draw(g, p2, WIDTH - 250);
        }
    }

    public void checkWinner() {
        if (p1.getHealth() <= 0) {
            winnerText = p2.name + " Wins!";
            gameOver = true;
            timer.stop();
            updateStatsAfterGame(p2.name, p1.name);
        } else if (p2.getHealth() <= 0) {
            winnerText = p1.name + " Wins!";
            gameOver = true;
            timer.stop();
            updateStatsAfterGame(p1.name, p2.name);
        }
    }

    public void updateStatsAfterGame(String winnerName, String loserName) {
        DatabaseManager.updateStats(winnerName, loserName);
    }

    private void restartGame() {
        p1.setHealth(100);
        p2.setHealth(100);
        gameOver = false;
        winnerText = "";
        timer.start();
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            p1.update();
            p2.update();
            checkWinner();
        }
        repaint();
    }
}
