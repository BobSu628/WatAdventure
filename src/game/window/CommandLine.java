package game.window;

import game.framework.Game;
import game.MainCanvas;
import game.framework.Command;
import game.framework.CommandArchive;
import game.framework.CommandHandler;
import game.framework.KeyInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CommandLine implements KeyListener {

    public static int COMMANDLINE_OFF = KeyEvent.VK_ESCAPE;
    public static int DELETE = KeyEvent.VK_BACK_SPACE;
    public static int SUBMIT = KeyEvent.VK_ENTER;

    private MainCanvas mainCanvas;
    private Game game;
    private CommandArchive archive = new CommandArchive();
    private CommandHandler commandHandler;
    private boolean active = false;
    private Command command;

    private String rawCommand = "", feedback = "";
    private int index = 0;

    public CommandLine(MainCanvas mainCanvas, Game game, Handler handler){
        this.mainCanvas = mainCanvas;
        this.game = game;
        this.commandHandler = new CommandHandler(handler);
    }

    public void tick(){

    }

    /*
    * textarea at bottom
    *
    */
    public void render(Graphics g){

        FontMetrics fm = g.getFontMetrics();

        archive.render(g, fm);

        int cursorX = 20 + fm.stringWidth(rawCommand.substring(0, index));
        int cursorY = Game.HEIGHT - 30 + 30/4;
        int timer = 60;
        //if(timer < 1)
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRect(0, Game.HEIGHT-30, Game.WIDTH, 30);

        g.setColor(Color.white);
        g.drawString(rawCommand, 20, Game.HEIGHT - 10);

        g.fillRect(cursorX, cursorY, 1, 30-30/2);

    }

    //submit command
    private void submit(){
        String feedback = this.parseCommand();
        
        command = new Command(rawCommand, 0, Game.HEIGHT-Command.HEIGHT*2);
        archive.addCommand(command);
        if(!feedback.equals("")) {
            archive.addCommand(new Command(feedback, 0, Game.HEIGHT - Command.HEIGHT * 2));
        }
        this.clearCommand();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //if(active) {
            int character = e.getKeyCode();
            if (character == COMMANDLINE_OFF) {
                this.clearCommand();
                this.setActive(false);
                Game g = game;
                KeyInput tmp = g.getKeyInput();
                mainCanvas.addKeyListener(tmp);
                mainCanvas.removeKeyListener(this);
                return;

            }else if (character == SUBMIT) {
                this.submit();
            }else if(character == DELETE){
                try{
                    rawCommand = rawCommand.substring(0, index-1) + rawCommand.substring(index);
                    index --;
                }catch (StringIndexOutOfBoundsException exc){

                }
            }else if(character == KeyEvent.VK_LEFT){
                index --;
                if(index < 0) index = 0;

            }else if(character == KeyEvent.VK_RIGHT){
                index ++;
                if(index > rawCommand.length()) index = rawCommand.length();

            }else{
                this.rawCommand = this.rawCommand.substring(0, index) + (char)character + this.rawCommand.substring(index);
                index ++;
            }
        //}
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public void startCommand(){
        this.rawCommand = "/";
        index = 1;
    }

    private void clearCommand(){
        this.rawCommand = "";
        index = 0;
    }

    private String parseCommand(){
        if(this.rawCommand.equals("/QUIT")) System.exit(1);

        feedback = "";
        if(rawCommand.length() > 0 && rawCommand.charAt(0) == '/'){
            String newCommand = rawCommand.substring(1);
            StringTokenizer st = new StringTokenizer(newCommand);
            //String[] tokens = rawCommand.split(" ");
            Queue<String> tokens = new LinkedList<>();
            while(st.hasMoreTokens()){
                tokens.add(st.nextToken());
            }
            feedback = commandHandler.executeCommand(tokens);
        }
        return feedback;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
