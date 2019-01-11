package gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import fbHelpers.AssetLoader;
import gameobjects.Bird;
import gameobjects.ScrollHandler;

/**
 * Created by pidho on 17.02.2018.
 */




public class GameWorld {

    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    public int midPointY;

    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    private GameState currentState;

    public GameWorld(int midPointY) {
        bird = new Bird(33, midPointY - 5, 17, 12);
        //трава должна начинаться на 66 писеля ниже midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 137, 11);
        this.midPointY=midPointY;
        currentState = GameState.READY;
    }

    public void update(float delta) {

        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {

    }

    public void updateRunning(float delta) {


        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            currentState = GameState.GAMEOVER;
            scroller.stop();
            bird.die();
            bird.decelerate();
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isReady(){
        if(currentState==GameState.READY) return true;
        else return false;
    }

    public void start(){
        currentState=GameState.RUNNING;
    }

    public boolean isGameOver(){
        if(currentState==GameState.GAMEOVER) return  true;
        else return false;
    }

    public void restart(){
        currentState=GameState.READY;
        score = 0;
        bird.onRestart(midPointY-5);
        scroller.onRestart();

    }

    public Bird getBird() {
        return bird;

    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }
}