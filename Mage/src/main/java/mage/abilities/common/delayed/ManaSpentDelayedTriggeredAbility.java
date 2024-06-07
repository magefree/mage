package mage.abilities.common.delayed;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ManaSpentDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final FilterStackObject filter;
    private boolean setTarget;

    public ManaSpentDelayedTriggeredAbility(Effect effect, FilterStackObject filter) {
        super(effect, Duration.Custom, true, false);
        this.filter = filter;
        setTriggerPhrase("When you spend this mana to cast " + filter.getMessage() + ", ");
    }

    public ManaSpentDelayedTriggeredAbility(Effect effect, FilterSpell filter) {
        super(effect, Duration.Custom, true, false);
        this.filter = filter;
        setTriggerPhrase("When you spend this mana to cast " + filter.getMessage() + ", ");
    }

    public ManaSpentDelayedTriggeredAbility(Effect effect, FilterStackObject filter, boolean setTarget) {
        super(effect, Duration.Custom, true, false);
        this.filter = filter;
        this.setTarget = setTarget;
        setTriggerPhrase("When you spend this mana to cast " + filter.getMessage() + ", ");
    }

    private ManaSpentDelayedTriggeredAbility(final ManaSpentDelayedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTarget = ability.setTarget;
    }

    @Override
    public ManaSpentDelayedTriggeredAbility copy() {
        return new ManaSpentDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getSourceId())) {
            return false;
        }
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        if (sourcePermanent == null
                || sourcePermanent
                .getAbilities(game)
                .stream()
                .map(Ability::getOriginalId)
                .map(UUID::toString)
                .noneMatch(event.getData()::equals)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());

        if (this.setTarget) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
        }

        // Need to check both the stackObject and the spell because a spell isnt always
        // a ability
        return spell != null && filter.match(spell, spell.getControllerId(), this, game)
                || stackObject != null && filter.match(stackObject, stackObject.getControllerId(), this, game);
    }

    @Override
    public boolean isInactive(Game game) {
        if (super.isInactive(game)) {
            return true;
        }
        // must remove effect on empty mana pool to fix accumulate bug
        // if no mana in pool then it can be discarded
        Player player = game.getPlayer(this.getControllerId());
        return player == null
                || player
                .getManaPool()
                .getManaItems()
                .stream()
                .map(ManaPoolItem::getSourceId)
                .noneMatch(getSourceId()::equals);
    }
}
