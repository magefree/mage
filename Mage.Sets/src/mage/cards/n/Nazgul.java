package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Nazgul extends CardImpl {

    public Nazgul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.WRAITH);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Nazgul enters the battlefield, the Ring tempts you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheRingTemptsYouEffect()));

        // Whenever the Ring tempts you, put a +1/+1 counter on each Wraith you control.
        this.addAbility(new NazgulTriggeredAbility());

        // A deck can have up to nine cards named Nazgul.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("a deck can have up to nine cards named Nazgul")
        ));
    }

    private Nazgul(final Nazgul card) {
        super(card);
    }

    @Override
    public Nazgul copy() {
        return new Nazgul(this);
    }
}

class NazgulTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WRAITH);

    NazgulTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter));
        setTriggerPhrase("Whenever the Ring tempts you, ");
    }

    private NazgulTriggeredAbility(final NazgulTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NazgulTriggeredAbility copy() {
        return new NazgulTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TEMPTED_BY_RING;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
