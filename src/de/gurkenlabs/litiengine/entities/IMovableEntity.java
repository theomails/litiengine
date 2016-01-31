package de.gurkenlabs.litiengine.entities;

import java.awt.geom.Point2D;
import java.util.function.Consumer;

import de.gurkenlabs.litiengine.physics.IEntityNavigator;

public interface IMovableEntity extends ICollisionEntity {

  public float getFacingAngle();

  public Direction getFacingDirection();
  
  public void setFacingDirection(Direction facingDirection);

  public IEntityNavigator getNavigator();

  public float getVelocityInPixelsPerSecond();

  public boolean isIdle();

  public void onMoved(Consumer<IMovableEntity> consumer);

  /**
   * Sets the facing direction.
   *
   * @param orientation
   *          the new facing direction
   */
  public void setFacingAngle(float orientation);

  /**
   * Sets the map location.
   *
   * @param location
   *          the new map location
   */
  public void setLocation(Point2D location);

  public void setNavigator(IEntityNavigator navigator);
}
