package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class RaidersSpoils extends CardImpl {

    public RaidersSpoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");

        // Creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)));
        
        // Whenever a Warrior you control deals combat damage to a player, you may pay 1 life.  If you do, draw a card.
        this.addAbility(new RaidersSpoilsTriggeredAbility());
    }

    private RaidersSpoils(final RaidersSpoils card) {
        super(card);
    }

    @Override
    public RaidersSpoils copy() {
        return new RaidersSpoils(this);
    }
}

class RaidersSpoilsTriggeredAbility extends TriggeredAbilityImpl {
    
    RaidersSpoilsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayLifeCost(1)), false);
        setTriggerPhrase("Whenever a Warrior you control deals combat damage to a player, ");
    }
    
    private RaidersSpoilsTriggeredAbility(final RaidersSpoilsTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public RaidersSpoilsTriggeredAbility copy() {
        return new RaidersSpoilsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && permanent != null && permanent.hasSubtype(SubType.WARRIOR, game) && permanent.isControlledBy(controllerId)) {
            return true;
        }
        return false;
    }

}
