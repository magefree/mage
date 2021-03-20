package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class GaeasAnthem extends CardImpl {

    public GaeasAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, false
        )));
    }

    private GaeasAnthem(final GaeasAnthem card) {
        super(card);
    }

    @Override
    public GaeasAnthem copy() {
        return new GaeasAnthem(this);
    }
}
