/**
 * type:    1 Asteroid
 *          2 Grants
 *          3 Special
 *          4 Bosses
 *          5 Royals
 *          6 Prince
 *          7 King
 *
 */


public class Enemies extends MovingObj {
    int type;
    int health;
    MovingObj head;

    public Enemies(double x, double y, double time){
        super(x,y,time);
        width = 50;
        height = 50;
        type = 1;
        health = 5;
    }

    public void getHead(MovingObj head){this.head = head;}

    public Enemies(double x, double y, double time, int type){
        super(x,y,time);
        setType(type);
    }

    private void setType(int type) {
        switch (type){
            case 1:
                width = 50;
                height = 50;
                health = 5;
                this.weight = 10;
                break;
            case 2:
                width = 50;
                height = 50;
                health = 10;
                break;
            case 3:
                width = 50;
                height = 50;
                health = 20;
                break;
            case 4:
                width = 50;
                height = 50;
                health = 50;
                break;
            case 5:
                width = 50;
                height = 50;
                health = 10;
                break;
            case 6:
                width = 50;
                height = 50;
                health = 10;
                break;
            case 7:
                width = 50;
                height = 50;
                health = 10;
                break;
        }
    }

    public void move(){
        double king = Math.sqrt((Math.pow(head.point.x - this.point.x,2))+(Math.pow(head.point.y - point.y,2)));
        xMovement.delta = (head.point.x - this.point.x)/king;
        yMovement.delta = (head.point.y - this.point.y)/king;

        point.x += (xMovement.delta)*(time/weight);
        point.y += (yMovement.delta)*(time/weight);
    }



}
