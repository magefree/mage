
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
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author dustinconrad
 */
public final class PyrostaticPillar extends CardImpl {

    public PyrostaticPillar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a player casts a spell with converted mana cost 3 or less, Pyrostatic Pillar deals 2 damage to that player.
        this.addAbility(new PyrostaticPillarTriggeredAbility());
    }

    private PyrostaticPillar(final PyrostaticPillar card) {
        super(card);
    }

    @Override
    public PyrostaticPillar copy() {
        return new PyrostaticPillar(this);
    }
}

class PyrostaticPillarTriggeredAbility extends TriggeredAbilityImpl {


    public PyrostaticPillarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"));
    }


    private PyrostaticPillarTriggeredAbility(final PyrostaticPillarTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public PyrostaticPillarTriggeredAbility copy() {
        return new PyrostaticPillarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getManaValue() <= 3){
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell with mana value 3 or less, {this} deals 2 damage to that player.";
    }
}