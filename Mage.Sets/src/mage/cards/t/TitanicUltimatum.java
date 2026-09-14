
package mage.cards.t;

import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
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
 * @author North, Eugen
 */
public final class TitanicUltimatum extends CardImpl {

    public TitanicUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}{G}{G}{G}{W}{W}");

        // Until end of turn, creatures you control get +5/+5 and gain first strike, trample, and lifelink.
        this.getSpellAbility().addEffect(new BoostGainAbilityGenericEffect(
                5, 5, Duration.EndOfTurn,
                FirstStrikeAbility.getInstance(), TrampleAbility.getInstance(), LifelinkAbility.getInstance()
        ).withDurationRuleAtStart(true).setTargetPointer(new FilterAllPermanentsTargetPointer(
                StaticFilters.FILTER_CONTROLLED_CREATURES)));
    }

    private TitanicUltimatum(final TitanicUltimatum card) {
        super(card);
    }

    @Override
    public TitanicUltimatum copy() {
        return new TitanicUltimatum(this);
    }
}
