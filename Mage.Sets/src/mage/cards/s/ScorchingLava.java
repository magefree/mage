package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.replacement.DiesReplacementEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

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
                new CantRegenerateTargetEffect(Duration.EndOfTurn, "If Scorching Lava was kicked, "
                        + "\n" + "that creature "),
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

class ScorchingLavaEffect extends OneShotEffect {

    public ScorchingLavaEffect() {
        super(Outcome.Exile);
    }

    public ScorchingLavaEffect(final ScorchingLavaEffect effect) {
        super(effect);
    }

    @Override
    public ScorchingLavaEffect copy() {
        return new ScorchingLavaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            game.addEffect(new DiesReplacementEffect(new MageObjectReference(targetCreature, game), Duration.EndOfTurn), source);
        }
        return true;
    }
}
