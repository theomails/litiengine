package de.gurkenlabs.litiengine.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

import de.gurkenlabs.litiengine.entities.Creature;

public class CollisionResolvingTests {
  final double EPSILON = 0.02;

  @Test
  public void testBasicMovement() {
    Creature ent = getNewCreature();

    IPhysicsEngine engine = new PhysicsEngine();
    engine.add(ent);

    // large rectangle at the bottom of the entity
    Rectangle2D rect1 = new Rectangle2D.Double(0, 25, 100, 10);
    engine.add(rect1);

    // move 10 px to the right
    engine.update();
    engine.move(ent, 90, 10);

    assertEquals(20, ent.getX(), EPSILON);
    assertEquals(10, ent.getY(), EPSILON);

    // move back 10 px to the left
    engine.move(ent, 270, 10);

    assertEquals(10, ent.getX(), EPSILON);

    // move 5 px up where no collision is
    engine.move(ent, 180, 5);

    assertEquals(5, ent.getY(), EPSILON);
    assertEquals(10, ent.getX(), EPSILON);
  }

  @Test
  public void testCollidingHorizontalMovement() {
    Creature ent = getNewCreature();

    IPhysicsEngine engine = new PhysicsEngine();
    engine.add(ent);
    
    // large rectangle at the bottom of the entity
    Rectangle2D rect1 = new Rectangle2D.Double(0, 20, 100, 10);
    engine.add(rect1);

    // move 10 px to the right
    engine.update();
    engine.move(ent, 90, 10);

    assertEquals(20, ent.getX(), EPSILON);
    assertEquals(10, ent.getY(), EPSILON);

    // move back 10 px to the left
    engine.move(ent, 270, 10);

    assertEquals(10, ent.getX(), EPSILON);
    
    // now "slide" along the rectangle to the bottom right
    engine.move(ent, 45, 14.14213562373095);
    
    assertEquals(20, ent.getX(), EPSILON);
    assertEquals(10, ent.getY(), EPSILON);
    
    // now "slide" along the rectangle to the bottom left
    engine.move(ent, 315, 14.14213562373095);
    
    assertEquals(10, ent.getX(), EPSILON);
    assertEquals(10, ent.getY(), EPSILON);
  }
  
  @Test
  public void testCollidingVerticalMovement() {
    Creature ent = getNewCreature();

    IPhysicsEngine engine = new PhysicsEngine();
    engine.add(ent);
    
    // large rectangle at the right of the entity
    Rectangle2D rect1 = new Rectangle2D.Double(20, 0, 10, 100);
    engine.add(rect1);
    
    // move 10 px down
    engine.update();
    engine.move(ent, 0, 10);

    assertEquals(10, ent.getX(), EPSILON);
    assertEquals(20, ent.getY(), EPSILON);

    // move back 10 px up
    engine.move(ent, 180, 10);

    assertEquals(10, ent.getY(), EPSILON);
    
    // now "slide" along the rectangle to the bottom right

    engine.move(ent, 45, 14.14213562373095);
    
    assertEquals(10, ent.getX(), EPSILON);
    assertEquals(20, ent.getY(), EPSILON);
    
    // now "slide" along the rectangle to the top left
    engine.move(ent, 135, 14.14213562373095);
    
    assertEquals(10, ent.getX(), EPSILON);
    assertEquals(10, ent.getY(), EPSILON);
  }
  
  @Test
  public void testFailingPhysics() {
    Creature ent = getNewCreature();

    IPhysicsEngine engine = new PhysicsEngine();
    engine.add(ent);
    
    // large rectangle at the bottom of the entity
    Rectangle2D rect1 = new Rectangle2D.Double(0, 20, 50, 30);
    
    // another rectangle that creates an angle on the right side
    Rectangle2D rect2 = new Rectangle2D.Double(50, 20, 10, 100);
    engine.add(rect1);
    engine.add(rect2);
    
    // first relocate the entity
    ent.setLocation(45, 10);
    
    // move 10 px down
    engine.move(ent, 0, 10);
    
    // the movement should have been denied
    assertEquals(45.0, ent.getX(), EPSILON);
    assertEquals(10.0, ent.getY(), EPSILON);
    
    // now "slide" along the rectangle to the bottom right
    engine.move(ent, 45, 14.14213562373095);
    
    assertEquals(55.0, ent.getX(), EPSILON);
    assertEquals(10.0, ent.getY(), EPSILON);
    
    // now "slide" back
    engine.move(ent, 315, 14.14213562373095);
    
    assertEquals(45.0, ent.getX(), EPSILON);
    assertEquals(10.0, ent.getY(), EPSILON);
    
    // first relocate the entity
    ent.setLocation(55, 10);
    
    // now "slide" again
    engine.move(ent, 45, 14.14213562373095);
    
    // the entity just went through the corner
    assertEquals(65.0, ent.getX(), EPSILON);
    assertEquals(20.0, ent.getY(), EPSILON);
    
    // first relocate the entity
    ent.setLocation(49, 10);
    
    // now "slide" again
    engine.move(ent, 45, 14.14213562373095);
    
    assertEquals(10.0, ent.getY(), EPSILON);
    assertEquals(59.0, ent.getX(), EPSILON);
  }

  private static Creature getNewCreature() {
    Creature ent = new Creature();
    ent.setX(10);
    ent.setY(10);
    ent.setWidth(10);
    ent.setHeight(10);
    ent.setCollisionBoxHeight(10);
    ent.setCollisionBoxWidth(10);
    ent.setCollision(true);

    return ent;
  }
}
