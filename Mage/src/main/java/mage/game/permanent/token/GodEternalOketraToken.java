package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GodEternalOketraToken extends TokenImpl {

    public GodEternalOketraToken() {
        super("Zombie Warrior Token", "4/4 black Zombie Warrior creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(VigilanceAbility.getInstance());
    }

    private GodEternalOketraToken(final GodEternalOketraToken token) {
        super(token);
    }

    @Override
    public GodEternalOketraToken copy() {
        return new GodEternalOketraToken(this);
    }
}
