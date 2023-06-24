package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Styxo
 */
public final class TIEFighterToken extends TokenImpl {

    public TIEFighterToken() {
        super("TIE Fighter", "1/1 black Starship artifact creature tokens with Spaceflight named TIE Fighter");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        color.setBlack(true);
        addAbility(SpaceflightAbility.getInstance());
        subtype.add(SubType.STARSHIP);
    }

    public TIEFighterToken(final TIEFighterToken token) {
        super(token);
    }

    public TIEFighterToken copy() {
        return new TIEFighterToken(this);
    }
}
