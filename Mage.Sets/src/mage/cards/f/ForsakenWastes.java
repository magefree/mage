
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class ForsakenWastes extends CardImpl {

    public ForsakenWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.supertype.add(SuperType.WORLD);

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantGainLifeAllEffect()));
        
        // At the beginning of each player's upkeep, that player loses 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), TargetController.ANY, false, true));
        
        // Whenever Forsaken Wastes becomes the target of a spell, that spell's controller loses 5 life.
        this.addAbility(new ForsakenWastesTriggeredAbility());
    }

    private ForsakenWastes(final ForsakenWastes card) {
        super(card);
    }

    @Override
    public ForsakenWastes copy() {
        return new ForsakenWastes(this);
    }
}

class ForsakenWastesTriggeredAbility extends TriggeredAbilityImpl {

    public ForsakenWastesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(5), false);
    }

    public ForsakenWastesTriggeredAbility(final ForsakenWastesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ForsakenWastesTriggeredAbility copy() {
        return new ForsakenWastesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (event.getTargetId().equals(this.getSourceId()) && eventSourceObject instanceof Spell) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell, that spell's controller loses 5 life.";
    }

}
