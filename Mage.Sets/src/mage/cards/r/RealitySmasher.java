
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class RealitySmasher extends CardImpl {

    public RealitySmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{C}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Reality Smasher becomes the target of a spell an opponent controls, counter that spell unless its controller discards a card.
        this.addAbility(new RealitySmasherTriggeredAbility());
    }

    private RealitySmasher(final RealitySmasher card) {
        super(card);
    }

    @Override
    public RealitySmasher copy() {
        return new RealitySmasher(this);
    }
}

class RealitySmasherTriggeredAbility extends TriggeredAbilityImpl {

    public RealitySmasherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new DiscardCardCost()), false);
    }

    public RealitySmasherTriggeredAbility(final RealitySmasherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RealitySmasherTriggeredAbility copy() {
        return new RealitySmasherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject spell = game.getStack().getStackObject(event.getSourceId());
        if (!(spell instanceof Spell)) {
            return false;
        } else {
            if (event.getTargetId().equals(this.getSourceId())
                    && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                getEffects().setTargetPointer(new FixedTarget(spell.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell an opponent controls, counter that spell unless its controller discards a card.";
    }

}
