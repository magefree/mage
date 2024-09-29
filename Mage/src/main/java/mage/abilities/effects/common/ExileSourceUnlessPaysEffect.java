
package mage.abilities.effects.common;

import java.util.Locale;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author fireshoes
 */
public class ExileSourceUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public ExileSourceUnlessPaysEffect(Cost cost) {
        super(Outcome.Exile);
        this.cost = cost;
    }

    protected ExileSourceUnlessPaysEffect(final ExileSourceUnlessPaysEffect effect) {
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
            if (controller.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            controller.moveCardsToExile(sourcePermanent, source, game, true, null, "");
            return true;
        }
        return false;
    }

    @Override
    public ExileSourceUnlessPaysEffect copy() {
        return new ExileSourceUnlessPaysEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder("exile {this} unless you ");
        String costText = cost.getText();
        if (costText.toLowerCase(Locale.ENGLISH).startsWith("discard")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("remove")
                || costText.toLowerCase(Locale.ENGLISH).startsWith("return")
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
