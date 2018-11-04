public class Bullet extends MovingObj {

    Bullet(double x, double y, double time){
        super(x,y,time);
    }

    void shot(MovingObj head, double mouseX, double mouseY){

        double king = Math.sqrt((Math.pow(mouseX - this.point.x,2))+(Math.pow(mouseY - point.y,2)));
        xMovement.delta = (mouseX - this.point.x)/king;
        yMovement.delta = (mouseY - this.point.y)/king;

        width = 10;
        height = 10;

        this.weight = 1.3;
    }
}
