
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward
 */
public final class WithengarUnbound extends CardImpl {

    public WithengarUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.color.setBlack(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(13);
        this.toughness = new MageInt(13);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(IntimidateAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        // Whenever a player loses the game, put thirteen +1/+1 counters on Withengar Unbound.
        this.addAbility(new WithengarUnboundTriggeredAbility());

    }

    private WithengarUnbound(final WithengarUnbound card) {
        super(card);
    }

    @Override
    public WithengarUnbound copy() {
        return new WithengarUnbound(this);
    }
}

class WithengarUnboundTriggeredAbility extends TriggeredAbilityImpl {

    public WithengarUnboundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(13)), false);
    }

    public WithengarUnboundTriggeredAbility(final WithengarUnboundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WithengarUnboundTriggeredAbility copy() {
        return new WithengarUnboundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player loses the game, put thirteen +1/+1 counters on Withengar Unbound.";
    }
}