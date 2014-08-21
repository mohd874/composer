package ae.mohd874.scanner;

import java.util.List;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Node;
import ae.mohd874.interfaces.Collidable;
import ae.mohd874.shapes.Line;

public abstract class Scanner extends Node implements Collidable {

    private Line    line;
    protected float   speed;
    PVector loc;
    int     size;
    float   initX;
    float   initY;

    public Scanner(Composer ps, float _initX, float _initY, float _speed)
    {
        super(ps);
//        scanner = this;
        size = 80;
        setSpeed(_speed);
        initX = _initX;
        initY = _initY;
        
        loc = new PVector(initX, initY);
        
        PVector p1 = new PVector(0, 0);
        PVector p2 = new PVector(0, 0);
        
        setLine(new Line(ps, p1, p2));
        
        resetLoc();
    }
    
    public void update(){}

    public void display() {
        ps.pushStyle();
        if(isSelected)
            ps.stroke(204, 102, 0);
        else
            ps.stroke(2);
        
        getLine().display();
        ps.popStyle();
    }

    public List<PVector> checkCollision(Collidable n) {
        return getLine().checkCollision(n);
    }
    
    public abstract void resetLoc();

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    } 
    
    @Override
    public boolean isMouseIn() {
        return PVector.dist(getLine().p1, new PVector(ps.mouseX, ps.mouseY)) <= 5;
    }
    
    float getSpeed() {
        return speed;
    }

    abstract void setSpeed(float _speed);
}
