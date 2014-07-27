class Scanner extends Node implements Collidable {
  Line line;
  float speed;
  PVector dir;
  PVector accel;
  PVector loc;
  int size;
  float initX;
  float initY;
  
  Scanner(float _initX, float _initY, float _speed, float _angle){
    super();
    size = 80;
    speed = _speed;
    float angle = _angle;
    initX = _initX;
    initY = _initY;
    
    isNew = false;
    
    dir = new PVector(0,0);
    accel = new PVector(0,0);
    loc = new PVector(initX, initY);
    
    PVector p1 = new PVector(0,0);
    PVector p2 = new PVector(0,0);
    
    line = new Line(p1, p2);

    setDirAngle(angle);    
    setAccel(angle);
    resetLoc();
  }
  
  void update(){
    if(!isPlaced)
    {
      line.setLoc(mouseX, mouseY);
      return;
    }
    
    if(isPlaying)
    {
      line.p1.add(accel);
      line.p2.add(accel);
    }
  }
  
  void display(){
    stroke(2);
    line.display();
  }
  
  void displaySelectionBorder(){}
  
  void setLoc(float _x, float _y)
  {
    initX = _x;
    initY = _y;
    line.setLoc(_x, _y);
  }
  
  void resetLoc()
  {
    line.p1.set(initX - (dir.y * (size/2))
         , initY - (dir.x * (size/2)));

    line.p2.set(initX + (dir.y * (size/2))
         , initY + (dir.x * (size/2)));
  }
  
  void setDirAngle(float deg)
  {
    dir.set(-cos(radians(deg)), sin(radians(deg)));
  }
  
  void setAccel(float deg)
  {
    accel.set(cos(radians(deg)) * speed, sin(radians(deg)) * speed);
  }
  
  PVector[] checkCollision(Collidable n)
  {
    return line.checkCollision(n);
  }
  
  boolean isMouseIn(){return false;}
}

