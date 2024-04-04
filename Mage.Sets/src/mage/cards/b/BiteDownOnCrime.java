package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class BiteDownOnCrime extends CardImpl {

    public BiteDownOnCrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // As an additional cost to cast this spell, you may collect evidence 6. This spell costs {2} less to cast if evidence was collected.
        this.addAbility(new CollectEvidenceAbility(6));

        this.getSpellAbility().addEffect(new InfoEffect("this spell costs {2} less to cast if evidence was collected"));
        this.getSpellAbility().setCostAdjuster(BiteDownOnCrimeAdjuster.instance);

        // Target creature you control gets +2/+0 until end of turn.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // It deals damage equal to its power to target creature you don't control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("It"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private BiteDownOnCrime(final BiteDownOnCrime card) {
        super(card);
    }

    @Override
    public BiteDownOnCrime copy() {
        return new BiteDownOnCrime(this);
    }
}


enum BiteDownOnCrimeAdjuster implements CostAdjuster {
    instance;

    private static final OptionalAdditionalCost collectEvidenceCost = CollectEvidenceAbility.makeCost(6);

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (CollectedEvidenceCondition.instance.apply(game, ability)
                || (game.inCheckPlayableState() && collectEvidenceCost.canPay(ability, null, ability.getControllerId(), game))) {
            CardUtil.reduceCost(ability, 2);
        }
    }
}
