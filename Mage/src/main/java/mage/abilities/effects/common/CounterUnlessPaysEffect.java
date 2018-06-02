
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CounterUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue genericMana;

    public CounterUnlessPaysEffect(Cost cost) {
        super(Outcome.Detriment);
        this.cost = cost;
    }

    public CounterUnlessPaysEffect(DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
    }

    public CounterUnlessPaysEffect(final CounterUnlessPaysEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
    }

    @Override
    public CounterUnlessPaysEffect copy() {
        return new CounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                Cost costToPay;
                if (cost != null) {
                    costToPay = cost.copy();
                } else {
                    costToPay = new GenericManaCost(genericMana.calculate(game, source, this));
                }
                String message;
                if (costToPay instanceof ManaCost) {
                    message = "Would you like to pay " + costToPay.getText() + " to prevent counter effect?";
                } else {
                    message = costToPay.getText() + " to prevent counter effect?";
                }
                costToPay.clearPaid();
                if (!(player.chooseUse(Outcome.Benefit, message, source, game) && costToPay.pay(source, game, spell.getSourceId(), spell.getControllerId(), false, null))) {
                    game.informPlayers(player.getLogName() + " chooses not to pay " + costToPay.getText() + " to prevent the counter effect");
                    return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                game.informPlayers(player.getLogName() + " chooses to pay " + costToPay.getText() + " to prevent the counter effect");
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            sb.append("counter it");
        } else {
            sb.append("counter target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" unless its controller pays ");
        if (cost != null) {
            sb.append(cost.getText());
        } else {
            sb.append("{X}");
        }
        if (genericMana != null && !genericMana.getMessage().isEmpty()) {
            sb.append(", where X is ");
            sb.append(genericMana.getMessage());
        }
        return sb.toString();
    }

}
