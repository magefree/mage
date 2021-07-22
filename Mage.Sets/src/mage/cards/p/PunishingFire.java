
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class PunishingFire extends CardImpl {

    public PunishingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Punishing Fire deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Whenever an opponent gains life, you may pay {R}. If you do, return Punishing Fire from your graveyard to your hand.
        this.addAbility(new PunishingFireTriggeredAbility());
    }

    private PunishingFire(final PunishingFire card) {
        super(card);
    }

    @Override
    public PunishingFire copy() {
        return new PunishingFire(this);
    }
}

class PunishingFireTriggeredAbility extends TriggeredAbilityImpl {

    public PunishingFireTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnToHandSourceEffect(), new ManaCostsImpl("{R}")));
    }

    public PunishingFireTriggeredAbility(final PunishingFireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PunishingFireTriggeredAbility copy() {
        return new PunishingFireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(this.controllerId).contains(event.getPlayerId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever an opponent gains life, " ;
    }
}
