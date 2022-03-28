
package mage.cards.i;

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
 * @author fireshoes
 */
public final class Insist extends CardImpl {

    public Insist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // The next creature spell you cast this turn can't be countered.
        this.getSpellAbility().addEffect(new InsistEffect());
        this.getSpellAbility().addWatcher(new InsistWatcher());

        // Draw a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("<br><br>Draw a card");
        this.getSpellAbility().addEffect(effect);
    }

    private Insist(final Insist card) {
        super(card);
    }

    @Override
    public Insist copy() {
        return new Insist(this);
    }
}

class InsistEffect extends ContinuousRuleModifyingEffectImpl {

    InsistEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next creature spell you cast this turn can't be countered";
    }

    InsistEffect(final InsistEffect effect) {
        super(effect);
    }

    @Override
    public InsistEffect copy() {
        return new InsistEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        InsistWatcher watcher = game.getState().getWatcher(InsistWatcher.class, source.getControllerId());
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
        InsistWatcher watcher = game.getState().getWatcher(InsistWatcher.class, source.getControllerId());
        return spell != null && watcher != null && watcher.isUncounterable(spell.getId());
    }
}

class InsistWatcher extends Watcher {

    protected boolean ready = false;
    protected UUID uncounterableSpell;

    InsistWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && ready) {
            if (uncounterableSpell == null && event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && (spell.isCreature(game))) {
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