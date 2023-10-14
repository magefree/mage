
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class TolarianEntrancer extends CardImpl {

    public TolarianEntrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Tolarian Entrancer becomes blocked by a creature, gain control of that creature at end of combat.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(new TolarianEntrancerDelayedTriggeredAbility()), false));
    }

    private TolarianEntrancer(final TolarianEntrancer card) {
        super(card);
    }

    @Override
    public TolarianEntrancer copy() {
        return new TolarianEntrancer(this);
    }

    class TolarianEntrancerDelayedTriggeredAbility extends DelayedTriggeredAbility {

        public TolarianEntrancerDelayedTriggeredAbility() {
            super(new GainControlTargetEffect(Duration.EndOfGame));
        }

        private TolarianEntrancerDelayedTriggeredAbility(final TolarianEntrancerDelayedTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public TolarianEntrancerDelayedTriggeredAbility copy() {
            return new TolarianEntrancerDelayedTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return true;
        }

        @Override
        public String getRule() {
            return "gain control of that creature at end of combat";
        }
    }
}
