package ae.mohd874;

import processing.core.PVector;
import ae.mohd874.interfaces.Collidable;

public interface Shape {
    PVector[] checkCollision(Collidable n);
    
    void display();
    
    void setLoc(float x, float y);
    abstract float getWidth();
    abstract float getHeight();
    abstract PVector getLoc();
}
