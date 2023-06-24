package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author weirddan455
 */
public final class Elemental21TrampleHasteToken extends TokenImpl {

    public Elemental21TrampleHasteToken() {
        super("Elemental Token", "2/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private Elemental21TrampleHasteToken(final Elemental21TrampleHasteToken token) {
        super(token);
    }

    @Override
    public Elemental21TrampleHasteToken copy() {
        return new Elemental21TrampleHasteToken(this);
    }
}
