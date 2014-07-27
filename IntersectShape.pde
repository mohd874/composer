import ddf.minim.spi.*;
import ddf.minim.signals.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.ugens.*;
import ddf.minim.effects.*;

import controlP5.*;

Scanner scanner;

ArrayList<Node> nodes;
Node selectedNode;

boolean isPlaying;

Minim minim;
String audioFilesPath;


// Construction
void setup() {
  size(640, 480, P3D);
  fill(255, 0, 0);

  nodes = new ArrayList<Node>();
  isPlaying = false;

  scanner = new Scanner(90, 90, 1f, 30);
  scanner.isPlaced = true;
  scanner.isNew = false;
  
  nodes.add(scanner);
  
  
  // Audio
  minim = new Minim(this);
  audioFilesPath = sketchPath+"/data/audio";
  
  // Control Panel
  createControlPanel();
  
}

String[] listAudioFiles()
{
  File file = new File(audioFilesPath);
  if (file.isDirectory()) {
    String names[] = file.list();
    return names;
  } else {
    // If it's not a directory
    return new String[0];
  }
}

// Loop
void update()
{
  for (Node n : nodes) {
    n.update();
  }
}

void draw()
{
  background(255);

  update();

  // Display.
  fill(0, 255, 255);

  for (Node n : nodes) {
    n.display();
  }

  displayPanel();
}

void setSelectedNode(Node n)
{
  if(selectedNode != null)
  {
    selectedNode.isPlaced = true;
    selectedNode.isSelected = false;
  }
  
  if(n != null)
  {
    selectedNode = n;
    n.isSelected = true;
    loadNodePropertiesToPanel(n);
  }
  else
  {
    selectedNode = null;
    clearNodePropertiesPanel();
  }
}



