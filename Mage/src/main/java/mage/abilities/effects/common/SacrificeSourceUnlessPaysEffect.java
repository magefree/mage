package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.ManaUtil;

/**
 * Created by IntelliJ IDEA. User: Loki Date: 21.12.10 Time: 9:21
 */
public class SacrificeSourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue genericMana;

    public SacrificeSourceUnlessPaysEffect(Cost cost) {
        super(Outcome.Sacrifice);
        this.cost = cost;
    }

    public SacrificeSourceUnlessPaysEffect(DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
    }

    public SacrificeSourceUnlessPaysEffect(final SacrificeSourceUnlessPaysEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
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
            if (costToPay.canPay(source, source, source.getControllerId(), game)
                    && player.chooseUse(Outcome.Benefit, message, source, game)
                    && costToPay.pay(source, game, source, source.getControllerId(), false, null)) {
                game.informPlayers(player.getLogName() + " chooses to pay " + costValueMessage + " to prevent sacrifice effect");
                return true;
            }

            game.informPlayers(player.getLogName() + " chooses not to pay " + costValueMessage + " to prevent sacrifice effect");
            if (source.getSourceObjectZoneChangeCounter() == game.getState().getZoneChangeCounter(source.getSourceId())
                    && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                sourcePermanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public SacrificeSourceUnlessPaysEffect copy() {
        return new SacrificeSourceUnlessPaysEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "sacrifice {this} unless you " + CardUtil.addCostVerb(cost != null ? cost.getText() : "{X}");
    }
}
