package ae.mohd874;

public class Entity {
    
    protected String name;
    protected Composer ps;
    
    public Entity(Composer _ps) {
        ps = _ps;
        name = this.getClass().getName() + this.getClass().toString();
    }
}
