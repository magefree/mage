/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage;

import java.io.Serializable;
import java.util.UUID;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import org.apache.log4j.Logger;

/**
 * A object reference that takes zone changes into account.
 *
 * @author LevelX2
 */
public class MageObjectReference implements Comparable<MageObjectReference>, Serializable {

    private static final transient Logger logger = Logger.getLogger(MageObjectReference.class);

    private final UUID sourceId;
    private final int zoneChangeCounter;

    public MageObjectReference(MageObject mageObject, Game game) {
        this.sourceId = mageObject.getId();
        this.zoneChangeCounter = mageObject.getZoneChangeCounter(game);
    }

    /**
     * That values manually (can be used to let it reference to a Permanent that
     * is not yet on the battlefield.
     *
     * @param sourceId
     * @param zoneChangeCounter
     * @param game
     */
    public MageObjectReference(UUID sourceId, int zoneChangeCounter, Game game) {
        this.sourceId = sourceId;
        this.zoneChangeCounter = zoneChangeCounter;
    }

    public MageObjectReference(UUID sourceId, Game game) {
        this.sourceId = sourceId;
        MageObject mageObject = game.getObject(sourceId);
        if (mageObject != null) {
            this.zoneChangeCounter = mageObject.getZoneChangeCounter(game);
        } else {
            if (game.getPlayerList().contains(sourceId)) {
                this.zoneChangeCounter = 0;
            } else {
                logger.error("The provided sourceId is not connected to an object in the game id:" + sourceId);
                for (StackObject stackObject : game.getStack()) {
                    logger.error("StackObject: " + stackObject.getId() + " sourceId" + stackObject.getSourceId() + " name" + stackObject.getName());
                }
                mageObject = game.getLastKnownInformation(sourceId, Zone.STACK);
                if (mageObject != null) {
                    this.zoneChangeCounter = mageObject.getZoneChangeCounter(game);
                    logger.error("SourceId found in LKI");
                } else {
                    logger.error("SourceId NOT found in LKI");
                    this.zoneChangeCounter = 0;
                }
            }
        }
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public int getZoneChangeCounter() {
        return zoneChangeCounter;
    }

    @Override
    public int compareTo(MageObjectReference o) {
        if (o.getSourceId().equals(this.sourceId)) {
            return o.getZoneChangeCounter() - this.zoneChangeCounter;
        }
        return o.getSourceId().compareTo(sourceId);
    }

    @Override
    public boolean equals(Object v) {
        if (v instanceof MageObjectReference) {
            if (((MageObjectReference) v).getSourceId().equals(this.sourceId)) {
                return ((MageObjectReference) v).getZoneChangeCounter() == this.zoneChangeCounter;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.sourceId != null ? this.sourceId.hashCode() + this.zoneChangeCounter : 0);
        return hash;
    }

    public boolean refersTo(UUID id, Game game) {
        return refersTo(game.getObject(id), game);
    }

    public boolean refersTo(MageObject mageObject, Game game) {
        if (mageObject != null) {
            if (mageObject instanceof Spell) {
                return ((Spell) mageObject).getSourceId().equals(sourceId) && this.zoneChangeCounter == mageObject.getZoneChangeCounter(game);
            }
            return mageObject.getId().equals(sourceId) && this.zoneChangeCounter == mageObject.getZoneChangeCounter(game);
        }
        return false;
    }

    public Permanent getPermanent(Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null && permanent.getZoneChangeCounter(game) == zoneChangeCounter) {
            return permanent;
        }
        return null;
    }

    public Permanent getPermanentOrLKIBattlefield(Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(sourceId);
        if (permanent != null && permanent.getZoneChangeCounter(game) == zoneChangeCounter) {
            return permanent;
        }
        return null;
    }

    public Card getCard(Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && card.getZoneChangeCounter(game) == zoneChangeCounter) {
            return card;
        }
        return null;
    }

}
