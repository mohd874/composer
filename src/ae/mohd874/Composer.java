package ae.mohd874;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;
import ae.mohd874.controls.ControlPanel;
import ae.mohd874.scanner.RadarScanner;
import ae.mohd874.scanner.Scanner;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import ddf.minim.Minim;

public class Composer extends BaseApplet {
    
    public enum PlayingState {
        PLAYING, STOPPED, PAUSED
    }

    public Scanner   scanner;
    
    private Node             selectedNode;
    
    private PlayingState   playingState;
    
    Minim            minim;
    String           audioFilesPath;
    
    public ControlP5 cp5;
    
    ControlPanel     panel;
    
    List<Node> toBeRemoved;
    
    // Construction
    public void setup() {
        size(640, 480, P3D);
//        rectMode(CENTER);
        ellipseMode(CENTER);
        
        // Audio
        minim = new Minim(this);
        audioFilesPath = sketchPath + "\\data\\audio";

        // Control Panel
        cp5 = new ControlP5(this);
        panel = new ControlPanel(this);

        setPlayingState(PlayingState.STOPPED);
        
        scanner = new RadarScanner(this, 90, 90, 1f);
        scanner.setPlaced(true);
        scanner.setNew(false);
        
        nodes.add(scanner);
        
        toBeRemoved = new ArrayList<>();
    }
    
    public String[] listAudioFiles() {
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
    @Override
    public void update() {
        super.update();
        for(Node n : toBeRemoved)
        {
            nodes.remove(n);
        }
        toBeRemoved.clear();
    }
    
    @Override
    public void draw() {
        update();
        super.draw();
        
        panel.display();
    }
    
    @Override
    public void mouseClicked(MouseEvent event) {
        super.mouseClicked(event);

        boolean selected = false;
        for(Node n : nodes){
            if(n.isMouseIn()){
                setSelectedNode(n);
                selected = true; // To prevent multi selection when nodes are stacked
                break;
            }
            else if(panel.isMouseInsidePanel()) {
                selected = true; // To retain selection when panel is clicked
            }
        }
        
        if(!selected){
            setSelectedNode(null);
        }
    }
    
    public void setSelectedNode(Node n) {
        if (selectedNode != null) {
            selectedNode.setPlaced(true);
            selectedNode.setSelected(false);
        }
        
        if (n != null) {
            selectedNode = n;
            n.setSelected(true);
            panel.loadNodePropertiesToPanel(n);
        } else {
            selectedNode = null;
            panel.clearNodePropertiesPanel();
        }
    }
    
    // Control Panel Events
    public void controlEvent(ControlEvent event) {
        
        
        for (Node n : nodes)
            n.handleControlEvent(event);
        
        panel.handleControlEvent(event);
    }
    
    boolean isMouseInsideAController() {
        return panel.isMouseInsideAController();
    }
    
    public static void main(String[] args) {
        PApplet.main(new String[] { "ae.mohd874.Composer" });
    }

    public PlayingState getPlayingState() {
        return playingState;
    }

    public void setPlayingState(PlayingState playingState) {
        this.playingState = playingState;
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void deleteNode(Node node) {
        toBeRemoved.add(node);
    }

//    public void resetNodesSound() {
//        for(Node n : nodes)
//            n.resetSound();
//    }
//
//    public void pauseNodesSound() {
//        for(Node n : nodes)
//            n.pauseSound();
//    }
//
//    public void playNodesSound() {
//        for(Node n : nodes)
//            n.playSound();
//    }
}
