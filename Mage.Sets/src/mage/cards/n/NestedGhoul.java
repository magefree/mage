
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author Loki
 */
public final class NestedGhoul extends CardImpl {

    public NestedGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
        this.addAbility(new NestedGhoulTriggeredAbility());
    }

    public NestedGhoul(final NestedGhoul card) {
        super(card);
    }

    @Override
    public NestedGhoul copy() {
        return new NestedGhoul(this);
    }

}

class NestedGhoulTriggeredAbility extends TriggeredAbilityImpl {

    NestedGhoulTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()));
    }

    NestedGhoulTriggeredAbility(final NestedGhoulTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NestedGhoulTriggeredAbility copy() {
        return new NestedGhoulTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.sourceId);
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to {this}, create a 2/2 black Zombie creature token.";
    }
}
