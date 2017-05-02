package bash;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Stage
{
	private double x;
	private double y;

	private Image image;
	private Image bg;

	public Stage(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics2D g)
	{
		g.drawImage(bg, 0, 0, null);
		g.drawImage(image, (int) x, (int) y, null);
	}

	public void paintHitboxes(Graphics2D g)
	{
		g.drawImage(bg, 0, 0, null);
		g.setColor(Color.CYAN);
		g.fill(getTopBounds());
		g.fill(getBottomBounds());
		g.fill(getLeftBounds());
		g.fill(getRightBounds());
	}

	public Rectangle getTopBounds()
	{
		return new Rectangle((int) x + 2, (int) y, image.getWidth(null) - 2, 1);
	}

	public Rectangle getLeftBounds()
	{
		return new Rectangle((int) x, (int) y, 1, image.getHeight(null));
	}

	public Rectangle getRightBounds()
	{
		return new Rectangle((int) (x + image.getWidth(null)), (int) y, 1, image.getHeight(null));
	}

	public Rectangle getBottomBounds()
	{
		return new Rectangle((int) x + 2, (int) (y + image.getHeight(null) - 1), image.getWidth(null) - 2, 1);
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int) x, (int) y, image.getWidth(null), image.getHeight(null));
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}
	
	public void setBg(Image bg)
	{
		this.bg = bg;
	}

}
