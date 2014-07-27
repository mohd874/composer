abstract class Node implements MouseListner
{
  Shape shape;
  Scanner scanner;
  boolean isScannerIn;
  PVector[] points;
  boolean isPlaced;
  boolean isNew;
  boolean isSelected;
  boolean initMouseDrag;
  String audioName;
  AudioPlayer player;
  
  Node()
  {
    isScannerIn = false;
    isPlaced = false;
    isNew = true;
    isSelected = false;
    initMouseDrag = false;
    points = new PVector[0];
  }
  
  Node(Shape _s, Scanner _scanner)
  {
    shape = _s;
    scanner = _scanner;
    isScannerIn = false;
    isPlaced = false;
    isNew = true;
    isSelected = false;
    initMouseDrag = false;
    points = new PVector[0];
  }
  
  void update()
  {
    if(!isPlaced)
    {
      shape.setLoc(mouseX, mouseY);
      return;
    }

    points = shape.checkCollision(scanner.line);
    if(points != null && points.length > 0){
      if(!isScannerIn)
      {
        isScannerIn = true;
        playSound();
      }
    }
    else
    {
      if(isScannerIn)
      {
        isScannerIn = false;
        resetSound();
      }
    }
    
  }
  
  void display()
  {
    shape.display();
    drawCollisionPoints();
    if(isSelected)
    {
      displaySelectionBorder();
    }
  }
  
  abstract void displaySelectionBorder();
  
  void playSound()
  {
    if(player != null) player.play();
  }
  
  void resetSound()
  {
//    println("Node Reset Sound");
  }
  
  void setSound(String fileName)
  {
    player = minim.loadFile(audioFilesPath+"/"+fileName);
  }
  
  void drawCollisionPoints()
  {
    for(PVector p : points)
    {
      ellipse(p.x, p.y, 5, 5);
    }
  }
  
  void mousePressed()
  {
    if(isMouseIn())
    {
      initMouseDrag = true;
    }
  }
  void mouseClicked()
  {
    if(isMouseIn())
    {
      isSelected = true;
      setSelectedNode(this);
    }
    else if(!isMouseInsideAController())
    {
      isSelected = false;
      setSelectedNode(null);
    }
  }
  void mouseDragged()
  {
    if(isMouseIn() && initMouseDrag == true)
    {
      isPlaced = false;
    }
  }
  void mouseMoved(){}
  void mouseReleased()
  {
    if(isNew) isNew = false;
    else isPlaced = true;
    
    initMouseDrag = false;
    if(!isMouseIn()) isSelected = false;
  }
  void mouseWheel(){}
  
  abstract boolean isMouseIn(); 
}
  
class RectNode extends Node
{
  Rectangle rectangle;
  
  RectNode(Rectangle _r, Scanner _scanner)
  {
    super(_r, _scanner);
    rectangle = _r;
  }
  
  void displaySelectionBorder()
  {
    Rectangle r = rectangle;
    
    stroke(204, 102, 0);
    noFill();
    rect(r.x, r.y, r.w, r.h);
    noStroke();
  }
  
  boolean isMouseIn()
  {
    float mx = mouseX;
    float my = mouseY;
    Rectangle r = rectangle;
    
    if(mx >= r.x && (mx <= r.x+r.w)
    && my >= r.y && (my <= r.y+r.h))
    {
      return true;
    }
    
    return false;
  }
  
}

class CircleNode extends Node
{
  Circle circle;
  
  CircleNode(Circle _c, Scanner _scanner)
  {
    super(_c, _scanner);
    circle = _c;
  }

  void displaySelectionBorder()
  {
    Circle r = circle;
    stroke(204, 102, 0);
    noFill();
    rect(r.x - r.r, r.y - r.r, 2*r.r, 2*r.r);
    noStroke();
  }

  boolean isMouseIn()
  {
    PVector m = new PVector(mouseX, mouseY);
    PVector circleCenter = new PVector(circle.x, circle.y);
    float dist = PVector.dist(m, circleCenter);  
    return (dist <= circle.r) ? true : false;
  }
    
}

