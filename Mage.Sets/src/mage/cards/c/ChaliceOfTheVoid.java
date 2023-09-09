
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public final class ChaliceOfTheVoid extends CardImpl {

    public ChaliceOfTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{X}");

        // Chalice of the Void enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // Whenever a player casts a spell with converted mana cost equal to the number of charge counters on Chalice of the Void, counter that spell.
        this.addAbility(new ChaliceOfTheVoidTriggeredAbility());
    }

    private ChaliceOfTheVoid(final ChaliceOfTheVoid card) {
        super(card);
    }

    @Override
    public ChaliceOfTheVoid copy() {
        return new ChaliceOfTheVoid(this);
    }
}

class ChaliceOfTheVoidTriggeredAbility extends TriggeredAbilityImpl {

    public ChaliceOfTheVoidTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CounterTargetEffect());
    }

    private ChaliceOfTheVoidTriggeredAbility(final ChaliceOfTheVoidTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public ChaliceOfTheVoidTriggeredAbility copy() {
        return new ChaliceOfTheVoidTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent chalice = game.getPermanent(getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && chalice != null && spell.getManaValue() == chalice.getCounters(game).getCount(CounterType.CHARGE)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell with mana value equal to the number of charge counters on {this}, counter that spell.";
    }
}
