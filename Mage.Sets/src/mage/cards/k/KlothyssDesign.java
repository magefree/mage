package mage.cards.k;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KlothyssDesign extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.G);

    public KlothyssDesign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Creatures you control get +X/+X until end of turn, where X is your devotion to green.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES,
                false, true
        ));
        this.getSpellAbility().addHint(new ValueHint("Devotion to green", xValue));
    }

    private KlothyssDesign(final KlothyssDesign card) {
        super(card);
    }

    @Override
    public KlothyssDesign copy() {
        return new KlothyssDesign(this);
    }
}
