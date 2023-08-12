package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ThievesAuction extends CardImpl {

    public ThievesAuction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}{R}");

        // Exile all nontoken permanents.
        // Starting with you, each player chooses one of the exiled cards and puts it onto the battlefield tapped under their control.
        // Repeat this process until all cards exiled this way have been chosen.
        this.getSpellAbility().addEffect(new ThievesAuctionEffect());
    }

    private ThievesAuction(final ThievesAuction card) {
        super(card);
    }

    @Override
    public ThievesAuction copy() {
        return new ThievesAuction(this);
    }
}

class ThievesAuctionEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanents");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    ThievesAuctionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all nontoken permanents. Starting with you, each player chooses one of the exiled cards and puts it onto the battlefield tapped under their control. Repeat this process until all cards exiled this way have been chosen";
    }

    private ThievesAuctionEffect(final ThievesAuctionEffect effect) {
        super(effect);
    }

    @Override
    public ThievesAuctionEffect copy() {
        return new ThievesAuctionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        // Exile all nontoken permanents.
        Cards exiledCards = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            exiledCards.add(permanent);
            controller.moveCardsToExile(permanent, source, game, true, CardUtil.getCardExileZoneId(game, source.getSourceId()), "Thieves' Auction");
        }

        // Starting with you, each player
        PlayerList playerList = game.getState().getPlayersInRange(controller.getId(), game);
        Player player = playerList.getCurrent(game);
        while (player != null && !exiledCards.isEmpty() && !game.hasEnded()) {
            if (!player.canRespond()) { continue; }

            // chooses one of the exiled cards
            TargetCard target = new TargetCardInExile(new FilterCard());
            if (player.choose(Outcome.PutCardInPlay, exiledCards, target, source, game)) {
                // and puts it onto the battlefield tapped under their control.
                Card chosenCard = exiledCards.get(target.getFirstTarget(), game);
                if (chosenCard != null) {
                    player.moveCards(chosenCard, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
                exiledCards.remove(chosenCard);
            } else {
                // TODO Why does this break?
                break;
            }

            // Repeat this process until all cards exiled this way have been chosen.
            player = playerList.getNext(game, false);
        }

        return true;
    }
}
