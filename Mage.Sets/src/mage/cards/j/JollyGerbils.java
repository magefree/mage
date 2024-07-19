package mage.cards.j;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JollyGerbils extends CardImpl {

    public JollyGerbils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HAMSTER);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you give a gift, draw a card.
        this.addAbility(new JollyGerbilsTriggeredAbility());
    }

    private JollyGerbils(final JollyGerbils card) {
        super(card);
    }

    @Override
    public JollyGerbils copy() {
        return new JollyGerbils(this);
    }
}

class JollyGerbilsTriggeredAbility extends TriggeredAbilityImpl {

    JollyGerbilsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever you give a gift, ");
    }

    private JollyGerbilsTriggeredAbility(final JollyGerbilsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JollyGerbilsTriggeredAbility copy() {
        return new JollyGerbilsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAVE_GIFT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
