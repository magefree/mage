package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author HCrescent
 */
public final class Lifeline extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent("another creature is on the battlefield"), false
    );

    public Lifeline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever a creature dies, if another creature is on the battlefield, return the first card to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new LifelineEffect(), false,
                StaticFilters.FILTER_PERMANENT_A_CREATURE, true
        ).withInterveningIf(condition));
    }

    private Lifeline(final Lifeline card) {
        super(card);
    }

    @Override
    public Lifeline copy() {
        return new Lifeline(this);
    }
}

class LifelineEffect extends OneShotEffect {

    LifelineEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return the first card to the battlefield under its owner's control at the beginning of the next end step";
    }

    private LifelineEffect(final LifelineEffect effect) {
        super(effect);
    }

    @Override
    public LifelineEffect copy() {
        return new LifelineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setText("return that card to the battlefield under it's owner's control at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}
