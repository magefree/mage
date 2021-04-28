package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class AttacksWithCreaturesTriggeredAbility extends TriggeredAbilityImpl {

    private FilterCreaturePermanent filter;
    private int minAttackers;

    public AttacksWithCreaturesTriggeredAbility(Effect effect, int minAttackers) {
        this(effect, minAttackers, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public AttacksWithCreaturesTriggeredAbility(Effect effect, int minAttackers, FilterCreaturePermanent filter) {
        this(Zone.BATTLEFIELD, effect, minAttackers, filter);
    }

    public AttacksWithCreaturesTriggeredAbility(Zone zone, Effect effect, int minAttackers, FilterCreaturePermanent filter) {
        super(zone, effect);
        this.filter = filter;
        this.minAttackers = minAttackers;
    }

    public AttacksWithCreaturesTriggeredAbility(final AttacksWithCreaturesTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.minAttackers = ability.minAttackers;
    }

    @Override
    public AttacksWithCreaturesTriggeredAbility copy() {
        return new AttacksWithCreaturesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getCombat().getAttackingPlayerId().equals(getControllerId())) {
            int attackerCount = 0;
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (filter.match(attacker, game)) {
                    attackerCount++;
                }
            }
            return attackerCount >= minAttackers;
        }
        return false;
    }

    @Override
    public String getRule() {
        if (minAttackers == 0) {
            return "Whenever you attack, " + super.getRule();
        }
        StringBuilder sb = new StringBuilder("Whenever you attack with " + CardUtil.numberToText(minAttackers) + " or more ");
        sb.append(filter.getMessage());
        sb.append(", ");
        sb.append(super.getRule());
        return sb.toString();
    }

}
