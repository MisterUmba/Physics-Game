import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Random;

public class Control extends Application {
    SkiplistList<MovingObj> objs;
    View view;
    MovingObj head;

    Timeline time;
    double frames;

    Random rand;
    boolean moves[];

    Game game;

    public void start(Stage prime){
        frames = 1000/100;
        objs = new SkiplistList<>();
        view = new View(objs);
        rand = new Random();
        moves =new boolean[] {false,false,false,false};
        game = new Game(view, this);


        userInput();
        fillStage();


        // Setting up the timer
        time = new Timeline(new KeyFrame(Duration.millis(frames), event -> {
            game();
            view.drawScreen();
        }));
        time.setCycleCount(-1);
        time.play();

        view.getHead(head);

        Scene scene = new Scene(view);
        scene.setFill(Color.GRAY);
        scene.setCursor(Cursor.NONE);


        prime.setResizable(false);
        prime.setTitle("Space War");
        prime.setScene(scene);
        prime.show();
    }

    private void userInput(){
        // For pressing down
        view.canvas.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.W))
                moves[0] = true;
            else if(event.getCode().equals(KeyCode.S))
                moves[1] = true;
            else if(event.getCode().equals(KeyCode.D))
                moves[2] = true;
            else if(event.getCode().equals(KeyCode.A))
                moves[3] = true;
            else if(event.getCode().equals(KeyCode.SPACE)){
                head.yMovement.delta = 0;
                head.xMovement.delta = 0;
            }else if(event.getCode().equals(KeyCode.P)){
                if(time.getStatus().toString().equals("RUNNING"))
                    time.pause();
                else
                    time.play();
            }
        });

        view.canvas.setOnKeyReleased(event -> {
            if(event.getCode().equals(KeyCode.W))
                moves[0] = false;
            else if(event.getCode().equals(KeyCode.S))
                moves[1] = false;
            else if(event.getCode().equals(KeyCode.D))
                moves[2] = false;
            else if(event.getCode().equals(KeyCode.A))
                moves[3] = false;
        });

        view.canvas.setOnMouseMoved(event -> {
            view.mouseX = event.getX();
            view.mouseY = event.getY();
        });

        view.canvas.setOnMouseClicked(event -> {
            view.shot(head, frames);
        });

        view.canvas.setOnMouseDragged(event -> {
            view.mouseX = event.getX();
            view.mouseY = event.getY();
        });


    }

    private void game(){

        if(head.point.x>view.canvas.getWidth())
            head.point.x -= view.canvas.getWidth();
        if(head.point.x<0)
            head.point.x += view.canvas.getWidth();
        if(head.point.y>view.canvas.getHeight())
            head.point.y -= view.canvas.getHeight();
        if(head.point.y<0)
            head.point.y += view.canvas.getHeight();

        for(MovingObj e: objs){
            e.move();
            if(e instanceof Bullet){
                if(e.point.x > view.canvas.getWidth() || e.point.x < 0){
                    objs.remove(e);
                }

                if(e.point.y > view.canvas.getHeight() || e.point.y < 0){
                    objs.remove(e);
                }

                collision(e);
            }
            if( e instanceof Enemies){
                if(((Enemies) e).health<= 0){
                    objs.remove(e);

                    Enemies temp = new Enemies(rand.nextInt((int)view.canvas.getWidth()-100)+100,
                            rand.nextInt((int)view.canvas.getHeight()-100) +100,frames,1);
                    temp.getHead(head);
                    objs.add(temp);
                }

                if(e.intercept(head)) gameOver();
            }
        }

        if(moves[0])head.moveUp();
        if(moves[1])head.moveDown();
        if(moves[2])head.moveRight();
        if(moves[3])head.moveLeft();

    }

    private void gameOver() {
        Label label = new Label("   Game Over!");
        label.setTextFill(Color.RED);
        label.setFont(Font.font("Time",75));
        label.setPrefSize(view.canvas.getWidth(),100);

        view.add(label, 0,0);
        time.pause();


        newGame();
    }

    private void newGame() {
        Button quit = new Button("Quit");
        Button restart = new Button("New Game");

        view.add(quit, 0,0);
        view.add(restart, 1,0);
    }

    private boolean collision(MovingObj e) {
        for(MovingObj k : objs){
            if(k instanceof Enemies && sameQuadrant(e, k)){
                if(e.intercept(k)){
                    ((Enemies) k).health --;
                    objs.remove(e);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean sameQuadrant(MovingObj e, MovingObj k) {
        double half_width = view.canvas.getWidth()/2;
        double half_height = view.canvas.getHeight()/2;

        if(e.point.x < half_width && k.point.x < half_width)
            if(e.point.y < half_height && k.point.y < half_height)
                return true;
        if(e.point.x >= half_width && k.point.x >= half_width)
            if(e.point.y < half_height && k.point.y < half_height)
                return true;
        if(e.point.x < half_width && k.point.x < half_width)
            if(e.point.y >= half_height && k.point.y >= half_height)
                return true;
        if(e.point.x >= half_width && k.point.x >= half_width)
            if(e.point.y >= half_height && k.point.y >= half_height)
                return true;
        return false;
    }


    private void fillStage(){
            head = new MovingObj(rand.nextInt((int)view.canvas.getWidth()),
                    rand.nextInt((int)view.canvas.getHeight()),frames);
            objs.add(head);

            Enemies temp = new Enemies(rand.nextInt((int)view.canvas.getWidth()),
                    rand.nextInt((int)view.canvas.getHeight()),frames);
            objs.add(temp);
            temp.getHead(head);
    }

    public static void main(String[] args) {launch(args);}
}
