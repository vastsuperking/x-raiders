package raiders;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import engine.core.Entity;
import engine.core.Game;
import engine.core.Scene;

/**
 * Connects to a running game and displays the Entity tree.
 */
public class Profiler {
	private Game m_game;
	private JTree m_tree;

	public Profiler(Game game) {
		m_game = game;
	}

	public void start() {
		JFrame frame = new JFrame("Profiler");
		frame.setSize(200, 500);

		m_tree = new JTree();
		update();
		frame.add(m_tree);

		frame.setLocation(0, 0);
		frame.setVisible(true);
	}

	private void display(DefaultMutableTreeNode root, Entity e) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(e.getName());
		root.add(node);
		for (Entity child : e.tree().getChildren()) {
			display(node, child);
		}
	}

	public void update() {
		Scene current = m_game.scenes().getCurrentScene();

		m_tree.setModel(null);

		DefaultMutableTreeNode scene = new DefaultMutableTreeNode("scene");
		for (Entity e : current.getRootEntities()) {
			display(scene, e);
		}

		m_tree.setModel(new DefaultTreeModel(scene));
	}
}
