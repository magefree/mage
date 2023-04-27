
package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class InvokePrejudice extends CardImpl {

    public InvokePrejudice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}{U}{U}");

        // Whenever an opponent casts a creature spell that doesn't share a color with a creature you control, counter that spell unless that player pays {X}, where X is its converted mana cost.
        this.addAbility(new InvokePrejudiceTriggeredAbility());
    }

    private InvokePrejudice(final InvokePrejudice card) {
        super(card);
    }

    @Override
    public InvokePrejudice copy() {
        return new InvokePrejudice(this);
    }
}

class InvokePrejudiceTriggeredAbility extends TriggeredAbilityImpl {

    public InvokePrejudiceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvokePrejudiceEffect(), false);
        setTriggerPhrase("Whenever an opponent casts a creature spell that doesn't share a color with a creature you control, ");
    }

    public InvokePrejudiceTriggeredAbility(final InvokePrejudiceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InvokePrejudiceTriggeredAbility copy() {
        return new InvokePrejudiceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature(game)) {
                boolean creatureSharesAColor = false;
                ObjectColor spellColor = spell.getColor(game);
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(), getControllerId(), game)) {
                    if (spellColor.shares(permanent.getColor(game))) {
                        creatureSharesAColor = true;
                        break;
                    }
                }
                if (!creatureSharesAColor) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

class InvokePrejudiceEffect extends CounterUnlessPaysEffect {

    public InvokePrejudiceEffect() {
        super(new GenericManaCost(1));
        this.staticText = "counter that spell unless that player pays {X}, where X is its mana value";
    }

    public InvokePrejudiceEffect(final InvokePrejudiceEffect effect) {
        super(effect);
    }

    @Override
    public InvokePrejudiceEffect copy() {
        return new InvokePrejudiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true;
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            CounterUnlessPaysEffect effect = new CounterUnlessPaysEffect(new GenericManaCost(spell.getManaValue()));
            effect.setTargetPointer(getTargetPointer());
            result = effect.apply(game, source);
        }
        return result;
    }
}
