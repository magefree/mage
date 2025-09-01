package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class BirdVigilanceToken extends TokenImpl {

    public BirdVigilanceToken() {
        super("Bird Token", "1/1 blue Bird creature token with flying and vigilance");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
    }

    private BirdVigilanceToken(final BirdVigilanceToken token) {
        super(token);
    }

    @Override
    public BirdVigilanceToken copy() {
        return new BirdVigilanceToken(this);
    }
}
