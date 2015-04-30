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
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class DragonOnTheBattlefieldWhileSpellWasCastWatcher extends Watcher {

    private static final FilterPermanent filter = new FilterPermanent("Dragon", "Dragons");

    private final Set<UUID> castWithDragonOnTheBattlefield = new HashSet<>();

    public DragonOnTheBattlefieldWhileSpellWasCastWatcher() {
        super("DragonOnTheBattlefieldWhileSpellWasCastWatcher", WatcherScope.GAME);
    }

    public DragonOnTheBattlefieldWhileSpellWasCastWatcher(final DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher) {
        super(watcher);
        this.castWithDragonOnTheBattlefield.addAll(watcher.castWithDragonOnTheBattlefield);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            // targetId is the unique ID of the spell
            Spell spell = game.getState().getStack().getSpell(event.getTargetId());
            // revealed a Dragon card or controlled a Dragon as you cast the spell
            if (spell != null) {
                boolean revealedOrOnBattlefield = false;
                for (Cost cost : spell.getSpellAbility().getCosts()) {
                    if (cost instanceof RevealTargetFromHandCost) {
                        revealedOrOnBattlefield = ((RevealTargetFromHandCost) cost).getNumberRevealedCards() > 0;
                        break;
                    }
                }
                if (!revealedOrOnBattlefield) {
                    revealedOrOnBattlefield = game.getBattlefield().countAll(filter, spell.getControllerId(), game) > 0;
                }
                if (revealedOrOnBattlefield){
                    castWithDragonOnTheBattlefield.add(spell.getId());
                }

            }
        }
    }

    @Override
    public void reset() {
        castWithDragonOnTheBattlefield.clear();
    }

    public boolean castWithConditionTrue(UUID spellId) {
        return castWithDragonOnTheBattlefield.contains(spellId);
    }

    @Override
    public DragonOnTheBattlefieldWhileSpellWasCastWatcher copy() {
        return new DragonOnTheBattlefieldWhileSpellWasCastWatcher(this);
    }
}
