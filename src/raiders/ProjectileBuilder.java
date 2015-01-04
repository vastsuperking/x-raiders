package raiders;

import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.Mass.Type;
import org.dyn4j.geometry.Rectangle;

import commons.Transform2f;
import commons.matrix.Vector2f;

import engine.core.Entity;
import engine.core.EntityBuilder;
import engine.core.asset.AssetManager;
import engine.core.script.XJava;
import engine.core.script.XScript;
import engine.imp.physics.dyn4j.CBody;
import engine.imp.physics.dyn4j.CollisionFilter;
import engine.imp.render.CRender;
import engine.imp.render.Material2D;

/**
 * Builds a projectile.
 */
public class ProjectileBuilder extends EntityBuilder {
	public static final int LAYER = 3;

	public ProjectileBuilder(Material2D image, Vector2f scale, String targetTag, final String ignoreTag, float damage) {
		AssetManager assets = AssetManager.instance();
		XScript bulletScript = assets.get("BulletScript", XJava.class);
		XScript damageScript = assets.get("DamageScript", XJava.class);

		this.getScriptData().put("damage", damage);
		this.getScriptData().put("target_tag", targetTag);

		CBody physics = new CBody();
		physics.setShape(new Rectangle(scale.getX(), scale.getY()));
		physics.setGravityScale(0f);
		physics.setMassType(Type.NORMAL);
		physics.setDensity(1);
		physics.setCollisionFriction(1);
		physics.setCollisionFilter(new CollisionFilter() {
			@Override
			public boolean canCollide(Entity entity1, Entity entity2) {
				if (entity2.tags().getTags().hasTag(ignoreTag))
					return false;

				return true;
			}

			@Override
			public boolean continueCollision(Entity entity1, Entity entity2, ContactConstraint constraint) {
				return false;
			}
		});

		this.addComponentBuilder(physics);
		CRender render = new CRender(image, LAYER, 1f);
		this.addComponentBuilder(render);
		this.addScript(bulletScript);
		this.addScript(damageScript);

		Transform2f trans = new Transform2f();
		trans.setScale(scale);
		this.setTransform(trans);
	}
}
