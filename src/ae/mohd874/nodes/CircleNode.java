package ae.mohd874.nodes;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Node;
import ae.mohd874.scanner.Scanner;
import ae.mohd874.shapes.Circle;

public class CircleNode extends Node
{
    Circle circle;
    
    public CircleNode(Composer ps, Circle _c)
    {
      super(ps, _c);
      circle = _c;
    }

    public void displaySelectionBorder()
    {
      Circle r = circle;
      ps.pushStyle();
      ps.stroke(204, 102, 0);
      ps.noFill();
      ps.rect(r.x, r.y, 2*r.r, 2*r.r);
      ps.noStroke();
      ps.popStyle();
    }

    public boolean isMouseIn()
    {
      PVector m = new PVector(ps.mouseX, ps.mouseY);
      PVector circleCenter = new PVector(circle.x, circle.y);
      float dist = PVector.dist(m, circleCenter);  
      return (dist <= circle.r) ? true : false;
    }

    @Override
    protected void updateShapeWithLerp() 
    {
        Circle r = circle;
        r.r = ps.lerp(r.r*0.85f, r.r, lerp);
    }
}
