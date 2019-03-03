package game.window;

import client.Client;
import game.framework.Game;
import game.MainCanvas;
import game.util.ButtonFunction;
import game.util.Button;
import game.util.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenu extends Menu{
    private int buttonX, buttonY, buttonWidth, buttonHeight;

    private Button but_newGame, but_loadGame, but_quit, but_multiplayer;

    private Font font = new Font ("Times New Roman",Font.PLAIN,20);
    private Color color = new Color (138,43,226,200);

    private LoadMenu loadMenu;

    public MainMenu (MainCanvas mainCanvas, Game game, int x, int y, int width, int height){
        super(mainCanvas, game, x, y, width, height);
        this.mainCanvas = mainCanvas;
        menuColor = new Color(0,0,0,0);
        buttonX = x;
        buttonY = y;
        buttonWidth = width;
        buttonHeight = height / 10;
        display = true;

        but_newGame =     new Button(buttonX, buttonY, buttonWidth, buttonHeight,"New Game",color, font, but_newGame_function);
        but_loadGame =    new Button(buttonX, buttonY + 2*buttonHeight, buttonWidth, buttonHeight, "Load Game", color, font, but_loadGame_function);
        but_multiplayer = new Button(buttonX, buttonY + 4*buttonHeight, buttonWidth, buttonHeight, "Multiplayer", color, font, but_multiplayer_function);
        but_quit =        new Button(buttonX, buttonY + 6*buttonHeight, buttonWidth, buttonHeight,"Quit", color, font, but_quit_function);

        buttons.add(but_newGame);
        buttons.add(but_loadGame);
        buttons.add(but_multiplayer);
        buttons.add(but_quit);

        for(int i = 0; i < buttons.size(); i ++){
            buttons.get(i).setNotSelectedColor(color);
        }

        loadMenu = new LoadMenu(this.mainCanvas,this.game,PausedMenu.iM_x,PausedMenu.iM_y,PausedMenu.iMWidth,PausedMenu.iMHeight);

    }

    public void tick(){
        super.tick();
        if (but_loadGame.isActive()){
            loadMenu.tick();
        }
    }

    public void render(Graphics g){
        if (display) {
            super.render(g);
        }
        else if (but_loadGame.isDisplay()) {
            loadMenu.render(g);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == ENTER){
            buttons.get(selectIndex).getButtonFunction().Function();

            //game.setMainMenuAtive(false);
            //game.setMainMenuDisplay(false);
        }
    }

    ButtonFunction but_newGame_function = () ->{
        mainCanvas.startNewGame();
        return true;
    };

    ButtonFunction but_loadGame_function = ()->{

       but_loadGame.setDisplay(true);
       but_loadGame.setActive(true);
       display = false;
       this.loadMenu.updateSLButtons();
        //System.out.println(1);
       mainCanvas.addKeyListener(this.loadMenu);
       mainCanvas.removeKeyListener(this);
       return true;
    };

    ButtonFunction but_multiplayer_function = ()->{
        mainCanvas.startMultiplayer();
        return true;
    };

    ButtonFunction but_quit_function =() -> {
        System.exit(0);
        return true;
    };

    public LoadMenu getLoadMenu() {
        return loadMenu;
    }
}
