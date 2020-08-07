package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RitesOfSpring extends CardImpl {

    public RitesOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Discard any number of cards. Search your library for up to that many basic land cards, reveal those cards, and put them into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new RitesOfSpringEffect());
    }

    private RitesOfSpring(final RitesOfSpring card) {
        super(card);
    }

    @Override
    public RitesOfSpring copy() {
        return new RitesOfSpring(this);
    }
}

class RitesOfSpringEffect extends OneShotEffect {

    RitesOfSpringEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard any number of cards. Search your library for up to that many basic land cards, " +
                "reveal those cards, and put them into your hand. Then shuffle your library.";
    }

    private RitesOfSpringEffect(final RitesOfSpringEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfSpringEffect copy() {
        return new RitesOfSpringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetDiscard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD, controller.getId());
        controller.choose(Outcome.AIDontUseIt, controller.getHand(), target, game);
        int numDiscarded = controller.discard(new CardsImpl(target.getTargets()), source, game).size();
        new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                0, numDiscarded, StaticFilters.FILTER_CARD_BASIC_LAND
        ), true, true).apply(game, source);
        return true;
    }
}
