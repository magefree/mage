package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class ApeVillainToken extends TokenImpl {

    public ApeVillainToken() {
        super("Ape Villain Token", "3/3 red Ape Villain creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.APE);
        subtype.add(SubType.VILLAIN);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(HasteAbility.getInstance());
    }

    private ApeVillainToken(final ApeVillainToken token) {
        super(token);
    }

    public ApeVillainToken copy() {
        return new ApeVillainToken(this);
    }
}
