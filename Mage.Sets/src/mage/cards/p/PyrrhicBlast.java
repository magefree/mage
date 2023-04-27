package mage.cards.p;

import java.util.UUID;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author weirddan455
 */
public final class PyrrhicBlast extends CardImpl {

    public PyrrhicBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Pyrrhic Blast deals damage equal to the sacrificed creature's power to any target. Draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(SacrificeCostCreaturesPower.instance)
                .setText("{this} deals damage equal to the sacrificed creature's power to any target"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private PyrrhicBlast(final PyrrhicBlast card) {
        super(card);
    }

    @Override
    public PyrrhicBlast copy() {
        return new PyrrhicBlast(this);
    }
}
