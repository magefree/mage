
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Tenacity extends CardImpl {

    public Tenacity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Creatures you control get +1/+1 and gain lifelink until end of turn. Untap those creatures.
        Effect boost = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        boost.setText("Creatures you control get +1/+1");
        this.getSpellAbility().addEffect(boost);
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), " and gain lifelink until end of turn"));
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, "Untap those creatures"));
    }

    private Tenacity(final Tenacity card) {
        super(card);
    }

    @Override
    public Tenacity copy() {
        return new Tenacity(this);
    }
}
