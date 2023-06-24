
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX
 */
public final class ZoZuThePunisher extends CardImpl {

    public ZoZuThePunisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // Whenever a land enters the battlefield, Zo-Zu the Punisher deals 2 damage to that land's controller.
        this.addAbility(new ZoZuThePunisherAbility());
    }

    private ZoZuThePunisher(final ZoZuThePunisher card) {
            super(card);
    }

    @Override
    public ZoZuThePunisher copy() {
            return new ZoZuThePunisher(this);
    }
}

class ZoZuThePunisherAbility extends TriggeredAbilityImpl {

    public ZoZuThePunisherAbility() {
            super(Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    ZoZuThePunisherAbility(final ZoZuThePunisherAbility ability) {
            super(ability);
    }

    @Override
    public ZoZuThePunisherAbility copy() {
            return new ZoZuThePunisherAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isLand(game)) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(player.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield, Zo-Zu the Punisher deals 2 damage to that land's controller.";
    }
}