
import java.awt.*;

public class HealthBar {
    public static void draw(Graphics g, Player player, int x) {
        g.setColor(Color.WHITE);
        g.drawRect(x, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(x, 20, player.getHealth() , 20);
    }
}
