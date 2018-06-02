
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PutLibraryIntoGraveTargetEffect extends OneShotEffect {

    private DynamicValue amount;

    public PutLibraryIntoGraveTargetEffect(int amount) {
        this(new StaticValue(amount));
    }

    public PutLibraryIntoGraveTargetEffect(DynamicValue amount) {
        super(Outcome.Detriment);
        this.amount = amount;
    }

    public PutLibraryIntoGraveTargetEffect(final PutLibraryIntoGraveTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    public void setAmount(DynamicValue value) {
        this.amount = value;
    }

    @Override
    public PutLibraryIntoGraveTargetEffect copy() {
        return new PutLibraryIntoGraveTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.moveCards(player.getLibrary().getTopCards(game, amount.calculate(game, source, this)), Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();

        sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        sb.append(" puts the top ");
        if (message.isEmpty()) {
            if (amount.toString().equals("1")) {
                sb.append("card ");
            } else {
                sb.append(CardUtil.numberToText(amount.toString())).append(" cards ");
            }
        } else {
            sb.append(" X cards ");
        }
        sb.append("of their library into their graveyard");

        if (!message.isEmpty()) {
            sb.append(", where X is the number of ");
            sb.append(message);
        }
        return sb.toString();
    }

}
