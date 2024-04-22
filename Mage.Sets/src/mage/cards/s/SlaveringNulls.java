
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SlaveringNulls extends CardImpl {

    public SlaveringNulls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Slavering Nulls deals combat damage to a player, if you control a Swamp, you may have that player discard a card.
        this.addAbility(new SlaveringNullsTriggeredAbility());
    }

    private SlaveringNulls(final SlaveringNulls card) {
        super(card);
    }

    @Override
    public SlaveringNulls copy() {
        return new SlaveringNulls(this);
    }
}

class SlaveringNullsTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public SlaveringNullsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1), true);
    }

    private SlaveringNullsTriggeredAbility(final SlaveringNullsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SlaveringNullsTriggeredAbility copy() {
        return new SlaveringNullsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            if (game.getBattlefield().countAll(filter, controllerId, game) > 0) {
                Permanent slaveringNulls = game.getPermanent(event.getSourceId());
                Player player = game.getPlayer(event.getTargetId());
                if (slaveringNulls != null && player != null) {
                    Effect effect = this.getEffects().get(0);
                    effect.setTargetPointer(new FixedTarget(player.getId()));
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, if you control a Swamp, you may have that player discard a card.";
    }

}
