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
package mage.cards.u;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.common.CreatureWasCastWatcher;

/**
 *
 * @author chrvanorle
 */
public class UphillBattle extends CardImpl {

    public UphillBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures played by your opponents enter the battlefield tapped.
        Ability tapAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new UphillBattleTapEffect());
        tapAbility.addWatcher(new CreatureWasCastWatcher());
        tapAbility.addWatcher(new PlayCreatureLandWatcher());
        addAbility(tapAbility);
    }

    public UphillBattle(final UphillBattle card) {
        super(card);
    }

    @Override
    public UphillBattle copy() {
        return new UphillBattle(this);
    }
}

class PlayCreatureLandWatcher extends Watcher {

    final Set<UUID> playerPlayedLand = new HashSet<>(); // player that played land
    final Set<UUID> landPlayed = new HashSet<>(); // land played

    public PlayCreatureLandWatcher() {
        super(PlayCreatureLandWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public PlayCreatureLandWatcher(final PlayCreatureLandWatcher watcher) {
        super(watcher);
        playerPlayedLand.addAll(watcher.playerPlayedLand);
        landPlayed.addAll(watcher.landPlayed);
    }

    @Override
    public PlayCreatureLandWatcher copy() {
        return new PlayCreatureLandWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND) {
            Card card = game.getCard(event.getTargetId());
            if (card != null
                    && card.isLand()
                    && card.isCreature()
                    && !playerPlayedLand.contains(event.getPlayerId())) {
                playerPlayedLand.add(event.getPlayerId());
                landPlayed.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        playerPlayedLand.clear();
        landPlayed.clear();
        super.reset();
    }

    public boolean landPlayed(UUID playerId) {
        return playerPlayedLand.contains(playerId);
    }

    public boolean wasLandPlayed(UUID landId) {
        return landPlayed.contains(landId);
    }
}

class UphillBattleTapEffect extends ReplacementEffectImpl {

    UphillBattleTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures played by your opponents enter the battlefield tapped";
    }

    UphillBattleTapEffect(final UphillBattleTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        CreatureWasCastWatcher creatureSpellWatcher = (CreatureWasCastWatcher) game.getState().getWatchers().get(CreatureWasCastWatcher.class.getSimpleName());
        PlayCreatureLandWatcher landWatcher = (PlayCreatureLandWatcher) game.getState().getWatchers().get(PlayCreatureLandWatcher.class.getSimpleName());

        if (target != null
                && ((creatureSpellWatcher != null && creatureSpellWatcher.wasCreatureCastThisTurn(target.getId()))
                || (landWatcher != null && landWatcher.wasLandPlayed(target.getId())))) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UphillBattleTapEffect copy() {
        return new UphillBattleTapEffect(this);
    }
}
