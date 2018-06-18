package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SaprolingToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class SaprolingInfestation extends CardImpl {

    public SaprolingInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        

        // Whenever a player kicks a spell, you put a 1/1 green Saproling creature token onto the battlefield.
        this.addAbility(new SaprolingInfestationTriggeredAbility());
    }

    public SaprolingInfestation(final SaprolingInfestation card) {
        super(card);
    }

    @Override
    public SaprolingInfestation copy() {
        return new SaprolingInfestation(this);
    }

    class SaprolingInfestationTriggeredAbility extends TriggeredAbilityImpl {

        SaprolingInfestationTriggeredAbility() {
            super(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken("INV")).setText("you create a 1/1 green Saproling creature token"), false);
        }

        SaprolingInfestationTriggeredAbility(final SaprolingInfestationTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public SaprolingInfestationTriggeredAbility copy() {
            return new SaprolingInfestationTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.SPELL_CAST;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Ability ability : spell.getAbilities()) {
                    if (ability instanceof KickerAbility && ((KickerAbility) ability).getKickedCounter(game, spell.getSpellAbility()) > 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a player kicks a spell, " + super.getRule();
        }
    }
}
