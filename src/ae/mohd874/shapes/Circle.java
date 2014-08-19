package ae.mohd874.shapes;

import processing.core.PVector;
import ae.mohd874.Entity;
import ae.mohd874.Composer;
import ae.mohd874.Shape;
import ae.mohd874.interfaces.Collidable;

public class Circle extends Entity implements Shape
{
    public float x;
    public float y;
    public float r;
    
    public Circle(Composer ps, float _x, float _y, float _r){
      super(ps);
      x = _x;
      y = _y;
      r = _r;
    }
    
    public void display(){
      ps.ellipse(x, y, 2*r, 2*r);
    }

    public float getWidth()
    {
      return r;
    }
    
    public float getHeight()
    {
      return getWidth();
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
    
    public PVector[] checkCollision(Collidable n)
    {
      return isLineIntersect((Line) n);
    }
    
    private PVector[] isLineIntersect(Line line) {
      return isLineIntersect(line.p1.x, line.p1.y, line.p2.x, line.p2.y, x, y, r);
    }
    
    private PVector[] isLineIntersect(float x1, float y1, float x2, float y2, float cx, float cy, float cr ) {
      PVector[] interPoints;
      
      float dx = x2 - x1;
      float dy = y2 - y1;
      float a = dx * dx + dy * dy;
      float b = 2 * (dx * (x1 - cx) + dy * (y1 - cy));
      float c = cx * cx + cy * cy;
      c += x1 * x1 + y1 * y1;
      c -= 2 * (cx * x1 + cy * y1);
      c -= cr * cr;
      float bb4ac = b * b - 4 * a * c;
     
      //println(bb4ac);
     
      if (bb4ac < 0) {  // Not intersecting
        return new PVector[0];
      }
      else {
        interPoints = new PVector[2];
        float mu = (-b + ps.sqrt( b*b - 4*a*c )) / (2*a);
        float ix1 = x1 + mu*(dx);
        float iy1 = y1 + mu*(dy);
        mu = (-b - ps.sqrt(b*b - 4*a*c )) / (2*a);
        float ix2 = x1 + mu*(dx);
        float iy2 = y1 + mu*(dy);
     
        // The intersection points
        interPoints[0] = new PVector(ix1, iy1);
        interPoints[1] = new PVector(ix2, iy2);
        return interPoints;
//        float testX;
//        float testY;
        // Figure out which point is closer to the circle
//        if (dist(x1, y1, cx, cy) < dist(x2, y2, cx, cy)) {
//          testX = x2;
//          testY = y2;
//        } else {
//          testX = x1;
//          testY = y1;
//        }
//         
//        if (dist(testX, testY, ix1, iy1) < dist(x1, y1, x2, y2) || dist(testX, testY, ix2, iy2) < dist(x1, y1, x2, y2)) {
//          return true;
//        } else {
//          return false;
//        }
      }
    }
}
