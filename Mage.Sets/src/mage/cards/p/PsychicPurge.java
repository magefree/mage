
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class PsychicPurge extends CardImpl {

    public PsychicPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Psychic Purge deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // When a spell or ability an opponent controls causes you to discard Psychic Purge, that player loses 5 life.
        Ability ability = new PsychicPurgeTriggeredAbility();
        this.addAbility(ability);
    }

    private PsychicPurge(final PsychicPurge card) {
        super(card);
    }

    @Override
    public PsychicPurge copy() {
        return new PsychicPurge(this);
    }
}

class PsychicPurgeTriggeredAbility extends TriggeredAbilityImpl {

    public PsychicPurgeTriggeredAbility() {
        super(Zone.GRAVEYARD, new LoseLifeTargetEffect(5), false);
    }

    private PsychicPurgeTriggeredAbility(final PsychicPurgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychicPurgeTriggeredAbility copy() {
        return new PsychicPurgeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getSourceId().equals(event.getTargetId())) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null) {
                if (game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
                    Effect effect = this.getEffects().get(0);
                    effect.setTargetPointer(new FixedTarget(stackObject.getControllerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a spell or ability an opponent controls causes you to discard {this}, that player loses 5 life.";
    }
}
