
package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
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
 *
 * @author ayratn
 */
public final class DissipationField extends CardImpl {

    public DissipationField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Whenever a permanent deals damage to you, return it to its owner's hand.
        this.addAbility(new DissipationFieldAbility());
    }

    private DissipationField(final DissipationField card) {
        super(card);
    }

    @Override
    public DissipationField copy() {
        return new DissipationField(this);
    }
}

class DissipationFieldAbility extends TriggeredAbilityImpl {

    public DissipationFieldAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect());
    }

    private DissipationFieldAbility(final DissipationFieldAbility effect) {
        super(effect);
    }

    @Override
    public DissipationFieldAbility copy() {
        return new DissipationFieldAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.controllerId)) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent deals damage to you, return it to its owner's hand.";
    }
}
