package bash.Attack;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bash.Character.Character;

public class SideLight extends Attack
{
	public Left left = new Left();
	public Right right = new Right();
	
	public SideLight(Character character)
	{
		super(character);
		super.setWidth(50);
		super.setHeight(20);
		super.setDamage(10);
	}

	public class Left
	{
		public void paint(Graphics2D g)
		{
			g.setColor(Color.BLUE);
			g.fill(getBounds());
		}

		public void paintHitboxes(Graphics2D g)
		{
			g.setColor(new Color(100, 100, 255, 150));
			g.fill(getBounds());
		}
		
		public Rectangle getBounds()
		{
			return new Rectangle((int) getCharacter().getX() - getWidth(), (int) getCharacter().getY(), getWidth(),
					getHeight());
		}
	}

	public class Right
	{
		public void paint(Graphics2D g)
		{
			g.setColor(Color.BLUE);
			g.fill(getBounds());
		}
		
		public void paintHitboxes(Graphics2D g)
		{
			g.setColor(new Color(100, 100, 255, 150));
			g.fill(getBounds());
		}

		public Rectangle getBounds()
		{
			return new Rectangle((int) getCharacter().getX() + getCharacter().getWidth(),
					(int) getCharacter().getY(), getWidth(), getHeight());
		}
	}
}
