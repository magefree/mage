package mage.abilities.common;


import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ManaPaidObjectSourceWatcher;

/**
 * @author Susucr
 */
public class CastSpellPaidBySourceTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterSpell filter;
    private final boolean setTargetPointer;

    public CastSpellPaidBySourceTriggeredAbility(Effect effect, FilterSpell filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever you cast " + filter.getMessage() + " using mana produced by {this}, ");
        addWatcher(new ManaPaidObjectSourceWatcher());

        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    protected CastSpellPaidBySourceTriggeredAbility(final CastSpellPaidBySourceTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public CastSpellPaidBySourceTriggeredAbility copy() {
        return new CastSpellPaidBySourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!controllerId.equals(event.getPlayerId())) {
            return false;
        }

        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !this.filter.match(spell, controllerId, this, game)) {
            return false;
        }

        ManaPaidObjectSourceWatcher watcher = game.getState().getWatcher(ManaPaidObjectSourceWatcher.class);
        if (watcher == null) {
            return false;
        }

        if (!watcher.checkManaFromSourceWasUsedToPay(
                new MageObjectReference(sourceId, game),
                new MageObjectReference(spell.getSourceId(), game)
        )) {
            return false;
        }

        if (setTargetPointer) {
            this.getAllEffects().setTargetPointer(new FixedTarget(spell.getId(), game));
        }

        return true;
    }
}