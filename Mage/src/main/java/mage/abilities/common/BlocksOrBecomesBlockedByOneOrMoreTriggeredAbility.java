
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author North, Loki
 */
public class BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected String rule;

    public BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional, null);
    }

    public BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(effect, filter, optional, null);
    }

    public BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, String rule) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.replaceRuleText = false;
        this.rule = rule;
        setTriggerPhrase("Whenever {this} blocks or becomes blocked by one or more " + (filter != null ? filter.getMessage() : "creatures") + ", ");
    }

    public BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(final BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (CombatGroup group : game.getCombat().getGroups()) {
            if (group.getAttackers().contains(sourceId)) {
                if (filter == null) {
                    return group.getBlocked();
                }
                for (UUID uuid : group.getBlockers()) {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(uuid);
                    if (filter.match(permanent, game)) {
                        return true;
                    }
                }
            } else if (group.getBlockers().contains(sourceId)) {
                if (filter == null) {
                    return true;
                }
                for (UUID uuid : group.getAttackers()) {
                    Permanent permanent = game.getPermanentOrLKIBattlefield(uuid);
                    if (filter.match(permanent, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return rule != null ? rule : super.getRule();
    }

    @Override
    public BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility copy() {
        return new BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(this);
    }
}
