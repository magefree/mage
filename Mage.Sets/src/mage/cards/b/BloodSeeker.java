
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class BloodSeeker extends CardImpl {

    public BloodSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.
        this.addAbility(new BloodSeekerTriggeredAbility());
    }

    private BloodSeeker(final BloodSeeker card) {
        super(card);
    }

    @Override
    public BloodSeeker copy() {
        return new BloodSeeker(this);
    }
}

class BloodSeekerTriggeredAbility extends TriggeredAbilityImpl {

    BloodSeekerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), true);
    }

    private BloodSeekerTriggeredAbility(final BloodSeekerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodSeekerTriggeredAbility copy() {
        return new BloodSeekerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
            Card card = zEvent.getTarget();
            if (card != null && card.isCreature(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.";
    }
}
