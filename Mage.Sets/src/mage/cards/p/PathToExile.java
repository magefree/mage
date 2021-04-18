
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PathToExile extends CardImpl {

    public PathToExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Exile target creature. Its controller may search their library for a basic land card,
        // put that card onto the battlefield tapped, then shuffle their library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PathToExileEffect());
    }

    private PathToExile(final PathToExile card) {
        super(card);
    }

    @Override
    public PathToExile copy() {
        return new PathToExile(this);
    }
}

class PathToExileEffect extends OneShotEffect {

    public PathToExileEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature. Its controller may search their library for a basic land card, put that card onto the battlefield tapped, then shuffle";
    }

    public PathToExileEffect(final PathToExileEffect effect) {
        super(effect);
    }

    @Override
    public PathToExileEffect copy() {
        return new PathToExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            // if the zone change to exile gets replaced does not prevent the target controller to be able to search
            controller.moveCardToExileWithInfo(permanent, null, "", source, game, Zone.BATTLEFIELD, true);
            if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for a basic land card?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
                if (player.searchLibrary(target, source, game)) {
                    Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    }
                }
                player.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }

}
