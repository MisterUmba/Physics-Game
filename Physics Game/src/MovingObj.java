public class MovingObj {

    Point point;
    MotionObject xMovement, yMovement;
    double time;
    double weight;
    double height;
    double width;

    public class Point{
        double x , y;
        Point(double x, double y){
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "Point("+x+", "+y+")";
        }
    }

    public void moveUp(){yMovement.slower();}
    public void moveDown(){yMovement.faster();}
    public void moveRight(){xMovement.faster();}
    public void moveLeft(){xMovement.slower();}

    public void move(){
        point.x += (xMovement.delta)*(time/weight);
        point.y += (yMovement.delta)*(time/weight);
    }

    public MovingObj(double x, double y, double time){
        point = new Point(x,y);
        xMovement = new MotionObject(time);
        yMovement = new MotionObject(time);
        this.time = time;
        weight = 100;

        width = 40;
        height = 50;
    }

    public boolean intercept(MovingObj b){
        return point.x - width <= (b.point.x + b.width) &&
                (point.x + width) >= b.point.x &&
                point.y <= (b.point.y + b.width) &&
                (point.y+ height) >= b.point.y;
    }

}
