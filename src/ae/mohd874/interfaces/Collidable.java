package ae.mohd874.interfaces;

import java.util.List;

import processing.core.PVector;

public interface Collidable {
    List<PVector> checkCollision(Collidable n);
}
