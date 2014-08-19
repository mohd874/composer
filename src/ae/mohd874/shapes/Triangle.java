package ae.mohd874.shapes;

import java.util.ArrayList;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Entity;
import ae.mohd874.Shape;
import ae.mohd874.interfaces.Collidable;

public class Triangle extends Entity implements Shape {
    
    private float x1, y1, x2, y2, x3, y3, x, y, w, h;
    
    public Triangle(Composer _ps, float _x1, float _y1, float _x2, float _y2,
            float _x3, float _y3) {
        super(_ps);
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
        x3 = _x3;
        y3 = _y3;
    }
    
    public Triangle(Composer _ps, float _x, float _y, float _w, float _h)
    {
       super(_ps); 
       w = _w;
       h = _h;
       x = _x;
       y = _y;
       
       x1 = w/2;
       y1 = y;
       x2 = x;
       y2 = y+h;
       x3 = x+w;
       y3 = y+h;
    }
    
    @Override
    public PVector[] checkCollision(Collidable n) {
      ArrayList<PVector> points = new ArrayList<PVector>();
      
      for(Line line : getLines())
      {
        PVector[] intersections = line.checkCollision(n);
        for(PVector p : intersections)
        {
          points.add(p);
        }
      }
      
      PVector[] pointsArr = new PVector[points.size()];
      
      for(int i=0; i < points.size(); i++)
      {
        pointsArr[i] = points.get(i);
      }
      
      return pointsArr;
    }
    
    Line[] getLines(){
      Line[] lines = new Line[3];
      
      lines[0] = new Line(ps, x1, y1, x2, y2);
      lines[1] = new Line(ps, x2, y2, x3, y3);
      lines[2] = new Line(ps, x3, y3, x1, y1);
     
      return lines; 
    }

    @Override
    public void display() {
       ps.triangle(x1, y1, x2, y2, x3, y3);
    }
    
    @Override
    public void setLoc(float _x, float _y) {
       x = _x;
       y = _y;
    }
    
    @Override
    public float getWidth() {
        return w;
    }
    
    @Override
    public float getHeight() {
        return h;
    }
    
    @Override
    public PVector getLoc() {
        return new PVector(x, y);
    }
    
}
