package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.HumanSoldierToken;
import mage.players.Player;

/**
 * @author muz
 */
public class RecruitEffect extends OneShotEffect {

    private final boolean showRule;

    public RecruitEffect() {
        this(true);
    }

    public RecruitEffect(boolean showRule) {
        super(Outcome.Benefit);
        this.showRule = showRule;
    }

    private RecruitEffect(final RecruitEffect effect) {
        super(effect);
        this.showRule = effect.showRule;
    }

    @Override
    public RecruitEffect copy() {
        return new RecruitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        player.drawCards(1, source, game);
        if (player.discard(1, false, false, source, game).count(StaticFilters.FILTER_CARDS_NON_LAND, game) > 0) {
            new HumanSoldierToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return showRule
                ? "recruit. <i>(Draw a card, then discard a card. If you discarded a nonland card, create a 1/1 white Human Soldier creature token.)</i>"
                : "recruit";
    }
}
