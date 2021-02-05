package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.common.CommanderPlaysCountWatcher;

/**
 * @author Plopman
 */
public class CommanderStormAbility extends TriggeredAbilityImpl {

    public CommanderStormAbility() {
        super(Zone.STACK, new CommanderStormEffect());
        this.ruleAtTheTop = true;
    }

    private CommanderStormAbility(final CommanderStormAbility ability) {
        super(ability);
    }

    @Override
    public CommanderStormAbility copy() {
        return new CommanderStormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())) {
            return false;
        }
        StackObject spell = game.getStack().getStackObject(getSourceId());
        if (!(spell instanceof Spell)) {
            return false;
        }
        for (Effect effect : this.getEffects()) {
            effect.setValue("StormSpell", spell);
            effect.setValue("StormSpellRef", new MageObjectReference(spell.getId(), game));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast this spell, copy it for each time you've "
                + "cast your commander from the command zone this game. "
                + "You may choose new targets for the copies.";
    }
}

class CommanderStormEffect extends OneShotEffect {

    CommanderStormEffect() {
        super(Outcome.Copy);
    }

    private CommanderStormEffect(final CommanderStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference spellRef = (MageObjectReference) this.getValue("StormSpellRef");
        if (spellRef == null) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (watcher == null) {
            return false;
        }
        int stormCount = game
                .getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)
                .stream()
                .mapToInt(watcher::getPlaysCount)
                .sum();
        if (stormCount == 0) {
            return true;
        }
        Spell spell = (Spell) this.getValue("StormSpell");
        if (spell == null) {
            return false;
        }
        game.informPlayers(spell.getLogName() + " will be copied " + stormCount + " time" + (stormCount > 1 ? "s" : ""));
        spell.createCopyOnStack(game, source, source.getControllerId(), true, stormCount);
        return true;
    }

    @Override
    public CommanderStormEffect copy() {
        return new CommanderStormEffect(this);
    }
}
