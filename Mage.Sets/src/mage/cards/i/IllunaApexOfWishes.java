package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllunaApexOfWishes extends CardImpl {

    public IllunaApexOfWishes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {3}{R/G}{U}{U}
        this.addAbility(new MutateAbility(this, "{3}{R/G}{U}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this creature mutates, exile cards from the top of your library until you exile a nonland permanent card. Put that card onto the battlefield or into your hand.
        this.addAbility(new MutatesSourceTriggeredAbility(new IllunaApexOfWishesEffect()));
    }

    private IllunaApexOfWishes(final IllunaApexOfWishes card) {
        super(card);
    }

    @Override
    public IllunaApexOfWishes copy() {
        return new IllunaApexOfWishes(this);
    }
}

class IllunaApexOfWishesEffect extends OneShotEffect {

    IllunaApexOfWishesEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland permanent card. " +
                "Put that card onto the battlefield or into your hand";
    }

    private IllunaApexOfWishesEffect(final IllunaApexOfWishesEffect effect) {
        super(effect);
    }

    @Override
    public IllunaApexOfWishesEffect copy() {
        return new IllunaApexOfWishesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card permCard = null;
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card == null || card.isLand(game) || !card.isPermanent(game)) {
                continue;
            }
            permCard = card;
            break;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        if (permCard == null) {
            return true;
        }
        Zone zone = player.chooseUse(
                outcome, "Put " + permCard.getName() + " into your hand or onto the battlefield?",
                "", "Hand", "Battlefield", source, game
        ) ? Zone.HAND : Zone.BATTLEFIELD;
        return player.moveCards(permCard, zone, source, game);
    }
}
