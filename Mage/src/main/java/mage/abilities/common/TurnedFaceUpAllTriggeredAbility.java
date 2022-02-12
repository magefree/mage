package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */

public class TurnedFaceUpAllTriggeredAbility extends TriggeredAbilityImpl {

    private FilterPermanent filter;
    private boolean setTargetPointer;

    public TurnedFaceUpAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public TurnedFaceUpAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, setTargetPointer, false);
    }

    public TurnedFaceUpAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        // has to be set so the ability triggers if card itself is turn faced up
        this.setWorksFaceDown(true);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TurnedFaceUpAllTriggeredAbility(final TurnedFaceUpAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
    }

    @Override
    public TurnedFaceUpAllTriggeredAbility copy() {
        return new TurnedFaceUpAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            MageObject sourceObj = this.getSourceObject(game);
            if (sourceObj != null) {
                if (sourceObj instanceof Card && ((Card) sourceObj).isFaceDown(game)) {
                    // if face down and it's not itself that is turned face up, it does not trigger
                    return false;
                }
            } else {
                // Permanent is and was not on the battlefield
                return false;
            }
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (setTargetPointer) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " is turned face up, " ;
    }
}

