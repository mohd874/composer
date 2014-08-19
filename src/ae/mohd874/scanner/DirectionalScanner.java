package ae.mohd874.scanner;

import processing.core.PVector;
import ae.mohd874.Composer;
import ae.mohd874.Composer.PlayingState;
import controlP5.ControlEvent;
import controlP5.Textfield;

public class DirectionalScanner extends Scanner {

    PVector dir;
    PVector accel;
    
    public DirectionalScanner(Composer ps, float _initX, float _initY, float _speed, float _angle) {
        super(ps, _initX, _initY, _speed);
        float angle = _angle;
        
        dir = new PVector(0, 0);
        accel = new PVector(0, 0);
        
        setDirAngle(angle);
        setAccel(angle);
        resetLoc();
    }
    
    @Override
    public void update() {
        if (!isPlaced()) {
            getLine().setLoc(ps.mouseX, ps.mouseY);
            return;
        }
        
        if (ps.getPlayingState() == PlayingState.PLAYING) {
            getLine().p1.add(accel);
            getLine().p2.add(accel);
        }
    }
    
    public void displaySelectionBorder() {
    }
    
    void setLoc(float _x, float _y) {
        initX = _x;
        initY = _y;
        getLine().setLoc(_x, _y);
    }
    
    public void resetLoc() {
        getLine().p1.set(initX - (dir.y * (size / 2)), initY - (dir.x * (size / 2)));
        
        getLine().p2.set(initX + (dir.y * (size / 2)), initY + (dir.x * (size / 2)));
    }
    
    void setDirAngle(float deg) {
        dir.set(-ps.cos(ps.radians(deg)), ps.sin(ps.radians(deg)));
    }
    
    void setAccel(float deg) {
        accel.set(ps.cos(ps.radians(deg)) * speed, ps.sin(ps.radians(deg))
                * speed);
    }
    
    
    public boolean isMouseIn() {
        return false;
    }
    
    @Override
    public void handleControlEvent(ControlEvent event) {
        if (event.getName() == "moveScanner")
            moveScanner();
        else if (event.getName() == "scannerAngle") {
            Textfield tf = (Textfield) event.getController();
            String angle = tf.getText();
            try {
                scannerAngle(Float.parseFloat(angle));
            } catch (NumberFormatException e) {
                /* ignore it */
            }
        }
        else if (event.getName() == "scannerSize") {
            Textfield tf = (Textfield) event.getController();
            String newSize = tf.getText();
            try{
                size = Integer.parseInt(newSize);
                resetLoc();
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
    
    private void scannerAngle(float deg) {
        float angle = deg;
        setDirAngle(angle);
        setAccel(angle);
        resetLoc();
    }

    @Override
    protected void updateShapeWithLerp() {
        
    }
    
}
