package bash.Attack;

import bash.Character.Character;

public class NeutralLight extends Attack
{
	public NeutralLight(Character character)
	{
		super(character);
		super.setWidth(30);
		super.setHeight(20);
		super.setDamage(10);
	}
}
