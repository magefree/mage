package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * based heavily on AttacksWithCreaturesTriggeredAbility
 * @author notgreat
 */
public class AttacksPlayerWithCreaturesTriggeredAbility extends TriggeredAbilityImpl {
    private final FilterPermanent filter;
    private final int minAttackers;
    private final boolean onlyOpponents;
    private final boolean setTargetPointer;

    public AttacksPlayerWithCreaturesTriggeredAbility(Effect effect, boolean setTargetPointer) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, setTargetPointer);
    }

    public AttacksPlayerWithCreaturesTriggeredAbility(Effect effect, FilterPermanent filter, boolean setTargetPointer) {
        this(effect, 1, filter, setTargetPointer, false);
    }

    public AttacksPlayerWithCreaturesTriggeredAbility(Effect effect, int minAttackers, FilterPermanent filter, boolean setTargetPointer, boolean onlyOpponents) {
        this(Zone.BATTLEFIELD, effect, minAttackers, filter, setTargetPointer, onlyOpponents, false);
    }

    public AttacksPlayerWithCreaturesTriggeredAbility(Zone zone, Effect effect, int minAttackers, FilterPermanent filter, boolean setTargetPointer, boolean onlyOpponents, boolean optional) {
        super(zone, effect, optional);
        this.filter = filter;
        this.minAttackers = minAttackers;
        this.onlyOpponents = onlyOpponents;
        this.setTargetPointer = setTargetPointer;
        if (minAttackers == 1 && StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED.equals(filter) && !onlyOpponents) {
            setTriggerPhrase("Whenever you attack a player, ");
        } else {
            setTriggerPhrase("Whenever " + CardUtil.numberToText(minAttackers) + " or more " + filter.getMessage() +
                    "attack" + (onlyOpponents ? "an opponent" : "a player") + ", ");
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
        if (player == null) {
            return false;
        }
        DefenderAttackedEvent attackedEvent = (DefenderAttackedEvent) event;
        List<Permanent> attackers = attackedEvent.getAttackers(game).stream()
                .filter(permanent -> filter.match(permanent, controllerId, this, game))
                .collect(Collectors.toList());
        if (attackers.size() < minAttackers || (onlyOpponents && !game.isOpponent(player, attackedEvent.getTargetId()))) {
            return false;
        }
        if (setTargetPointer) {
            getEffects().setTargetPointer(new FixedTargets(new ArrayList<>(attackers), game));
        }
        return true;
    }
}
