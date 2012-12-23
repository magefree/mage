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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class LavaballTrap extends CardImpl<LavaballTrap> {

    public LavaballTrap(UUID ownerId) {
        super(ownerId, 135, "Lavaball Trap", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{6}{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setRed(true);

        // If an opponent had two or more lands enter the battlefield under his or her control this turn, you may pay {3}{R}{R} rather than pay Lavaball Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new LavaballTrapAlternativeCost());
        this.addWatcher(new LavaballTrapWatcher());

        // Destroy two target lands. Lavaball Trap deals 4 damage to each creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));
        this.getSpellAbility().addTarget(new TargetLandPermanent(2));

    }

    public LavaballTrap(final LavaballTrap card) {
        super(card);
    }

    @Override
    public LavaballTrap copy() {
        return new LavaballTrap(this);
    }
}

class LavaballTrapWatcher extends WatcherImpl<LavaballTrapWatcher> {

    int landPlayed;

    public LavaballTrapWatcher() {
        super("LavaballTrapWatcher", Constants.WatcherScope.GAME);
    }

    public LavaballTrapWatcher(final LavaballTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public LavaballTrapWatcher copy() {
        return new LavaballTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { // no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Constants.Zone.BATTLEFIELD) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm.getCardType().contains(CardType.LAND) && !perm.getControllerId().equals(controllerId)) {
                landPlayed += 1;
                if (landPlayed >= 2) {
                    condition = true;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        landPlayed = 0;
        condition = false;
    }
}

class LavaballTrapAlternativeCost extends AlternativeCostImpl<LavaballTrapAlternativeCost> {

    public LavaballTrapAlternativeCost() {
        super("you may pay {3}{R}{R} rather than pay Lavaball Trap's mana cost");
        this.add(new ManaCostsImpl("{3}{R}{R}"));
    }

    public LavaballTrapAlternativeCost(final LavaballTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public LavaballTrapAlternativeCost copy() {
        return new LavaballTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        LavaballTrapWatcher watcher = (LavaballTrapWatcher) game.getState().getWatchers().get("LavaballTrapWatcher");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent had two or more lands enter the battlefield under his or her control this turn, you may pay {3}{R}{R} rather than pay Lavaball Trap's mana cost";
    }
}