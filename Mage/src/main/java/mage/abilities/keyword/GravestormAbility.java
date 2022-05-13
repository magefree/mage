package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.common.GravestormWatcher;

/**
 * @author emerald000
 */
public class GravestormAbility extends TriggeredAbilityImpl {

    public GravestormAbility() {
        super(Zone.STACK, new GravestormEffect());
        this.addWatcher(new GravestormWatcher());
    }

    private GravestormAbility(final GravestormAbility ability) {
        super(ability);
    }

    @Override
    public GravestormAbility copy() {
        return new GravestormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            StackObject spell = game.getStack().getStackObject(this.getSourceId());
            if (spell instanceof Spell) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("GravestormSpell", spell);
                    effect.setValue("GravestormSpellRef", new MageObjectReference(spell.getId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Gravestorm <i>(When you cast this spell, copy it for each permanent put into a graveyard this turn. You may choose new targets for the copies.)</i>";
    }
}

class GravestormEffect extends OneShotEffect {

    GravestormEffect() {
        super(Outcome.Copy);
    }

    GravestormEffect(final GravestormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference spellRef = (MageObjectReference) this.getValue("GravestormSpellRef");
        if (spellRef != null) {
            GravestormWatcher watcher = game.getState().getWatcher(GravestormWatcher.class);
            if (watcher != null) {
                int gravestormCount = watcher.getGravestormCount();
                if (gravestormCount > 0) {
                    Spell spell = (Spell) this.getValue("GravestormSpell");
                    if (spell != null) {
                        if (!game.isSimulation()) {
                            game.informPlayers("Gravestorm: " + spell.getName() + " will be copied " + gravestormCount + " time" + (gravestormCount > 1 ? "s" : ""));
                        }
                        spell.createCopyOnStack(game, source, source.getControllerId(), true, gravestormCount);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public GravestormEffect copy() {
        return new GravestormEffect(this);
    }
}
