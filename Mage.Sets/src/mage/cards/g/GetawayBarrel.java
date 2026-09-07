package mage.cards.g;

import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class GetawayBarrel extends CardImpl {

    public GetawayBarrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        // When this artifact is put into a graveyard from the battlefield, reveal the top thirteen cards of your library.
        // Put a random creature card from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new GetawayBarrelEffect()));
    }

    private GetawayBarrel(final GetawayBarrel card) {
        super(card);
    }

    @Override
    public GetawayBarrel copy() {
        return new GetawayBarrel(this);
    }
}

class GetawayBarrelEffect extends OneShotEffect {

    public GetawayBarrelEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top thirteen cards of your library. "
            + "Put a random creature card from among them onto the battlefield. "
            + "Put the rest on the bottom of your library in a random order";
    }

    private GetawayBarrelEffect(final GetawayBarrelEffect effect) {
        super(effect);
    }

    @Override
    public GetawayBarrelEffect copy() {
        return new GetawayBarrelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }

        // Reveal the top thirteen cards of your library.
        CardsImpl cards = new CardsImpl();
        cards.addAllCards(player.getLibrary().getTopCards(game, 13));
        player.revealCards(source.getSourceObject(game).getIdName(), cards, game);

        // Put a random creature card from among them onto the battlefield.
        CardsImpl creatureCards = new CardsImpl(cards.getCards(game).stream().filter(card -> card.isCreature(game)).collect(Collectors.toSet()));
        if (!creatureCards.isEmpty()) {
            CardImpl creature = (CardImpl) creatureCards.getRandom(game);
            if (creature != null) {
                player.moveCards(creature, Zone.BATTLEFIELD, source, game);
                cards.remove(creature);
            }
        }
        // Put the rest on the bottom of your library in a random order.
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
