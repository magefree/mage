package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 * @author Styxo
 */
public class AttacksWithCreaturesTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;
    private final int minAttackers;

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
        if (minAttackers == 1) {
            setTriggerPhrase("Whenever you attack, ");
        } else {
            StringBuilder sb = new StringBuilder("Whenever you attack with ");
            sb.append(CardUtil.numberToText(minAttackers));
            sb.append(" or more ");
            sb.append(filter.getMessage());
            sb.append(", ");
            setTriggerPhrase(sb.toString());
        }
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
        if (!isControlledBy(game.getCombat().getAttackingPlayerId())) {
            return false;
        }
        int attackers = game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(permanent -> filter.match(permanent, controllerId, this, game))
                .mapToInt(x -> 1)
                .sum();
        if (attackers < minAttackers) {
            return false;
        }
        getEffects().setValue("attackers", attackers);
        return true;
    }
}
