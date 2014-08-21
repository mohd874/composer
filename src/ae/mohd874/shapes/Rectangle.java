package ae.mohd874.shapes;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Entity;
import ae.mohd874.Shape;
import ae.mohd874.interfaces.Collidable;

public class Rectangle extends Entity implements Shape
{
    public float x;
    public float y;
    public int w;
    public int h;
    
    public Rectangle(Composer ps, float _x, float _y, int _w, int _h){
        super(ps);
      x = _x;
      y = _y;
      w = _w;
      h = _h;
    }
    
    public void display(){
      ps.rect(x, y, w, h, 5);
    }
    
    public float getWidth()
    {
      return w;
    }
    
    public float getHeight()
    {
      return h;
    }
    
    public PVector getLoc()
    {
      return new PVector(x, y);
    }
    
    public void setLoc(float _x, float _y)
    {
      x = _x;
      y = _y;
    }

    Line[] getLines(){
      Line[] lines = new Line[4];
      
      lines[0] = new Line(ps, x, y, x+w, y); 
      lines[1] = new Line(ps, x+w, y, x+w, y+h); 
      lines[2] = new Line(ps, x+w, y+h, x, y+h); 
      lines[3] = new Line(ps, x, y+h, x, y);
     
     return lines; 
    }
    
    public List<PVector> checkCollision(Collidable n)
    {
      ArrayList<PVector> points = new ArrayList<PVector>();
      
      for(Line line : getLines())
      {
        points.addAll(line.checkCollision(n));
      }
      
      return points;
    }
}
