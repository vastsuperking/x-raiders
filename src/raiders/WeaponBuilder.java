package raiders;

import commons.Transform2f;
import commons.matrix.Vector2f;

import engine.core.CTransform.TransformMode;
import engine.core.EntityBuilder;
import engine.core.asset.AssetManager;
import engine.core.script.XJava;
import engine.core.script.XScript;
import engine.imp.render.CRender;
import engine.imp.render.Material2D;

/**
 * Builds a weapon.
 */
public class WeaponBuilder extends EntityBuilder {
	public static final int LAYER = 3;

	private WeaponBuilder(Material2D image, Transform2f transform, Vector2f center) {
		this.setTransform(transform);

		EntityBuilder child = new EntityBuilder();
		child.setTransform(new Transform2f(center, 0f, new Vector2f(1f, 1f)));
		child.addComponentBuilder(new CRender(image, LAYER, 1f));
		this.addChildBuilder("weapon", child);
	}

	/**
	 * @param image
	 * @param transform
	 * @param center
	 * @return a weapon which doesn't do anything
	 */
	public static EntityBuilder buildBasic(Material2D image, Transform2f transform, Vector2f center) {
		return new WeaponBuilder(image, transform, center);
	}

	/**
	 * @param image
	 * @param transform
	 * @param center
	 * @param bullet
	 * @param range
	 *            in units
	 * @param speed
	 *            in units / second
	 * @param cooldown
	 *            gun cooldown in milliseconds
	 * @return a gun with a cooldown rate and the GunScript attached
	 */
	public static EntityBuilder buildGun(Material2D image, Transform2f transform, Vector2f center, EntityBuilder bullet,
			float range, float speed, float cooldown) {
		AssetManager assets = AssetManager.instance();
		XScript gun = assets.get("GunScript", XJava.class);

		bullet.setTransformMode(TransformMode.NONE);

		WeaponBuilder wBuilder = new WeaponBuilder(image, transform, center);
		EntityBuilder builder = wBuilder.getEntityBuilders().get("weapon");
		builder.addScript(gun);
		builder.getScriptData().put("cooldown", cooldown);
		builder.getScriptData().put("speed", speed);

		float time = (range / speed) * 1000;
		int numBullets = (int) (time / cooldown) + 1;
		builder.getScriptData().put("numbullets", numBullets);
		bullet.getScriptData().put("flight_time", time);

		for (int i = 0; i < numBullets; i++) {
			builder.addChildBuilder("bullet" + i, bullet);
		}

		return wBuilder;
	}
}
