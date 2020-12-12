
package mage.cards.m;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MesmericOrb extends CardImpl {

    public MesmericOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a permanent becomes untapped, that permanent's controller puts the top card of their library into their graveyard.
        this.addAbility(new MesmericOrbTriggeredAbility());
    }

    private MesmericOrb(final MesmericOrb card) {
        super(card);
    }

    @Override
    public MesmericOrb copy() {
        return new MesmericOrb(this);
    }
}

class MesmericOrbTriggeredAbility extends TriggeredAbilityImpl {

    MesmericOrbTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(1), false);
    }

    private MesmericOrbTriggeredAbility(final MesmericOrbTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MesmericOrbTriggeredAbility copy() {
        return new MesmericOrbTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent becomes untapped, that permanent's controller mills a card.";
    }

}
