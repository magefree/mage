package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * based heavily on AttacksWithCreaturesTriggeredAbility
 * @author notgreat
 */
public class AttacksPlayerWithCreaturesTriggeredAbility extends TriggeredAbilityImpl {
    private final FilterPermanent filter;
    private final int minAttackers;
    private final boolean onlyOpponents;
    private final SetTargetPointer setTargetPointer;

    public AttacksPlayerWithCreaturesTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, setTargetPointer);
    }

    public AttacksPlayerWithCreaturesTriggeredAbility(Effect effect, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        this(effect, 1, filter, setTargetPointer, false);
    }

    public AttacksPlayerWithCreaturesTriggeredAbility(Effect effect, int minAttackers, FilterPermanent filter, SetTargetPointer setTargetPointer, boolean onlyOpponents) {
        this(Zone.BATTLEFIELD, effect, minAttackers, filter, setTargetPointer, onlyOpponents, false);
    }

    public AttacksPlayerWithCreaturesTriggeredAbility(Zone zone, Effect effect, int minAttackers, FilterPermanent filter, SetTargetPointer setTargetPointer, boolean onlyOpponents, boolean optional) {
        super(zone, effect, optional);
        this.filter = filter;
        this.minAttackers = minAttackers;
        this.onlyOpponents = onlyOpponents;
        this.setTargetPointer = setTargetPointer;
        if (minAttackers == 1 && StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED.equals(filter) && !onlyOpponents) {
            setTriggerPhrase("Whenever you attack a player, ");
        } else {
            setTriggerPhrase("Whenever " + CardUtil.numberToText(minAttackers) + " or more " + filter.getMessage() +
                    " attack " + (onlyOpponents ? "an opponent" : "a player") + ", ");
        }
    }

    protected AttacksPlayerWithCreaturesTriggeredAbility(final AttacksPlayerWithCreaturesTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.minAttackers = ability.minAttackers;
        this.onlyOpponents = ability.onlyOpponents;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public AttacksPlayerWithCreaturesTriggeredAbility copy() {
        return new AttacksPlayerWithCreaturesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        UUID attackedId = event.getTargetId();
        if (player == null || game.getPlayer(attackedId) == null) {
            return false;
        }
        DefenderAttackedEvent attackedEvent = (DefenderAttackedEvent) event;
        List<Permanent> attackers = attackedEvent.getAttackers(game).stream()
                .filter(permanent -> filter.match(permanent, controllerId, this, game))
                .collect(Collectors.toList());
        if (attackers.size() < minAttackers || (onlyOpponents && !game.isOpponent(player, attackedId))) {
            return false;
        }
        switch (setTargetPointer){
            case NONE:
                break;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(attackedId));
                break;
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTargets(new ArrayList<>(attackers), game));
                break;
            default:
                throw new UnsupportedOperationException("Unexpected setTargetPointer in AttacksPlayerWithCreaturesTriggeredAbility: " + setTargetPointer);

        }
        this.getEffects().setValue("playerAttacked",attackedId);
        return true;
    }
}
