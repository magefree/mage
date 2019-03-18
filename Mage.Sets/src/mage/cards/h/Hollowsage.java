
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class Hollowsage extends CardImpl {
    
    public Hollowsage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hollowsage becomes untapped, you may have target player discard a card.
        TriggeredAbility ability = new BecomesUntappedTriggeredAbility(new DiscardTargetEffect(1), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }
    
    public Hollowsage(final Hollowsage card) {
        super(card);
    }
    
    @Override
    public Hollowsage copy() {
        return new Hollowsage(this);
    }
}

class BecomesUntappedTriggeredAbility extends TriggeredAbilityImpl {
    
    public BecomesUntappedTriggeredAbility(Effect effect, boolean isOptional) {
        super(Zone.BATTLEFIELD, effect, isOptional);
    }

    public BecomesUntappedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public BecomesUntappedTriggeredAbility(final BecomesUntappedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesUntappedTriggeredAbility copy() {
        return new BecomesUntappedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(sourceId);
    }

    @Override
    public String getRule() {
        return "When {this} becomes untapped, " + super.getRule();
    }
}
