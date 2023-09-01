
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class PresenceOfTheMaster extends CardImpl {

    public PresenceOfTheMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // Whenever a player casts an enchantment spell, counter it.
        this.addAbility(new PresenceOfTheMasterTriggeredAbility());
    }

    private PresenceOfTheMaster(final PresenceOfTheMaster card) {
        super(card);
    }

    @Override
    public PresenceOfTheMaster copy() {
        return new PresenceOfTheMaster(this);
    }
}

class PresenceOfTheMasterTriggeredAbility extends TriggeredAbilityImpl {
   

    public PresenceOfTheMasterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterEffect());
    }


    private PresenceOfTheMasterTriggeredAbility(final PresenceOfTheMasterTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public PresenceOfTheMasterTriggeredAbility copy() {
        return new PresenceOfTheMasterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && (spell.isEnchantment(game))){
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an enchantment spell, counter it.";
    }
}


class CounterEffect extends OneShotEffect {

    public CounterEffect() {
        super(Outcome.Detriment);
    }

    private CounterEffect(final CounterEffect effect) {
        super(effect);
    }

    @Override
    public CounterEffect copy() {
        return new CounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getStack().counter(this.getTargetPointer().getFirst(game, source), source, game);
    }

}
