package ae.mohd874;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class BaseApplet extends PApplet {
    
    public ArrayList<Node> nodes;
    
    public BaseApplet() {
        nodes = new ArrayList<Node>();
    }
    
    public void update(){
        for(Node n : nodes)
            n.update();
    }
    
    public void draw(){
        background(230);

        fill(0, 255, 255);
        for(Node n : nodes)
            n.display();
    }

    // Events
    @Override
    public void keyPressed(KeyEvent event) {
        for (Node n : nodes) {
            n.keyPressed(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        for (Node n : nodes) {
            n.keyPressed(event);
        }
    }
    
    // Mouse Events
    public void mousePressed(MouseEvent event) {
        for (Node n : nodes) {
            n.mousePressed(event);
        }
    }
    
    public void mouseClicked(MouseEvent event) {
        for (Node n : nodes) {
            n.mouseClicked(event);
        }
    }
    
    public void mouseDragged(MouseEvent event) {
        for (Node n : nodes) {
            n.mouseDragged(event);
        }
    }
    
    public void mouseMoved(MouseEvent event) {
        for (Node n : nodes) {
            n.mouseMoved(event);
        }
    }
    
    public void mouseReleased(MouseEvent event) {
        for (Node n : nodes) {
            n.mouseReleased(event);
        }
    }
    
    public void mouseWheel(MouseEvent event) {
        for (Node n : nodes) {
            n.mouseWheel(event);
        }
    }
}
