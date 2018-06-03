
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author cbt33
 */
public final class SavageFirecat extends CardImpl {

    public SavageFirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Savage Firecat enters the battlefield with seven +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(7))));
        // Whenever you tap a land for mana, remove a +1/+1 counter from Savage Firecat.
        this.addAbility(new SavageFirecatTriggeredAbility(new RemoveCounterSourceEffect(CounterType.P1P1.createInstance())));

    }

    public SavageFirecat(final SavageFirecat card) {
        super(card);
    }

    @Override
    public SavageFirecat copy() {
        return new SavageFirecat(this);
    }
}

class SavageFirecatTriggeredAbility extends TriggeredAbilityImpl {

    public SavageFirecatTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public SavageFirecatTriggeredAbility(final SavageFirecatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SavageFirecatTriggeredAbility copy() {
            return new SavageFirecatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCard(event.getSourceId()).isLand() &&
                event.getPlayerId().equals(this.controllerId);
    }
    
    @Override
    public String getRule() {
        return "Whenever you tap a land for mana, remove a +1/+1 counter from {this}";
    }

}