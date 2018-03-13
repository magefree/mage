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
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.SpoilsOfBloodHorrorToken;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class SpoilsOfBlood extends CardImpl {

    public SpoilsOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Create an X/X black Horror creature token, where X is the number of creatures that died this turn.
        this.getSpellAbility().addEffect(new SpoilsOfBloodEffect());
        this.getSpellAbility().addWatcher(new CreaturesDiedThisTurnWatcher());
    }

    public SpoilsOfBlood(final SpoilsOfBlood card) {
        super(card);
    }

    @Override
    public SpoilsOfBlood copy() {
        return new SpoilsOfBlood(this);
    }
}

class SpoilsOfBloodEffect extends OneShotEffect {

    public SpoilsOfBloodEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Create an X/X black Horror creature token, where X is the number of creatures that died this turn";
    }

    public SpoilsOfBloodEffect(SpoilsOfBloodEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreaturesDiedThisTurnWatcher watcher = (CreaturesDiedThisTurnWatcher) game.getState().getWatchers().get(CreaturesDiedThisTurnWatcher.class.getSimpleName());
            if (watcher != null) {
                new CreateTokenEffect(new SpoilsOfBloodHorrorToken(watcher.creaturesDiedThisTurn)).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SpoilsOfBloodEffect copy() {
        return new SpoilsOfBloodEffect(this);
    }

}

class CreaturesDiedThisTurnWatcher extends Watcher {

    public int creaturesDiedThisTurn = 0;

    public CreaturesDiedThisTurnWatcher() {
        super(CreaturesDiedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CreaturesDiedThisTurnWatcher(final CreaturesDiedThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public CreaturesDiedThisTurnWatcher copy() {
        return new CreaturesDiedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (mageObject != null && mageObject.isCreature()) {
                creaturesDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesDiedThisTurn = 0;
    }

}
