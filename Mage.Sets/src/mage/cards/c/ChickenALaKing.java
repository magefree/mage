
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.RollDiceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class ChickenALaKing extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Chicken you control");

    static {
        filter.add(Predicates.not(TappedPredicate.instance));
        filter.add(new SubtypePredicate(SubType.CHICKEN));
    }

    public ChickenALaKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.CHICKEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a 6 is rolled on a six-sided die, put a +1/+1 counter on each Chicken.
        this.addAbility(new ChickenALaKingTriggeredAbility());

        // Tap an untapped Chicken you control: Roll a six-sided die.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RollDiceEffect(null, Outcome.Benefit, 6), new TapTargetCost(new TargetControlledPermanent(1, 1, filter, false))));
    }

    public ChickenALaKing(final ChickenALaKing card) {
        super(card);
    }

    @Override
    public ChickenALaKing copy() {
        return new ChickenALaKing(this);
    }
}

class ChickenALaKingTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each Chicken");

    static {
        filter.add(new SubtypePredicate(SubType.CHICKEN));
    }

    public ChickenALaKingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter));
    }

    public ChickenALaKingTriggeredAbility(final ChickenALaKingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChickenALaKingTriggeredAbility copy() {
        return new ChickenALaKingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId()) && event.getFlag()) {
            // event.getData holds the num of sides of the die to roll
            String data = event.getData();
            if (data != null) {
                int numSides = Integer.parseInt(data);
                if (event.getAmount() == 6 && numSides == 6) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a 6 is rolled on a six-sided die, put a +1/+1 counter on each Chicken";
    }
}
