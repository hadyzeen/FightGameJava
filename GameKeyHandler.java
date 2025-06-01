import java.awt.event.*;

public class GameKeyHandler extends KeyAdapter {
    static Player p1, p2;
    GamePanel panel;

    public GameKeyHandler(Player p1, Player p2, GamePanel panel) {
        GameKeyHandler.p1 = p1;
        GameKeyHandler.p2 = p2;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Player 1 keys
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_D || code == KeyEvent.VK_W ||
                code == KeyEvent.VK_S || code == KeyEvent.VK_F) {
            p1.pressKey(code);
        }

        // Player 2 keys
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_UP ||
                code == KeyEvent.VK_DOWN || code == KeyEvent.VK_L || code == KeyEvent.VK_K) {
            p2.pressKey(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_D || code == KeyEvent.VK_F || code == KeyEvent.VK_S)
            p1.releaseKey(code);

        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_L || code == KeyEvent.VK_K)
            p2.releaseKey(code);
    }
}
