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
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import static mage.constants.Zone.BATTLEFIELD;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.HAND;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileForSourceEffect extends OneShotEffect {

    private Zone returnToZone;
    private boolean tapped;
    private boolean previousZone;

    /**
     *
     * @param zone Zone the card should return to
     */
    public ReturnFromExileForSourceEffect(Zone zone) {
        this(zone, false);
    }

    public ReturnFromExileForSourceEffect(Zone zone, boolean tapped) {
        this(zone, tapped, true);
    }

    /**
     *
     * @param zone
     * @param tapped
     * @param previousZone if this is used from a dies leave battlefield or
     * destroyed trigger, the exile zone is based on previous zone of the object
     */
    public ReturnFromExileForSourceEffect(Zone zone, boolean tapped, boolean previousZone) {
        super(Outcome.PutCardInPlay);
        this.returnToZone = zone;
        this.tapped = tapped;
        this.previousZone = previousZone;
        setText();
    }

    public ReturnFromExileForSourceEffect(final ReturnFromExileForSourceEffect effect) {
        super(effect);
        this.returnToZone = effect.returnToZone;
        this.tapped = effect.tapped;
        this.previousZone = effect.previousZone;
    }

    @Override
    public ReturnFromExileForSourceEffect copy() {
        return new ReturnFromExileForSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            int zoneChangeCounter = source.getSourceObjectZoneChangeCounter();
            if (zoneChangeCounter > 0 && previousZone && !(sourceObject instanceof PermanentToken)) {
                zoneChangeCounter--;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exile != null) { // null is valid if source left battlefield before enters the battlefield effect resolved
                if (returnToZone.equals(Zone.BATTLEFIELD)) {
                    controller.moveCards(exile.getCards(game), returnToZone, source, game, false, false, true, null);
                } else {
                    controller.moveCards(exile, null, returnToZone, source, game);
                }
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("return the exiled cards ");
        switch (returnToZone) {
            case BATTLEFIELD:
                sb.append("to the battlefield under its owner's control");
                if (tapped) {
                    sb.append(" tapped");
                }
                break;
            case HAND:
                sb.append("to their owner's hand");
                break;
            case GRAVEYARD:
                sb.append("to their owner's graveyard");
                break;
        }
        staticText = sb.toString();
    }

}
