package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeterminedIteration extends CardImpl {

    public DeterminedIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of combat on your turn, populate. The token created this way gains haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DeterminedIterationEffect(), TargetController.YOU, false
        ));
    }

    private DeterminedIteration(final DeterminedIteration card) {
        super(card);
    }

    @Override
    public DeterminedIteration copy() {
        return new DeterminedIteration(this);
    }
}

class DeterminedIterationEffect extends OneShotEffect {

    DeterminedIterationEffect() {
        super(Outcome.Benefit);
        staticText = "populate. The token created this way gains haste. Sacrifice it at the beginning of the " +
                "next end step. <i>(To populate, create a token that's a copy of a creature token you control.)</i>";
    }

    private DeterminedIterationEffect(final DeterminedIterationEffect effect) {
        super(effect);
    }

    @Override
    public DeterminedIterationEffect copy() {
        return new DeterminedIterationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PopulateEffect effect = new PopulateEffect();
        effect.apply(game, source);
        if (effect.getAddedPermanents().isEmpty()) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice the token")
                        .setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game))
        ), source);
        return true;
    }
}
