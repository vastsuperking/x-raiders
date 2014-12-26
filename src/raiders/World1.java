package raiders;

import commons.matrix.Vector2f;

import engine.core.Game;
import engine.core.Scene;
import engine.core.asset.AssetManager;
import engine.core.script.XJava;
import engine.core.script.XScript;
import engine.imp.render.Material2D;

public class World1 extends Scene {
	public World1(Game game) {
		super(game);

		makePlayer();
	}

	private void makePlayer() {
		AssetManager assets = AssetManager.instance();
		Material2D snowman = assets.get("snowman", Material2D.class);
		XScript playerScript = assets.get("PlayerScript", XJava.class);

		Vector2f playerScale = new Vector2f(0.5f, 1f);

		SpaceshipBuilder builder = new SpaceshipBuilder(snowman, playerScript, playerScale);
		this.createEntity("player", this, builder);
	}
}
