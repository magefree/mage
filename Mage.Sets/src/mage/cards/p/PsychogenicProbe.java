
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class PsychogenicProbe extends CardImpl {

    public PsychogenicProbe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever a spell or ability causes a player to shuffle their library, Psychogenic Probe deals 2 damage to that player.
        this.addAbility(new PsychogenicProbeTriggeredAbility());
    }

    private PsychogenicProbe(final PsychogenicProbe card) {
        super(card);
    }

    @Override
    public PsychogenicProbe copy() {
        return new PsychogenicProbe(this);
    }
}

class PsychogenicProbeTriggeredAbility extends TriggeredAbilityImpl {

    PsychogenicProbeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    private PsychogenicProbeTriggeredAbility(final PsychogenicProbeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychogenicProbeTriggeredAbility copy() {
        return new PsychogenicProbeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SHUFFLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability causes a player to shuffle their library, {this} deals 2 damage to that player.";
    }
}
