
package mage.cards.p;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PureIntentions extends CardImpl {

    public PureIntentions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");
        this.subtype.add(SubType.ARCANE);

        // Whenever a spell or ability an opponent controls causes you to discard cards this turn, return those cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PureIntentionsAllTriggeredAbility()));

        // When a spell or ability an opponent controls causes you to discard Pure Intentions, return Pure Intentions from your graveyard to your hand at the beginning of the next end step.
        this.addAbility(new PureIntentionsTriggeredAbility());
    }

    private PureIntentions(final PureIntentions card) {
        super(card);
    }

    @Override
    public PureIntentions copy() {
        return new PureIntentions(this);
    }
}

class PureIntentionsAllTriggeredAbility extends DelayedTriggeredAbility {

    public PureIntentionsAllTriggeredAbility() {
        super(new ReturnFromGraveyardToHandTargetEffect(), Duration.EndOfTurn, false);
    }

    private PureIntentionsAllTriggeredAbility(final PureIntentionsAllTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null
                && game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isOwnedBy(getControllerId())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PureIntentionsAllTriggeredAbility copy() {
        return new PureIntentionsAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls causes you to discard cards this turn, return those cards from your graveyard to your hand.";
    }
}

class PureIntentionsTriggeredAbility extends TriggeredAbilityImpl {

    public PureIntentionsTriggeredAbility() {
        super(Zone.ALL, new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceFromGraveyardToHandEffect())), false);
    }

    private PureIntentionsTriggeredAbility(final PureIntentionsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PureIntentionsTriggeredAbility copy() {
        return new PureIntentionsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getTargetId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null
                    && game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a spell or ability an opponent controls causes you to discard {this}, return {this} from your graveyard to your hand at the beginning of the next end step.";
    }
}
