package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author balazskristof
 */
public class HeroToken extends TokenImpl {

    public HeroToken() {
        super("Hero Token", "1/1 colorless Hero creature token");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HERO);
    }

    private HeroToken(final HeroToken token) { super(token); }

    public HeroToken copy() { return new HeroToken(this); }
}
