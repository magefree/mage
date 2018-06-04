
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Plopman
 */
public final class ArchangelOfThune extends CardImpl {

    public ArchangelOfThune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Whenever you gain life, put a +1/+1 counter on each creature you control.
        this.addAbility(new ArchangelOfThuneAbility());
        
    }

    public ArchangelOfThune(final ArchangelOfThune card) {
        super(card);
    }

    @Override
    public ArchangelOfThune copy() {
        return new ArchangelOfThune(this);
    }
}

class ArchangelOfThuneAbility extends TriggeredAbilityImpl {

    public ArchangelOfThuneAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), false);
    }

    public ArchangelOfThuneAbility(final ArchangelOfThuneAbility ability) {
        super(ability);
    }

    @Override
    public ArchangelOfThuneAbility copy() {
        return new ArchangelOfThuneAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, put a +1/+1 counter on each creature you control.";
    }

}
