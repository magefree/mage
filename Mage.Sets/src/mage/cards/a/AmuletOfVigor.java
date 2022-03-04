package mage.cards.a;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.GameLog;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Loki
 */
public final class AmuletOfVigor extends CardImpl {

    public AmuletOfVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever a permanent enters the battlefield tapped and under your control, untap it.
        this.addAbility(new AmuletOfVigorTriggeredAbility());
    }

    private AmuletOfVigor(final AmuletOfVigor card) {
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
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isTapped() && permanent.isControlledBy(this.getControllerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(
                        new FixedTarget(event.getTargetId(), game)
                                .withData("triggeredName", GameLog.getColoredObjectIdNameForTooltip(permanent))
                );
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        // that triggers depends on stack order, so make each trigger unique with extra info
        return "Whenever a permanent enters the battlefield tapped and under your control, untap it."
                + Optional
                .of(this.getEffects().get(0).getTargetPointer())
                .map(targetPointer -> targetPointer.getData("triggeredName"))
                .filter(s -> s != null && !s.isEmpty())
                .map(s -> " Triggered permanent: " + s)
                .orElse("");
    }
}
