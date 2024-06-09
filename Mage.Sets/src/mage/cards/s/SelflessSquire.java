package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class SelflessSquire extends CardImpl {

    public SelflessSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Selfless Squire enters the battlefield, prevent all damage that would be dealt to you this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PreventDamageToControllerEffect(Duration.EndOfTurn), false));

        // Whenever damage that would be dealt to you is prevented, put that many +1/+1 counters on Selfless Squire.
        this.addAbility(new SelflessSquireTriggeredAbility());
    }

    private SelflessSquire(final SelflessSquire card) {
        super(card);
    }

    @Override
    public SelflessSquire copy() {
        return new SelflessSquire(this);
    }
}

class SelflessSquireTriggeredAbility extends TriggeredAbilityImpl {

    public SelflessSquireTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private SelflessSquireTriggeredAbility(final SelflessSquireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SelflessSquireTriggeredAbility copy() {
        return new SelflessSquireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return EventType.PREVENTED_DAMAGE == event.getType();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getTargetId())) {
            getEffects().clear();
            getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance(event.getAmount())));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever damage that would be dealt to you is prevented, put that many +1/+1 counters on {this}.";
    }
}
