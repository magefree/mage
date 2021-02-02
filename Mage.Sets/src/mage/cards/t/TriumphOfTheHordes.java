
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 * @author North
 */
public final class TriumphOfTheHordes extends CardImpl {


    public TriumphOfTheHordes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");


        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(InfectAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private TriumphOfTheHordes(final TriumphOfTheHordes card) {
        super(card);
    }

    @Override
    public TriumphOfTheHordes copy() {
        return new TriumphOfTheHordes(this);
    }
}
