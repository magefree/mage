package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Thud extends CardImpl {

    public Thud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Thud deals damage equal to the sacrificed creature's power to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(SacrificeCostCreaturesPower.instance)
                .setText("{this} deals damage equal to the sacrificed creature's power to any target"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Thud(final Thud card) {
        super(card);
    }

    @Override
    public Thud copy() {
        return new Thud(this);
    }
}
