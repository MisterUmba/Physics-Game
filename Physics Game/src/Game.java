import java.util.Random;

/**
 * - Enemies
 * - Levels
 * - Upgrades
 */
public class Game {
    View view;
    Control control;
    Random rand;

    public class Grats extends MovingObj{
        Grats(double x, double y, double time){
            super(x,y,time);
            control.objs.add(this);
        }

        public void move(MovingObj head){
            xMovement.delta = this.point.x - head.point.x;
            yMovement.delta = this.point.y - head.point.y;
        }
    }

    public class Mids extends Grats{
        Mids(double x, double y, double time){
            super(x,y,time);
            control.objs.add(this);
        }

        public void move(MovingObj head){
            xMovement.delta = head.point.x - this.point.x;
            yMovement.delta = head.point.y - this.point.y;
        }

        public void shot(MovingObj head){
            Bullet bullet = new Bullet(this.point.x, this.point.y,time);
            control.objs.add(bullet);
            bullet.xMovement.delta = this.point.x - view.head.point.x;
            bullet.yMovement.delta = this.point.y - view.head.point.y;
        }
    }

    public Game(View view, Control control){
        this.view = view;
        this.control = control;
        rand = new Random();
    }

    public Grats getGrats(){return new Grats(view.canvas.getWidth() - 10 ,
            view.canvas.getHeight() - 10, control.frames);
    }

    public Mids getMids(){return new Mids(view.canvas.getWidth() - 10 ,
            view.canvas.getHeight() - 10, control.frames);
    }

}
