package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class StoneBoulderToken extends TokenImpl {

    public StoneBoulderToken() {
        super("Stone Boulder", "3/1 colorless Wall artifact creature token with defender named Stone Boulder");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WALL);
        power = new MageInt(3);
        toughness = new MageInt(1);

        this.addAbility(DefenderAbility.getInstance());
    }

    private StoneBoulderToken(final StoneBoulderToken token) {
        super(token);
    }

    public StoneBoulderToken copy() {
        return new StoneBoulderToken(this);
    }
}
