
package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public final class AmuletOfVigor extends CardImpl {

    public AmuletOfVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        this.addAbility(new AmuletOfVigorTriggeredAbility());
    }

    public AmuletOfVigor(final AmuletOfVigor card) {
        super(card);
    }

    @Override
    public AmuletOfVigor copy() {
        return new AmuletOfVigor(this);
    }
}

class AmuletOfVigorTriggeredAbility extends TriggeredAbilityImpl {
    AmuletOfVigorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapTargetEffect());
    }

    AmuletOfVigorTriggeredAbility(final AmuletOfVigorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AmuletOfVigorTriggeredAbility copy() {
        return new AmuletOfVigorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent p = game.getPermanent(event.getTargetId());
        if (p != null && p.isTapped() && p.isControlledBy(this.controllerId)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent enters the battlefield tapped and under your control, untap it.";
    }
}
