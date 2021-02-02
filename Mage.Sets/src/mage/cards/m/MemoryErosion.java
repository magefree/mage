
package mage.cards.m;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
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
 * @author Plopman
 */
public final class MemoryErosion extends CardImpl {

    public MemoryErosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");


        // Whenever an opponent casts a spell, that player puts the top two cards of their library into their graveyard.
        this.addAbility(new SpellCastTriggeredAbility());
    }

    private MemoryErosion(final MemoryErosion card) {
        super(card);
    }

    @Override
    public MemoryErosion copy() {
        return new MemoryErosion(this);
    }
}


class SpellCastTriggeredAbility extends TriggeredAbilityImpl {

    public SpellCastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(2), false);
    }



    public SpellCastTriggeredAbility(final SpellCastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());   
        if (spell != null && game.getOpponents(this.getControllerId()).contains(spell.getControllerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, that player mills two cards";
    }

    @Override
    public SpellCastTriggeredAbility copy() {
        return new SpellCastTriggeredAbility(this);
    }
}