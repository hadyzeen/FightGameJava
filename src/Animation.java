
import java.awt.Image;
import java.util.*;

public class Animation {
    private ArrayList<Image> frames;
    private int currentFrame;
    private long lastFrameTime;
    private long frameDuration;

    public Animation(ArrayList<Image> frames, long frameDuration) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.currentFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frames.size();
            lastFrameTime = now;
        }
    }

    public Image getCurrentFrame() {
        return frames.get(currentFrame);
    }

    public void reset() {
        currentFrame = 0;
    }
}
