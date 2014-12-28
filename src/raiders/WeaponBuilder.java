package raiders;

import commons.Transform2f;

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

	private WeaponBuilder(Material2D image, Transform2f transform) {
		this.addComponentBuilder(new CRender(image, LAYER, 1f));
		this.setTransform(transform);
	}

	/**
	 * @param image
	 * @param transform
	 * @return a weapon which doesn't do anything
	 */
	public static WeaponBuilder buildBasic(Material2D image, Transform2f transform) {
		return new WeaponBuilder(image, transform);
	}

	/**
	 * @param image
	 * @param transform
	 * @param bullet
	 * @param range
	 *            in units
	 * @param speed
	 *            in units / second
	 * @param cooldown
	 *            gun cooldown in milliseconds
	 * @return a gun with a cooldown rate and the GunScript attached
	 */
	public static WeaponBuilder buildGun(Material2D image, Transform2f transform, EntityBuilder bullet, float range, float speed,
			float cooldown) {
		AssetManager assets = AssetManager.instance();
		XScript gun = assets.get("GunScript", XJava.class);

		bullet.setTransformMode(TransformMode.NONE);

		WeaponBuilder builder = new WeaponBuilder(image, transform);
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

		return builder;
	}
}
