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
import mage.watchers.common.CastSpellLastTurnWatcher;
import org.apache.log4j.Logger;

/**
 * @author Plopman
 */
public class StormAbility extends TriggeredAbilityImpl {

    public StormAbility() {
        super(Zone.STACK, new StormEffect());
    }

    private StormAbility(final StormAbility ability) {
        super(ability);
    }

    @Override
    public StormAbility copy() {
        return new StormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            StackObject spell = game.getStack().getStackObject(getSourceId());
            if (spell instanceof Spell) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("StormSpell", spell);
                    effect.setValue("StormSpellRef", new MageObjectReference(spell.getId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Storm <i>(When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)</i>";
    }
}

class StormEffect extends OneShotEffect {

    public StormEffect() {
        super(Outcome.Copy);
    }

    public StormEffect(final StormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference spellRef = (MageObjectReference) this.getValue("StormSpellRef");
        if (spellRef != null) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null) {
                int stormCount = watcher.getSpellOrder(spellRef, game) - 1;
                if (stormCount > 0) {
                    Spell spell = (Spell) this.getValue("StormSpell");
                    if (spell != null) {
                        if (!game.isSimulation()) {
                            game.informPlayers("Storm: " + spell.getLogName() + " will be copied " + stormCount + " time" + (stormCount > 1 ? "s" : ""));
                        }
                        spell.createCopyOnStack(game, source, source.getControllerId(), true, stormCount);
                    }
                }
            } else {
                Logger.getLogger(StormEffect.class).fatal("CastSpellLastTurnWatcher not found. game = " + game.getGameType().toString());
            }
            return true;
        }
        return false;
    }

    @Override
    public StormEffect copy() {
        return new StormEffect(this);
    }
}
