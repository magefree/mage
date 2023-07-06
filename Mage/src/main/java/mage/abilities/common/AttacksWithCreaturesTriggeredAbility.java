package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Styxo
 */
public class AttacksWithCreaturesTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final int minAttackers;
    private final boolean setTargetPointer;

    public AttacksWithCreaturesTriggeredAbility(Effect effect, int minAttackers) {
        this(effect, minAttackers, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public AttacksWithCreaturesTriggeredAbility(Effect effect, int minAttackers, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, minAttackers, filter);
    }

    public AttacksWithCreaturesTriggeredAbility(Zone zone, Effect effect, int minAttackers, FilterPermanent filter) {
        this(zone, effect, minAttackers, filter, false);
    }

    public AttacksWithCreaturesTriggeredAbility(Zone zone, Effect effect, int minAttackers, FilterPermanent filter, boolean setTargetPointer) {
        super(zone, effect);
        this.filter = filter;
        this.minAttackers = minAttackers;
        this.setTargetPointer = setTargetPointer;
        if (minAttackers == 1 && StaticFilters.FILTER_PERMANENT_CREATURES.equals(filter)) {
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
        this.setTargetPointer = ability.setTargetPointer;
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
        List<Permanent> attackers = game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> filter.match(permanent, controllerId, this, game))
                .collect(Collectors.toList());
        if (attackers.size() < minAttackers) {
            return false;
        }
        getEffects().setValue("attackers", attackers.size());
        if (setTargetPointer) {
            getEffects().setTargetPointer(new FixedTargets(attackers, game));
        }
        return true;
    }
}
