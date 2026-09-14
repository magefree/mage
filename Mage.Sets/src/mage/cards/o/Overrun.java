package mage.cards.o;

import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

import java.util.UUID;

/**
 *
 * @author LokiX
 */
public final class Overrun extends CardImpl {

    public Overrun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}{G}");

        // Creatures you control get +3/+3 and gain trample until end of turn.
        this.getSpellAbility().addEffect(new BoostGainAbilityGenericEffect(
                3, 3, Duration.EndOfTurn, TrampleAbility.getInstance()
        ).setTargetPointer(new FilterAllPermanentsTargetPointer(
                StaticFilters.FILTER_CONTROLLED_CREATURES)));
    }

    private Overrun(final Overrun card) {
        super(card);
    }

    @Override
    public Overrun copy() {
        return new Overrun(this);
    }
}
