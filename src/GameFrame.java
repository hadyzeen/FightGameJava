import javax.swing.*;
public class GameFrame extends JFrame {
    private String player1Name;
    private String player2Name;

    public GameFrame() {
        setTitle("2 Player Fighting Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(900, 500);
        setLocationRelativeTo(null);

        showCharacterSelect();

        setVisible(true);
    }

    public void setPlayerNames(String name1, String name2) {
        this.player1Name = name1;
        this.player2Name = name2;
    }

    public void showCharacterSelect() {
        getContentPane().removeAll();
        add(new CharacterSelectPanel(this));
        revalidate();
        repaint();
    }

    public void startGame(String p1Path, String p2Path, String name1, String name2) {
        setPlayerNames(name1, name2);  // store names here!

        getContentPane().removeAll();
        GamePanel panel = new GamePanel(p1Path, p2Path, name1, name2);
        add(panel);
        revalidate();
        repaint();

        SwingUtilities.invokeLater(() -> panel.requestFocusInWindow());
    }
}
