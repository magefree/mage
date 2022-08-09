
package mage.cards.a;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
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
public final class AeonChronicler extends CardImpl {

    public AeonChronicler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Aeon Chronicler's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(CardsInControllerHandCount.instance, Duration.EndOfGame)));
        
        // Suspend X-{X}{3}{U}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{3}{U}"), this, true));
        
        // Whenever a time counter is removed from Aeon Chronicler while it's exiled, draw a card.
        this.addAbility(new AeonChroniclerTriggeredAbility());
    }

    private AeonChronicler(final AeonChronicler card) {
        super(card);
    }

    @Override
    public AeonChronicler copy() {
        return new AeonChronicler(this);
    }
}

class AeonChroniclerTriggeredAbility extends TriggeredAbilityImpl {

    public AeonChroniclerTriggeredAbility() {
        super(Zone.EXILED, new DrawCardSourceControllerEffect(1), false);
        setTriggerPhrase("Whenever a time counter is removed from {this} while it's exiled, " );
    }

    public AeonChroniclerTriggeredAbility(final AeonChroniclerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AeonChroniclerTriggeredAbility copy() {
        return new AeonChroniclerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return Objects.equals(event.getData(), CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }
}
