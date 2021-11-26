import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Fire {
    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(4, this);
    private int passingTime = 0;
    private int spentFire = 0;

    private BufferedImage image;

    private ArrayList<Fire> fires = new ArrayList<Fire>();

    private int fireDirY = 3;
    private int ballX = 0;
    private int ballDirX = 2;

    private int rocketX = 0;
    private int rocketDirX = 20;
    // top ile merminin cakısmasını kotnrol eden method
    public boolean checkIt() {
        for (Fire fire : fires) {
            if (new Rectangle(fire.getX(), fire.getY(), 10, 20).intersects(new Rectangle(ballX, 0, 20, 20))) {
                return true;
            }
        }
        return false;
    }

    public Game() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzayGemi.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.BLACK);
        timer.start();
    }
    // image kısımları
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        passingTime += 5;
        g.setColor(Color.YELLOW);

        g.fillOval(ballX, 0, 20, 20);
        g.drawImage(image, rocketX, 490, image.getWidth() / 10, image.getHeight() / 10, this);

        for (Fire fire : fires) {
            if (fire.getY() < 0) {
                fires.remove(fire);
            }
        }
        g.setColor(Color.blue);

        for (Fire fire : fires) {
            g.fillRect(fire.getX(), fire.getY(), 10, 20);
        }
        if (checkIt()) {
            timer.stop();
            String message = "Tebrikler !! " + passingTime / 1000 + " sn sürede \n" + " Harcanan mermi :" + spentFire;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }

    @Override
    public void repaint() {
        super.repaint();
    }
    // haritada yerleşim sınırlarının kontrolü
    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fire fire : fires) {
            fire.setY(fire.getY() - fireDirY);

        }
        ballX += ballDirX;

        if (ballX > 750) {
            ballDirX = -ballDirX;
        }
        if (ballX <= 0) {
            ballDirX = -ballDirX;
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {


    }
    /// klavye ile kontrol
    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            rocketX = rocketX <= 0 ? (rocketX = 0) : (rocketX -= rocketDirX);

        }
        if (c == KeyEvent.VK_RIGHT) {
            rocketX = rocketX > 680 ? (rocketX = 680) : (rocketX += rocketDirX);

        } else if (c == KeyEvent.VK_CONTROL) {
            fires.add(new Fire(rocketX + 44, 480));

            spentFire++;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
