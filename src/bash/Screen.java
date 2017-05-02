package bash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bash.Character.Character;

/*
 * TODO:
 * implement attack
 * implement menu/starting screen
 */
@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable
{
	private Thread animator;
	private InputManager input;
	private Dimension size = new Dimension(1000, 600);

	private final Point STAGE_START = new Point((int) size.getWidth() / 2 - 200, (int) size.getHeight()/2);
	private final Point CHARACTER_START = new Point((int)STAGE_START.getX(), (int)STAGE_START.getY()-100);
	private final int DELAY = 15;

	private final String controls = "Movement Player 1: I J K L\n" + "Movement Player 2: W A S D\n" + "Press Shift to view hitboxes\n" + "Press Esc to exit";

	private ArrayList<Character> characters = new ArrayList<Character>();

	private Stage stageOne;
	private Stage currentStage;

	public Screen()
	{
		setPreferredSize(size);
		setFocusable(true);
		input = new InputManager();
		addKeyListener(input);

		stageOne = new Stage(STAGE_START.x, STAGE_START.y);
		currentStage = stageOne;
		stageOne.setImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Stages/stageOne.png")));
		stageOne.setBg(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Stages/stageOneBg.png")));

		characters.add(new Character(CHARACTER_START.x, CHARACTER_START.y, this, input, "Basherman", Character.START_RIGHT));
		characters.get(0).setControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_F);
		characters.get(0).setCharacterImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Characters/basherman.png")));
		
		characters.add(new Character(CHARACTER_START.x + 300, CHARACTER_START.y, this, input, "Dot Com", Character.START_LEFT));
		characters.get(1).setControls(KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON);
		characters.get(1).setCharacterImage(
				Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Characters/dotcom.png")));
	}

	@Override
	public void addNotify()
	{
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(new Font("Calibri", Font.PLAIN, 20));

		if (input.isKeyDown(KeyEvent.VK_SHIFT))
		{
			stageOne.paintHitboxes(g2d);
			for(Character x : characters)
			{
				x.paintHitboxes(g2d);
			}
		} else
		{
			stageOne.paint(g2d);
			for(Character x : characters)
			{
				x.paint(g2d);
			}
		}
	}

	public void drawScore(Graphics2D g){
		int offset = 10;
		for(Character x : characters)
		{
			g.setColor(Color.WHITE);
			g.drawString(x.getName() + ": " + x.getScore(), offset, 10);
			offset += 100;
		}
	}
	public void checkIfClosed()
	{
		if (input.isKeyDown(KeyEvent.VK_ESCAPE))
		{
			System.exit(ABORT);
		}
	}

	public void update()
	{
		for(Character x : characters)
		{
			x.update();
		}
		repaint();
		checkIfClosed();
		input.update();
	}

	@Override
	public void run()
	{
		long beforeTime, timeDifference, sleep;

		beforeTime = System.currentTimeMillis();

		JOptionPane.showMessageDialog(this, controls, "Controls", JOptionPane.PLAIN_MESSAGE);

		while (true)
		{
			update(); 

			timeDifference = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDifference;

			if (sleep < 0)
			{
				sleep = 2;
			}

			try
			{
				Thread.sleep(sleep);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	public Dimension getSize()
	{
		return size;
	}

	public Point getCHARACTER_START()
	{
		return CHARACTER_START;
	}

	public Point getSTAGE_START()
	{
		return STAGE_START;
	}

	public Stage getStageOne()
	{
		return stageOne;
	}

	public Stage getCurrentStage()
	{
		return currentStage;
	}

	public ArrayList<Character> getCharacters()
	{
		return characters;
	}

	public void setSize(Dimension size)
	{
		this.size = size;
	}

}
