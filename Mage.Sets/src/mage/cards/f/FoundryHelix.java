package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class FoundryHelix extends CardImpl {

    public FoundryHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");

        // As an additional cost to cast this spell, sacrifice a permanent.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_SHORT_TEXT)));

        // Foundry Helix deals 4 damage to any target. If the sacrificed permanent was an artifact, you gain 4 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(4), FoundryHelixCondition.instance));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FoundryHelix(final FoundryHelix card) {
        super(card);
    }

    @Override
    public FoundryHelix copy() {
        return new FoundryHelix(this);
    }
}

enum FoundryHelixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                    if (permanent.isArtifact(game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "the sacrificed permanent was an artifact";
    }
}
