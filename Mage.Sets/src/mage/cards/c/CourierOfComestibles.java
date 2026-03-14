package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourierOfComestibles extends CardImpl {

    public CourierOfComestibles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When this creature enters, you may search your library for a Food card, reveal it, put it into your hand, then shuffle. If you don't put a card into your hand this way, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CourierOfComestiblesEffect()));
    }

    private CourierOfComestibles(final CourierOfComestibles card) {
        super(card);
    }

    @Override
    public CourierOfComestibles copy() {
        return new CourierOfComestibles(this);
    }
}

class CourierOfComestiblesEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard(SubType.FOOD);

    CourierOfComestiblesEffect() {
        super(Outcome.Benefit);
        staticText = "you may search your library for a Food card, reveal it, put it into your hand, " +
                "then shuffle. If you don't put a card into your hand this way, create a Food token";
    }

    private CourierOfComestiblesEffect(final CourierOfComestiblesEffect effect) {
        super(effect);
    }

    @Override
    public CourierOfComestiblesEffect copy() {
        return new CourierOfComestiblesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Search your library for a Food card?", source, game)) {
            return new FoodToken().putOntoBattlefield(1, game, source);
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        boolean flag;
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
            flag = Zone.HAND.match(game.getState().getZone(card.getId()));
        } else {
            flag = false;
        }
        player.shuffleLibrary(source, game);
        if (!flag) {
            new FoodToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
