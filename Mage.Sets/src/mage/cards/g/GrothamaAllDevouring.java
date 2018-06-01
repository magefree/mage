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
package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class GrothamaAllDevouring extends CardImpl {

    public GrothamaAllDevouring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(10);
        this.toughness = new MageInt(8);

        // Other creatures have "Whenever this creature attacks, you may have it fight Grothama, All-Devouring."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrothamaAllDevouringGainAbilityEffect()));

        // When Grothama leaves the battlefield, each player draws cards equal to the amount of damage dealt to Grothama this turn by sources they controlled.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new GrothamaAllDevouringDrawCardsEffect(), false), new GrothamaAllDevouringWatcher());
    }

    public GrothamaAllDevouring(final GrothamaAllDevouring card) {
        super(card);
    }

    @Override
    public GrothamaAllDevouring copy() {
        return new GrothamaAllDevouring(this);
    }
}

class GrothamaAllDevouringGainAbilityEffect extends GainAbilityAllEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(new AnotherPredicate());
    }

    GrothamaAllDevouringGainAbilityEffect() {
        super(new AttacksTriggeredAbility(
                new GrothamaAllDevouringFightEffect(null, null), true
        ), Duration.WhileOnBattlefield, filter);
        this.staticText = "Other creatures have \"Whenever this creature attacks, you may have it fight {this}.\"";
    }

    GrothamaAllDevouringGainAbilityEffect(final GrothamaAllDevouringGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public GrothamaAllDevouringGainAbilityEffect copy() {
        return new GrothamaAllDevouringGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        ability = new AttacksTriggeredAbility(new GrothamaAllDevouringFightEffect(permanent.getId(), permanent.getName()), true);
        return super.apply(game, source);
    }
}

class GrothamaAllDevouringFightEffect extends OneShotEffect {

    private final UUID fightId;

    GrothamaAllDevouringFightEffect(UUID fightId, String fightName) {
        super(Outcome.Benefit);
        this.fightId = fightId;
        this.staticText = "you may have it fight " + fightName;
    }

    GrothamaAllDevouringFightEffect(final GrothamaAllDevouringFightEffect effect) {
        super(effect);
        this.fightId = effect.fightId;
    }

    @Override
    public GrothamaAllDevouringFightEffect copy() {
        return new GrothamaAllDevouringFightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent grothama = game.getPermanent(fightId);
        Permanent creature = game.getPermanent(source.getSourceId());
        if (grothama == null || creature == null) {
            return false;
        }
        return grothama.fight(creature, source, game);
    }
}

class GrothamaAllDevouringDrawCardsEffect extends OneShotEffect {

    GrothamaAllDevouringDrawCardsEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player draws cards equal to the amount of "
                + "damage dealt to {this} this turn by sources they controlled.";
    }

    GrothamaAllDevouringDrawCardsEffect(final GrothamaAllDevouringDrawCardsEffect effect) {
        super(effect);
    }

    @Override
    public GrothamaAllDevouringDrawCardsEffect copy() {
        return new GrothamaAllDevouringDrawCardsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        GrothamaAllDevouringWatcher watcher = (GrothamaAllDevouringWatcher) game.getState().getWatchers().get(GrothamaAllDevouringWatcher.class.getSimpleName());
        if (watcher == null) {
            return false;
        }
        Map<UUID, Integer> damageMap = watcher.getDamageMap(new MageObjectReference(source.getSourceId(), source.getSourceObjectZoneChangeCounter() - 1, game));
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int toDraw = damageMap.getOrDefault(player.getId(), 0);
                if (toDraw > 0) {
                    player.drawCards(toDraw, game);
                }
            }
        }
        return true;
    }
}

class GrothamaAllDevouringWatcher extends Watcher {

    Map<MageObjectReference, Map<UUID, Integer>> damageMap = new HashMap<>();

    GrothamaAllDevouringWatcher() {
        super(GrothamaAllDevouringWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    GrothamaAllDevouringWatcher(final GrothamaAllDevouringWatcher watcher) {
        super(watcher);
        for (MageObjectReference mor : watcher.damageMap.keySet()) {
            this.damageMap.putIfAbsent(mor, new HashMap<>());
            for (UUID key : watcher.damageMap.get(mor).keySet()) {
                this.damageMap.get(mor).putIfAbsent(key, 0);
                this.damageMap.get(mor).compute(key, (k, damage) -> damage + watcher.damageMap.get(mor).get(key));
            }
        }
    }

    @Override
    public GrothamaAllDevouringWatcher copy() {
        return new GrothamaAllDevouringWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_CREATURE) {
            return;
        }
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (damaged == null) {
            return;
        }
        MageObjectReference mor = new MageObjectReference(damaged, game);
        damageMap.putIfAbsent(mor, new HashMap<>());
        damageMap.get(mor).putIfAbsent(event.getPlayerId(), 0);
        damageMap.get(mor).compute(event.getPlayerId(), (k, damage) -> damage + event.getAmount());
    }

    @Override
    public void reset() {
        super.reset();
        damageMap.clear();
    }

    public Map<UUID, Integer> getDamageMap(MageObjectReference mor) {
        return damageMap.getOrDefault(mor, new HashMap<>());
    }
}
