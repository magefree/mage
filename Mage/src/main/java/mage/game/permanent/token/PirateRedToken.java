package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PirateRedToken extends TokenImpl {

    public PirateRedToken() {
        super("Pirate Token", "1/1 red Pirate creature token with menace and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.PIRATE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new MenaceAbility(false));
        addAbility(HasteAbility.getInstance());
    }

    public PirateRedToken(final PirateRedToken token) {
        super(token);
    }

    public PirateRedToken copy() {
        return new PirateRedToken(this);
    }
}
