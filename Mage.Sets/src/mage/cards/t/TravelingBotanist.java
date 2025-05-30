package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TravelingBotanist extends CardImpl {

    public TravelingBotanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever this creature becomes tapped, look at the top card of your library. If it's a land card, you may reveal it and put it into your hand. If you don't put the card into your hand, you may put it into your graveyard.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new TravelingBotanistEffect()));
    }

    private TravelingBotanist(final TravelingBotanist card) {
        super(card);
    }

    @Override
    public TravelingBotanist copy() {
        return new TravelingBotanist(this);
    }
}

class TravelingBotanistEffect extends OneShotEffect {

    TravelingBotanistEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's a land card, you may reveal it and " +
                "put it into your hand. If you don't put the card into your hand, you may put it into your graveyard";
    }

    private TravelingBotanistEffect(final TravelingBotanistEffect effect) {
        super(effect);
    }

    @Override
    public TravelingBotanistEffect copy() {
        return new TravelingBotanistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of library", card, game);
        if (card.isLand(game) && player.chooseUse(
                Outcome.DrawCard, "Reveal " + card.getName() +
                        " and put it into your hand?", source, game
        )) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
            return true;
        }
        if (player.chooseUse(Outcome.Neutral, "Put " + card.getName() + " into your graveyard?", source, game)) {
            return player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
