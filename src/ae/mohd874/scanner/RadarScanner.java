package ae.mohd874.scanner;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Composer.PlayingState;
import controlP5.ControlEvent;
import controlP5.Textfield;

public class RadarScanner extends Scanner{

    private float angle;

    public RadarScanner(Composer ps, float _initX, float _initY, float _speed) {
        super(ps, _initX, _initY, _speed);
        angle = 0;
        resetLoc();
    }

    @Override
    public void update() {
        if (!isPlaced()) {
            getLine().setLoc(ps.mouseX, ps.mouseY);
            return;
        }
        
        float rad = ps.radians(angle);
        PVector p1 = getLine().p1;
        PVector p2 = getLine().p2;
        p2.x = p1.x + (size * ps.sin(rad));
        p2.y = p1.y + (size * ps.cos(rad));
        
        if (ps.getPlayingState() == PlayingState.PLAYING) {
            angle += getSpeed();
            if(angle >= 360)
                angle -= 360;
        }
    }

    @Override
    public void resetLoc() {
        getLine().p1.x = initX;
        getLine().p1.y = initY;
        getLine().p2.x = initX + size;
        getLine().p2.y = initY;
    }

    @Override
    protected void updateShapeWithLerp() {
        
    }

    @Override
    public void displaySelectionBorder() {
        
    }
    
    @Override
    public void handleControlEvent(ControlEvent event) {
        if(!isSelected)
            return;
        
        if (event.getName() == "moveScanner")
            moveScanner();
        else if (event.getName() == "scannerAngle") {
            Textfield tf = (Textfield) event.getController();
            String newAngle = tf.getText();
            try{
                angle = Float.parseFloat(newAngle);
            }catch(NumberFormatException e){
                // ignore it
            }
        }
        else if (event.getName() == "scannerSize") {
            Textfield tf = (Textfield) event.getController();
            String newSize = tf.getText();
            try{
                size = Integer.parseInt(newSize);
            }
            catch(NumberFormatException e)
            {
                // Ignore It
            }
        }
        else if(event.getName() == "scannerSpeed") {
            Textfield tf = (Textfield) event.getController();
            String newSpeed = tf.getText();
            try{
                setSpeed(Integer.parseInt(newSpeed));
            }
            catch(NumberFormatException e)
            {
                // Ignore It
            }
        }
    }

    private void moveScanner() {
        setPlaced(false);
        setNew(true); /* WOOOOOOOT */
    }

    @Override
    void setSpeed(float _speed) {
        this.speed = 360/(_speed * ps.frameRate);
    }

}
