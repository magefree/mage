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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class SentinelTower extends CardImpl {

    public SentinelTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever an instant or sorcery spell is cast during your turn, Sentinel Tower deals damage to any target equal to 1 plus the number of instant and sorcery spells cast before that spell this turn.
        this.addAbility(new SentinelTowerTriggeredAbility(), new SentinelTowerWatcher());
    }

    public SentinelTower(final SentinelTower card) {
        super(card);
    }

    @Override
    public SentinelTower copy() {
        return new SentinelTower(this);
    }
}

class SentinelTowerTriggeredAbility extends SpellCastAllTriggeredAbility {

    SentinelTowerTriggeredAbility() {
        super(new DamageTargetEffect(0), StaticFilters.FILTER_INSTANT_OR_SORCERY_SPELL, false);
        this.addTarget(new TargetAnyTarget());
    }

    SentinelTowerTriggeredAbility(final SentinelTowerTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public SentinelTowerTriggeredAbility copy() {
        return new SentinelTowerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getActivePlayerId().equals(getControllerId())
                && super.checkTrigger(event, game)) {
            SentinelTowerWatcher watcher = (SentinelTowerWatcher) game.getState().getWatchers().get(SentinelTowerWatcher.class.getSimpleName());
            if (watcher == null) {
                return false;
            }
            List<MageObjectReference> spellsCast = watcher.getSpellsThisTurn();
            MageObject object = game.getObject(event.getTargetId());
            if (object == null || spellsCast == null) {
                return false;
            }
            int damageToDeal = 0;
            for (MageObjectReference mor : spellsCast) {
                damageToDeal++;
                if (mor.refersTo(object, game)) {
                    break;
                }
            }
            for (Effect effect : this.getEffects()) {
                if (effect instanceof DamageTargetEffect) {
                    ((DamageTargetEffect) effect).setAmount(new StaticValue(damageToDeal));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an instant or sorcery spell is cast during your turn, "
                + "{this} deals damage to any target equal to 1 "
                + "plus the number of instant and sorcery spells cast before that spell this turn.";
    }
}

class SentinelTowerWatcher extends Watcher {

    private final List<MageObjectReference> spellsThisTurn;

    SentinelTowerWatcher() {
        super(SentinelTowerWatcher.class.getSimpleName(), WatcherScope.GAME);
        this.spellsThisTurn = new ArrayList<>();
    }

    SentinelTowerWatcher(final SentinelTowerWatcher effect) {
        super(effect);
        this.spellsThisTurn = effect.spellsThisTurn;
    }

    @Override
    public SentinelTowerWatcher copy() {
        return new SentinelTowerWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            MageObject object = game.getObject(event.getTargetId());
            if (object != null && (object.isInstant() || object.isSorcery())) {
                spellsThisTurn.add(new MageObjectReference(object, game));
            }
        }
    }

    @Override
    public void reset() {
        spellsThisTurn.clear();
    }

    public List<MageObjectReference> getSpellsThisTurn() {
        return spellsThisTurn;
    }
}
