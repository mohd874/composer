// Events
void keyPressed()
{
//  if (key == '1')
//  {
//    addCircle(0);
//  } else if (key == '2')
//  {
//    addRect(0);
//  }
}

// Mouse Events
void mousePressed()
{
  for (Node n : nodes) {
    n.mousePressed();
  }
}

void mouseClicked()
{
  for (Node n : nodes) {
    n.mouseClicked();
  }
}

void mouseDragged()
{
  for (Node n : nodes) {
    n.mouseDragged();
  }
}

void mouseMoved()
{
  for (Node n : nodes) {
    n.mouseMoved();
  }
}

void mouseReleased()
{
  for (Node n : nodes) {
    n.mouseReleased();
  }
}

void mouseWheel()
{
  for (Node n : nodes) {
    n.mouseWheel();
  }
}


// Control Panel Events
public void controlEvent(ControlEvent theEvent) 
{
  if (theEvent.isGroup()) {
    int index = (int)theEvent.getGroup().getValue();
    DropdownList list = (DropdownList)theEvent.getGroup();
    String fileName = list.getItem(index).getText();
    if(selectedNode != null)
    {
      selectedNode.setSound(fileName);
    }
  }
}



public void addRect(int theValue) 
{
  Rectangle r = new Rectangle(mouseX, mouseY, 20, 20);
  nodes.add(new RectNode(r, scanner));
}

public void addCircle(int theValue) 
{
  Circle r = new Circle(mouseX, mouseY, 30);
  nodes.add(new CircleNode(r, scanner));
}

public void playScene(int theValue) 
{
  if(isPlaying){
    isPlaying = false;
    cp5.getController("playScene").setCaptionLabel("Resume");
  }
  else{
    isPlaying = true; 
    cp5.getController("playScene").setCaptionLabel("Pause");
  }
}

public void stopScene(int theValue) 
{
  isPlaying = false;
  cp5.getController("playScene").setCaptionLabel("Play");
  scanner.resetLoc();
}

public void moveScanner(int theValue) 
{
  scanner.isPlaced = false;
  scanner.isNew = true; /* WOOOOOOOT */
}

public void scannerAngle(String deg)
{
  try
  {
    float angle = Float.parseFloat(deg);
    scanner.setDirAngle(angle);
    scanner.setAccel(angle);
    scanner.resetLoc();
  }
  catch(NumberFormatException e)
  {
  }
}

