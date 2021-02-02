
package mage.cards.t;

import java.util.UUID;
import mage.abilities.CompoundAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author North, Eugen
 */
public final class TitanicUltimatum extends CardImpl {

    public TitanicUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}{G}{G}{G}{W}{W}");

        // Until end of turn, creatures you control get +5/+5 and gain first strike, trample, and lifelink.
        this.getSpellAbility().addEffect(new BoostControlledEffect(5, 5, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("Until end of turn, creatures you control get +5/+5"));
        CompoundAbility ability = new CompoundAbility(FirstStrikeAbility.getInstance(), TrampleAbility.getInstance(), LifelinkAbility.getInstance());
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("and gain first strike, trample, and lifelink"));
    }

    private TitanicUltimatum(final TitanicUltimatum card) {
        super(card);
    }

    @Override
    public TitanicUltimatum copy() {
        return new TitanicUltimatum(this);
    }
}
