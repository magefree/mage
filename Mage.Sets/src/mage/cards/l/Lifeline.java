package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author HCrescent
 */
public final class Lifeline extends CardImpl {


    public Lifeline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");


        // Whenever a creature dies, if another creature is on the battlefield, return the first card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new DiesCreatureTriggeredAbility(Zone.BATTLEFIELD, new LifelineEffect(), false, StaticFilters.FILTER_PERMANENT_CREATURE, true),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_CREATURE, false),
                "Whenever a creature dies, if another creature is on the battlefield, return the first card to the battlefield under its owner's control at the beginning of the next end step.");
        this.addAbility(ability);
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

    public LifelineEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "";
    }

    public LifelineEffect(final LifelineEffect effect) {
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
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}