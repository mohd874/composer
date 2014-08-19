package ae.mohd874.controls;

import processing.core.PVector;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.ControllerGroup;
import controlP5.Group;

public class ControlPanelCreator {
    
    private PVector pos;
    private PVector space;
    private PVector init;
    
    private ControlP5 cp5;
    
    public ControlPanelCreator(ControlP5 cp5, PVector initPos)
    {
        init = new PVector(initPos.x, initPos.y);
        pos = new PVector(initPos.x, initPos.y);
        space = new PVector(10, 20);
        this.cp5 = cp5;
    }
    
    public ControlPanelCreator(ControlP5 cp5, int x, int y)
    {
        this(cp5, new PVector(x,y));
    }
    
    public void addController(Controller<?> c, PVector size)
    {
        addController(c, size, null);
    }

    public void addController(Controller<?> c, PVector size, Group group)
    {
        c.setPosition(pos.x, pos.y);
        c.setSize((int)size.x, (int)size.y);
        pos.add(size.x + space.x, 0f, 0f);

        if(group != null)
            c.moveTo(group);
    }
    
    
    public void addController(ControllerGroup<?> c, PVector size)
    {
        addController(c, size, null);
    }
    
    public void addController(ControllerGroup<?> c, PVector size, Group group)
    {
        c.setPosition(pos.x, pos.y);
        c.setSize((int)size.x, (int)size.y);
        pos.add(size.x + space.x, 0f, 0f);
        
        if(group != null)
            c.moveTo(group);
    }

    public void moveTo(Controller<?> c, ControllerGroup<?> dest)
    {
        c.moveTo(dest.getName());
    }
    
    public void linebreak()
    {
        pos.add(0f, space.y, 0f);
        pos.x = init.x;
    }
    
    public void reset()
    {
        pos.x = init.x;
        pos.y = init.y;
    }
}
