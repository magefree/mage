package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class SpellControlledDealsDamageTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedEvent> {

    private final FilterSpell filter;

    public SpellControlledDealsDamageTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional) {
        super(zone, effect, optional);
        this.filter = filter;
        setTriggerPhrase(getWhen() + CardUtil.addArticle(filter.getMessage()) + " you control deals damage, ");
    }

    protected SpellControlledDealsDamageTriggeredAbility(final SpellControlledDealsDamageTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public SpellControlledDealsDamageTriggeredAbility copy() {
        return new SpellControlledDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant if triggers at all
        Spell spell = game.getSpellOrLKIStack(event.getSourceId());
        if (spell == null
                || !isControlledBy(spell.getControllerId())
                || !filter.match(spell, getControllerId(), this, game)) {
            return false;
        }
        getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
