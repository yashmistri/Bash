package bash.Character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import bash.InputManager;
import bash.Screen;
import bash.Stage;
import bash.Attack.SideLight;

/*
 * TO DO:
 * implement finite state machine
 */
public class Character
{
	private double x;
	private double y;
	private String name;

	private int startX;
	private int startY;
	private int jumps = 3;
	private int wallJumpTimerLeft = 0;
	private int wallJumpTimerRight = 0;
	private int score = 0;
	private int damage = 0;

	private int up;
	private int down;
	private int left;
	private int right;
	private int lightAttack;

	private double speedX = 0;
	private double fastfall = 0;
	private double gravity = 0;

	private boolean hasMovedLeft = false;
	private boolean hasMovedRight = false;
	private boolean showLeft = false;
	private boolean showRight = true;
	public static final int START_LEFT = 0;
	public static final int START_RIGHT = 1;

	private boolean hasJumped = false;
	private boolean hasFastfallen = false;
	private boolean wallJumpLeft = false;
	private boolean wallJumpRight = false;
	private boolean isJumped = false;
	private boolean canJump = true;
	private boolean canMoveLeft = true;
	private boolean canMoveRight = true;

	private final int NUMBER_JUMPS = 3;
	private final double CONSTANT = .1;

	private final int WALLJUMP_TIMER = (int) (150 * CONSTANT);
	private final double GRAVITY_INCREMENT = 1 * CONSTANT;
	private final double INITIAL_GRAVITY = 30 * CONSTANT;
	private final double MASTER_SPEED = 20 * CONSTANT;
	private final double FASTFALL = 3 * CONSTANT;

	private Image characterImage;

	private Screen screen;
	private InputManager input;
	private State characterState;
	private State inputState;

	private SideLight sLight = new SideLight(this);

	public Character(int x, int y, Screen screen, InputManager input, String name, int startDirection)
	{
		this.startX = x;
		this.startY = y;
		this.x = x;
		this.y = y;
		this.screen = screen;
		this.input = input;
		this.name = name;
		if (startDirection == START_LEFT)
		{
			showLeft = true;
			showRight = false;
		} else
		{
			showLeft = false;
			showRight = true;
		}
	}

	public void paint(Graphics2D g)
	{
		if (showRight)
		{
			g.drawImage(characterImage, (int) x + 100, (int) y,
					-100, 100, null);
		} else if (showLeft)
		{
			g.drawImage(characterImage, (int) x, (int) y, 100, 100, null);
		}
		if (sLight.isPaintAttack())
		{
			sLight.left.paint(g);
		}
		g.setColor(Color.WHITE);
		g.drawString("Jumps: " + jumps, (int) x, (int) y - 30);
		g.drawString(name, (int) x, (int) y - 10);
	}

	public void paintHitboxes(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		g.fill(getBounds());
		if (sLight.isPaintAttack())
		{
			sLight.left.paintHitboxes(g);
		}
		g.setColor(Color.WHITE);
		g.drawString("Jumps: " + jumps, (int) x, (int) y - 30);
		g.drawString(name, (int) x, (int) y - 10);
	}

	private void move()
	{

		if (!hasMovedLeft && hasMovedRight && canMoveRight)// moves right
		{
			speedX = MASTER_SPEED;
			showRight = true;
			showLeft = false;
		} else if (!hasMovedRight && hasMovedLeft && canMoveLeft)// moves left
		{
			speedX = -MASTER_SPEED;
			showRight = false;
			showLeft = true;
		} else// is not moving left or right
		{
			speedX = 0;
		}

		if (hasJumped && canJump && jumps > 0 && !hasFastfallen)
		{
			gravity = -INITIAL_GRAVITY;
			jumps--;
			fastfall = 0;
			canJump = false;
			if (wallJumpLeft)
				wallJumpTimerLeft = WALLJUMP_TIMER;
			if (wallJumpRight)
				wallJumpTimerRight = WALLJUMP_TIMER;
		}

		if (wallJumpTimerRight > 0)
		{
			canMoveLeft = false;
			speedX = MASTER_SPEED;
			wallJumpTimerRight--;
		}

		if (wallJumpTimerLeft > 0)
		{
			canMoveRight = false;
			speedX = -MASTER_SPEED;
			wallJumpTimerLeft--;
		}

		x += speedX;

		if (hasFastfallen && !hasJumped)
		{
			fastfall = FASTFALL;
		}

		if (getBounds().intersects(screen.getCurrentStage().getTopBounds()))// top
		{
			characterState = State.STANDING;
			jumps = NUMBER_JUMPS;
			if (input.isKeyUp(up))
			{
				canJump = true;
			}
			if (!hasJumped || gravity > 0)
			{
				gravity = 0;
			}
		} else if (getBounds().intersects(screen.getCurrentStage().getRightBounds())// right
				&& !getBounds().intersects(screen.getCurrentStage().getBottomBounds()))
		{
			canMoveLeft = false;
			jumps = NUMBER_JUMPS;
			wallJumpRight = true;
			wallJumpLeft = false;
			if (input.isKeyUp(up))
			{
				canJump = true;
			}
			if (!hasJumped || (hasJumped && !canJump))
			{
				gravity = INITIAL_GRAVITY / 4;
			}
		} else if (getBounds().intersects(screen.getCurrentStage().getLeftBounds())// left
				&& !getBounds().intersects(screen.getCurrentStage().getBottomBounds()))
		{
			canMoveRight = false;
			jumps = NUMBER_JUMPS;
			wallJumpLeft = true;
			wallJumpRight = false;
			if (input.isKeyUp(up))
			{
				canJump = true;
			}
			if (!hasJumped || (hasJumped && !canJump))
			{
				gravity = INITIAL_GRAVITY / 4;
			}
		} else if (getBounds().intersects(screen.getCurrentStage().getBottomBounds()))// bottom
		{
			gravity = -gravity;
		} else// in the air
		{
			wallJumpLeft = false;
			wallJumpRight = false;
			canMoveRight = true;
			canMoveLeft = true;
			gravity += GRAVITY_INCREMENT + fastfall;
			if (jumps > 0 && !hasJumped)
			{
				canJump = true;
			}

			if (!hasFastfallen)
			{
				fastfall = 0;
			}
		}

		if (y > screen.getHeight() + 200 || y < -200 || x < -200 || x > screen.getWidth() + 200)
		{// fell off stage
			x = startX;
			y = startY;
			gravity = INITIAL_GRAVITY;
			canJump = true;
			score--;
		}

		y += gravity;
	}

	private void attack()
	{
		if (sLight.isAttacking())
		{
			sLight.setPaintAttack(true);
		} else
		{
			sLight.setPaintAttack(false);
		}
	}

	public void update()
	{
		updateKeys();
		move();
		attack();
	}

	/*
	 * implement Finite State Machine
	 */
	public void updateKeys()
	{
		if (input.isKeyDown(left))
		{
			hasMovedLeft = true;
		}

		if (input.isKeyDown(right))
		{
			hasMovedRight = true;
		}

		if (input.isKeyDown(up))
		{
			hasJumped = true;
		}

		if (input.isKeyDown(down))
		{
			hasFastfallen = true;
		}

		if (input.isKeyDown(lightAttack))
		{
			sLight.setAttacking(true);
			canMoveLeft = false;
		}

		if (input.isKeyUp(left))
		{
			hasMovedLeft = false;
		}

		if (input.isKeyUp(right))
		{
			hasMovedRight = false;
		}

		if (input.isKeyUp(up))
		{
			hasJumped = false;
		}

		if (input.isKeyUp(down))
		{
			hasFastfallen = false;
		}

		if (input.isKeyUp(lightAttack))
		{
			sLight.setAttacking(false);
			canMoveLeft = true;
		}

	}

	public void setControls(int up, int down, int left, int right, int lightAttack)
	{
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.lightAttack = lightAttack;
	}

	public void moveLeft()
	{
		speedX = -MASTER_SPEED;
	}

	public void moveRight()
	{
		speedX = MASTER_SPEED;
	}

	public void fastfall()
	{
		fastfall = FASTFALL;
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int) x, (int) y, characterImage.getWidth(null), characterImage.getHeight(null));
	}

	public int getWidth()
	{
		return characterImage.getWidth(null);
	}

	public int getHeight()
	{
		return characterImage.getHeight(null);
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public double getSpeed()
	{
		return MASTER_SPEED;
	}

	public double getSpeedX()
	{
		return speedX;
	}

	public double getMASTER_SPEED()
	{
		return MASTER_SPEED;
	}

	public double getGravity()
	{
		return gravity;
	}

	public void setSpeedX(double speedX)
	{
		this.speedX = speedX;
	}

	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}

	public boolean isJumped()
	{
		return isJumped;
	}

	public void setJumped(boolean isJumped)
	{
		this.isJumped = isJumped;
	}

	public boolean isCanMoveLeft()
	{
		return canMoveLeft;
	}

	public boolean isCanMoveRight()
	{
		return canMoveRight;
	}

	public void setCanMoveLeft(boolean canMoveLeft)
	{
		this.canMoveLeft = canMoveLeft;
	}

	public void setCanMoveRight(boolean canMoveRight)
	{
		this.canMoveRight = canMoveRight;
	}

	public double getINITIAL_GRAVITY()
	{
		return INITIAL_GRAVITY;
	}

	public int getStartX()
	{
		return startX;
	}

	public int getStartY()
	{
		return startY;
	}

	public void setStartX(int startX)
	{
		this.startX = startX;
	}

	public void setStartY(int startY)
	{
		this.startY = startY;
	}

	public Image getCharacterImage()
	{
		return characterImage;
	}

	public String getName()
	{
		return name;
	}

	public void setCharacterImage(Image characterImage)
	{
		this.characterImage = characterImage;
	}

	public int getScore()
	{
		return score;
	}
}
