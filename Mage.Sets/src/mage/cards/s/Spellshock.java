
package mage.cards.s;

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
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class Spellshock extends CardImpl {

    public Spellshock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Whenever a player casts a spell, Spellshock deals 2 damage to that player.
        this.addAbility(new SpellshockTriggeredAbility());
    }

    private Spellshock(final Spellshock card) {
        super(card);
    }

    @Override
    public Spellshock copy() {
        return new Spellshock(this);
    }
}

class SpellshockTriggeredAbility extends TriggeredAbilityImpl {


    public SpellshockTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"));
    }


    private SpellshockTriggeredAbility(final SpellshockTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public SpellshockTriggeredAbility copy() {
        return new SpellshockTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null){
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell, {this} deals 2 damage to that player.";
    }
}