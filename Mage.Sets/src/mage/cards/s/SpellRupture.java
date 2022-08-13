package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
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
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SpellRupture extends CardImpl {

    public SpellRupture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new SpellRuptureCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(new ValueHint("Greatest power among your creatures", new GreatestPowerCountCreatureYouControl()));
    }

    private SpellRupture(final SpellRupture card) {
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
        this.staticText = "Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control";
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
            MageObject sourceObject = game.getObject(source);
            if (player != null && controller != null && sourceObject != null) {
                int maxPower = new GreatestPowerCountCreatureYouControl().calculate(game, source, this);
                Cost cost = ManaUtil.createManaCost(maxPower, true);
                if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "? (otherwise " + spell.getName() + " will be countered)", source, game)
                        && cost.pay(source, game, source, player.getId(), false)) {
                    return true;
                }
                game.informPlayers(sourceObject.getName() + ": cost wasn't payed - countering " + spell.getName());
                return game.getStack().counter(source.getFirstTarget(), source, game);
            }
        }
        return false;
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
    public GreatestPowerCountCreatureYouControl copy() {
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
