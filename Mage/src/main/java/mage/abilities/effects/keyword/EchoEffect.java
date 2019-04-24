package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AsThoughEffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Locale;

public class EchoEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue amount;

    public EchoEffect(Cost costs) {
        super(Outcome.Sacrifice);
        this.cost = costs;
        this.amount = null;
    }

    public EchoEffect(DynamicValue amount) {
        super(Outcome.Sacrifice);
        this.amount = amount;
        this.cost = null;
    }

    public EchoEffect(final EchoEffect effect) {
        super(effect);
        this.cost = effect.cost;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (cost == null) {
            cost = new ManaCostsImpl(Integer.toString(amount.calculate(game, source, this)));
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && source.getSourceObjectIfItStillExists(game) != null) {
            if (game.getContinuousEffects().asThough(source.getSourceId(), AsThoughEffectType.PAY_0_ECHO, source, source.getControllerId(), game) != null) {
                Cost altCost = new ManaCostsImpl("{0}");
                if (controller.chooseUse(Outcome.Benefit, "Pay {0} instead of the echo cost?", source, game)) {
                    altCost.clearPaid();
                    if (altCost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ECHO_PAID, source.getSourceId(), source.getSourceId(), source.getControllerId()));
                        return true;
                    }
                }
            }
            if (controller.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ECHO_PAID, source.getSourceId(), source.getSourceId(), source.getControllerId()));
                    return true;
                }
            }
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public EchoEffect copy() {
        return new EchoEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("sacrifice {this} unless you ");
        String costText = cost.getText();
        if (costText.toLowerCase(Locale.ENGLISH).startsWith("discard")) {
            sb.append(costText.substring(0, 1).toLowerCase(Locale.ENGLISH));
            sb.append(costText.substring(1));
        } else {
            sb.append("pay ").append(costText);
        }

        return sb.toString();

    }
}
