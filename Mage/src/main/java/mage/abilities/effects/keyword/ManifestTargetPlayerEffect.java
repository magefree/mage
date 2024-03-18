package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class ManifestTargetPlayerEffect extends OneShotEffect {

    private final int amount;
    private final String prefix;

    public ManifestTargetPlayerEffect(int amount, String prefix) {
        super(Outcome.PutCreatureInPlay);
        this.amount = amount;
        this.prefix = prefix;
        this.staticText = setText();
    }

    protected ManifestTargetPlayerEffect(final ManifestTargetPlayerEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.prefix = effect.prefix;
    }

    @Override
    public ManifestTargetPlayerEffect copy() {
        return new ManifestTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }

        return ManifestEffect.doManifestCards(game, source, targetPlayer, targetPlayer.getLibrary().getTopCards(game, amount));
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        if (prefix != null && !prefix.isEmpty()) {
            sb.append(prefix).append(' ');
        }
        sb.append("manifest the top ");
        if (amount > 1) {
            sb.append(CardUtil.numberToText(amount)).append(" cards ");
        } else {
            sb.append("card ");
        }
        sb.append("of their library. ");
        if (amount > 1) {
            sb.append("<i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. The controller may turn it face up at any time for its mana cost if it's a creature card.)</i>");
        } else {
            sb.append("<i>(That player puts the top card of their library onto the battlefield face down as a 2/2 creature. If it's a creature card, it can be turned face up any time for its mana cost.)</i>");
        }
        return sb.toString();
    }
}
