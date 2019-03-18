
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author TheElk801
 */
public final class BloodstoneGoblin extends CardImpl {

    public BloodstoneGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a spell, if that spell was kicked, Bloodstone Goblin gets +1/+1 and gains menace until end of turn.
        this.addAbility(new BloodstoneGoblinTriggeredAbility());
    }

    public BloodstoneGoblin(final BloodstoneGoblin card) {
        super(card);
    }

    @Override
    public BloodstoneGoblin copy() {
        return new BloodstoneGoblin(this);
    }
}

class BloodstoneGoblinTriggeredAbility extends TriggeredAbilityImpl {

    BloodstoneGoblinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("{this} gets +1/+1"), false);
        this.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn).setText("and gains menace until end of turn"));
    }

    BloodstoneGoblinTriggeredAbility(final BloodstoneGoblinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodstoneGoblinTriggeredAbility copy() {
        return new BloodstoneGoblinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isControlledBy(controllerId)) {
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
        return "Whenever you cast a spell, if that spell was kicked, " + super.getRule();
    }
}
