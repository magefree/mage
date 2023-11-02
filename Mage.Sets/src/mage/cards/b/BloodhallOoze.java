

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author loki
 */
public final class BloodhallOoze extends CardImpl {

    public BloodhallOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BloodhallOozeTriggeredAbility1());
        this.addAbility(new BloodhallOozeTriggeredAbility2());
    }

    private BloodhallOoze(final BloodhallOoze card) {
        super(card);
    }

    @Override
    public BloodhallOoze copy() {
        return new BloodhallOoze(this);
    }

}

class BloodhallOozeTriggeredAbility1 extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public BloodhallOozeTriggeredAbility1() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    private BloodhallOozeTriggeredAbility1(final BloodhallOozeTriggeredAbility1 ability) {
        super(ability);
    }

    @Override
    public BloodhallOozeTriggeredAbility1 copy() {
        return new BloodhallOozeTriggeredAbility1(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) >= 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control a black permanent, you may put a +1/+1 counter on {this}.";
    }
}

class BloodhallOozeTriggeredAbility2 extends TriggeredAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BloodhallOozeTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    private BloodhallOozeTriggeredAbility2(final BloodhallOozeTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public BloodhallOozeTriggeredAbility2 copy() {
        return new BloodhallOozeTriggeredAbility2(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) >= 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control a green permanent, you may put a +1/+1 counter on {this}.";
    }
}