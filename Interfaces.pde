interface Collidable
{
  PVector[] checkCollision(Collidable n);
}

interface MouseListner
{
  void mousePressed();
  void mouseClicked();
  void mouseDragged();
  void mouseMoved();
  void mouseReleased();
  void mouseWheel();
  boolean isMouseIn();
}

