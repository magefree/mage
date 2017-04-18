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

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author emerald000
 */
public class SpinerockKnoll extends CardImpl {

    public SpinerockKnoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Hideaway
        this.addAbility(new HideawayAbility());

        // {tap}: Add {R} to your mana pool.
        this.addAbility(new RedManaAbility());

        // {R}, {tap}: You may play the exiled card without paying its mana cost if an opponent was dealt 7 or more damage this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new HideawayPlayEffect(),
                new ColoredManaCost(ColoredManaSymbol.R),
                new SpinerockKnollCondition());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new SpinerockKnollWatcher());
    }

    public SpinerockKnoll(final SpinerockKnoll card) {
        super(card);
    }

    @Override
    public SpinerockKnoll copy() {
        return new SpinerockKnoll(this);
    }
}

class SpinerockKnollCondition extends IntCompareCondition {

    SpinerockKnollCondition() {
        super(ComparisonType.MORE_THAN, 6);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int maxDamageReceived = 0;
        SpinerockKnollWatcher watcher = (SpinerockKnollWatcher) game.getState().getWatchers().get("SpinerockKnollWatcher", source.getSourceId());
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                int damageReceived = watcher.getDamageReceived(opponentId);
                if (damageReceived > maxDamageReceived) {
                    maxDamageReceived = damageReceived;
                }
            }
        }
        return maxDamageReceived;
    }

    @Override
    public String toString() {
        return "if an opponent was dealt 7 or more damage this turn";
    }
}

class SpinerockKnollWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfDamageReceivedThisTurn = new HashMap<>(1);

    SpinerockKnollWatcher() {
        super("SpinerockKnollWatcher", WatcherScope.CARD);
    }

    SpinerockKnollWatcher(final SpinerockKnollWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfDamageReceivedThisTurn.entrySet()) {
            amountOfDamageReceivedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
                amount += event.getAmount();
                amountOfDamageReceivedThisTurn.put(playerId, amount);
            }
        }
    }

    public int getDamageReceived(UUID playerId) {
        return amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        amountOfDamageReceivedThisTurn.clear();
    }

    @Override
    public SpinerockKnollWatcher copy() {
        return new SpinerockKnollWatcher(this);
    }
}
