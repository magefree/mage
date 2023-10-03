package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class DimirSpybug extends CardImpl {

    public DimirSpybug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you surveil, put a +1/+1 counter on Dimir Spybug.
        this.addAbility(new DimirSpybugTriggeredAbility());
    }

    private DimirSpybug(final DimirSpybug card) {
        super(card);
    }

    @Override
    public DimirSpybug copy() {
        return new DimirSpybug(this);
    }
}

class DimirSpybugTriggeredAbility extends TriggeredAbilityImpl {

    public DimirSpybugTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()
        ), false);
    }

    private DimirSpybugTriggeredAbility(final DimirSpybugTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DimirSpybugTriggeredAbility copy() {
        return new DimirSpybugTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SURVEILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you surveil, put a +1/+1 counter on {this}.";
    }
}
