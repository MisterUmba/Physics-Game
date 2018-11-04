import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;


import java.util.Collection;

public class View extends GridPane{
    Canvas canvas;
    GraphicsContext pen;
    private Collection<MovingObj> objs;

    double mouseX, mouseY;
    double angle = 0;


    Image photo = new Image(getClass().
            getResourceAsStream("ship.png"));

    ImageView post;

    MovingObj head;

    public View(Collection<MovingObj> objs){
        mouseX = 0;
        mouseY = 0;

        canvas = new Canvas(600,450);
        pen = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);


        post = new ImageView(new Image("space.png"));
        post.setFitWidth(canvas.getWidth());
        post.setFitHeight(canvas.getHeight());

        clearScreen();

        this.add(post, 0,0);
        this.add(canvas,0,0);
        this.objs = objs;
    }

    void getHead(MovingObj head){this.head = head;}


    public void drawScreen(){
        clearScreen();
        pen.setFill(Color.WHITE);
        pen.drawImage(new Image("crossair.png"),mouseX-8,mouseY-8);
        pen.save();
        angle = getAngle(head.point.x+20,head.point.y+25,mouseX+8,mouseY+8);
        rotate(pen,angle*-1,head.point.x, head.point.y);
        pen.drawImage(photo,head.point.x, head.point.y,20,25);
        pen.restore();

        for(MovingObj e: objs){
            if(!e.equals(head)){
                pen.fillRect(e.point.x - 5, e.point.y - 5,e.width,e.height);
            }
        }

    }

    public void rotate(GraphicsContext pen, double angle, double px, double py){
        Rotate r = new Rotate(angle, px+10, py+12.5);
        pen.setTransform(r.getMxx(), r.getMyx(),r.getMxy(),r.getMyy(),
                r.getTx(),r.getTy());
    }

    public void shot(MovingObj head, double time){
        Bullet bullet = new Bullet(head.point.x+10,head.point.y+12.5, time);
        objs.add(bullet);
        bullet.shot(head, mouseX, mouseY);
    }

    public double getAngle(double x1, double y1, double x2, double y2){
        double temp = Math.toDegrees(Math.atan2(x1-x2,y1-y2));
        if(temp < 0)
            temp += 360;
        return temp;
    }


    private void clearScreen(){
        pen.setFill(Color.BLACK);
        //pen.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
        pen.save();
        pen.setGlobalAlpha(0.75);
        pen.clearRect(0,0, canvas.getWidth(),canvas.getHeight());
        pen.restore();
    }
}
