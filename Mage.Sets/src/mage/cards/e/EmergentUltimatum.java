package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithDifferentNameInLibrary;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergentUltimatum extends CardImpl {

    public EmergentUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{G}{G}{G}{U}{U}");

        // Search your library for up to three monocolored cards with different names and exile them. An opponent chooses one of those cards. Shuffle that card into your library. You may cast the other cards without paying their mana costs. Exile Emergent Ultimatum.
        this.getSpellAbility().addEffect(new EmergentUltimatumEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private EmergentUltimatum(final EmergentUltimatum card) {
        super(card);
    }

    @Override
    public EmergentUltimatum copy() {
        return new EmergentUltimatum(this);
    }
}

class EmergentUltimatumEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCard("monocolored cards with different names");

    static {
        filter.add(MonocoloredPredicate.instance);
    }

    EmergentUltimatumEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for up to three monocolored cards with different names and exile them. " +
                "An opponent chooses one of those cards. Shuffle that card into your library. " +
                "You may cast the other cards without paying their mana costs";
    }

    private EmergentUltimatumEffect(final EmergentUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public EmergentUltimatumEffect copy() {
        return new EmergentUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary targetCardInLibrary = new TargetCardWithDifferentNameInLibrary(0, 3, filter);
        targetCardInLibrary.setNotTarget(true);
        boolean searched = player.searchLibrary(targetCardInLibrary, source, game);
        Cards cards = new CardsImpl(targetCardInLibrary.getTargets());
        player.moveCards(cards, Zone.EXILED, source, game);
        if (cards.isEmpty()) {
            if (searched) {
                player.shuffleLibrary(source, game);
            }
            return false;
        }
        TargetOpponent targetOpponent = new TargetOpponent();
        targetOpponent.setNotTarget(true);
        player.choose(outcome, targetOpponent, source, game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            if (searched) {
                player.shuffleLibrary(source, game);
            }
            return false;
        }
        TargetCardInExile targetCardInExile = new TargetCardInExile(StaticFilters.FILTER_CARD);
        targetCardInExile.setNotTarget(true);
        opponent.choose(outcome, cards, targetCardInExile, game);
        Card toShuffle = game.getCard(targetCardInExile.getFirstTarget());
        if (toShuffle != null) {
            player.putCardsOnBottomOfLibrary(toShuffle, game, source, false);
            player.shuffleLibrary(source, game);
            cards.remove(toShuffle);
        }
        CardUtil.castMultipleWithAttributeForFree(player, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}
