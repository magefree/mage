
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class SpellRupture extends CardImpl {

    public SpellRupture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new SpellRuptureCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public SpellRupture(final SpellRupture card) {
        super(card);
    }

    @Override
    public SpellRupture copy() {
        return new SpellRupture(this);
    }
}

class SpellRuptureCounterUnlessPaysEffect extends OneShotEffect {

    public SpellRuptureCounterUnlessPaysEffect() {
        super(Outcome.Detriment);
    }

    public SpellRuptureCounterUnlessPaysEffect(final SpellRuptureCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public SpellRuptureCounterUnlessPaysEffect copy() {
        return new SpellRuptureCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (player != null && controller != null && sourceObject != null) {
                int amount = new GreatestPowerCountCreatureYouControl().calculate(game, source, this);
                GenericManaCost cost = new GenericManaCost(amount);
                StringBuilder sb = new StringBuilder("Pay {").append(amount).append("}? (otherwise ").append(spell.getName()).append(" will be countered)");
                if (player.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                    cost.pay(source, game, source.getSourceId(), player.getId(), false);
                }
                if (!cost.isPaid()) {
                    if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
                        game.informPlayers(sourceObject.getName() + ": cost wasn't payed - countering " + spell.getName());
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control";
    }
}

class GreatestPowerCountCreatureYouControl implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)) {
            if (creature != null && creature.getPower().getValue() > value) {
                value = creature.getPower().getValue();
            }
        }
        return value;
    }

    @Override
    public DynamicValue copy() {
        return new GreatestPowerCountCreatureYouControl();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest power among creatures you control";
    }
}
