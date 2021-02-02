
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public final class FarsightMask extends CardImpl {

    public FarsightMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever a source an opponent controls deals damage to you, if Farsight Mask is untapped, you may draw a card.
        this.addAbility(new FarsightMaskTriggeredAbility());
    }

    private FarsightMask(final FarsightMask card) {
        super(card);
    }

    @Override
    public FarsightMask copy() {
        return new FarsightMask(this);
    }
}

class FarsightMaskTriggeredAbility extends TriggeredAbilityImpl {

    public FarsightMaskTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public FarsightMaskTriggeredAbility(final FarsightMaskTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FarsightMaskTriggeredAbility copy() {
        return new FarsightMaskTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && !permanent.isTapped();
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(controllerId)) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && game.getOpponents(getControllerId()).contains(sourceControllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you, if {this} is untapped, you may draw a card.";
    }
}
