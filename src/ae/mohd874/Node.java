package ae.mohd874;

import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import ae.mohd874.Composer.PlayingState;
import ae.mohd874.interfaces.ControlListener;
import ae.mohd874.scanner.Scanner;
import controlP5.ControlEvent;
import ddf.minim.AudioPlayer;

public abstract class Node extends Entity implements ControlListener {
    public Shape          shape;
    protected Scanner     scanner;
    private boolean       isScannerIn;
    protected PVector[]   points;
    protected boolean     isPlaced;
    protected boolean     isNew;
    protected boolean     isSelected;
    protected boolean     initMouseDrag;
    private String      soundFileName;
    private int      soundFileIndex;
    private AudioPlayer player;
    protected float lerp;
    protected float lerpStep;
    protected int color;
    protected int lerpColor;
    protected DragDirection dragDirect;

    enum DragDirection{
        FREE, HORIZANTAL, VERTICAL, DIAGONAL
    }
    
    public Node(Composer ps) {
        this(ps, null, null);
    }
    
    public Node(Composer ps, Shape _s, Scanner _scanner) {
        super(ps);
        shape = _s;
        scanner = _scanner;
        setScannerIn(false);
        setPlaced(false);
        setNew(true);
        setSelected(false);
        initMouseDrag = false;
        points = new PVector[0];
        lerp = 1;
        lerpStep = 0.08f;
        color = ps.color(200,0,200);
        lerpColor = ps.color(200,0,200);
        dragDirect = DragDirection.FREE;
    }
    
    // Update Logic
    
    public void update() {
        if (!isPlaced()) {
            switch(dragDirect)
            {
                case FREE:
                    shape.setLoc(ps.mouseX, ps.mouseY);
                    break;
                case DIAGONAL:
                    float x = shape.getLoc().x;
                    float y = shape.getLoc().y;
                    float d = ps.max(ps.abs(x), ps.abs(y));
                    float dx = (x > 0) ? d : -d;
                    float dy = (y > 0) ? d : -d;
                    shape.setLoc(dx, dy);
                    break;
                case VERTICAL:
                    shape.setLoc(shape.getLoc().x, ps.mouseY);
                    break;
                case HORIZANTAL:
                    shape.setLoc(ps.mouseX, shape.getLoc().y);
                    break;
            }
            return;
        }
        
        stepLerp();
//        updateShapeWithLerp();

        points = shape.checkCollision(scanner.getLine());
        setScannerIn((points.length > 0));
        
        if (isScannerIn() && ps.getPlayingState() == PlayingState.PLAYING) {
            playSound();
            lerp = 0;
        }
    }
    
    private void stepLerp() {
        lerp += lerpStep;
        if(lerp > 1)
            lerp = 1;
    }
    
    protected abstract void updateShapeWithLerp();
    
    public void display() {
        ps.pushStyle();
        ps.fill(lerpColor);
        shape.display();
        drawCollisionPoints();
        if (isSelected()) {
            displaySelectionBorder();
        }
        ps.popStyle();
    }
    
    public abstract void displaySelectionBorder();
    
    void drawCollisionPoints() {
        for (PVector p : points) {
            ps.ellipse(p.x, p.y, 5, 5);
        }
    }
    
    void playSound() {
        if(!getPlayer().isPlaying())
            getPlayer().rewind();

        if (getPlayer() != null)
            getPlayer().play();
    }
    
    void pauseSound() {
        if (getPlayer() != null)
            getPlayer().pause();
    }
    
    void resetSound() {
        if (getPlayer() != null) {
            getPlayer().rewind();
            pauseSound();
        }
    }
    
    public void setSound(String fileName, int fileIndex) {
        setPlayer(ps.minim.loadFile(ps.audioFilesPath + "/" + fileName));
        soundFileName = fileName;
        soundFileIndex = fileIndex;
    }
    
    boolean isSelected() {
        return isSelected;
    }
    
    void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    // Events
    
    public void keyPressed(KeyEvent event) {
        if(event.getKey() == ps.DELETE && isSelected())
        {
            ps.deleteNode(this);
        }
        modifyDrag(event);
    }
    
    private void modifyDrag(KeyEvent event) {
        if(ps.key == ps.CODED)
        {
            if(event.isShiftDown())
            {
                dragDirect = DragDirection.VERTICAL;
            }
            else if(event.isControlDown())
            {
                dragDirect = DragDirection.HORIZANTAL;
            }
            else
            {
                dragDirect = DragDirection.FREE;
            }
        }
    }
    
    void mousePressed(MouseEvent event) {
        if (isMouseIn()) {
            initMouseDrag = true;
        }
    }
    
    void mouseClicked(MouseEvent event) {
    }
    
    void mouseDragged(MouseEvent event) {
        if (isMouseIn() && initMouseDrag == true) {
            setPlaced(false);
        }
    }
    
    void mouseMoved(MouseEvent event) {
    }
    
    void mouseReleased(MouseEvent event) {
        if (isNew())
            setNew(false);
        else
            setPlaced(true);
        
        initMouseDrag = false;
    }
    
    void mouseWheel(MouseEvent event) {
    }
    
    @Override
    public void handleControlEvent(ControlEvent event) {
        String eName = event.getName();
        
        if (eName == "stopScene" && getPlayer() != null) {
            resetSound();
        } 
        else if (eName == "playScene" && getPlayer() != null) {
            switch(ps.getPlayingState()){
                case PLAYING:
                    pauseSound();
                    break;
                case PAUSED:
//                    playSound();
                    break;
                case STOPPED:
//                    resetSound();
                    break;
                default:
                    break;
            }
        }
    }
    
    // Setters and Getters

    public boolean isPlayerPlaying() {
        if (getPlayer() == null)
            return false;
        
        return getPlayer().isPlaying();
    }
    
    public abstract boolean isMouseIn();
    
    protected boolean isPlaced() {
        return isPlaced;
    }
    
    protected void setPlaced(boolean isPlaced) {
        this.isPlaced = isPlaced;
    }
    
    public boolean isNew() {
        return isNew;
    }
    
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    protected boolean isScannerIn() {
        return isScannerIn;
    }
    
    protected void setScannerIn(boolean isScannerIn) {
        this.isScannerIn = isScannerIn;
    }

    protected AudioPlayer getPlayer() {
        return player;
    }

    protected void setPlayer(AudioPlayer player) {
        this.player = player;
    }

    public String getSoundFileName() {
        return soundFileName;
    }

    public int getSoundFileIndex() {
        return soundFileIndex;
    }

}
