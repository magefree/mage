package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public class SpiritEvilBorosCharmToken extends TokenImpl {

    public SpiritEvilBorosCharmToken() {
        super("Spirit Token", "1/1 colorless Spirit creature token with lifelink and haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);

        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    private SpiritEvilBorosCharmToken(final SpiritEvilBorosCharmToken token) {
        super(token);
    }

    @Override
    public SpiritEvilBorosCharmToken copy() {
        return new SpiritEvilBorosCharmToken(this);
    }
}
