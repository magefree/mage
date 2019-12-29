package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AtlaPalaniToken extends TokenImpl {

    public AtlaPalaniToken() {
        super("Egg", "0/1 green Egg creature token with defender");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.EGG);
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(1);
        addAbility(DefenderAbility.getInstance());
    }

    private AtlaPalaniToken(final AtlaPalaniToken token) {
        super(token);
    }

    public AtlaPalaniToken copy() {
        return new AtlaPalaniToken(this);
    }
}
