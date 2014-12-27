package raiders;

import commons.Transform2f;

import engine.core.EntityBuilder;
import engine.core.script.XScript;
import engine.imp.render.CRender;
import engine.imp.render.Material2D;

/**
 * Builds a weapon.
 */
public class WeaponBuilder extends EntityBuilder {
	public static final int LAYER = 3;

	public WeaponBuilder(Material2D image, Transform2f transform) {
		this.addComponentBuilder(new CRender(image, LAYER, 1f));
		this.setTransform(transform);
	}

	public WeaponBuilder(Material2D image, Transform2f transform, XScript script) {
		this.addComponentBuilder(new CRender(image, LAYER, 1f));
		this.addScript(script);
		this.setTransform(transform);
	}
}
