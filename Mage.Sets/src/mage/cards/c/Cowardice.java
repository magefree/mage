
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author dustinconrad
 */
public final class Cowardice extends CardImpl {

    public Cowardice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // Whenever a creature becomes the target of a spell or ability, return that creature to its owner's hand.
        this.addAbility(new CowardiceTriggeredAbility());
    }

    private Cowardice(final Cowardice card) {
        super(card);
    }

    @Override
    public Cowardice copy() {
        return new Cowardice(this);
    }
}

class CowardiceTriggeredAbility extends TriggeredAbilityImpl {

    public CowardiceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), false);
    }

    public CowardiceTriggeredAbility(CowardiceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature becomes the target of a spell or ability, return that creature to its owner's hand";
    }

    @Override
    public CowardiceTriggeredAbility copy() {
        return new CowardiceTriggeredAbility(this);
    }
}