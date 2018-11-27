package main.framework;

import java.awt.*;

public class Command {

    private String command;
    public static int WIDTH = Game.WIDTH;
    public static int HEIGHT = Game.HEIGHT/32;
    private int x, y;

    public Command(String command, int x, int y){
        this.command = command;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g){
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawString(command, 20, y+HEIGHT*2/3);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
