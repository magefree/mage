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
package mage.cards.h;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class HopeOfGhirapur extends CardImpl {

    public HopeOfGhirapur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.supertype.add("Legendary");
        this.subtype.add("Thopter");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice Hope of Ghirapur: Until your next turn, target player who was dealt combat damage by Hope of Ghirapur this turn can't cast noncreature spells.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HopeOfGhirapurCantCastEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability, new HopeOfGhirapurCombatDamageWatcher());

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            if (ability.getEffects().size() > 0 && (ability.getEffects().get(0) instanceof HopeOfGhirapurCantCastEffect)) {
                MageObject sourceObject = ability.getSourceObject(game);
                if (sourceObject != null) {
                    ability.getTargets().clear();
                    FilterPlayer playerFilter = new FilterPlayer("player who was dealt combat damage by " + sourceObject.getIdName() + " this turn");
                    playerFilter.add(new HopeOfGhirapurPlayerLostLifePredicate(ability.getSourceId()));
                    ability.addTarget(new TargetPlayer(1, 1, false, playerFilter));
                }
            }
        }
    }

    public HopeOfGhirapur(final HopeOfGhirapur card) {
        super(card);
    }

    @Override
    public HopeOfGhirapur copy() {
        return new HopeOfGhirapur(this);
    }
}

class HopeOfGhirapurCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public HopeOfGhirapurCantCastEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Until your next turn, target player who was dealt combat damage by {this} this turn can't cast noncreature spells";
    }

    public HopeOfGhirapurCantCastEffect(final HopeOfGhirapurCantCastEffect effect) {
        super(effect);
    }

    @Override
    public HopeOfGhirapurCantCastEffect copy() {
        return new HopeOfGhirapurCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast noncreature spells this turn (you were dealt damage by " + mageObject.getLogName() + ")";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null && player.getId().equals(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
}

class HopeOfGhirapurPlayerLostLifePredicate implements Predicate<Player> {

    private final UUID sourceId;

    public HopeOfGhirapurPlayerLostLifePredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(Player input, Game game) {
        HopeOfGhirapurCombatDamageWatcher watcher = (HopeOfGhirapurCombatDamageWatcher) game.getState().getWatchers().get(HopeOfGhirapurCombatDamageWatcher.class.getName());
        if (watcher != null) {
            return watcher.playerGotCombatDamage(sourceId, input.getId());
        }
        return false;
    }
}

class HopeOfGhirapurCombatDamageWatcher extends Watcher {

    private final HashMap<UUID, Set<UUID>> combatDamagedPlayers = new HashMap<>();

    public HopeOfGhirapurCombatDamageWatcher() {
        super(HopeOfGhirapurCombatDamageWatcher.class.getName(), WatcherScope.GAME);
    }

    public HopeOfGhirapurCombatDamageWatcher(final HopeOfGhirapurCombatDamageWatcher watcher) {
        super(watcher);
        for (UUID objectId : watcher.combatDamagedPlayers.keySet()) {
            Set<UUID> players = new HashSet<>();
            players.addAll(watcher.combatDamagedPlayers.get(objectId));
            this.combatDamagedPlayers.put(objectId, players);
        }
    }

    @Override
    public HopeOfGhirapurCombatDamageWatcher copy() {
        return new HopeOfGhirapurCombatDamageWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Set<UUID> players;
            if (combatDamagedPlayers.containsKey(event.getSourceId())) {
                players = combatDamagedPlayers.get(event.getSourceId());
            } else {
                players = new HashSet<>();
                combatDamagedPlayers.put(event.getSourceId(), players);
            }
            players.add(event.getTargetId());
        }
    }

    /**
     * Checks if the current object with sourceId has damaged the player during
     * the current turn. The zoneChangeCounter will be taken into account.
     *
     * @param sourceId
     * @param game
     * @return
     */
    public boolean playerGotCombatDamage(UUID sourceId, UUID playerId) {
        if (combatDamagedPlayers.containsKey(sourceId)) {
            return combatDamagedPlayers.get(sourceId).contains(playerId);
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        combatDamagedPlayers.clear();
    }
}
