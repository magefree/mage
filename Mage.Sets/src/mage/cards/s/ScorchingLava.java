package mage.cards.s;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class ScorchingLava extends CardImpl {

    public ScorchingLava(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));
        // Scorching Lava deals 2 damage to any target. If Scorching Lava was kicked, 
        // that creature can't be regenerated this turn and if it would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(
                new CantRegenerateTargetEffect(Duration.EndOfTurn, "If this spell was kicked, that creature"),
                new LockedInCondition(KickedCondition.ONCE)));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ExileTargetIfDiesEffect(),
                new LockedInCondition(KickedCondition.ONCE),
                "and if it would die this turn, exile it instead."));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ScorchingLava(final ScorchingLava card) {
        super(card);
    }

    @Override
    public ScorchingLava copy() {
        return new ScorchingLava(this);
    }
}
