package game.framework;

import game.MainCanvas;
import game.entities.Player;
import game.window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
<<<<<<< HEAD
TODO fix movement switching bug
*/

public class KeyInput extends KeyAdapter {

    /*
    public static int UP = KeyEvent.VK_W;
    public static int DOWN = KeyEvent.VK_S;
    public static int LEFT = KeyEvent.VK_A;
    public static int RIGHT = KeyEvent.VK_D;
    public static int ATTACK = KeyEvent.VK_J;
    public static int JUMP = KeyEvent.VK_SPACE;
    public static int COMMANDLINE_ON = KeyEvent.VK_SLASH;
    public static int MENU = KeyEvent.VK_P;
    public static int MENU = KeyEvent.VK_BACK_SPACE;
    */
    public static int UP = KeyEvent.VK_W;
    public static int DOWN = KeyEvent.VK_S;
    public static int LEFTWALK = KeyEvent.VK_A;
    public static int LEFTRUN = KeyEvent.VK_C;
    public static int RIGHTWALK = KeyEvent.VK_D;
    public static int RIGHTRUN = KeyEvent.VK_B;
    public static int ATTACK = KeyEvent.VK_K;
    public static int JUMP = KeyEvent.VK_J;
    public static int USE_ITEM = KeyEvent.VK_U;
    public static int COMMANDLINE_ON = KeyEvent.VK_SLASH;
    public static int MENU = KeyEvent.VK_P ;
    public static int SWITCHATTACK = KeyEvent.VK_I;
    public static int SAVE = KeyEvent.VK_F5;

    private transient MainCanvas mainCanvas;
    private Game game;
    private Handler handler;

    //private boolean canStop = true; //whether keyInput can be disabled

    public KeyInput(MainCanvas mainCanvas, Game game, Handler handler)
    {
        this.mainCanvas = mainCanvas;
        this.game = game;
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e){
        //canStop = false;

        int key = e.getKeyCode();
        Player player = handler.player;

        if(key == RIGHTWALK || key == RIGHTRUN){
            if(!player.isInKnockBack() && !player.isAttacking()) {
                if(key == RIGHTWALK) handler.setRightWalk(true);
                else if(key == RIGHTRUN) handler.setRightRun(true);
            }
        }
        if(key == LEFTWALK || key == LEFTRUN){
            if(!player.isInKnockBack() && !player.isAttacking()) {
                if(key == LEFTWALK) handler.setLeftWalk(true);
                else if(key == LEFTRUN) handler.setLeftRun(true);
            }
        }
        if(key == JUMP) {
            player.jump();
        }
        if(key == ATTACK && !player.isAttacking() && !player.isInKnockBack() && player.isCanAttack()){
            player.attack();
            player.setCanAttack(false);
            if(player.getAttackState() == Player.AttackState.Ranged){
                player.setRangeAttacking(true);
            }
        }
        if(key == UP && player.isCanClimb()){
            player.setClimbing(true);
        }
        /*

        if(key == KeyEvent.VK_ESCAPE){
            System.exit(1);
        }*/
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        Player player = handler.player;

        if(key == RIGHTWALK || key == RIGHTRUN) {
            if(!player.isInKnockBack()) {
                if(key == RIGHTRUN) handler.setRightRun(false);
                else if(key == RIGHTWALK) handler.setRightWalk(false);
            }
        }
        if(key == LEFTWALK || key == LEFTRUN) {
            if(!player.isInKnockBack()){
                if(key == LEFTRUN) handler.setLeftRun(false);
                else if(key == LEFTWALK) handler.setLeftWalk(false);
            }
        }
        if(key == ATTACK){
            player.setCanAttack(true);
            if(player.getAttackState() == Player.AttackState.Ranged){
                player.setRangeAttacking(false);
            }
        }
        if(key == USE_ITEM){
            player.useItem();
        }
        if(key == SWITCHATTACK){
            player.switchAttackState();
        }
        if(key == COMMANDLINE_ON){
            suspendControlledMovement();

            game.getCommandLine().startCommand();
            game.getCommandLine().setActive(true);
            switchKeyListener(game.getCommandLine());//disable keyInput
            return;
        }
        if (key == MENU){
            suspendControlledMovement();

            //Game.paused = true;
            game.getPausedMenu().setActive(true);
            game.getPausedMenu().setDisplay(true);
            switchKeyListener(game.getPausedMenu());
            game.getPausedMenu().setSelectIndex(0);
            return;
        }
        if(key == UP && player.isClimbing()){
            player.setClimbing(false);
        }

    }

    private void switchKeyListener(KeyListener listener){
        mainCanvas.addKeyListener(listener);
        mainCanvas.removeKeyListener(this);
    }

    private void suspendControlledMovement(){
        handler.setLeftWalk(false);
        handler.setRightWalk(false);
        handler.setLeftRun(false);
        handler.setRightRun(false);
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
