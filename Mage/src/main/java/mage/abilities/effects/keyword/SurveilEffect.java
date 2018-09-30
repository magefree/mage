package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public class SurveilEffect extends OneShotEffect {

    protected int surveilNumber;

    public SurveilEffect(int scryNumber) {
        super(Outcome.Benefit);
        this.surveilNumber = scryNumber;
        this.setText();
    }

    public SurveilEffect(final SurveilEffect effect) {
        super(effect);
        this.surveilNumber = effect.surveilNumber;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            return player.surveil(surveilNumber, source, game);
        }
        return false;
    }

    @Override
    public SurveilEffect copy() {
        return new SurveilEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("surveil ").append(surveilNumber);
        if (surveilNumber == 1) {
            sb.append(". <i>(Look at the top card of your library. You may put that card into your graveyard.)</i>");
        } else {
            sb.append(". <i>(Look at the top ");
            sb.append(CardUtil.numberToText(surveilNumber));
            sb.append(" cards of your library, then put any number of them into your graveyard and the rest on top in any order.)</i>");
        }
        staticText = sb.toString();
    }
}
