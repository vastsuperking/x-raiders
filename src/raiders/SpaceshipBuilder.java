package raiders;

import org.dyn4j.geometry.Mass.Type;
import org.dyn4j.geometry.Rectangle;

import commons.matrix.Vector2f;

import engine.core.EntityBuilder;
import engine.core.script.XScript;
import engine.imp.physics.dyn4j.CBody;
import engine.imp.render.CRender;
import engine.imp.render.Material2D;

/**
 * Builds a spaceship.
 */
public class SpaceshipBuilder extends EntityBuilder {
	public static final int LAYER = 1;

	public SpaceshipBuilder(Material2D image, XScript script, Vector2f scale, EntityBuilder... turrets) {
		CBody physics = new CBody();
		physics.setShape(new Rectangle(scale.getX(), scale.getY()));
		physics.setGravityScale(0f);
		physics.setMassType(Type.NORMAL);
		physics.setDensity(1);
		physics.setCollisionFriction(1);

		this.addComponentBuilder(new CRender(image, LAYER, 1f));
		this.addComponentBuilder(physics);
		this.addScript(script);
		this.getTransform().setScale(scale);

		for (int i = 0; i < turrets.length; i++) {
			this.addChildBuilder("turret" + i, turrets[i]);
		}
	}
}