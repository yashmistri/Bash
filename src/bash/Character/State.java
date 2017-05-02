package bash.Character;

import bash.InputManager;
import bash.Character.CharacterStates.*;
import bash.Character.InputStates.*;

public interface State
{
	public static final JumpingState JUMPING = new JumpingState();
	public static final StandingState STANDING = new StandingState();
	
	public static final DownState DOWN = new DownState();
	public static final UpState UP = new UpState();
	public static final LeftState LEFT = new LeftState();
	public static final RightState RIGHT = new RightState();

	public void handleInput(Character c, InputManager input);

	public void update(Character c);

}