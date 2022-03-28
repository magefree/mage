
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class WarReport extends CardImpl {

    public WarReport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        this.getSpellAbility().addEffect(new WarReportEffect());
    }

    private WarReport(final WarReport card) {
        super(card);
    }

    @Override
    public WarReport copy() {
        return new WarReport(this);
    }
}

class WarReportEffect extends OneShotEffect {

    public WarReportEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to the number of creatures on the battlefield plus the number of artifacts on the battlefield";
    }

    public WarReportEffect(final WarReportEffect effect) {
        super(effect);
    }

    @Override
    public WarReportEffect copy() {
        return new WarReportEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int lifeToGain = game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game);
            lifeToGain += game.getBattlefield().count(new FilterArtifactPermanent(), source.getControllerId(), source, game);
            player.gainLife(lifeToGain, game, source);
        }
        return true;
    }

}
