package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class GargoyleToken extends TokenImpl {

    public GargoyleToken() {
        super("Gargoyle Token", "3/4 colorless Gargoyle artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(3);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    protected GargoyleToken(final GargoyleToken token) {
        super(token);
    }

    public GargoyleToken copy() {
        return new GargoyleToken(this);
    }
}
