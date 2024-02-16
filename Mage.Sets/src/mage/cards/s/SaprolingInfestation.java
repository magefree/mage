package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author noahg
 */
public final class SaprolingInfestation extends CardImpl {

    public SaprolingInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a player kicks a spell, you put a 1/1 green Saproling creature token onto the battlefield.
        this.addAbility(new SaprolingInfestationTriggeredAbility());
    }

    private SaprolingInfestation(final SaprolingInfestation card) {
        super(card);
    }

    @Override
    public SaprolingInfestation copy() {
        return new SaprolingInfestation(this);
    }

    private static final class SaprolingInfestationTriggeredAbility extends TriggeredAbilityImpl {

        private SaprolingInfestationTriggeredAbility() {
            super(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), false);
        }

        private SaprolingInfestationTriggeredAbility(final SaprolingInfestationTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public SaprolingInfestationTriggeredAbility copy() {
            return new SaprolingInfestationTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.KICKED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return true;
        }

        @Override
        public String getRule() {
            return "Whenever a player kicks a spell, you create a 1/1 green Saproling creature token.";
        }
    }
}
