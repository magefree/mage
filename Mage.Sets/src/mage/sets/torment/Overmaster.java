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
package mage.sets.torment;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifiyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public class Overmaster extends CardImpl {

    public Overmaster(UUID ownerId) {
        super(ownerId, 104, "Overmaster", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "TOR";

        this.color.setRed(true);

        // The next instant or sorcery spell you cast this turn can't be countered by spells or abilities.
        this.getSpellAbility().addEffect(new OvermasterEffect());
        this.getSpellAbility().addWatcher(new OvermasterWatcher());
        
        // Draw a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("<br><br>Draw a card");
        this.getSpellAbility().addEffect(effect);
    }

    public Overmaster(final Overmaster card) {
        super(card);
    }

    @Override
    public Overmaster copy() {
        return new Overmaster(this);
    }
}

class OvermasterEffect extends ContinuousRuleModifiyingEffectImpl {
    
    OvermasterEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next instant or sorcery spell you cast this turn can't be countered by spells or abilities";
    }

    OvermasterEffect(final OvermasterEffect effect) {
        super(effect);
    }

    @Override
    public OvermasterEffect copy() {
        return new OvermasterEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        OvermasterWatcher watcher = (OvermasterWatcher) game.getState().getWatchers().get("overmasterWatcher", source.getControllerId());
            if (watcher != null) {
                watcher.setReady();
            }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            return "This spell can't be countered (" + sourceObject.getName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            OvermasterWatcher watcher = (OvermasterWatcher) game.getState().getWatchers().get("overmasterWatcher", source.getControllerId());
            if (spell != null && watcher != null && watcher.isUncounterable(spell.getId())) {
                return true;
            }
        }
        return false;
    }
}

class OvermasterWatcher extends Watcher {

    protected boolean ready = false;
    protected UUID uncounterableSpell;

    OvermasterWatcher() {
        super("overmasterWatcher", WatcherScope.PLAYER);
    }

    OvermasterWatcher(final OvermasterWatcher watcher) {
        super(watcher);
        this.uncounterableSpell = watcher.uncounterableSpell;
    }

    @Override
    public OvermasterWatcher copy() {
        return new OvermasterWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && ready) {
            if (uncounterableSpell == null && event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && (spell.getCardType().contains(CardType.SORCERY) || spell.getCardType().contains(CardType.INSTANT))) {                    
                    uncounterableSpell = spell.getId();
                    ready = false;
                }
            }
        }
    }

    public boolean isUncounterable(UUID spellId) {
        return spellId.equals(uncounterableSpell);
    }
    
    public void setReady() {
        ready = true;
    }
}
