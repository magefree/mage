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
package mage.sets.zendikar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class WhiplashTrap extends CardImpl<WhiplashTrap> {

    public WhiplashTrap(UUID ownerId) {
        super(ownerId, 77, "Whiplash Trap", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setBlue(true);

        // If an opponent had two or more creatures enter the battlefield under his or her control this turn, you may pay {U} rather than pay Whiplash Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new WhiplashAlternativeCost());
        this.addWatcher(new WhiplashTrapWatcher());

        // Return two target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
        
    }

    public WhiplashTrap(final WhiplashTrap card) {
        super(card);
    }

    @Override
    public WhiplashTrap copy() {
        return new WhiplashTrap(this);
    }
}

class WhiplashTrapWatcher extends WatcherImpl<WhiplashTrapWatcher> {

    private Map<UUID, Integer> amountOfCreaturesPlayedThisTurn = new HashMap<UUID, Integer>();

    public WhiplashTrapWatcher() {
        super("WhiplashTrapWatcher", Constants.WatcherScope.GAME);
    }

    public WhiplashTrapWatcher(final WhiplashTrapWatcher watcher) {
        super(watcher);
        for (Map.Entry<UUID, Integer> entry : watcher.amountOfCreaturesPlayedThisTurn.entrySet()) {
            amountOfCreaturesPlayedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public WhiplashTrapWatcher copy() {
        return new WhiplashTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm.getCardType().contains(CardType.CREATURE)) {
                Integer amount = amountOfCreaturesPlayedThisTurn.get(perm.getControllerId());
                if (amount == null) {
                    amount = Integer.valueOf(1);
                } else {
                    ++amount;
                }
                amountOfCreaturesPlayedThisTurn.put(perm.getControllerId(), amount);
            }
        }
    }
    
    public int maxCreaturesAnOpponentPlayedThisTurn(UUID playerId, Game game) {
        int maxCreatures = 0;
        for (UUID opponentId : game.getOpponents(playerId)) {
            Integer amount = amountOfCreaturesPlayedThisTurn.get(opponentId);
            if (amount != null && amount.intValue() > maxCreatures) {
                maxCreatures = amount.intValue();
            }
        }
        return maxCreatures;
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCreaturesPlayedThisTurn.clear();
    }
}

class WhiplashAlternativeCost extends AlternativeCostImpl<WhiplashAlternativeCost> {

    public WhiplashAlternativeCost() {
        super("you may pay {U} rather than pay Whiplash Trap's mana cost");
        this.add(new ManaCostsImpl("{U}"));
    }

    public WhiplashAlternativeCost(final WhiplashAlternativeCost cost) {
        super(cost);
    }

    @Override
    public WhiplashAlternativeCost copy() {
        return new WhiplashAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        WhiplashTrapWatcher watcher = (WhiplashTrapWatcher) game.getState().getWatchers().get("WhiplashTrapWatcher");
        if (watcher != null && watcher.maxCreaturesAnOpponentPlayedThisTurn(source.getControllerId(), game) >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent had two or more creatures enter the battlefield under his or her control this turn, you may pay {U} rather than pay Whiplash Trap's mana cost";
    }
}