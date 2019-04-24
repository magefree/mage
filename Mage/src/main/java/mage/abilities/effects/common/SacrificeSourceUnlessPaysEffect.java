package mage.abilities.effects.common;

import java.util.Locale;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * Created by IntelliJ IDEA. User: Loki Date: 21.12.10 Time: 9:21
 */
public class SacrificeSourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public SacrificeSourceUnlessPaysEffect(Cost cost) {
        super(Outcome.Sacrifice);
        this.cost = cost;
    }

    public SacrificeSourceUnlessPaysEffect(final SacrificeSourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            StringBuilder sb = new StringBuilder(cost.getText()).append('?');
            if (!sb.toString().toLowerCase(Locale.ENGLISH).startsWith("exile ") && !sb.toString().toLowerCase(Locale.ENGLISH).startsWith("return ")) {
                sb.insert(0, "Pay ");
            }
            String message = CardUtil.replaceSourceName(sb.toString(), sourcePermanent.getLogName());
            message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
            if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                    && controller.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    game.informPlayers(controller.getLogName() + " pays " + cost.getText());
                    return true;
                }
            }
            if (source.getSourceObjectZoneChangeCounter() == game.getState().getZoneChangeCounter(source.getSourceId())
                    && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                sourcePermanent.sacrifice(source.getSourceId(), game);
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

        StringBuilder sb = new StringBuilder("sacrifice {this} unless you ");
        String costText = cost.getText();
        if (costText.toLowerCase(Locale.ENGLISH).startsWith("discard")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("remove")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("return")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("put")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("exile")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("sacrifice")) {
            sb.append(costText.substring(0, 1).toLowerCase(Locale.ENGLISH));
            sb.append(costText.substring(1));
        } else {
            sb.append("pay ").append(costText);
        }

        return sb.toString();
    }
}
