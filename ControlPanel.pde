
Rectangle panelBg;
int panelBgColor;
ControlP5 cp5;

void createControlPanel()
{
  // Panel Background
  int panelW = 100;
  int panelH = height;
  int panelX = width-panelW;
  int panelY = 0;
  panelBgColor = 190;
  panelBg = new Rectangle(panelX, panelY, panelW, panelH);
  
  // Panel Controls
  cp5 = new ControlP5(this);
  
  cp5.addButton("addRect")
      .setPosition(panelX+10, panelY+10)
      .setSize(40, 15);
      
  cp5.addButton("addCircle")
     .setPosition(panelX+10, panelY+30)
     .setSize(46, 15);
  
  cp5.addButton("playScene")
     .setPosition(panelX+10, panelY+50)
     .setCaptionLabel("Play")
     .setSize(46, 15);
  
  cp5.addButton("stopScene")
     .setPosition(panelX+10, panelY+70)
     .setCaptionLabel("stop")
     .setSize(46, 15);
   
   cp5.addButton("moveScanner")
     .setPosition(panelX+10, panelY+90)
     .setCaptionLabel("Move Scanner")
     .setSize(66, 15);
   
   cp5.addTextfield("scannerAngle")
     .setPosition(panelX+10, panelY+110)
     .setCaptionLabel("Scanner Angle")
     .setSize(66, 15);
   
   cp5.addTextfield("locationX")
     .setPosition(panelX+10, panelY+140)
     .setCaptionLabel("x")
     .setAutoClear(false)
     .setSize(40, 15);
   
   cp5.addTextfield("locationY")
     .setPosition(panelX+50, panelY+140)
     .setCaptionLabel("Y")
     .setAutoClear(false)
     .setSize(40, 15);
   
   cp5.addTextfield("shapeWidth")
     .setPosition(panelX+10, panelY+170)
     .setCaptionLabel("width")
     .setAutoClear(false)
     .setSize(40, 15);
   
   cp5.addTextfield("shapeHeight")
     .setPosition(panelX+50, panelY+170)
     .setCaptionLabel("height")
     .setAutoClear(false)
     .setSize(40, 15);  
     
   cp5.addDropdownList("audioDDL")
      .setPosition(panelX+10, panelY+210)
      .setCaptionLabel("audio")
      .addItems(listAudioFiles())
      .setSize(80, 80);
}

void displayPanel()
{
  fill(panelBgColor);
  panelBg.display();
}

void loadNodePropertiesToPanel(Node n)
{
  Textfield widthCtrl = cp5.get(Textfield.class, "shapeWidth");
  Textfield heightCtrl = cp5.get(Textfield.class, "shapeHeight");
  Textfield xCtrl = cp5.get(Textfield.class, "locationX");
  Textfield yCtrl = cp5.get(Textfield.class, "locationY");
  
  PVector loc = n.shape.getLoc();
  
  widthCtrl.setValue(n.shape.getWidth()+"");
  heightCtrl.setValue(n.shape.getHeight()+"");
  xCtrl.setValue(loc.x+"");
  yCtrl.setValue(loc.y+"");
}

void clearNodePropertiesPanel()
{
  Textfield widthCtrl = cp5.get(Textfield.class, "shapeWidth");
  Textfield heightCtrl = cp5.get(Textfield.class, "shapeHeight");
  Textfield xCtrl = cp5.get(Textfield.class, "locationX");
  Textfield yCtrl = cp5.get(Textfield.class, "locationY");
  
  widthCtrl.clear();
  heightCtrl.clear();
  xCtrl.clear();
  yCtrl.clear();
}

boolean isMouseInsideAController()
{
  for(ControllerInterface c : cp5.getAll())
  {
    if(c.isMouseOver()) return true;
  }
  
  return false;
}

