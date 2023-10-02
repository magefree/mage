
package mage.cards.p;

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
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class Pangosaur extends CardImpl {

    public Pangosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever a player plays a land, return Pangosaur to its owner's hand.
        this.addAbility(new PangosaurTriggeredAbility());
    }

    private Pangosaur(final Pangosaur card) {
        super(card);
    }

    @Override
    public Pangosaur copy() {
        return new Pangosaur(this);
    }
}

class PangosaurTriggeredAbility extends TriggeredAbilityImpl {

    PangosaurTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandSourceEffect());
    }

    private PangosaurTriggeredAbility(final PangosaurTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public PangosaurTriggeredAbility copy() {
        return new PangosaurTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a player plays a land, return {this} to its owner's hand.";
    }
}
