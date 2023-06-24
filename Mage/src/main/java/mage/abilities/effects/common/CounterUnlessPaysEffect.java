package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.ManaUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CounterUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue genericMana;
    private final boolean exile;

    public CounterUnlessPaysEffect(Cost cost) {
        this(cost, false);
    }

    public CounterUnlessPaysEffect(Cost cost, boolean exile) {
        super(Outcome.Detriment);
        this.cost = cost;
        this.exile = exile;
    }

    public CounterUnlessPaysEffect(DynamicValue genericMana) {
        this(genericMana, false);
    }

    public CounterUnlessPaysEffect(DynamicValue genericMana, boolean exile) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
        this.exile = exile;
    }

    public CounterUnlessPaysEffect(final CounterUnlessPaysEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
        this.exile = effect.exile;
    }

    @Override
    public CounterUnlessPaysEffect copy() {
        return new CounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell == null) {
            return false;
        }
        Player player = game.getPlayer(spell.getControllerId());
        if (player == null) {
            return false;
        }
        Cost costToPay;
        String costValueMessage;
        if (cost != null) {
            costToPay = cost.copy();
            costValueMessage = costToPay.getText();
        } else {
            costToPay = ManaUtil.createManaCost(genericMana, game, source, this);
            costValueMessage = "{" + genericMana.calculate(game, source, this) + "}";
        }
        String message = "";
        if (costToPay instanceof ManaCost) {
            message += "Pay ";
        }
        message += costValueMessage + '?';

        costToPay.clearPaid();
        if (!(player.chooseUse(Outcome.Benefit, message, source, game)
                && costToPay.pay(source, game, source, spell.getControllerId(), false, null))) {
            game.informPlayers(player.getLogName() + " chooses not to pay " + costValueMessage + " to prevent the counter effect");
            game.getStack().counter(spell.getId(), source, game, exile ? PutCards.EXILED : PutCards.GRAVEYARD);
        }
        game.informPlayers(player.getLogName() + " chooses to pay " + costValueMessage + " to prevent the counter effect");
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("counter ");
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "it"));
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
        if (exile) {
            sb.append(". If that spell is countered this way, exile it instead of putting it into its owner's graveyard");
        }
        return sb.toString();
    }
}
