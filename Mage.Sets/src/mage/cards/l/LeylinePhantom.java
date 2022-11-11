package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Plopman
 */
public final class LeylinePhantom extends CardImpl {

    public LeylinePhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //Whenever Leyline Phantom deals combat damage, return it to its owner's hand.
        this.addAbility(new LeylinePhantomTriggeredAbility());
    }

    private LeylinePhantom(final LeylinePhantom card) {
        super(card);
    }

    @Override
    public LeylinePhantom copy() {
        return new LeylinePhantom(this);
    }
}

class LeylinePhantomTriggeredAbility extends TriggeredAbilityImpl {

    public LeylinePhantomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), false);
        setTriggerPhrase("Whenever {this} deals combat damage, ");
    }

    public LeylinePhantomTriggeredAbility(final LeylinePhantomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LeylinePhantomTriggeredAbility copy() {
        return new LeylinePhantomTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((DamagedEvent) event).isCombatDamage() && event.getSourceId().equals(this.getSourceId());
    }
}
