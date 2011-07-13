package mage.game.permanent.token;

import mage.Constants;
import mage.MageInt;

public class MyrToken extends Token {
	public MyrToken() {
		super("Myr", "1/1 colorless Myr artifact creature token");
		cardType.add(Constants.CardType.CREATURE);
		cardType.add(Constants.CardType.ARTIFACT);
		subtype.add("Myr");
		power = new MageInt(1);
		toughness = new MageInt(1);
	}
}