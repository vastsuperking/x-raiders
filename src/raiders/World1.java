package raiders;

import commons.Transform2f;
import commons.matrix.Vector2f;

import engine.core.Entity;
import engine.core.EntityBuilder;
import engine.core.Game;
import engine.core.Scene;
import engine.core.asset.AssetManager;
import engine.core.script.XJava;
import engine.core.script.XScript;
import engine.imp.render.CCamera;
import engine.imp.render.CLight;
import engine.imp.render.CRender;
import engine.imp.render.LightFactory;
import engine.imp.render.Material2D;
import glcommon.Color;

public class World1 extends Scene {
	public static final int STAR_LAYER = 0;
	public static final int PLANET_LAYER = 1;

	public World1(Game game) {
		super(game);

		makePlayer();
		makeSun();
	}

	private void makePlayer() {
		AssetManager assets = AssetManager.instance();
		Material2D fighter = assets.get("fighter", Material2D.class);
		XScript playerScript = assets.get("PlayerScript", XJava.class);
		Vector2f playerScale = new Vector2f(0.5f, 1f);

		WeaponBuilder gun1 = new WeaponBuilder(fighter, new Transform2f(new Vector2f(0.4f, -0.1f), 0f, new Vector2f(0.2f, 0.3f)));
		WeaponBuilder gun2 = new WeaponBuilder(fighter, new Transform2f(new Vector2f(-0.4f, -0.1f), 0f, new Vector2f(0.2f, 0.3f)));
		SpaceshipBuilder builder = new SpaceshipBuilder(fighter, playerScale, playerScript, gun1, gun2);
		Entity player = this.createEntity("player", this, builder);

		EntityBuilder cameraBuilder = new EntityBuilder();
		cameraBuilder.addComponentBuilder(new CCamera(1f, true));
		this.createEntity("camera", player, cameraBuilder);
	}

	private void makeSun() {
		AssetManager assets = AssetManager.instance();
		Material2D sun = assets.get("red_sun", Material2D.class);

		Vector2f sunPosition = new Vector2f(0f, 0f);
		Vector2f sunScale = new Vector2f(1f, 1f);

		EntityBuilder sunBuilder = new EntityBuilder();
		sunBuilder.addComponentBuilder(new CRender(sun, PLANET_LAYER, 2f));
		sunBuilder.addComponentBuilder(new CLight(LightFactory.createAmbient(new Color(0.5f, 0.2f, 0.2f))));
		Entity sunEntity = this.createEntity("red_sun", this, sunBuilder);
		sunEntity.transform().setTransform(new Transform2f(sunPosition, 0f, sunScale));
	}
}
