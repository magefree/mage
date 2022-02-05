package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SpiritRedToken extends TokenImpl {

    public SpiritRedToken() {
        super("Spirit token", "2/2 red Spirit creature token with menace");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(new MenaceAbility());
    }

    public SpiritRedToken(final SpiritRedToken token) {
        super(token);
    }

    public SpiritRedToken copy() {
        return new SpiritRedToken(this);
    }
}
