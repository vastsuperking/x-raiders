package raiders;

import org.dyn4j.geometry.Mass.Type;
import org.dyn4j.geometry.Rectangle;

import commons.matrix.Vector2f;

import engine.core.CTransform.TransformMode;
import engine.core.EntityBuilder;
import engine.core.script.XScript;
import engine.imp.physics.dyn4j.CBody;
import engine.imp.render.CRender;
import engine.imp.render.Material2D;

/**
 * Builds a spaceship.
 */
public class SpaceshipBuilder extends EntityBuilder {
	public static final int LAYER = 2;
	public static final String WEAPON_COUNT = "weapon_count";

	public SpaceshipBuilder(Material2D image, Vector2f scale, XScript script, EntityBuilder... weapons) {
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

		for (int i = 0; i < weapons.length; i++) {
			EntityBuilder weapon = weapons[i];
			weapon.setTransformMode(TransformMode.RTRANSLATE);
			this.addChildBuilder("weapon" + i, weapons[i]);
		}

		this.getScriptData().put(WEAPON_COUNT, weapons.length);
	}
}