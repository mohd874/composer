package ae.mohd874.interfaces;

import processing.core.PVector;

public interface Collidable {
    PVector[] checkCollision(Collidable n);
}
