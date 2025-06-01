import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.awt.event.KeyEvent;

public class Player {
    int x, y, speed = 6, health = 200;
    boolean moveLeft, moveRight, attacking;
    boolean jumping = false;
    int jumpFrame = 0;
    int[] jumpY = {-40, -35, -30, -20, -10, 0, 10, 20, 30, 35, 40};

    String action = "idle";
    String name;
    Color labelColor;
    boolean flip = false;
    boolean isPlayerOne;

    Animation idleAnim, punchAnim, kickAnim, jumpAnim, currentAnim;

    public Player(String basePath, int x, int y, String name, Color labelColor, boolean isPlayerOne) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.labelColor = labelColor;
        this.isPlayerOne = isPlayerOne;

        if (basePath.toLowerCase().contains("girl")) flip = true;

        idleAnim = loadAnimation(basePath + "/idle", 120);
        punchAnim = loadAnimation(basePath + "/punch", 100);
        kickAnim = loadAnimation(basePath + "/kick", 100);
        jumpAnim = loadAnimation(basePath + "/jump", 80);

        currentAnim = idleAnim;
    }

    private Animation loadAnimation(String folder, int frameTime) {
        File dir = new File(folder);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));

        if (files == null || files.length == 0) {
            System.out.println("⚠️ No frames in " + folder);
            return new Animation(new ArrayList<>(), frameTime);
        }

        Arrays.sort(files, Comparator.comparingInt(f -> {
            String n = f.getName().replaceAll("\\D+", "");
            return n.isEmpty() ? 0 : Integer.parseInt(n);
        }));

        ArrayList<Image> frames = new ArrayList<>();
        for (File file : files) {
            Image img = new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            frames.add(img);
        }

        return new Animation(frames, frameTime);
    }

    public void update() {
        if (jumping) {
            if (jumpFrame < jumpY.length) {
                y = 300 + jumpY[jumpFrame++];
            } else {
                jumping = false;
                y = 300;
                jumpFrame = 0;
            }
            currentAnim = jumpAnim;
        } else if (action.equals("punch")) {
            currentAnim = punchAnim;
        } else if (action.equals("kick")) {
            currentAnim = kickAnim;
        } else {
            currentAnim = idleAnim;
        }

        if (moveLeft) x -= speed;
        if (moveRight) x += speed;
        currentAnim.update();
        x = Math.max(0, Math.min(x, 820));
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void draw(Graphics g) {
        Image frame = currentAnim.getCurrentFrame();
        if (frame != null) {
            if (flip) drawFlippedImage(g, frame, x, y, 80, 120);
            else g.drawImage(frame, x, y, null);
        }

        g.setColor(labelColor);
        g.drawString(name, x + 10, y - 10);
    }

    private void drawFlippedImage(Graphics g, Image img, int x, int y, int w, int h) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, x + w, y, -w, h, null);
    }

    public void punch(Player opponent) {
        if (!attacking && Math.abs(this.x - opponent.x) < 100) opponent.health -= 10;
        action = "punch";
        attacking = true;
        punchAnim.reset();
    }

    public void kick(Player opponent) {
        if (!attacking && Math.abs(this.x - opponent.x) < 100) opponent.health -= 15;
        action = "kick";
        attacking = true;
        kickAnim.reset();
    }

    public void stopAttack() {
        attacking = false;
        action = "idle";
    }

    public int getHealth() {
        return health;
    }

    public void pressKey(int code) {
        if (isPlayerOne) {
            if (code == KeyEvent.VK_A) moveLeft = true;
            if (code == KeyEvent.VK_D) moveRight = true;
            if (code == KeyEvent.VK_F) punch(GameKeyHandler.p2);
            if (code == KeyEvent.VK_S) kick(GameKeyHandler.p2);
            if (code == KeyEvent.VK_W && !jumping) {
                jumping = true;
                jumpFrame = 0;
                jumpAnim.reset();
            }
        } else {
            if (code == KeyEvent.VK_LEFT) moveLeft = true;
            if (code == KeyEvent.VK_RIGHT) moveRight = true;
            if (code == KeyEvent.VK_L) punch(GameKeyHandler.p1);
            if (code == KeyEvent.VK_DOWN) kick(GameKeyHandler.p1);
            if (code == KeyEvent.VK_UP && !jumping) {
                jumping = true;
                jumpFrame = 0;
                jumpAnim.reset();
            }
        }
    }

    public void releaseKey(int code) {
        if (isPlayerOne) {
            if (code == KeyEvent.VK_A) moveLeft = false;
            if (code == KeyEvent.VK_D) moveRight = false;
            if (code == KeyEvent.VK_F || code == KeyEvent.VK_S) stopAttack();
        } else {
            if (code == KeyEvent.VK_LEFT) moveLeft = false;
            if (code == KeyEvent.VK_RIGHT) moveRight = false;
            if (code == KeyEvent.VK_L || code == KeyEvent.VK_DOWN) stopAttack();
        }
    }
}
