
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 * @author JRHerlehy
 */
public final class HeartOfKiran extends CardImpl {

    public HeartOfKiran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // You may remove a loyalty counter from a planeswalker you control rather than pay Heart of Kiran's crew cost.
        Cost cost = new HeartOfKiranAlternateCrewCost(CounterType.LOYALTY, 1);
        Effect effect = new AddCardTypeSourceEffect(Duration.EndOfTurn, CardType.CREATURE);
        effect.setText("You may remove a loyalty counter from a planeswalker you control rather than pay {this}'s crew cost");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, cost));
    }

    private HeartOfKiran(final HeartOfKiran card) {
        super(card);
    }

    @Override
    public HeartOfKiran copy() {
        return new HeartOfKiran(this);
    }
}

class HeartOfKiranAlternateCrewCost extends CostImpl {

    private final CounterType counterTypeToRemove;
    private final int countersToRemove;

    private static final FilterControlledPlaneswalkerPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalker you control");

    public HeartOfKiranAlternateCrewCost(CounterType counterTypeToRemove, int countersToRemove) {
        this.counterTypeToRemove = counterTypeToRemove;
        this.countersToRemove = countersToRemove;
    }

    public HeartOfKiranAlternateCrewCost(final HeartOfKiranAlternateCrewCost cost) {
        super(cost);
        this.counterTypeToRemove = cost.counterTypeToRemove;
        this.countersToRemove = cost.countersToRemove;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;

        Target target = new TargetControlledPermanent(1, 1, filter, true);

        if (target.choose(Outcome.Benefit, controllerId, source.getSourceId(), game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            int originalLoyalty = permanent.getCounters(game).getCount(counterTypeToRemove);

            GameEvent event = new GameEvent(GameEvent.EventType.CREW_VEHICLE, target.getFirstTarget(), source, controllerId);
            if (!game.replaceEvent(event)) {
                permanent.removeCounters(counterTypeToRemove.createInstance(), source, game);
            }

            paid = permanent.getCounters(game).getCount(counterTypeToRemove) < originalLoyalty;

            if (paid) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREWED_VEHICLE, target.getFirstTarget(), source, controllerId));
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.VEHICLE_CREWED, source.getSourceId(), source, controllerId));
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return !game.getBattlefield().getAllActivePermanents(filter, game).isEmpty();
    }

    @Override
    public HeartOfKiranAlternateCrewCost copy() {
        return new HeartOfKiranAlternateCrewCost(this);
    }
}
