package mage.cards.l;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LilianaVess extends CardImpl {

    public LilianaVess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        this.setStartingLoyalty(5);
        // +1: Target player discards a card.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DiscardTargetEffect(1), 1);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // -2: Search your library for a card, then shuffle your library and put that card on top of it.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary()), -2));

        // -8: Put all creature cards from all graveyards onto the battlefield under your control.
        this.addAbility(new LoyaltyAbility(new LilianaVessEffect(), -8));

    }

    private LilianaVess(final LilianaVess card) {
        super(card);
    }

    @Override
    public LilianaVess copy() {
        return new LilianaVess(this);
    }

}

class LilianaVessEffect extends OneShotEffect {

    public LilianaVessEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put all creature cards from all graveyards onto the battlefield under your control";
    }

    public LilianaVessEffect(final LilianaVessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> creatureCards = new LinkedHashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    creatureCards.addAll(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
                }
            }
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }

    @Override
    public LilianaVessEffect copy() {
        return new LilianaVessEffect(this);
    }

}
