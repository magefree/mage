package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author LoneFox
 */
public final class TarmogoyfToken extends TokenImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.ALL;

    public TarmogoyfToken() {
        super("Tarmogoyf Token",
                "Tarmogoyf’s power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.");
        manaCost = new ManaCostsImpl<>("{1}{G}");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.LHURGOYF);
        power = new MageInt(0);
        toughness = new MageInt(1);

        // Tarmogoyf’s power is equal to the number of card types among cards in all
        // graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(powerValue)));
    }

    private TarmogoyfToken(final TarmogoyfToken token) {
        super(token);
    }

    @Override
    public TarmogoyfToken copy() {
        return new TarmogoyfToken(this);
    }
}
