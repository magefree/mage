
package mage.cards.m;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
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
 * @author LevelX2
 */
public final class MesmericOrb extends CardImpl {

    public MesmericOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever a permanent becomes untapped, that permanent's controller puts the top card of their library into their graveyard.
        Effect effect = new PutTopCardOfLibraryIntoGraveTargetEffect(1);
        effect.setText("that permanent's controller puts the top card of their library into their graveyard");
        this.addAbility(new BecomesUntappedPermanentTriggeredAbility(effect, false));
    }

    public MesmericOrb(final MesmericOrb card) {
        super(card);
    }

    @Override
    public MesmericOrb copy() {
        return new MesmericOrb(this);
    }
}

class BecomesUntappedPermanentTriggeredAbility extends TriggeredAbilityImpl{

    public BecomesUntappedPermanentTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesUntappedPermanentTriggeredAbility(final BecomesUntappedPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesUntappedPermanentTriggeredAbility copy() {
        return new BecomesUntappedPermanentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNTAPPED;
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
        return "Whenever a permanent becomes untapped, " + super.getRule();
    }

}
