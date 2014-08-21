package ae.mohd874.nodes;

import ae.mohd874.Composer;
import ae.mohd874.Node;
import ae.mohd874.scanner.Scanner;
import ae.mohd874.shapes.Rectangle;

public class RectNode extends Node
{
    Rectangle rect;
    Rectangle originalRect;
    
    public RectNode(Composer ps, Rectangle _r)
    {
      super(ps, _r);
      rect = _r;
      originalRect = new Rectangle(ps, rect.x, rect.y, rect.w, rect.h);
    }
    
    public void displaySelectionBorder()
    {
      Rectangle r = rect;
      ps.pushStyle();
      ps.stroke(204, 102, 0);
      ps.noFill();
      ps.rect(r.x, r.y, r.w, r.h, 5);
      ps.noStroke();
      ps.popStyle();
    }
    
    public boolean isMouseIn()
    {
      float mx = ps.mouseX;
      float my = ps.mouseY;
      Rectangle r = rect;
      
      if(mx >= r.x && (mx <= r.x+r.w)
      && my >= r.y && (my <= r.y+r.h))
      {
        return true;
      }
      
      return false;
    }

    @Override
    protected void updateShapeWithLerp() {
        Rectangle r = originalRect;
        int nw = (int)ps.lerp(r.w*0.80f, r.w, lerp);
        int nh = (int)ps.lerp(r.h*0.80f, r.h, lerp);
        rect.w = nw;
        rect.h = nh;
        
        lerpColor = (int)ps.lerp(color*0.90f, color, lerp);
    }

    
    
}
