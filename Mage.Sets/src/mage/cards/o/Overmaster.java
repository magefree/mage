
package mage.cards.o;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public final class Overmaster extends CardImpl {

    public Overmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");


        // The next instant or sorcery spell you cast this turn can't be countered.
        this.getSpellAbility().addEffect(new OvermasterEffect());
        this.getSpellAbility().addWatcher(new OvermasterWatcher());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Overmaster(final Overmaster card) {
        super(card);
    }

    @Override
    public Overmaster copy() {
        return new Overmaster(this);
    }
}

class OvermasterEffect extends ContinuousRuleModifyingEffectImpl {
    
    OvermasterEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next instant or sorcery spell you cast this turn can't be countered";
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
        OvermasterWatcher watcher = game.getState().getWatcher(OvermasterWatcher.class, source.getControllerId());
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
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            return "This spell can't be countered (" + sourceObject.getName() + ").";
        }
        return null;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        OvermasterWatcher watcher = game.getState().getWatcher(OvermasterWatcher.class, source.getControllerId());
        return spell != null && watcher != null && watcher.isUncounterable(spell.getId());
    }
}

class OvermasterWatcher extends Watcher {

    protected boolean ready = false;
    protected UUID uncounterableSpell;

    OvermasterWatcher() {
        super(WatcherScope.PLAYER);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && ready) {
            if (uncounterableSpell == null && event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && (spell.isSorcery(game) || spell.isInstant(game))) {
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
