
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author emerald000
 */
public class CrewAbility extends SimpleActivatedAbility {

    private final int value;

    public CrewAbility(int value) {
        super(Zone.BATTLEFIELD, new AddCardTypeSourceEffect(Duration.EndOfTurn, CardType.ARTIFACT), new CrewCost(value));
        this.addEffect(new AddCardTypeSourceEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE));
        this.value = value;
    }

    public CrewAbility(final CrewAbility ability) {
        super(ability);
        this.value = ability.value;
    }

    @Override
    public CrewAbility copy() {
        return new CrewAbility(this);
    }

    @Override
    public String getRule() {
        return "Crew " + value + " <i>(Tap any number of creatures you control with total power " + value + " or more: This Vehicle becomes an artifact creature until end of turn.)</i>";
    }
}

class CrewCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    private final int value;

    CrewCost(int value) {
        this.value = value;
    }

    CrewCost(final CrewCost cost) {
        super(cost);
        this.value = cost.value;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        if (target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            int sumPower = 0;
            for (UUID targetId : target.getTargets()) {
                GameEvent event = new GameEvent(GameEvent.EventType.CREW_VEHICLE, targetId, sourceId, controllerId);
                if (!game.replaceEvent(event)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.tap(game)) {
                        sumPower += permanent.getPower().getValue();
                    }
                }
            }
            paid = sumPower >= value;
            if (paid) {
                for (UUID targetId : target.getTargets()) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREWED_VEHICLE, targetId, sourceId, controllerId));
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            int powerToAdd = permanent.getPower().getValue();
            if (powerToAdd > 0) {
                sumPower += powerToAdd;
            }
            if (sumPower >= value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CrewCost copy() {
        return new CrewCost(this);
    }
}
