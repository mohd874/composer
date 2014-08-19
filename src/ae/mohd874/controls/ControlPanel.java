package ae.mohd874.controls;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Composer.PlayingState;
import ae.mohd874.Entity;
import ae.mohd874.Node;
import ae.mohd874.nodes.CircleNode;
import ae.mohd874.nodes.RectNode;
import ae.mohd874.shapes.Circle;
import ae.mohd874.shapes.Rectangle;
import controlP5.Accordion;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.ControllerInterface;
import controlP5.DropdownList;
import controlP5.Group;
import controlP5.Textfield;
import controlP5.Textlabel;

public class ControlPanel extends Entity
{
    ControlP5 cp5;
    
    public Rectangle panelBg;
    public int       panelBgColor;

    public ControlPanel(Composer ps) {
        super(ps);
        cp5 = ps.cp5;
        initGui();
    }

    private void initGui() {
        // Panel Background
        int panelW = ps.width/5;
        int panelH = ps.height;
        int panelX = ps.width - panelW;
        int panelY = 0;
        panelBgColor = 190;
        panelBg = new Rectangle(ps, panelX, panelY, panelW, panelH);
        
        PVector initPos = new PVector(10, 10);
        PVector smBtn = new PVector(50, 15);
        PVector wideBtn = new PVector(110, 15);
        PVector wideDdl = new PVector(110, 80);
        
        ControlPanelCreator cpc = new ControlPanelCreator(cp5, initPos);

        // Panel Controls
        Accordion accordion = cp5.addAccordion("shapes");
        
        Group shapesGroup = cp5.addGroup("shapesGroup");
        Group scannerGroup = cp5.addGroup("scannerGroup");
        Group propGroup = cp5.addGroup("propGroup");

        cpc.addController(cp5.addButton("addRect"), smBtn, shapesGroup);
        cpc.addController(cp5.addButton("addCircle"), smBtn, shapesGroup);
        cpc.reset();
        cpc.addController(cp5.addButton("playScene"), smBtn, scannerGroup);
        cpc.addController(cp5.addButton("stopScene"), smBtn, scannerGroup);
        cpc.linebreak();
        cpc.addController(cp5.addButton("moveScanner"), wideBtn, scannerGroup);
        cpc.linebreak();
        cpc.addController(cp5.addTextfield("scannerAngle"), smBtn, scannerGroup);
        cpc.addController(cp5.addTextfield("scannerSize"), smBtn, scannerGroup);
        cpc.linebreak();
        cpc.linebreak();
        cpc.addController(cp5.addTextfield("scannerSpeed"), smBtn, scannerGroup);
        cpc.reset();
        cpc.addController(cp5.addTextfield("locationX"), smBtn, propGroup);
        cpc.addController(cp5.addTextfield("locationY"), smBtn, propGroup);
        cpc.linebreak();
        cpc.linebreak();
        cpc.addController(cp5.addTextfield("shapeWidth"), smBtn, propGroup);
        cpc.addController(cp5.addTextfield("shapeHeight"), smBtn, propGroup);
        cpc.linebreak();
        cpc.linebreak();
        cpc.addController(cp5.addDropdownList("audioDDL"), wideDdl, propGroup);
        
        scannerGroup.setSize(panelW, 130);
        accordion.addItem(shapesGroup);
        accordion.addItem(scannerGroup);
        accordion.addItem(propGroup);
        
        accordion.open(0,1,2);
        accordion.setPosition(panelX, panelY);
        accordion.setSize(panelW, panelH);
        accordion.setCollapseMode(Accordion.MULTI); 
        
        cp5.get("playScene").setCaptionLabel("play");
        cp5.get("stopScene").setCaptionLabel("stop");
        cp5.get("moveScanner").setCaptionLabel("Move Scanner");
        cp5.get("scannerAngle").setCaptionLabel("Angle");
        cp5.get("scannerSize").setCaptionLabel("Size");
        cp5.get("scannerSpeed").setCaptionLabel("Speed");
        ((Textfield) cp5.get("locationY").setCaptionLabel("Y")).setAutoClear(false);
        ((Textfield) cp5.get("locationX").setCaptionLabel("X")).setAutoClear(false);
        ((Textfield) cp5.get("shapeWidth").setCaptionLabel("Width")).setAutoClear(false);
        ((Textfield) cp5.get("shapeHeight").setCaptionLabel("Height")).setAutoClear(false);
        ((DropdownList) cp5.get("audioDDL").setCaptionLabel("audio")).addItems(ps.listAudioFiles());
    }

    public void display() {
        ps.fill(panelBgColor);
        ps.pushStyle();
        ps.rectMode(ps.CORNER);
        panelBg.display();
        ps.popStyle();
    }
    
    public void loadNodePropertiesToPanel(Node n) {
        Textfield widthCtrl = cp5.get(Textfield.class, "shapeWidth");
        Textfield heightCtrl = cp5.get(Textfield.class, "shapeHeight");
        Textfield xCtrl = cp5.get(Textfield.class, "locationX");
        Textfield yCtrl = cp5.get(Textfield.class, "locationY");
        DropdownList audioDDL = cp5.get(DropdownList.class, "audioDDL");
        
        PVector loc = n.shape.getLoc();
        
        widthCtrl.setValue(n.shape.getWidth() + "");
        heightCtrl.setValue(n.shape.getHeight() + "");
        xCtrl.setValue(loc.x + "");
        yCtrl.setValue(loc.y + "");
        audioDDL.setIndex(n.getSoundFileIndex());
    }
    
    public void clearNodePropertiesPanel() {
        Textfield widthCtrl = cp5.get(Textfield.class, "shapeWidth");
        Textfield heightCtrl = cp5.get(Textfield.class, "shapeHeight");
        Textfield xCtrl = cp5.get(Textfield.class, "locationX");
        Textfield yCtrl = cp5.get(Textfield.class, "locationY");
        DropdownList audioDDL = cp5.get(DropdownList.class, "audioDDL");
        
        widthCtrl.clear();
        heightCtrl.clear();
        xCtrl.clear();
        yCtrl.clear();
        audioDDL.setIndex(0);
    }

    
    public void handleControlEvent(ControlEvent event) {
        String eName = event.getName();

        if(eName == "addRect"){
            addRect();
        }
        else if(eName == "addCircle"){
            addCircle();
        }
        else if(eName == "playScene"){
            playScene();
        }
        else if(eName == "stopScene"){
            stopScene();
        }
        else if(eName == "audioDDL"){
            audioDropdown(event);
        }
        
    }

    private void audioDropdown(ControlEvent event) {
        if (event.isGroup()) {
            int index = (int) event.getGroup().getValue();
            DropdownList list = (DropdownList) event.getGroup();
            String fileName = list.getItem(index).getText();
            if (ps.getSelectedNode() != null) {
                ps.getSelectedNode().setSound(fileName, index);
            }
        }
    }

    private void addRect() {
        Rectangle r = new Rectangle(ps, ps.mouseX, ps.mouseY, 20, 20);
        ps.addNode(new RectNode(ps, r, ps.scanner));
    }
    
    private void addCircle() {
        Circle r = new Circle(ps, ps.mouseX, ps.mouseY, 30);
        ps.addNode(new CircleNode(ps, r, ps.scanner));
    }
    
    private void playScene() {
        switch(ps.getPlayingState()){
            case STOPPED:
            case PAUSED:
                cp5.getController("playScene").setCaptionLabel("Pause");
                ps.setPlayingState(PlayingState.PLAYING);
                break;
            case PLAYING:
                cp5.getController("playScene").setCaptionLabel("Resume");
                ps.setPlayingState(PlayingState.PAUSED);
                break;
        }
    }
    
    private void stopScene() {
        ps.setPlayingState(PlayingState.STOPPED);
        cp5.getController("playScene").setCaptionLabel("Play");
        ps.scanner.resetLoc();
    }

    public boolean isMouseInsidePanel() {
        float mx = ps.mouseX;
        float my = ps.mouseY;
        Rectangle r = panelBg;
        
        if(mx >= r.x && (mx <= r.x+r.w)
        && my >= r.y && (my <= r.y+r.h))
        {
          return true;
        }
        
        return false;
    }

    public boolean isMouseInsideAController() {
        for (ControllerInterface<?> c : cp5.getAll()) {
            if (c.isMouseOver())
                return true;
        }
        
        return false;
    }
 
}
