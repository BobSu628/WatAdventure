package main.window;

import main.MainCanvas;

import javax.swing.*;
import java.awt.*;

public class Window {

    JFrame frame;
    Canvas currentCanvas;

    public Window(int w, int h, String title, MainCanvas mainCanvas){
        this.currentCanvas = mainCanvas;
        mainCanvas.setPreferredSize(new Dimension(w, h));
        mainCanvas.setMaximumSize(new Dimension(w, h));
        mainCanvas.setMinimumSize(new Dimension(w, h));

        frame = new JFrame(title);
        frame.add(mainCanvas);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainCanvas.start();
    }

}
