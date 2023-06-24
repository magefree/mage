package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ScryEffect extends OneShotEffect {

    protected final int scryNumber;
    protected final boolean showEffectHint;

    public ScryEffect(int scryNumber) {
        this(scryNumber, true);
    }

    public ScryEffect(int scryNumber, boolean showEffectHint) {
        super(Outcome.Benefit);
        this.scryNumber = scryNumber;
        this.showEffectHint = showEffectHint;
        this.setText();
    }

    public ScryEffect(final ScryEffect effect) {
        super(effect);
        this.scryNumber = effect.scryNumber;
        this.showEffectHint = effect.showEffectHint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            return player.scry(scryNumber, source, game);
        }
        return false;
    }

    @Override
    public ScryEffect copy() {
        return new ScryEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("scry ").append(scryNumber);
        if (!showEffectHint) {
            staticText = sb.toString();
            return;
        }
        if (scryNumber == 1) {
            sb.append(". <i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>");
        } else {
            sb.append(". <i>(Look at the top ");
            sb.append(CardUtil.numberToText(scryNumber));
            sb.append(" cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)</i>");
        }
        staticText = sb.toString();
    }
}
