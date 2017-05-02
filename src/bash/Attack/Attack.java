package bash.Attack;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import bash.Character.Character;

public class Attack
{
	private double x;
	private double y;

	private int width;
	private int height;

	private int damage;

	private boolean paintAttack = false;
	private boolean attacking = false;

	private Character character;

	public Attack(Character character)
	{
		this.setCharacter(character);
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getDamage()
	{
		return damage;
	}

	public Character getCharacter()
	{
		return character;
	}

	public boolean isPaintAttack()
	{
		return paintAttack;
	}

	public boolean isAttacking()
	{
		return attacking;
	}

	public void setPaintAttack(boolean paintAttack)
	{
		this.paintAttack = paintAttack;
	}

	public void setAttacking(boolean attacking)
	{
		this.attacking = attacking;
	}

	public void setCharacter(Character character)
	{
		this.character = character;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}
}
