package mage.abilities.common.delayed;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ManaSpentDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final FilterSpell filter;

    public ManaSpentDelayedTriggeredAbility(Effect effect, FilterSpell filter) {
        super(effect, Duration.Custom, true, false);
        this.filter = filter;
        setTriggerPhrase("When you spend this mana to cast " + filter.getMessage() + ", ");
    }

    private ManaSpentDelayedTriggeredAbility(final ManaSpentDelayedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
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
        return spell != null && filter.match(spell, spell.getControllerId(), this, game);
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
