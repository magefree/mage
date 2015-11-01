/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.stack;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.UUID;
import mage.MageObject;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
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
        } finally {
            if (top != null) {
                if (contains(top)) {
                    logger.warn("StackObject was still on the stack after resoving" + top.getName());
                    this.remove(top);
                }
            }
        }
    }

    public boolean remove(StackObject object) {
        for (StackObject spell : this) {
            if (spell.getId().equals(object.getId())) {
                return super.remove(spell);
            }
        }
        return false;
    }

    public boolean counter(UUID objectId, UUID sourceId, Game game) {
        // the counter logic is copied by some spells to handle replacement effects of the countered spell
        // so if logic is changed here check those spells for needed changes too
        // Concerned cards to check: Hinder, Spell Crumple
        StackObject stackObject = getStackObject(objectId);
        MageObject sourceObject = game.getObject(sourceId);
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
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, sourceId, stackObject.getControllerId()))) {
                if (stackObject instanceof Spell) {
                    game.rememberLKI(objectId, Zone.STACK, (Spell) stackObject);
                } else {
                    this.remove(stackObject);
                }
                stackObject.counter(sourceId, game);
                if (!game.isSimulation()) {
                    game.informPlayers(counteredObjectName + " is countered by " + sourceObject.getLogName());
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, sourceId, stackObject.getControllerId()));
            } else if (!game.isSimulation()) {
                game.informPlayers(counteredObjectName + " could not be countered by " + sourceObject.getLogName());
            }
            return true;
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
        for (StackObject stackObject : this) {
            if (stackObject instanceof Spell) {
                if (stackObject.getId().equals(id) || stackObject.getSourceId().equals(id)) {
                    return (Spell) stackObject;
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

}
