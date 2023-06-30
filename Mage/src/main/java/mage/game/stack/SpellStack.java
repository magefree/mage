
package mage.game.stack;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.PutCards;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpellStack extends ArrayDeque<StackObject> {

    private static final Logger logger = Logger.getLogger(SpellStack.class);

    protected Date dateLastAdded;

    public SpellStack() {
    }

    public SpellStack(final SpellStack stack) {

        for (StackObject spell : stack) {
            this.addLast(spell.copy());
        }
    }

    //resolve top StackObject
    public void resolve(Game game) {
        StackObject top = null;
        try {
            top = this.peek();
            top.resolve(game);
            game.resetControlAfterSpellResolve(top.getId());
        } finally {
            if (top != null) {
                if (contains(top)) {
                    logger.warn("StackObject was still on the stack after resolving" + top.getName());
                    this.remove(top, game);
                }
            }
        }
    }

    public boolean remove(StackObject object, Game game) {
        for (StackObject spell : this) {
            if (spell.getId().equals(object.getId())) {
                game.getState().setZone(spell.getId(), null);
                return super.remove(spell);
            }
        }
        return false;
    }

    public boolean counter(UUID objectId, Ability source, Game game) {
        return counter(objectId, source, game, PutCards.GRAVEYARD);
    }

    public boolean counter(UUID objectId, Ability source, Game game, PutCards putCard) {
        StackObject stackObject = getStackObject(objectId);
        MageObject sourceObject = game.getObject(source);
        if (stackObject != null && sourceObject != null) {
            MageObject targetSourceObject = game.getObject(stackObject.getSourceId());
            String counteredObjectName, targetSourceName;
            if (targetSourceObject == null) {
                targetSourceName = "[Object not found]";
            } else {
                targetSourceName = game.getObject(stackObject.getSourceId()).getLogName();
            }
            if (stackObject instanceof Spell) {
                counteredObjectName = targetSourceName;
            } else {
                counteredObjectName = "Ability (" + stackObject.getStackAbility().getRule(targetSourceName) + ") of " + targetSourceName;
            }
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, source, stackObject.getControllerId()))) {
                if (!(stackObject instanceof Spell)) { // spells are removed from stack by the card movement
                    this.remove(stackObject, game);
                }
                stackObject.counter(source, game, putCard);
                if (!game.isSimulation()) {
                    game.informPlayers(counteredObjectName + " is countered by " + sourceObject.getLogName());
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, source, stackObject.getControllerId()));
                return true;
            } else if (!game.isSimulation()) {
                game.informPlayers(counteredObjectName + " could not be countered by " + sourceObject.getLogName());
            }
        }
        return false;
    }

    public StackObject getStackObject(UUID id) {
        for (StackObject stackObject : this) {
            UUID objectId = stackObject.getId();
            if (objectId.equals(id)) {
                return stackObject;
            }
            UUID sourceId = stackObject.getSourceId();
            if (sourceId.equals(id)) {
                return stackObject;
            }
        }
        return null;
    }

    public Spell getSpell(UUID id) {
        return getSpell(id, true);
    }

    public Spell getSpell(UUID id, boolean allowCopies) {
        for (StackObject stackObject : this) {
            if (stackObject instanceof Spell) {
                if (stackObject.getId().equals(id) || stackObject.getSourceId().equals(id)) {
                    if (allowCopies || !stackObject.isCopy()) {
                        return (Spell) stackObject;
                    }
                }
            }
        }
        return null;
    }

    public SpellStack copy() {
        return new SpellStack(this);
    }

    @Override
    public void push(StackObject e) {
        super.push(e);
        this.dateLastAdded = new Date();
    }

    public Date getDateLastAdded() {
        return dateLastAdded;
    }

    @Override
    public String toString() {
        return this.size() + (this.isEmpty() ? "" : " (top: " + CardUtil.substring(this.getFirst().toString(), 100) + ")");
    }
}
