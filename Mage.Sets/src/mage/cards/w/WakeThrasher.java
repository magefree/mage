
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class WakeThrasher extends CardImpl {

    public WakeThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a permanent you control becomes untapped, Wake Thrasher gets +1/+1 until end of turn.
        this.addAbility(new BecomesUntappedControlledPermanentTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));

    }

    private WakeThrasher(final WakeThrasher card) {
        super(card);
    }

    @Override
    public WakeThrasher copy() {
        return new WakeThrasher(this);
    }
}

class BecomesUntappedControlledPermanentTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesUntappedControlledPermanentTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a permanent you control becomes untapped, ");
    }

    public BecomesUntappedControlledPermanentTriggeredAbility(final BecomesUntappedControlledPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesUntappedControlledPermanentTriggeredAbility copy() {
        return new BecomesUntappedControlledPermanentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getPermanent(event.getTargetId()).isControlledBy(this.controllerId);
    }
}
