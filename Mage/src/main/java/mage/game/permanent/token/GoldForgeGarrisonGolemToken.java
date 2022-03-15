package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GoldForgeGarrisonGolemToken extends TokenImpl {

    public GoldForgeGarrisonGolemToken() {
        super("Golem Token", "4/4 colorless Golem artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);

        subtype.add(SubType.GOLEM);
        power = new MageInt(4);
        toughness = new MageInt(4);

    }

    public GoldForgeGarrisonGolemToken(final GoldForgeGarrisonGolemToken token) {
        super(token);
    }

    public GoldForgeGarrisonGolemToken copy() {
        return new GoldForgeGarrisonGolemToken(this);
    }
}
