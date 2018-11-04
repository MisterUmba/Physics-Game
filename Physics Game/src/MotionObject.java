public class MotionObject {
    double delta;
    final double MAX = 40;
    double time;

    public MotionObject(double time){
        delta = 0;
        this.time = time;
    }

    public void faster(){
        if(delta < MAX)
            delta ++;
    }

    public void slower(){
        if(delta > MAX*-1)
            delta--;
    }

    public String toString(){
        return "Speed: "+delta;
    }

}
