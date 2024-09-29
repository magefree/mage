package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class Fox22VigilanceToken extends TokenImpl {

    public Fox22VigilanceToken() {
        super("Fox Token", "2/2 white Fox creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.FOX);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private Fox22VigilanceToken(final Fox22VigilanceToken token) {
        super(token);
    }

    @Override
    public Fox22VigilanceToken copy() {
        return new Fox22VigilanceToken(this);
    }
}
